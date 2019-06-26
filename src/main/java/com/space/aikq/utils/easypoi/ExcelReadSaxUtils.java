package com.space.aikq.utils.easypoi;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.handler.inter.IReadHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

/**
 *  通过 easypoi 读取 excel 文件
 **/
@Slf4j
public class ExcelReadSaxUtils {


    public static void bigDataReadTest() throws IOException {

        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        long start = new Date().getTime();

        // 获取resource的文件流
        ClassPathResource resource = new ClassPathResource("excelfile/BigDataExport50000.xlsx");
        InputStream inputStream = resource.getInputStream();

        ExcelImportUtil.importExcelBySax(inputStream, Map.class, params, new MyReadHandler());

    }


    /**
     * 自定义handler
     */
    static class MyReadHandler implements IReadHandler{

        @Override
        public void handler(Object o) {
            Map<String, String> map = (Map<String, String>) o;
            for (String str  : map.values()) {
                System.out.print(str + " | ");
            }
            System.out.println();
        }

        @Override
        public void doAfterAll() {

        }
    }

    public static void main(String[] args) {
        try {
            bigDataReadTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
