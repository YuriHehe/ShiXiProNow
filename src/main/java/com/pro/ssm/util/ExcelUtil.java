package com.pro.ssm.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {
    public static void writeXls(String filename, List<Map<String, Object>> lst, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            filename = filename + ".xls";
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
                    response.setHeader("content-disposition",
                            "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
                    OutputStream out = response.getOutputStream();
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
