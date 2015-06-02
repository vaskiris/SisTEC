/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.pkg;

/**
 *
 * @author Master
 */

import equitrack1.pkg.FXMLDocumentController;
import static equitrack1.pkg.FXMLDocumentController.dataOpen;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadWriteExcelFile {
    
        //public static Data dataO = new Data();
 
	public static void readXLSFile(String path) throws IOException
	{
                
                File excel =  new File (path);
                FileInputStream fis = new FileInputStream(excel);
                HSSFWorkbook wb = new HSSFWorkbook(fis);
                HSSFSheet ws = wb.getSheet("Sheet1");

                int rowNum = ws.getLastRowNum() + 1;
                int colNum = ws.getRow(0).getLastCellNum();
                

                for(int i = 0; i <rowNum; i++){
                    HSSFRow row = ws.getRow(i);
                        for (int j = 0; j < colNum; j++){
                            HSSFCell cell = row.getCell(j);
                            if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                                
                                long value = Long.parseLong(cell.toString());
                           
                            
                            if(j == 0){
                                dataOpen.addDeslocamento(value);
                                System.out.println(value);
                            }
                            if(j == 1){
                                dataOpen.addForca(value);
                                System.out.println(value);
                            }
                             }
                        }
                }
                
	
	}
	
	public static void writeXLSFile(String fileName, Data data) throws IOException {
		
		String excelFileName = fileName;//name of excel file
 
		String sheetName = "Sheet1";//name of sheet
 
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName) ;
 
		
                //iterating r number of rows
		for (int r=0;r < data.getLength(); r++ )
		{
			HSSFRow row = sheet.createRow(r);
 
			//iterating c number of columns
			for (int c=0;c < 2; c++ )
			{
                            if(r == 0 && c == 0){
                                HSSFCell cell = row.createCell(c);
                                cell.setCellValue("Deslocamento");
                            }
                            if(r == 0 && c == 1){
                                HSSFCell cell = row.createCell(c);
                                cell.setCellValue("Força");
                            }
                            if(r >0 && c == 0){
				HSSFCell cell = row.createCell(c);
	
				cell.setCellValue(data.getDeslocamento(r-1));
                            }
                            if(r > 0 && c == 1){
                                HSSFCell cell = row.createCell(c);
	
				cell.setCellValue(data.getForca(r-1));
                            }
			}
		}
		
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
	
	public static void readXLSXFile(String path) throws IOException
	{
		File excel =  new File (path);
                FileInputStream fis = new FileInputStream(excel);
                XSSFWorkbook wb = new XSSFWorkbook(fis);
                XSSFSheet ws = wb.getSheet("Sheet1");

                int rowNum = ws.getLastRowNum() + 1;
                int colNum = ws.getRow(0).getLastCellNum();
                

                for(int i = 0; i <rowNum; i++){
                    XSSFRow row = ws.getRow(i);
                        for (int j = 0; j < colNum; j++){
                            XSSFCell cell = row.getCell(j);
                            if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                                long l = (new Double(cell.getNumericCellValue())).longValue();

                               //long value = Long.parseLong(cell.toString());
                           
                            
                            if(j == 0){
                                dataOpen.addDeslocamento(l);
                                System.out.println(dataOpen.getDeslocamento(i-1));
                            }
                            if(j == 1){
                                dataOpen.addForca(l);
                                System.out.println(dataOpen.getForca(i-1));
                            }
                             }
                        }
                }
	
	}
	
	public static void writeXLSXFile(String fileName, Data data) throws IOException {
		
		String excelFileName = fileName;//name of excel file
 
		String sheetName = "Sheet1";//name of sheet
 
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;
 
		//iterating r number of rows
		for (int r=0;r < data.getLength(); r++ )
		{
			XSSFRow row = sheet.createRow(r);
 
			//iterating c number of columns
			for (int c=0;c < 2; c++ )
			{
                            if(r == 0 && c == 0){
                                XSSFCell cell = row.createCell(c);
                                cell.setCellValue("Deslocamento");
                            }
                            if(r == 0 && c == 1){
                                XSSFCell cell = row.createCell(c);
                                cell.setCellValue("Força");
                            }
                            if(r >0 && c == 0){
				XSSFCell cell = row.createCell(c);
	
				cell.setCellValue(data.getDeslocamento(r-1));
                            }
                            if(r > 0 && c == 1){
                                XSSFCell cell = row.createCell(c);
	
				cell.setCellValue(data.getForca(r-1));
                            }
			}
		}
 
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
 
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
}