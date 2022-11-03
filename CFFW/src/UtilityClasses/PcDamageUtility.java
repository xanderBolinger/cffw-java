package UtilityClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PcDamageUtility {

	
	public static String getDamageString(int epen, int dc, boolean open, int roll) throws IOException {
		
		FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "hittable.xlsx"));
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet worksheet = workbook.getSheetAt(0);
		
		// get location 
		
		// get dc column 
		
		// get epen column 
		
		// get and return damage string
		
		workbook.close();
		
		return ""; 
		
	}
	
	public int getHitLocationRow(boolean open, int hitRoll, Sheet worksheet) {
		
		int column = open ? 1 : 0;
		
		Cell cell = worksheet.getRow(4).getCell(column);
		
		while(cell == null || cell.getCellType() == CellType.BLANK) {
			
			if(cell.getCellType() == CellType.NUMERIC && hitRoll >= cell.getNumericCellValue()) {
				return cell.getRowIndex();
			}
			
		}
		
		return -1; 
	}
	
}
