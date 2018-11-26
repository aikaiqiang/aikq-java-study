package com.space.aikq.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExcelUtil03 {
	private static Logger logger = LoggerFactory.getLogger(ExcelUtil03.class);
	// 错误信息
	private String errorInfo;
	// 错误信息
	private static int readSheet = 0;

	private static boolean readSheetNum = false;

	// 验证excel文件
	public boolean validateExcel(String filePath) {
		// 检查文件名是否为空或者是否是Excel格式的文件
		if (filePath == null || !(is2003Excel(filePath) || is2007Excel(filePath))) {
			errorInfo = "文件名不是excel格式";
			return false;
		}
		// 检查文件是否存在
		File file = new File(filePath);
		if (file == null || !file.exists()) {
			errorInfo = "excel文件不存在";
			return false;
		}
		return true;
	}

	/**
	 * 根据文件名读取excel文件
	 * @param filePath
	 * @return
	 */
	public List<List<String>> readExcel(String filePath, int rowStartIndex, boolean flag) {
		List<List<String>> dataList = new ArrayList<List<String>>();
		InputStream is = null;
		try {
			// 验证文件是否合法
			if (!validateExcel(filePath)) {
				logger.error(errorInfo);
				return null;
			}
			// 判断文件的类型，是2003还是2007
			boolean is2003Excel = true;
			if (is2007Excel(filePath)) {
				is2003Excel = false;
			}
			// 调用本类提供的根据流读取的方法
			File file = new File(filePath);
			is = new FileInputStream(file);
			dataList = readFile(is, is2003Excel, rowStartIndex, flag);
			is.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					e.printStackTrace();
				}
			}
		}
		// 返回最后读取的结果
		return dataList;
	}

	/**
	 * 根据流读取Excel文件
	 * @param inputStream
	 * @param is2003Excel
	 * @return
	 */
	public List<List<String>> readFile(InputStream inputStream, boolean is2003Excel, int rowStartIndex, boolean flag) {
		List<List<String>> dataLists = null;
		try {
			// 根据版本选择创建Workbook的方式
			Workbook wb = null;
			if (is2003Excel) {
				wb = new HSSFWorkbook(inputStream);
			} else {
				wb = new XSSFWorkbook(inputStream);
			}
			// sheet循环
			int sheetNum = sheetCirculation(wb);
			List<List<String>> dataList = new ArrayList<List<String>>();
			if (flag) {
				for (int i = 0; i < sheetNum; i++) {
					dataLists = read(dataList, wb, i, rowStartIndex);
				}
			} else {
				dataLists = read(dataList, wb, readSheet, rowStartIndex);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataLists;
	}

	/**
	 * 读取数据
	 * @param dataList
	 * @param wb
	 * @param sheets
	 * @return
	 */
	private List<List<String>> read(List<List<String>> dataList, Workbook wb, int sheets, int rowStartIndex) {
		// 总行数
		int totalRows = 0;
		// 总列数
		int totalCells = 0;
		// 第一个shell页
		Sheet sheet = wb.getSheetAt(sheets);
		// Excel的行数
		totalRows = sheet.getPhysicalNumberOfRows();
		// Excel的列数
		if (totalRows >= 1 && sheet.getRow(rowStartIndex) != null) {
			totalCells = sheet.getRow(rowStartIndex).getPhysicalNumberOfCells();
		}
		// 遍历Excel的行
		if (sheets == 0){
			rowStartIndex = rowStartIndex - 1;
		}
		for (int r = rowStartIndex; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			List<String> rowLst = new ArrayList<String>();
			// 遍历Excel的列
			for (int c = 0; c < totalCells; c++) {
				Cell cell = row.getCell(c);
				String cellValue = "";
				if (null != cell) {
					// 以下是判断数据的类型
					switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC: {
							// 数字
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								double d = cell.getNumericCellValue();
								Date date = HSSFDateUtil.getJavaDate(d);
								SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
								cellValue = dformat.format(date);
							} else {
								NumberFormat nf = NumberFormat.getInstance();
								// true时的格式：1,234,567,890
								nf.setGroupingUsed(false);
								// 数值类型的数据为double，所以需要转换一下
								cellValue = nf.format(cell.getNumericCellValue());
							}
							break;
						}
						case HSSFCell.CELL_TYPE_STRING: {
							// 字符串
							cellValue = cell.getStringCellValue();
							break;
						}
						case HSSFCell.CELL_TYPE_BOOLEAN:{
							// Boolean
							cellValue = cell.getBooleanCellValue() + "";
							break;
						}
						case HSSFCell.CELL_TYPE_FORMULA: {
							// 公式
							try {
								BigDecimal gongshi = new BigDecimal(cell.getNumericCellValue());
								double gongshi1 = gongshi.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
								cellValue = String.valueOf(gongshi1);
							} catch (IllegalStateException e) {
								cellValue = String.valueOf(cell.getRichStringCellValue())+"";
							}
							break;
						}
						case HSSFCell.CELL_TYPE_BLANK: {
							// 空值
							cellValue = "";
							break;
						}
						case HSSFCell.CELL_TYPE_ERROR: {
							// 故障
							cellValue = "非法字符";
							break;
						}
						default:  {
							cellValue = "未知类型";
							break;
						}
					}
				}
				rowLst.add(cellValue);
			}
			// 保存第r行记录
			dataList.add(rowLst);
		}
		return dataList;
	}

	private int sheetCirculation(Workbook wb) {
		int sheetCount = -1;
		sheetCount = wb.getNumberOfSheets();
		return sheetCount;
	}

	/**
	 * 是否是2003的excel，返回true是2003
	 * @param filePath
	 * @return
	 */
	public static boolean is2003Excel(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");

	}

	/**
	 * 是否是2007的excel，返回true是2007
	 * @param filePath
	 * @return
	 */
	public static boolean is2007Excel(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}

	public ExcelUtil03() {
	}

	/**
	 * 得到错误信息
	 * @return
	 */
	public String getErrorInfo() {
		return errorInfo;
	}

	public static void main(String[] args){
		ExcelUtil03 excelUtil03=new ExcelUtil03();
		String filename = "C:\\Users\\Administrator\\Desktop\\上网人员上网记录20181019101352.xls";
		List<List<String>> test=excelUtil03.readExcel(filename,1,true);
	}
}

