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
    public static void writeXls(List<Map<String, Object>> lst, String title, Map<String, String> title_map, OutputStream out) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet(title);                     // 创建工作表

            List<String> rowName = new ArrayList<String>();
            Set<String> tset = lst.get(0).keySet();
            for (String s : title_map.keySet()) {
                rowName.add(s);
            }

            HSSFRow row1 = sheet.createRow(0);
            for (int i = 0; i < rowName.size(); ++i) {
                row1.createCell(i).setCellValue(title_map.get(rowName.get(i)));
            }

            for (int i = 0; i < lst.size(); ++i) {
                HSSFRow row = sheet.createRow(i + 1);
                for (int j = 0; j < rowName.size(); ++j) {
                    row.createCell(j).setCellValue(
                            lst.get(i).get(rowName.get(j)) != null ?
                                    lst.get(i).get(rowName.get(j)).toString() : ""
                    );
                }
            }

            if (workbook != null) {
                try {
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
