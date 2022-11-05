package UtilityClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import CorditeExpansionDamage.BloodLossLocation;
import CorditeExpansionStatBlock.MedicalStatBlock;
import Trooper.Trooper;

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
	
	public static BloodLossLocation getBloodLossPD(double epen, int dc, String locationName, Trooper trooper) {
		if (trooper.entirelyMechanical) {
			return null;
		} else {

			for (int i = 0; i < trooper.mechanicalZones.size(); i++) {
				if (trooper.mechanicalZones.get(i).equals(locationName))
					return null;
			}

		}

		double bloodLossPD = 0;

		if (locationName.equals("Neck Flesh") || locationName.equals("Neck Spine")
				|| locationName.equals("Base of Neck")) {
			bloodLossPD += 800;
		} else if (locationName.equals("Arm Flesh") || locationName.equals("Arm Bone")) {
			bloodLossPD += 600;
		} else if (locationName.equals("Shoulder")) {
			bloodLossPD += 400;
		} else if (locationName.equals("Stomach") || locationName.equals("Stomach - Rib")
				|| locationName.equals("Stomach Spleen") || locationName.equals("Stomach-Kidney")
				|| locationName.equals("Intestines")) {
			if (epen <= 1) {
				bloodLossPD += 300;
			} else if (epen == 2) {
				bloodLossPD += 600;
			} else {
				bloodLossPD += 900;
			}
		} else if (locationName.equals("Thigh Flesh")) {
			if (epen <= 1) {
				bloodLossPD += 400;
			} else {
				bloodLossPD += 600;
			}
		} else if (locationName.equals("Thigh Bone")) {
			if (epen <= 1) {
				bloodLossPD += 600;
			} else if (epen == 2) {
				bloodLossPD += 800;
			} else if (epen == 3) {
				bloodLossPD += 1000;
			} else {
				bloodLossPD += 1200;
			}
		}

		
		if (dc <= 2) {
			bloodLossPD = bloodLossPD * 0.5;
		} else if (dc <= 4) {
			bloodLossPD = bloodLossPD * 1;
		} else if (dc <= 5) {
			bloodLossPD = bloodLossPD * 1.2;
		} else if (dc <= 8) {
			bloodLossPD = bloodLossPD * 1.5;
		} else if (dc <= 10) {
			bloodLossPD = bloodLossPD * 2;
		}

		if(bloodLossPD <= 0)
			return null;
		
		return new BloodLossLocation((int) bloodLossPD, locationName);

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
	
	public static String getDamageString(int epen, int dc, boolean open, int hitRoll, Sheet worksheet) throws IOException {
		
		
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
	
	public static String getHitLocationName(boolean open, int hitRoll, Sheet worksheet) {
		
		int column = open ? 1 : 0;
		
		int i = 4;
		Cell cell = worksheet.getRow(i).getCell(column);
		
		while(cell != null && cell.getCellType() != CellType.BLANK) {
			
			if(cell.getCellType() == CellType.NUMERIC && hitRoll <= cell.getNumericCellValue()) {
				return worksheet.getRow(i).getCell(2).getStringCellValue();
			}
			
			i++; 
			
			cell = worksheet.getRow(i).getCell(column);
			
		}
		
		return "None"; 
	}
	
	public static void setRecovery(int aidMod, int recoveryRollMod, 
			int physicalDamage, boolean recivingFirstAid, MedicalStatBlock medicalStatBlock) {
		
		int recoveryRoll = -1, criticalTime = -1;
		
		try {
			// System.out.println("Path: "+path);
			FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "\\aid.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			org.apache.poi.ss.usermodel.Sheet worksheet = workbook.getSheetAt(0);

			int woundRow = 0;
			woundRow -= aidMod;
			for (int i = 2; i < 41; i++) {
				if (physicalDamage <= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
					woundRow += i;
					break;
				}
			}

			if (woundRow < 2) {
				woundRow = 2;
			}

			// System.out.println("WR: "+woundRow+" PD: "+physicalDamage);
			if (recivingFirstAid) {
				recoveryRoll = (int) worksheet.getRow(woundRow).getCell(4).getNumericCellValue() + recoveryRollMod;

				String ctp = worksheet.getRow(woundRow).getCell(3).getStringCellValue();
				// System.out.println("CTP: "+ctp);

				if (ctp.length() == 2) {

					int value = Character.getNumericValue(ctp.charAt(0));

					if (ctp.charAt(1) == 'h') {
						value = value * 60 * 120;
					} else if (ctp.charAt(1) == 'm') {
						value = value * 120;
					} else if (ctp.charAt(1) == 'p') {
						value = value * 2;
					} else if (ctp.charAt(1) == 'd') {
						value = value * 60 * 120 * 24;
					}

					// System.out.println("CTP Value: "+value);
					criticalTime = value;

				} else {
					int value = Integer.parseInt(ctp.substring(0, 2));

					if (ctp.charAt(2) == 'h') {
						value = value * 60 * 120;
					} else if (ctp.charAt(2) == 'm') {
						value = value * 120;
					} else if (ctp.charAt(2) == 'p') {
						value = value * 2;
					} else if (ctp.charAt(2) == 'd') {
						value = value * 60 * 120 * 24;
					}
					// System.out.println("CTP Value: "+value);
					criticalTime = value;
				}

			} else {

				recoveryRoll = (int) worksheet.getRow(woundRow).getCell(2).getNumericCellValue() + recoveryRollMod;

				String ctp = worksheet.getRow(woundRow).getCell(1).getStringCellValue();
				// System.out.println("CTP No First Aid: "+ctp);

				if (ctp.length() == 2) {

					int value = Character.getNumericValue(ctp.charAt(0));

					if (ctp.charAt(1) == 'h') {
						value = value * 60 * 120;
					} else if (ctp.charAt(1) == 'm') {
						value = value * 120;
					} else if (ctp.charAt(1) == 'p') {
						value = value * 2;
					} else if (ctp.charAt(1) == 'd') {
						value = value * 60 * 120 * 24;
					}
					// System.out.println("CTP Value: "+value);
					criticalTime = value;

				} else {
					int value = Integer.parseInt(ctp.substring(0, 2));

					if (ctp.charAt(2) == 'h') {
						value = value * 60 * 120;
					} else if (ctp.charAt(2) == 'm') {
						value = value * 120;
					} else if (ctp.charAt(2) == 'p') {
						value = value * 2;
					} else if (ctp.charAt(2) == 'd') {
						value = value * 60 * 120 * 24;
					}
					System.out.println("CTP Value: "+value);
					criticalTime = value;
				}

			}

			excelFile.close();
			workbook.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Recovery Chance: "+recoveryRoll+", Critical Time: "+criticalTime);
		medicalStatBlock.recoveryChance = recoveryRoll;
		medicalStatBlock.criticalTime = criticalTime;
		
	}
}
