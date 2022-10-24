package UtilityClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	public static String path = System.getProperty("user.dir");
	
	
	
	public double getResultsTwoWay(String value1, String value2, String fileName) throws Exception {
		
		double result = -1; 
		
		try {

			FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "\\"+fileName));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);
			int rows = countRows(worksheet);
			int columns = countColumns(worksheet);
			
			int targetColumn = -1; 
			for(int i = 0; i < columns; i++) {
				
				if(value1.equals(worksheet.getRow(0).getCell(i).getStringCellValue())) {
					targetColumn = i;
					break; 
				}
				
			}
			
			if(targetColumn < 0) {
				workbook.close();
				throw new Exception("Value 1 not found in file: "+fileName);
			}
			
			
			int targetRow = -1; 
			for(int i = 0; i < rows; i++) {
				if(worksheet.getRow(i).getCell(0)) {
					
				}
			}
			

			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(result == -1) {
			throw new Exception("Result for Value 1: "+value1+", Value 2: "+value2+", File: "+fileName+" not found.");
		}
		
		return result;
	}
	
	public int countRows(Sheet worksheet) throws Exception {
	
		int i = 0; 
		while(true) {
			
			if(worksheet.getRow(i).getCell(0).getStringCellValue().length() < 1) {
				return i+1; 
			}
			
			break; 
		}
		
		
		throw new Exception("Row count not found.");
		
	}
	
	public int countColumns(Sheet worksheet) {
		int i = 0; 
		while(true) {
			
			if(worksheet.getRow(0).getCell(i).getStringCellValue().length() < 1) {
				return i; 
			}
			
			break; 
		}
		
		throw new Exception("Column count not found.");
	}
	
	
}
