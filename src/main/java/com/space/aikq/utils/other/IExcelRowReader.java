package com.space.aikq.utils.other;

import java.util.List;

/**
 * @description
 * @author aikq
 * @date 2018年10月26日 8:47
 */
public interface IExcelRowReader {

	void getRows(int sheetIndex, int curRow, List<String> rowlist);
}
