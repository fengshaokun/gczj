package com.lfy.gczj.utils;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoiUtils {

    public String getCellFromExcel(String path, String row, String col) throws Exception {
        return getCellFromExcel(path, 0, Integer.valueOf(row), Integer.valueOf(col));
    }

    public String getRowFromExcel(String path, String row) throws Exception {
        return getRowFromExcel(path, 0, Integer.valueOf(row));
    }

    public String getRowFromExcel(String path, int sheet, int row) throws Exception {
        File xlsx = new File(path);
        Workbook workbook = WorkbookFactory.create(xlsx);
        Sheet sheet1 = workbook.getSheetAt(sheet);
        Row row1 = sheet1.getRow(row);
        List<String> list = new ArrayList<>();
        int rowNum = row1.getLastCellNum();
        for (int i = 0; i < rowNum; i++) {
            list.add(getCellValueByCell(row1.getCell(i)));
        }
        return list.toString();
    }


    public String getCellFromExcel(String path, int sheet, int row, int col) throws Exception {
        File xlsx = new File(path);
        Workbook workbook = WorkbookFactory.create(xlsx);
        Sheet sheet1 = workbook.getSheetAt(sheet);
        Row row1 = sheet1.getRow(row);
        String cell = getCellValueByCell(row1.getCell(col));
        return cell;
    }

    //获取单元格各类型值，返回字符串类型
    private static String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell == null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        int cellType = cell.getCellType();

        // 以下是判断数据的类型
        switch (cellType) {
            case HSSFCell.CELL_TYPE_NUMERIC: // 数字

                if (0 == cell.getCellType()) {//判断单元格的类型是否则NUMERIC类型
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {// 判断是否为日期类型
                        Date date = cell.getDateCellValue();
                        DateFormat formater = new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm");
                        cellValue = formater.format(date);
                    } else {
                        cellValue = cell.getNumericCellValue() + "";
                    }
                }
                break;


            case HSSFCell.CELL_TYPE_STRING: // 字符串
                cellValue = cell.getStringCellValue();
                break;


            case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = cell.getBooleanCellValue() + "";
                break;


            case HSSFCell.CELL_TYPE_FORMULA: // 公式
                cellValue = cell.getCellFormula() + "";
                break;


            case HSSFCell.CELL_TYPE_BLANK: // 空值
                cellValue = "";
                break;


            case HSSFCell.CELL_TYPE_ERROR: // 故障
                cellValue = "非法字符";
                break;


            default:
                cellValue = "未知类型";
                break;

        }
        return cellValue;
    }
}
