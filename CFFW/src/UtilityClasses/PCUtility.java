package UtilityClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Conflict.Game;
import Conflict.GameWindow;
import Hexes.Hex;
import Items.Weapons;
import Trooper.Trooper;
import Unit.Unit;

public class PCUtility {
	
	
	
	// Takes EAL and boolean for singleShot or fullAuto as parameter 
	// Returns the cell value according to the params 
	public static int getOddsOfHitting(boolean singleShot, int EAL) {
		
		FileInputStream excelFile;
		try {
					
			excelFile = new FileInputStream(new File(ExcelUtility.path+"\\PC Hit Calc Xlsxs\\EAL.xlsx"));

			Workbook workbook = new XSSFWorkbook(excelFile);
		    Sheet worksheet = workbook.getSheetAt(0);
		    
		    int rowNum = 0; 
		    for(int i = 1; i < 40; i++) {
		    	if(EAL >= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
		    		rowNum = i; 
		    		break; 
		    	}
		    }
		    
		    workbook.close();
		    excelFile.close();
		    
		    Row row = worksheet.getRow(rowNum);
		    
		    if(singleShot) {
		    	return (int) row.getCell(1).getNumericCellValue();
		    } else {
		    	return (int) row.getCell(2).getNumericCellValue();
		    }
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1; 
	}
	
	// Inverse of get odds of hitting 
	// takes percentage value, returns EAL according to the value 
	public static int getEAL(boolean singleShot, int value) {
		
		FileInputStream excelFile;
		try {
					
			excelFile = new FileInputStream(new File(ExcelUtility.path+"\\PC Hit Calc Xlsxs\\EAL.xlsx"));

			Workbook workbook = new XSSFWorkbook(excelFile);
		    Sheet worksheet = workbook.getSheetAt(0);
		    
		    // Target col for comparison 
		    int targetCol = 1; 
		    
		    if(singleShot)
		    	targetCol = 2; 
		    
		    int rowNum = 0; 
		    
		    for(int i = 1; i < 40; i++) {
		    	if(value >= worksheet.getRow(i).getCell(targetCol).getNumericCellValue()) {
		    		rowNum = i; 
		    		break; 
		    	}
		    }
		    
		    workbook.close();
		    excelFile.close();
		    
		    // Returns EAL 
		    return (int) worksheet.getRow(rowNum).getCell(0).getNumericCellValue();

		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1; 
	}
	
	
	public static int getSL(String skill, Trooper trooper) {
	
		int sl = 0; 
		
		if(skill.equals("Pistol")) {
			sl += trooper.skills.getSkill("Pistol").value; 
		} else if(skill.equals("Subgun")) {
			sl +=  trooper.skills.getSkill("Subgun").value; 
		} else if(skill.equals("Heavy")) {
			sl +=  trooper.skills.getSkill("Heavy").value; 
		} else if(skill.equals("Launcher")) {
			sl +=  trooper.skills.getSkill("Launcher").value; 
		} else {
			sl +=  trooper.skills.getSkill("Rifle").value; 
		}
		//System.out.println("Trooper: "+trooper.number+" "+trooper.name);
		//System.out.println("Weapon Skill: "+sl);
		trooper.weaponPercent = Float.toString(sl); 
		//System.out.println("Fighter Skill: "+trooper.skills.getSkill("Fighter").value);
		sl += trooper.skills.getSkill("Fighter").value;
		
		return getSL(sl);
		
		//System.out.println("Trooper original SL: "+trooper.sl);
		

	}
	
	public static int getSL(int sl) {
		float val = ((float) sl) / 2f; 
		
		int skillLevel = 0;
		
		if(val <= 20) {
			skillLevel = 0; 
		} else if(val <= 40) {
			skillLevel = 1; 
		} else if(val <= 45) {
			skillLevel = 2; 
		} else if(val <= 60) {
			skillLevel = 3; 
		} else if(val <= 65) {
			skillLevel = 4; 
		} else if(val <= 70) {
			skillLevel = 5; 
		} else if(val <= 80) {
			skillLevel = 6; 
		} else if(val <= 85) {
			skillLevel = 7; 
		} else if(val <= 88) {
			skillLevel = 8; 
		} else if(val <= 95) {
			skillLevel = 9; 
		} else {
			skillLevel = 10; 
		}

		//System.out.println("New SL: "+ skillLevel+", Val: "+val);
		
		if(skillLevel == 0) {
			return 0; 
		} else if (skillLevel == 1) {
			return 5; 
		} else if (skillLevel == 2) {
			return 7; 
		} else if (skillLevel == 3) {
			return 9; 
		} else if (skillLevel == 4) {
			return 10; 
		} else if (skillLevel == 5) {
			return 11; 
		} else if (skillLevel == 6) {
			return 12; 
		} else if (skillLevel == 7) {
			return 13; 
		} else if (skillLevel == 8) {
			return 14; 
		} else if (skillLevel == 9) {
			return 15; 
		} else if (skillLevel == 10) {
			return 16; 
		} else {
			return 16; 
		}
	}
	
	
	
	public static int findVisibiltyALM(Unit targetUnit, Trooper shooterTrooper, int rangeInPCHexes) {
		
		return findVisibiltyALM(targetUnit.X, targetUnit.Y, shooterTrooper, rangeInPCHexes);
	}
	
	// Returns ALM mod for visibilty of conflict 
	public static int findVisibiltyALM(int x, int y, Trooper shooterTrooper, int rangeInPCHexes) {
		
		int ALM = 0; 
		GameWindow game = GameWindow.gameWindow;
		
		// Find hex obscuration 
		// Apply obscuration 
		Hex hex = game.findHex(x, y);
		
		if(hex == null)
			return 0; 
		
		ALM -= hex.obscuration;
		
		String visibility = game.visibility;
		
		if(shooterTrooper.nightVisionInUse) {
			if(visibility.equals("Good Visibility")) {
				
			} else if(visibility.equals("Dusk")) {
				ALM -= 2; 
			} else if(visibility.equals("Night - Full Moon")) {
				
				
				
			} else if(visibility.equals("Night - Half Moon")) {
				
				if(shooterTrooper.nightVisionEffectiveness == 1) {
					ALM -= 2; 
				} else if(shooterTrooper.nightVisionEffectiveness == 2) {
					 
				} else if(shooterTrooper.nightVisionEffectiveness == 3) {
					
				} else if(shooterTrooper.nightVisionEffectiveness == 4) {
					
				}
				
				
			} else if(visibility.equals("Night - No Moon")) {
				if(shooterTrooper.nightVisionEffectiveness == 1) {
					ALM -= 8; 
				} else if(shooterTrooper.nightVisionEffectiveness == 2) {
					ALM -= 6; 
				} else if(shooterTrooper.nightVisionEffectiveness == 3) {
					ALM -= 3;
				} else if(shooterTrooper.nightVisionEffectiveness == 4) {
					ALM -= 2; 
				}
				
			} else if(visibility.equals("Smoke/Fog/Haze/Overcast")) {
				ALM -= 6; 
			} else if(visibility.equals("Dusk - Smoke/Fog/Haze/Overcast")) {
				ALM -= 8; 
			} else if(visibility.equals("Night - Smoke/Fog/Haze/Overcast")) {
				if(shooterTrooper.nightVisionEffectiveness == 1) {
					ALM -= 10; 
				} else if(shooterTrooper.nightVisionEffectiveness == 2) {
					ALM -= 9; 
				} else if(shooterTrooper.nightVisionEffectiveness == 3) {
					ALM -= 8;
				} else if(shooterTrooper.nightVisionEffectiveness == 4) {
					ALM -= 6; 
				}
			} else if(visibility.equals("No Visibility - Heavy Fog - White Out")) {
				ALM -= 14; 
			}
			
		} else if(shooterTrooper.weaponLightOn && visibility.substring(0, 5).equals("Night")) {
			if(visibility.equals("Good Visibility")) {
				
			} else if(visibility.equals("Dusk")) {
				ALM -= 2; 
			} else if(visibility.equals("Night - Full Moon")) {
			 
			} else if(visibility.equals("Night - Half Moon")) {
				 
			} else if(visibility.equals("Night - No Moon")) {
				 
			} else if(visibility.equals("Smoke/Fog/Haze/Overcast")) {
				ALM -= 6; 
			} else if(visibility.equals("Dusk - Smoke/Fog/Haze/Overcast")) {
				ALM -= 8; 
			} else if(visibility.equals("Night - Smoke/Fog/Haze/Overcast")) {
				ALM -= 6; 
			} else if(visibility.equals("No Visibility - Heavy Fog - White Out")) {
				ALM -= 14; 
			}
		} else {
			if(visibility.equals("Good Visibility")) {
				
			} else if(visibility.equals("Dusk")) {
				ALM -= 2; 
			} else if(visibility.equals("Night - Full Moon")) {
				ALM -= 4; 
			} else if(visibility.equals("Night - Half Moon")) {
				ALM -= 6; 
			} else if(visibility.equals("Night - No Moon")) {
				ALM -= 12; 
			} else if(visibility.equals("Smoke/Fog/Haze/Overcast")) {
				ALM -= 6; 
			} else if(visibility.equals("Dusk - Smoke/Fog/Haze/Overcast")) {
				ALM -= 8; 
			} else if(visibility.equals("Night - Smoke/Fog/Haze/Overcast")) {
				ALM -= 12; 
			} else if(visibility.equals("No Visibility - Heavy Fog - White Out")) {
				ALM -= 14; 
			}
		} 
		
		if(visibility.equals("Good Visibility")) {
			
		} else if(visibility.equals("Dusk")) {
			ALM -= 2; 
		} else if(visibility.equals("Night - Full Moon")) {
			ALM -= 4; 
		} else if(visibility.equals("Night - Half Moon")) {
			ALM -= 6; 
		} else if(visibility.equals("Night - No Moon")) {
			ALM -= 12; 
		} else if(visibility.equals("Smoke/Fog/Haze/Overcast")) {
			ALM -= 6; 
		} else if(visibility.equals("Dusk - Smoke/Fog/Haze/Overcast")) {
			ALM -= 8; 
		} else if(visibility.equals("Night - Smoke/Fog/Haze/Overcast")) {
			ALM -= 12; 
		} else if(visibility.equals("No Visibility - Heavy Fog - White Out")) {
			ALM -= 14; 
		}
		
		
		
		return ALM; 
	}

	
	// Takes range in PC hexes 
	// Returns corresponding rabge alm
	public static int findRangeALM(int rangeInPCHexes) {
		
		int rangeALM = 0;
		
		// Finds range ALM 
		FileInputStream excelFile;
		try {
			excelFile = new FileInputStream(new File(ExcelUtility.path+"\\PC Hit Calc Xlsxs\\"
					+ "range.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
		    Sheet worksheet = workbook.getSheetAt(0);
				
		    int row = 0; 
		    
		    for(int i = 1; i <53; i++) {
		    	//System.out.println("rangeInPCHexes: "+ rangeInPCHexes);
		    	//System.out.println("Range num in table: "+ worksheet.getRow(i).getCell(0).getNumericCellValue());
		    	if(rangeInPCHexes <= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
		    		//System.out.println("Row = i "+ i);
		    		row = i; 
		    		break; 
		    	}
		    	
		    }
		    
			rangeALM = (int) worksheet.getRow(row).getCell(1).getNumericCellValue();
			workbook.close();
			excelFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//System.out.println("Range ALM: "+rangeALM);
		return rangeALM;
	}
		
	// Takes a range of values between 0 and 99 
	// Returns an array list 
	public static ArrayList<Location> getPCLocations(int lowerBound, int upperBound, boolean open) {
		
		if(lowerBound < 0 || lowerBound > 99 || upperBound < 0 || upperBound > 99) {
			return null; 
		}
		
		//System.out.println("Searching Through: "+lowerBound+", "+upperBound+" Open: "+open);
		
		ArrayList<Location> locations = new ArrayList<>();
		
		FileInputStream excelFile;
		try {
					
			excelFile = new FileInputStream(new File(ExcelUtility.path+"\\hittable.xlsx"));

			Workbook workbook = new XSSFWorkbook(excelFile);
		    Sheet worksheet = workbook.getSheetAt(0);
		    
		    int rowCount; 
		    
		    if(open) 
		    	rowCount = 43;
		    else
		    	rowCount = 20;
		    
		    int colCount; 
		    
		    if(open)
		    	colCount = 1;
		    else colCount = 0; 
		    
		    //System.out.println("RoW Count: "+rowCount+" Col Count: "+ colCount);
		    
		    for(int i = 4; i < rowCount; i++) {
		    	
		    	//System.out.println("I: "+i);
		    	int cellValue = (int) worksheet.getRow(i).getCell(colCount).getNumericCellValue();
		    	//System.out.println("Upper Bound: "+cellValue);
		    	int locationLowerBound = 0; 
	    		
	    		if((cellValue != 0 && open) || (cellValue != 2 && !open)) {
	    			locationLowerBound = (int) worksheet.getRow(i-1).getCell(colCount).getNumericCellValue() + 1;
	    		}
	    		
	    		//System.out.println("Lower Bound: "+locationLowerBound);
	    		//System.out.println("Cell: "+worksheet.getRow(i).getCell(2).getStringCellValue());
	    		
		    	if(cellValue >= lowerBound && cellValue <= upperBound) {	
		    		
		    		if(lowerBound > locationLowerBound)
		    			locationLowerBound = lowerBound;
		    		
		    		if(upperBound < cellValue)
		    			cellValue = upperBound;
		    		
		    		locations.add(new Location(worksheet.getRow(i).getCell(2).getStringCellValue(), locationLowerBound, cellValue));
		    	}
		    	
		    }
		    
		    workbook.close();
		    excelFile.close();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return locations; 
	}
}
