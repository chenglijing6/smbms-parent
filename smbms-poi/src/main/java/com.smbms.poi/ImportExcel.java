package com.smbms.poi;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ImportExcel {
    public static void main(String[] args) {
        //读取
        try {
            //创建一个Workbook对象 《-》test.xlsx
            Workbook book = new XSSFWorkbook(new FileInputStream("d:\\j80.xlsx"));
            //读取工作页
            Sheet sheet1 = book.getSheet("Sheet1");
            //读取行
            Row row2 = sheet1.getRow(2);
            System.out.println(row2.getCell(2).getStringCellValue() + ":" + row2.getCell(3).getStringCellValue() + ":" + row2.getCell(4).getStringCellValue());
            Row row3 = sheet1.getRow(3);
            System.out.println(row3.getCell(2).getStringCellValue() + ":" + row3.getCell(3).getStringCellValue() + ":" + row3.getCell(4).getStringCellValue());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
