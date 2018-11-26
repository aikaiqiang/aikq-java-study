package com.space.aikq.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
public class ExcelXlsx2Csv {

	private static final Logger log = LoggerFactory.getLogger(ExcelXlsx2Csv.class);

	private static class SheetToCSV implements SheetContentsHandler {
		private boolean firstCellOfRow = false;
		private int currentRow = -1;
		private int currentCol = -1;

		private StringBuffer lineBuffer = new StringBuffer();

		/**
		 * Destination for data
		 */
		private FileOutputStream outputStream;

		public SheetToCSV(FileOutputStream outputStream) {
			this.outputStream = outputStream;
		}

		@Override
		public void startRow(int rowNum) {
			/**
			 * If there were gaps, output the missing rows:
			 *  outputMissingRows(rowNum - currentRow - 1);
			 */
			// Prepare for this row
			firstCellOfRow = true;
			currentRow = rowNum;
			currentCol = -1;

			// clear lineBuffer
			lineBuffer.delete(0, lineBuffer.length());
		}

		@Override
		public void endRow(int rowNum) {
			lineBuffer.append('\n');
			try {
				outputStream.write(lineBuffer.substring(0).getBytes());
			} catch (IOException e) {
				log.error("save date to file error at row number: {}", currentCol);
				throw new RuntimeException("save date to file error at row number: " + currentCol);
			}
		}

		@Override
		public void cell(String cellReference, String formattedValue, XSSFComment comment) {
			if (firstCellOfRow) {
				firstCellOfRow = false;
			} else {
				lineBuffer.append(',');
			}

			// gracefully handle missing CellRef here in a similar way as XSSFCell does
			if (cellReference == null) {
				cellReference = new CellAddress(currentRow, currentCol).formatAsString();
			}

			int thisCol = (new CellReference(cellReference)).getCol();
			//空缺单元格的个数，合并单元格和没有内容的单元格都算是丢失的col
			int missedCols = thisCol - currentCol - 1;
			if (missedCols > 1) {
				//合并单元格的地方，不打印逗号
				lineBuffer.append(',');
			}
			currentCol = thisCol;
			if (formattedValue.contains("\n")) {
				//去除换行符
				formattedValue = formattedValue.replace("\n", "");
			}
			formattedValue = "\"" + formattedValue + "\"";
			//有些excel文档 2300的数值为2,300
			lineBuffer.append(formattedValue);
		}

		@Override
		public void headerFooter(String text, boolean isHeader, String tagName) {
			// Skip, no headers or footers in CSV
		}
	}


	/**
	 * Parses and shows the content of one sheet
	 * using the specified styles and shared-strings tables.
	 *
	 * @param styles           The table of styles that may be referenced by cells in the sheet
	 * @param strings          The table of strings that may be referenced by cells in the sheet
	 * @param sheetInputStream The stream to read the sheet-data from.
	 * @throws java.io.IOException An IO exception from the parser,
	 *                             possibly from a byte stream or character stream
	 *                             supplied by the application.
	 * @throws SAXException        if parsing the XML data fails.
	 */
	private static void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings,
			SheetContentsHandler sheetHandler, InputStream sheetInputStream) throws Exception {
		DataFormatter formatter = new DataFormatter();
		InputSource sheetSource = new InputSource(sheetInputStream);
		try {
			XMLReader sheetParser = SAXHelper.newXMLReader();
			ContentHandler handler = new XSSFSheetXMLHandler(
					styles, null, strings, sheetHandler, formatter, false);
			sheetParser.setContentHandler(handler);
			sheetParser.parse(sheetSource);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
		}
	}

	/**
	 * Initiates the processing of the XLS workbook file to CSV.
	 *
	 * @throws IOException  If reading the data from the package fails.
	 * @throws SAXException if parsing the XML data fails.
	 */
	public static void process(String srcFile, String destFile) throws Exception {
		File xlsxFile = new File(srcFile);
		OPCPackage xlsxPackage = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ);
		ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(xlsxPackage);
		XSSFReader xssfReader = new XSSFReader(xlsxPackage);
		StylesTable styles = xssfReader.getStylesTable();
		XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
		int index = 0;
		while (iter.hasNext()) {
			InputStream stream = iter.next();
			String sheetName = iter.getSheetName();
			log.info(sheetName + " [index=" + index + "]");
			FileOutputStream fileOutputStream = new FileOutputStream(destFile);
			processSheet(styles, strings, new SheetToCSV(fileOutputStream), stream);
			stream.close();
			fileOutputStream.flush();
			fileOutputStream.close();
			++index;
		}
		xlsxPackage.close();
	}

	public static void main(String[] args) throws Exception {
		ExcelXlsx2Csv.process("C:\\Users\\Administrator\\Desktop\\上网人员上网记录20181019101351.xlsx", "C:\\Users\\Administrator\\Desktop\\test-1.csv");
	}
}