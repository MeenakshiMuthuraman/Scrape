package com.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {

    public void writeCellValue(String excelpath, String sheetname, int rowNum, int colNum, String value)
            throws IOException {
        File excelFile = new File(excelpath);

        try (FileInputStream fileInputStream = new FileInputStream(excelFile);
             XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {

            XSSFSheet sheet = workbook.getSheet(sheetname);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetname);
            }

            Row row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }

            Cell cell = row.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellValue(value);

            try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
                workbook.write(fileOut);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeColumnValues(String excelpath, String sheetname, int colNum, Iterable<String> values)
            throws IOException {
        int rowNum = 0;

        for (String value : values) {
            writeCellValue(excelpath, sheetname, rowNum++, colNum, value);
        }
    }
    public int getCurrentRow(String excelpath, String sheetname) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(excelpath);
             XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {

            XSSFSheet sheet = workbook.getSheet(sheetname);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetname); // Create the sheet if it doesn't exist
            }

            Row row = sheet.getRow(0);
            if (row == null) {
                row = sheet.createRow(0); // Create the row if it doesn't exist
                Cell cell = row.createCell(0, CellType.NUMERIC); // Create a numeric cell
                cell.setCellValue(0); // Initialize the cell with a default value
            }

            Cell cell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            return (int) cell.getNumericCellValue();
        }
    }
    public void updateRowNumber(String excelpath, String sheetname, int newRow) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(excelpath);
             XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(excelpath)) {

            XSSFSheet sheet = workbook.getSheet(sheetname);

            // Update the first cell of the first row with the new row number
            Row row = sheet.getRow(0);
            if (row == null) {
                row = sheet.createRow(0);
            }
            Cell cell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellValue(newRow);

            workbook.write(fileOutputStream);
        }
    }
}