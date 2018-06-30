package com.pro.ssm.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {
    public static void writeXls(String path, List<Map<String, Object>> lst) throws Exception {
        try {
            String title = "worksheet";
            HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet(title);                     // 创建工作表

            //
            List<String> rowName = new ArrayList<String>();
            Set<String> tset = lst.get(0).keySet();
            for (String s : tset) {
                rowName.add(s);
            }

            HSSFRow row1 = sheet.createRow(0);
            for(int i = 0; i < rowName.size(); ++i){
                row1.createCell(i).setCellValue(rowName.get(i));
            }

            for(int i = 0; i < lst.size(); ++i){
                HSSFRow row = sheet.createRow(i+1);
                for(int j = 0; j < rowName.size(); ++j){
                    row.createCell(j).setCellValue(lst.get(i).get(rowName.get(j)).toString());
                }
            }

            if (workbook != null) {
                try {
                    FileOutputStream out = new FileOutputStream(path + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
                    workbook.write(out);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
