package com.space.aikq.utils;

import cn.hutool.core.lang.Console;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.Excel03SaxReader;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;

import java.util.List;

/**
 * @description
 * @author aikq
 * @date 2018年10月24日 18:05
 */
public class SaxExcelDemo {

	public static void main(String[] args) {
		String path = "C:\\Users\\Administrator\\Desktop\\上网人员上网记录20181019101359.xls";
//		ExcelUtil.read07BySax("C:\\Users\\Administrator\\Desktop\\上网人员上网记录20181019101353.xlsx", 0, createRowHandler());
//		Excel07SaxReader reader = new Excel07SaxReader(new MyRowHandler(2));
//		reader.read(path, -1);

		ExcelUtil.read03BySax(path, 0, createRowHandler());
		Excel03SaxReader reader = new Excel03SaxReader(new MyRowHandler(3));
		reader.read(path, -1);
	}

	private static RowHandler createRowHandler() {
		return new RowHandler() {
			@Override
			public void handle(int sheetIndex, int rowIndex, List<Object> rowlist) {
				Console.log("[{}] [{}] {}", sheetIndex, rowIndex, rowlist);
			}
		};
	}
}

class MyRowHandler implements  RowHandler{

	private int titleRowIndex;

	public MyRowHandler(int titleRowIndex) {
		this.titleRowIndex = titleRowIndex - 1;
	}

	@Override
	public void handle(int i, int i1, List<Object> list) {

		// sheet表下标
		if(i1 >= titleRowIndex){
			if (i1 == titleRowIndex){
				// 标题
				System.out.println("表头");
				System.out.println(list.toString());
			}else {
				// 数据
				System.out.println("数据：" + list.toString());
			}
		}

	}
}
