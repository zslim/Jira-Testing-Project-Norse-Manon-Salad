package com.codecool.testautomation.norsemanonsalad.testutils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {


    private static Sheet getSheet(String sheetName) throws IOException, InvalidFormatException {
        final String FILE_PATH = "testData/jira_test_data.xlsx";
        Workbook workbook = WorkbookFactory.create(new File(FILE_PATH));
        int numberOfSheets = workbook.getNumberOfSheets();

        Sheet sheetToRead = null;

        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getSheetName().equals(sheetName)) {
                sheetToRead = sheet;
                break;
            }
        }

        return sheetToRead;
    }

    public static List readSheet(String sheetName) {
        Sheet sheet = null;

        try {
            sheet = getSheet(sheetName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        Iterator<Row> rowIterator = sheet.rowIterator();
        Row header = rowIterator.next();

        Iterator<Cell> headerIterator = header.cellIterator();
        int numOfCols = 0;
        while (headerIterator.hasNext()) {
            headerIterator.next();
            numOfCols++;
        }

        List<String[]> data = new ArrayList<>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String[] rowValues = new String[numOfCols];
            Iterator<Cell> cellIterator = row.cellIterator();

            for (int i = 0; i < numOfCols; i++) {
                Cell cell = cellIterator.next();
                rowValues[i] = cell.getStringCellValue();
            }

            data.add(rowValues);
        }

        return data;
    }
}
