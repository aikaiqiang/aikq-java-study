package com.space.aikq.utils.other;

import java.util.List;

/**
 * @description
 * @author aikq
 * @date 2018年10月26日 8:48
 */
public class ExcelRowReader implements IExcelRowReader{
	@Override
	public void getRows(int sheetIndex, int curRow, List<String> rowlist) {
		System.out.print(curRow+" ");
		for (int i = 0; i < rowlist.size(); i++) {
			System.out.print(rowlist.get(i)==""?"*":rowlist.get(i) + " ");
		}
		System.out.println();
	}
}
