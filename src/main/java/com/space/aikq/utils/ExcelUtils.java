package com.space.aikq.utils;

import cn.hutool.core.date.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.cglib.proxy.Enhancer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description Excel工具类
 * @author aikq
 * @date 2018年10月22日 11:06
 */
public class ExcelUtils {

	private static Long EXCEL_FILE_LIMIT_SIZE = 100 * 1024 * 1024L;
	private static FormulaEvaluator evaluator;

	public static List<Map<String, Object>> importExcel2(String filePath, int tableTitleRowNum) throws IOException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		File file = new File(filePath);
		long fileSile = file.length();
		if(fileSile > EXCEL_FILE_LIMIT_SIZE){
			System.out.println("文件大小不能超过100M");
			return null;
		}
		String uploadFileName = file.getName();
		String FileName = uploadFileName.substring(0, uploadFileName.lastIndexOf("."));

		Workbook wookbook  = null;
		InputStream ins = new FileInputStream(filePath);
		if (!uploadFileName.endsWith(".xls") && !uploadFileName.endsWith(".xlsx")){
			System.out.println("文件类型必须为.xlx或.xlsx");
			return null;
		}

		try{
			//2003版本的excel，用.xls结尾
			wookbook = new HSSFWorkbook(ins);
		}catch (Exception ex){
			try{
				//2007版本的excel，用.xlsx结尾
				ins = new FileInputStream(filePath);
				wookbook = new XSSFWorkbook(ins);
			} catch (IOException e){
				e.printStackTrace();
			}
		}

		// excel表sheet数量
		int sheetNum = wookbook.getNumberOfSheets();
		if (sheetNum < 1){
			System.out.println("excel文件sheet表数量为0");
			return null;
		}

		for (int i = 0; i < sheetNum; i++) {
			Sheet sheet = wookbook.getSheetAt(0);

			// 获得表头
			Row rowHead = sheet.getRow(tableTitleRowNum);
			int cellNum = rowHead.getLastCellNum();

			// 判断表头


			// 获得数据的总行数
			int totalRowNum = sheet.getLastRowNum();

			for (int j = tableTitleRowNum + 1; j <= totalRowNum; j++) {
				HashMap<String, Object> tmp = new HashMap<String, Object>();
				//获得第i行对象
				Row row = sheet.getRow(j);
				for (int k = 0; k < cellNum; k++) {
					Cell cell = row.getCell(k);
					int cellType = cell.getCellType();
					if(cellType==Cell.CELL_TYPE_FORMULA){
						//表达式类型
						cellType = evaluator.evaluate(cell).getCellType();
					}
					switch (cellType){
						case Cell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								//判断日期类型
								String cellValue = DateUtil.format(cell.getDateCellValue(), "yyyy-MM-dd");
								tmp.put("cell-" + k, cellValue);
							} else {
								BigDecimal cellValue = new BigDecimal(cell.getNumericCellValue()).setScale(3, BigDecimal.ROUND_DOWN);
							}
							break;

						case Cell.CELL_TYPE_STRING:
							tmp.put("cell-" + k, cell.getStringCellValue().trim());
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							tmp.put("cell-" + k, cell.getBooleanCellValue());
							break;
						default:
							tmp.put("cell-" + k, cell.getStringCellValue());
							break;
					}
				}
				list.add(tmp);
			}

		}
		return list;
	}


	public static void main(String[] args) {
		try{
//			List<Map<String, Object>> list = importExcel(null, 2);
//			System.out.println(list.size());
//			for (Map<String,Object> map : list) {
//				System.out.println(map.toString());
//			}

			String filePath = "C:\\Users\\Administrator\\Desktop\\上网人员上网记录20181019101351.xlsx";
			List<Map<String, Object>> list = importExcel2(filePath, 2);
			System.out.println(list.size());
			for (Map<String,Object> map : list) {
				System.out.println(map.toString());
			}

			Enhancer enhancer = new Enhancer();

		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
