package UtilityClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PcDamageUtility {

	
	public static int getDamageValue(String damageString) {
		
		Pattern pattern = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(damageString);
	    boolean matchFound = matcher.find();
	    if(matchFound) {
	      
	    	return Integer.parseInt(matcher.group());
	    	
	    }
		
		return -1;
	}
	
	// H : 100, K : 1000, T : 10000, X : 100000, M : 1000000, D : disabled  
	public static int getMultiplier(String damageString) {
		
		Pattern pattern = Pattern.compile("[A-CE-Z]", Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(damageString);
	    boolean matchFound = matcher.find();
	    
	    char damageCharacter = '0';
	    
	    if(matchFound) {
	    	damageCharacter = matcher.group().toCharArray()[0];
	    }
		
	    int multiplier; 
	    
	    switch(damageCharacter) {
	    	case 'H': 
	    		multiplier = 100; 
	    		break;
	    	case 'K':
	    		multiplier = 1000; 
	    		break;
	    	case 'T':
	    		multiplier = 10000; 
	    		break;
	    	case 'X':
	    		multiplier = 100000; 
	    		break;
	    	case 'M':
	    		multiplier = 1000000; 
	    		break;
	    	default: 
	    		multiplier = 1;
	    }
	    
		return multiplier;
	}
	
	public static boolean getDisabled(String damageString) {
		Pattern pattern = Pattern.compile("(?:D)", Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(damageString);
	    boolean matchFound = matcher.find();
	    
	    if(matchFound) { 
	    	return true;
	    }
		
		return false; 
	}
	
	public static String getDamageString(int epen, int dc, boolean open, int hitRoll) throws IOException {
		
		FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "\\hittable.xlsx"));
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet worksheet = workbook.getSheetAt(0);
		
		// get location 
		int row = getHitLocationRow(open, hitRoll, worksheet);
		
		// get dc column 
		int dcColumn = getDcColumn(dc, worksheet);
		
		// get epen column 
		int column = getEpenColumn(dcColumn, epen, worksheet);
		
		// get and return damage string
		String results;
		Cell cell = worksheet.getRow(row).getCell(column);
		if(cell.getCellType() == CellType.NUMERIC) {
			results = Integer.toString((int) cell.getNumericCellValue());
		} else {
			results = cell.getStringCellValue();
		}
		
		workbook.close();
		
		return results; 
		
	}
	
	public static int getDcColumn(int dc, Sheet worksheet) {
		
		int i = 0; 
		Cell cell = worksheet.getRow(1).getCell(i);
		while(true) {
			
			if(cell != null && cell.getCellType() == CellType.NUMERIC && dc == cell.getNumericCellValue()) {
				return i;
			}
			
			i++;
			cell = worksheet.getRow(1).getCell(i);
		}
		
	}
	
	public static int getEpenColumn (int dcColumn, int epen, Sheet worksheet) {
		
		if(epen > 10) {
			epen = 10;
		}
		
		int i = dcColumn; 
		Cell cell = worksheet.getRow(3).getCell(i);
		while(true) {
			
			if(cell != null && cell.getCellType() == CellType.NUMERIC && epen <= cell.getNumericCellValue()) {
				return i;
			}
			
			i++;
			cell = worksheet.getRow(3).getCell(i);
		}
	}
	
	public static int getHitLocationRow(boolean open, int hitRoll, Sheet worksheet) {
		
		int column = open ? 1 : 0;
		
		int i = 4;
		Cell cell = worksheet.getRow(i).getCell(column);
		
		while(cell != null && cell.getCellType() != CellType.BLANK) {
			
			if(cell.getCellType() == CellType.NUMERIC && hitRoll <= cell.getNumericCellValue()) {
				return cell.getRowIndex();
			}
			
			i++; 
			
			cell = worksheet.getRow(i).getCell(column);
			
		}
		
		return -1; 
	}
	
}
