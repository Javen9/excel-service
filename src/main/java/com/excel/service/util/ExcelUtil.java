package com.excel.service.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ExcelUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 读取Excel
     */
    public static LinkedHashMap<String, ArrayList<String>> readExcel(InputStream inputStream) throws Exception {
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            LinkedHashMap<String, ArrayList<String>> dataList = new LinkedHashMap<>();
            List<String> titleList = new ArrayList<>();
            List<Integer> cellIndexList = new ArrayList<>();
            boolean readKey = false;
            for (Row row : workbook.getSheetAt(0)) {
                if (!readKey) {
                    for (Cell cell : row) {
                        cell.setCellType(CellType.STRING);
                        String title = cell.getStringCellValue();
                        dataList.put(title, new ArrayList<>());
                        titleList.add(title);
                        cellIndexList.add(cell.getColumnIndex());
                    }
                    readKey = true;
                } else {
                    for (int titleIndex = 0; titleIndex < titleList.size(); titleIndex++) {
                        Cell cell = row.getCell(cellIndexList.get(titleIndex));
                        String value = "";
                        if (cell != null) {
                            if (CellType.NUMERIC.equals(cell.getCellTypeEnum())) {
                                value = Double.toString(cell.getNumericCellValue());
                                if (titleList.get(titleIndex).indexOf("日期") >= 0) {
                                    cell.setCellType(CellType.FORMULA);
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
                                    value = format.format(cell.getDateCellValue());
                                }
                            } else {
                                cell.setCellType(CellType.STRING);
                                value = cell.getStringCellValue();
                                if (titleList.get(titleIndex).indexOf("日期") >= 0) {
                                    if (value.indexOf("-") != -1) {
                                        value = value.replace("-", ".");
                                    }
                                }
                            }
                        }

                        ArrayList<String> list = dataList.get(titleList.get(titleIndex));
                        if (StringUtils.isEmpty(value)) {
                            if(!list.isEmpty()){
                                if (titleList.get(titleIndex).indexOf("日期") >= 0 || titleList.get(titleIndex).indexOf("凭证号") >= 0) {
                                    value = list.get(list.size() - 1);
                                }
                            }
                        }
                        list.add(value);
                    }
                }
            }
            return dataList;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 生成excel表格
     */
    public static XSSFWorkbook buildExcel(LinkedHashMap<String, ArrayList<String>> mapData) {
        if (mapData == null || mapData.isEmpty()) {
            return null;
        }
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");
        int cellIndex = 0;
        for (String title : mapData.keySet()) {
            XSSFRow row = sheet.getRow(0);
            if (row == null) {
                row = sheet.createRow(0);
            }
            XSSFCell cell = row.createCell(cellIndex);
            cell.setCellValue(title);
            List<String> list = mapData.get(title);
            for (int rowIndex = 0; rowIndex < list.size(); rowIndex++) {
                row = sheet.getRow(rowIndex + 1);
                if (row == null) {
                    row = sheet.createRow(rowIndex + 1);
                }
                cell = row.createCell(cellIndex);
                cell.setCellValue(list.get(rowIndex));
            }
            cellIndex++;
        }
        return workbook;
    }
}
