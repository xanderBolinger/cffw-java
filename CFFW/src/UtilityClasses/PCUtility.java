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

			excelFile = new FileInputStream(new File(ExcelUtility.path + "\\PC Hit Calc Xlsxs\\EAL.xlsx"));

			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			int rowNum = 0;
			for (int i = 1; i < 40; i++) {
				if (EAL >= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
					rowNum = i;
					break;
				}
			}

			workbook.close();
			excelFile.close();

			Row row = worksheet.getRow(rowNum);

			if (singleShot) {
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

			excelFile = new FileInputStream(new File(ExcelUtility.path + "\\PC Hit Calc Xlsxs\\EAL.xlsx"));

			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			// Target col for comparison
			int targetCol = 1;

			if (singleShot)
				targetCol = 2;

			int rowNum = 0;

			for (int i = 1; i < 40; i++) {
				if (value >= worksheet.getRow(i).getCell(targetCol).getNumericCellValue()) {
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

		trooper.weaponPercent = "";

		if (skill.equals("Pistol")) {
			sl += trooper.skills.getSkill("Pistol").value;
			trooper.weaponPercent += "Pistol: ";
		} else if (skill.equals("Subgun")) {
			sl += trooper.skills.getSkill("Subgun").value;
			trooper.weaponPercent += "Subgun: ";
		} else if (skill.equals("Heavy")) {
			sl += trooper.skills.getSkill("Heavy").value;
			trooper.weaponPercent += "Heavy: ";
		} else if (skill.equals("Launcher")) {
			sl += trooper.skills.getSkill("Launcher").value;
			trooper.weaponPercent += "Launcher: ";
		} else {
			sl += trooper.skills.getSkill("Rifle").value;
			trooper.weaponPercent += "Rifle: ";
		}
		// System.out.println("Trooper: "+trooper.number+" "+trooper.name);
		// System.out.println("Weapon Skill: "+sl);
		trooper.weaponPercent += Float.toString(sl);
		// System.out.println("Fighter Skill:
		// "+trooper.skills.getSkill("Fighter").value);
		sl += trooper.skills.getSkill("Fighter").value;

		return getSL(sl);

		// System.out.println("Trooper original SL: "+trooper.sl);

	}

	public static int getSL(int sl) {
		float val = ((float) sl) / 2f;

		int skillLevel = 0;

		if (val <= 20) {
			return 0;
		} else if (val <= 30) {
			return 3;
		} else if (val <= 45) {
			return 5;
		} else if (val <= 50) {
			return 7;
		} else if (val <= 55) {
			return 10;
		} else if (val <= 60) {
			return 11;
		} else if (val <= 65) {
			return 12;
		} else if (val <= 75) {
			return 13;
		} else if (val <= 85) {
			return 14;
		} else if (val <= 88) {
			return 15;
		} else if (val <= 95) {
			return 16;
		} else {
			return 17;
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

		if (hex == null)
			return 0;

		ALM -= hex.obscuration;

		String visibility = game.visibility;

		if (shooterTrooper.nightVisionInUse) {
			if (visibility.equals("Good Visibility")) {

			} else if (visibility.equals("Dusk")) {
				ALM -= 2;
			} else if (visibility.equals("Night - Full Moon")) {

			} else if (visibility.equals("Night - Half Moon")) {

				if (shooterTrooper.nightVisionEffectiveness == 1) {
					ALM -= 2;
				} else if (shooterTrooper.nightVisionEffectiveness == 2) {

				} else if (shooterTrooper.nightVisionEffectiveness == 3) {

				} else if (shooterTrooper.nightVisionEffectiveness == 4) {

				}

			} else if (visibility.equals("Night - No Moon")) {
				if (shooterTrooper.nightVisionEffectiveness == 1) {
					ALM -= 8;
				} else if (shooterTrooper.nightVisionEffectiveness == 2) {
					ALM -= 6;
				} else if (shooterTrooper.nightVisionEffectiveness == 3) {
					ALM -= 3;
				} else if (shooterTrooper.nightVisionEffectiveness == 4) {
					ALM -= 2;
				}

			} else if (visibility.equals("Smoke/Fog/Haze/Overcast")) {
				ALM -= 6;
			} else if (visibility.equals("Dusk - Smoke/Fog/Haze/Overcast")) {
				ALM -= 8;
			} else if (visibility.equals("Night - Smoke/Fog/Haze/Overcast")) {
				if (shooterTrooper.nightVisionEffectiveness == 1) {
					ALM -= 10;
				} else if (shooterTrooper.nightVisionEffectiveness == 2) {
					ALM -= 9;
				} else if (shooterTrooper.nightVisionEffectiveness == 3) {
					ALM -= 8;
				} else if (shooterTrooper.nightVisionEffectiveness == 4) {
					ALM -= 6;
				}
			} else if (visibility.equals("No Visibility - Heavy Fog - White Out")) {
				ALM -= 14;
			}

		} else if (shooterTrooper.weaponLightOn && visibility.substring(0, 5).equals("Night")) {
			if (visibility.equals("Good Visibility")) {

			} else if (visibility.equals("Dusk")) {
				ALM -= 2;
			} else if (visibility.equals("Night - Full Moon")) {

			} else if (visibility.equals("Night - Half Moon")) {

			} else if (visibility.equals("Night - No Moon")) {

			} else if (visibility.equals("Smoke/Fog/Haze/Overcast")) {
				ALM -= 6;
			} else if (visibility.equals("Dusk - Smoke/Fog/Haze/Overcast")) {
				ALM -= 8;
			} else if (visibility.equals("Night - Smoke/Fog/Haze/Overcast")) {
				ALM -= 6;
			} else if (visibility.equals("No Visibility - Heavy Fog - White Out")) {
				ALM -= 14;
			}
		} else {
			if (visibility.equals("Good Visibility")) {

			} else if (visibility.equals("Dusk")) {
				ALM -= 2;
			} else if (visibility.equals("Night - Full Moon")) {
				ALM -= 4;
			} else if (visibility.equals("Night - Half Moon")) {
				ALM -= 6;
			} else if (visibility.equals("Night - No Moon")) {
				ALM -= 12;
			} else if (visibility.equals("Smoke/Fog/Haze/Overcast")) {
				ALM -= 6;
			} else if (visibility.equals("Dusk - Smoke/Fog/Haze/Overcast")) {
				ALM -= 8;
			} else if (visibility.equals("Night - Smoke/Fog/Haze/Overcast")) {
				ALM -= 12;
			} else if (visibility.equals("No Visibility - Heavy Fog - White Out")) {
				ALM -= 14;
			}
		}

		if (visibility.equals("Good Visibility")) {

		} else if (visibility.equals("Dusk")) {
			ALM -= 2;
		} else if (visibility.equals("Night - Full Moon")) {
			ALM -= 4;
		} else if (visibility.equals("Night - Half Moon")) {
			ALM -= 6;
		} else if (visibility.equals("Night - No Moon")) {
			ALM -= 12;
		} else if (visibility.equals("Smoke/Fog/Haze/Overcast")) {
			ALM -= 6;
		} else if (visibility.equals("Dusk - Smoke/Fog/Haze/Overcast")) {
			ALM -= 8;
		} else if (visibility.equals("Night - Smoke/Fog/Haze/Overcast")) {
			ALM -= 12;
		} else if (visibility.equals("No Visibility - Heavy Fog - White Out")) {
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
			excelFile = new FileInputStream(new File(ExcelUtility.path + "\\PC Hit Calc Xlsxs\\" + "range.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			int row = 0;

			for (int i = 1; i < 53; i++) {
				// System.out.println("rangeInPCHexes: "+ rangeInPCHexes);
				// System.out.println("Range num in table: "+
				// worksheet.getRow(i).getCell(0).getNumericCellValue());
				if (rangeInPCHexes <= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
					// System.out.println("Row = i "+ i);
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

		// System.out.println("Range ALM: "+rangeALM);
		return rangeALM;
	}

	// Takes a range of values between 0 and 99
	// Returns an array list
	public static ArrayList<Location> getPCLocations(int lowerBound, int upperBound, boolean open) {

		if (lowerBound < 0 || lowerBound > 99 || upperBound < 0 || upperBound > 99) {
			return null;
		}

		// System.out.println("Searching Through: "+lowerBound+", "+upperBound+" Open:
		// "+open);

		ArrayList<Location> locations = new ArrayList<>();

		FileInputStream excelFile;
		try {

			excelFile = new FileInputStream(new File(ExcelUtility.path + "\\hittable.xlsx"));

			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			int rowCount;

			if (open)
				rowCount = 43;
			else
				rowCount = 20;

			int colCount;

			if (open)
				colCount = 1;
			else
				colCount = 0;

			// System.out.println("RoW Count: "+rowCount+" Col Count: "+ colCount);

			for (int i = 4; i < rowCount; i++) {

				// System.out.println("I: "+i);
				int cellValue = (int) worksheet.getRow(i).getCell(colCount).getNumericCellValue();
				// System.out.println("Upper Bound: "+cellValue);
				int locationLowerBound = 0;

				if ((cellValue != 0 && open) || (cellValue != 2 && !open)) {
					locationLowerBound = (int) worksheet.getRow(i - 1).getCell(colCount).getNumericCellValue() + 1;
				}

				// System.out.println("Lower Bound: "+locationLowerBound);
				// System.out.println("Cell:
				// "+worksheet.getRow(i).getCell(2).getStringCellValue());

				if (cellValue >= lowerBound && cellValue <= upperBound) {

					if (lowerBound > locationLowerBound)
						locationLowerBound = lowerBound;

					if (upperBound < cellValue)
						cellValue = upperBound;

					locations.add(new Location(worksheet.getRow(i).getCell(2).getStringCellValue(), locationLowerBound,
							cellValue));
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

	// Takes trooper PC size in yards
	// Modifies trooper size from stance
	// Finds size ALM
	// Returns size ALM
	public static int findSizeALM(String stance, double size) {

		if (stance.equals("Standing")) {
			// System.out.println("Standing");
			size = size / 1;
		} else if (stance.equals("Crouching")) {
			// System.out.println("Crouching");
			size = size / 1.25;
		} else {
			// System.out.println("Prone");
			size = size / 2;
		}

		int sizeALM = 0;

		// Finds range ALM
		FileInputStream excelFile;
		try {
			excelFile = new FileInputStream(new File(ExcelUtility.path + "\\PC Hit Calc Xlsxs\\" + "size.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			int row = 0;

			for (int i = 1; i < 53; i++) {

				if (size <= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
					row = i;
					break;
				}

			}

			sizeALM = (int) worksheet.getRow(row).getCell(1).getNumericCellValue();
			workbook.close();
			excelFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("Range ALM: "+sizeALM);
		return sizeALM;

	}
	
	public static int defensiveALM(int isf) {

		if(isf <= 3) {
			return 16;
		} else if(isf <= 4) {
			return 13;
		} else if(isf <= 5) {
			return 11;
		} else if(isf <= 6) {
			return 10;
		} else if(isf <= 7) {
			return 8;
		} else if(isf <= 8) {
			return 7;
		} else if(isf <= 9) {
			return 6;
		} else if(isf <= 10) {
			return 5;
		} else if(isf <= 11) {
			return 4;
		} else if(isf <= 12) {
			return 3;
		} else if(isf <= 14) {
			return 2;
		} else if(isf <= 16) {
			return 1;
		} else if(isf <= 17) {
			return 0;
		} else if(isf <= 19) {
			return -1;
		} else if(isf <= 22) {
			return -2;
		} else if(isf <= 24) {
			return -3;
		} else if(isf <= 27) {
			return -4;
		} else if(isf <= 30) {
			return -5;
		} else if(isf <= 34) {
			return -6;
		} else if(isf <= 38) {
			return -7;
		} else if(isf <= 40) {
			return -8;
		} else {
			System.out.println("ISF not found for defensive ALM");
			return 0;
		}

	}
}
