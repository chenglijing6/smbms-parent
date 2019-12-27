package com.smbms.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExportExcel {
    public static void main(String[] args) {
        //excel文档看成一个Workbook对象
        //HSSFWorkbook-》2003excel或以下版本； .xls
        //XSSFWorkbook-》2007excel或以上版本； .xlsx

        //Workbook book = new HSSFWorkbook();
        //创建了一个对象      工作薄
        Workbook book = new XSSFWorkbook();
        //sheet   工作表
        Sheet sheet = book.createSheet("Sheet1");
        //row
        Row row = sheet.createRow(2);
        Cell cell2 = row.createCell(2);
        Cell cell3 = row.createCell(3);
        Cell cell4 = row.createCell(4);
        cell2.setCellValue("姓名");
        cell3.setCellValue("班级");
        cell4.setCellValue("学号");

        Row row1 = sheet.createRow(3);
        Cell cell12 = row1.createCell(2);
        Cell cell13 = row1.createCell(3);
        Cell cell14 = row1.createCell(4);
        cell12.setCellValue("张三");
        cell13.setCellValue("j80");
        cell14.setCellValue("20080506");

        File file = new File("d:\\j80.xlsx");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            book.write(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
