package com.lfy.gczj.controller;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;

public class ReadExcel {
    public static void main(String[] args) throws Exception {
        File xlsFile = new File("E:\\upload\\01安装材料价格-很全.xlsx");

        // 工作表
        Workbook workbook = WorkbookFactory.create(xlsFile);

        // 表个数。
        int numberOfSheets = workbook.getNumberOfSheets();

        // 遍历表。
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);

            // 行数。
            int rowNumbers = sheet.getLastRowNum() + 1;

            // Excel第一行。
            Row temp = sheet.getRow(0);
            if (temp == null) {
                continue;
            }

            int cells = temp.getPhysicalNumberOfCells();

            // 读数据。
            for (int row = 0; row < rowNumbers; row++) {
                Row r = sheet.getRow(row);
                for (int col = 0; col < cells; col++) {
                    System.out.print(r.getCell(col).toString()+" ");
                }
                // 换行。
                System.out.println();
            }
        }
    }

}
