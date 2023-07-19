package Melee.Damage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import UtilityClasses.DiceRoller;
import UtilityClasses.ExcelUtility;

public class MeleeHitLocation implements Serializable {

	public enum MeleeDamageType {
		CUTTING, PEIRICNG, BLUNT
	}

	public static Pair<MeleeHitLocationData, MeleeHitLocationPcResults> GetHitLocationResults(
			MeleeDamageType damageType, int damagePoints, int damageLevel, int zone, int subZoneRoll, int armorValue) throws Exception {

		var data = GetMeleeHitLocation(damageType, damageLevel, zone, subZoneRoll);
		var pdText = GetPDText(armorValue, damagePoints, data.zoneName, damageType);
		var pcRslts = GetPdFromCell(pdText.getFirst());

		var pcHit = new MeleeHitLocationPcResults(pdText.getSecond(), pcRslts.getFirst(), pcRslts.getSecond());

		return new Pair<MeleeHitLocationData, MeleeHitLocationPcResults>(data, pcHit);
	}

	private static String GetFileName(MeleeDamageType damageType) throws Exception {
		String fileName;

		switch (damageType) {
		case CUTTING:
			fileName = "PcDamageTableCutting.xlsx";
			break;
		case PEIRICNG:
			fileName = "PcDamageTableStabbing.xlsx";
			break;
		case BLUNT:
			fileName = "PcDamageTableBlunt.xlsx";
			break;
		default:
			throw new Exception("Damage Type not found for damage type of: " + damageType);
		}

		return fileName;
	}

	public static Pair<String, String> GetPDText(int armorValue, int damagePoints, String hitLocation,
			MeleeDamageType damageType) throws Exception {

		String fileName = GetFileName(damageType);

		int avRow = GetAvRow(armorValue, fileName);
		int damageColumn = GetDamageColumn(avRow, damagePoints, fileName);

		FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "/MeleeHitTables/" + fileName));
		Workbook workbook = new XSSFWorkbook(excelFile);

		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();

		for (int i = 0; i < rowCount; i++) {
			Row row = sheet.getRow(i);

			if (row == null) {
				throw new RuntimeException(
						"Hit Location Table for: " + fileName + ", With AV: " + armorValue + " and Damage: "
								+ damagePoints + " searching for Hit Location: " + hitLocation + " not found.");
			}

			Cell cell = row.getCell(0);
			cell.setCellType(CellType.STRING);
			String cellValue = cell.getStringCellValue();

			switch (damageType) {
			case CUTTING:
				Pair<Boolean, Pair<String, String>> cuttingResult = GetCuttingCellValue(fileName, damageColumn,
						hitLocation);
				if (cuttingResult.getFirst()) {
					workbook.close();
					return cuttingResult.getSecond();
				}
				break;
			case PEIRICNG:
				Pair<Boolean, Pair<String, String>> stabbingResult = GetStabbingCellValue(fileName, damageColumn,
						hitLocation);
				if (stabbingResult.getFirst()) {
					workbook.close();
					return stabbingResult.getSecond();
				}
				break;
			case BLUNT:
				Pair<Boolean, Pair<String, String>> bluntResult = GetBluntCellValue(fileName, damageColumn,
						hitLocation);
				if (bluntResult.getFirst()) {
					workbook.close();
					return bluntResult.getSecond();
				}
				break;
			}

			if (cellValue.equals(hitLocation)) {
				Cell damageCell = row.getCell(damageColumn);
				damageCell.setCellType(CellType.STRING);
				String pdValue = damageCell.getStringCellValue();
				workbook.close();
				return new Pair<>(pdValue, hitLocation);
			}
		}

		workbook.close();
		throw new RuntimeException("Hit Location Table for: " + fileName + ", With AV: " + armorValue + " and Damage: "
				+ damagePoints + " searching for Hit Location: " + hitLocation + " not found.");
	}

	private static Pair<Boolean, Pair<String, String>> GetStabbingCellValue(String fileName, int damageColumn,
			String hitLocation) throws Exception {
		if ("Shoulder".equals(hitLocation)) {
			int roll = DiceRoller.roll(20, 25);

			if (roll <= 21) {
				// Shoulder socket
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 12, damageColumn), "Shoulder Socket"));
			} else {
				// Shoulder scapula
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 13, damageColumn), "Shoulder Scapula"));
			}
		} else if ("Skull".equals(hitLocation)) {
			return new Pair<>(true, new Pair<>(GetCellValue(fileName, 7, damageColumn), "Skull"));
		} else if ("Face".equals(hitLocation)) {
			int roll = DiceRoller.roll(6, 14);

			if (roll <= 7) {
				// Eye
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 8, damageColumn), "Eye"));
			} else {
				// Mouth
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 9, damageColumn), "Mouth"));
			}
		} else if ("Throat".equals(hitLocation)) {
			int roll = DiceRoller.roll(15, 19);

			if (roll <= 17) {
				// Neck
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 10, damageColumn), "Neck"));
			} else {
				// Base of neck
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 11, damageColumn), "Base of Neck"));
			}
		} else if ("Chest".equals(hitLocation) || "Ribcage".equals(hitLocation)) {
			int roll = DiceRoller.roll(26, 39);

			if (roll <= 30) {
				// Lung
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 14, damageColumn), "Lung"));
			} else if (roll <= 32) {
				// Heart
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 15, damageColumn), "Heart"));
			} else if (roll <= 34) {
				// Liver
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 16, damageColumn), "Liver"));
			} else if (roll <= 36) {
				// Liver-Spine
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 19, damageColumn), "Liver-Spine"));
			} else if (roll <= 38) {
				// Liver-Kidney
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 20, damageColumn), "Liver-Kidney"));
			} else {
				// Spine
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 22, damageColumn), "Spine"));
			}
		} else if ("Stomach".equals(hitLocation)) {
			int roll = DiceRoller.roll(39, 54);
			if (roll <= 39) {
				// Stomach
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 17, damageColumn), "Stomach"));
			}
			if (roll <= 43) {
				// Stomach Kidney
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 18, damageColumn), "Stomach Kidney"));
			}
			if (roll <= 46) {
				// Intestines
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 21, damageColumn), "Instestines"));
			}
			if (roll <= 47) {
				// Spine
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 22, damageColumn), "Spine"));
			}
			if (roll <= 54) {
				// Intestines-Pelvis
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 23, damageColumn), "Intestines-Pelvis"));
			}
		} else if ("Pelvis".equals(hitLocation)) {
			int roll = DiceRoller.roll(1, 3);
			if (roll == 1) {
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 21, damageColumn), "Instestines"));
			} else if (roll == 2) {
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 23, damageColumn), "Intestines-Pelvis"));
			} else {
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 24, damageColumn), "Hip Socket"));
			}
		} else if ("Forearm".equals(hitLocation) || "Elbow".equals(hitLocation)) {
			int roll = DiceRoller.roll(59, 74);

			if (roll <= 66) {
				// Upper arm
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 25, damageColumn), "Upper Arm"));
			} else {
				// Forearm
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 26, damageColumn), "Forearm"));
			}
		} else if ("Hip".equals(hitLocation))
			return new Pair<>(true, new Pair<>(GetCellValue(fileName, 24, damageColumn), "Hip Socket"));

		return new Pair<>(false, new Pair<>("", ""));

	}

	private static Pair<Boolean, Pair<String, String>> GetCuttingCellValue(String fileName, int damageColumn,
			String hitLocation) throws Exception {
		if ("Neck".equals(hitLocation)) {
			return new Pair<>(true, new Pair<>(GetCellValue(fileName, 9, damageColumn), "Throat"));
		}

		if ("Forearm".equals(hitLocation) || "Elbow".equals(hitLocation)) {
			int roll = DiceRoller.roll(1, 15);

			if (roll <= 9) {
				// Upper arm
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 15, damageColumn), "Upper Arm"));
			} else {
				// Forearm
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 16, damageColumn), "Forearm"));
			}
		}

		if ("Chest".equals(hitLocation) || "Ribcage".equals(hitLocation)) {
			int roll = DiceRoller.roll(1, 3);

			if (roll == 1) {
				// Lower chest
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 12, damageColumn), "Lower Chest"));
			} else {
				// Upper chest
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 11, damageColumn), "Upper Chest"));
			}
		}

		if ("Hip".equals(hitLocation)) {
			return new Pair<>(true, new Pair<>(GetCellValue(fileName, 14, damageColumn), "Pelvis"));
		}

		return new Pair<>(false, new Pair<>("", ""));
	}

	private static Pair<Boolean, Pair<String, String>> GetBluntCellValue(String fileName, int damageColumn,
			String hitLocation) throws Exception {
		if ("Neck".equals(hitLocation)) {
			return new Pair<>(true, new Pair<>(GetCellValue(fileName, 9, damageColumn), "Throat"));
		}

		if ("Forearm".equals(hitLocation) || "Elbow".equals(hitLocation)) {
			int roll = DiceRoller.roll(1, 15);

			if (roll <= 9) {
				// Upper arm
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 15, damageColumn), "Upper Arm"));
			} else {
				// Forearm
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 16, damageColumn), "Forearm"));
			}
		}

		if ("Chest".equals(hitLocation) || "Ribcage".equals(hitLocation)) {
			int roll = DiceRoller.roll(1, 3);

			if (roll == 1) {
				// Lower chest
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 12, damageColumn), "Lower Chest"));
			} else {
				// Upper chest
				return new Pair<>(true, new Pair<>(GetCellValue(fileName, 11, damageColumn), "Upper Chest"));
			}
		}
		
		if ("Hip".equals(hitLocation)) {
			return new Pair<>(true, new Pair<>(GetCellValue(fileName, 14, damageColumn), "Pelvis"));
		}

		return new Pair<>(false, new Pair<>("", ""));
	}

	private static String GetCellValue(String fileName, int rowIndex, int columnIndex) throws Exception {
		FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "/MeleeHitTables/" + fileName));
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet sheet = workbook.getSheetAt(0);

		int rowCount = sheet.getPhysicalNumberOfRows();

		for (int i = 0; i < rowCount; i++) {
			Row row = sheet.getRow(i);

			if (i == rowIndex) {
				Cell cell = row.getCell(columnIndex);
				if (cell != null) {
					cell.setCellType(CellType.STRING);
					workbook.close();
					return cell.getStringCellValue();
				}
			}
		}

		workbook.close();
		throw new Exception(
				"Cell value not found for row: " + rowIndex + ", col: " + columnIndex + ", File name: " + fileName);
	}

	public static MeleeHitLocationData GetMeleeHitLocation(MeleeDamageType damageType, int damageLevel, int zone,
			int subZoneRoll) throws Exception {
		String fileName;

		switch (damageType) {
		case CUTTING:
			fileName = "MeleeDamageTablesCutting.xlsx";
			break;
		case PEIRICNG:
			fileName = "MeleeDamageTablesPuncture.xlsx";
			break;
		case BLUNT:
			fileName = "MeleeDamageTablesBlunt.xlsx";
			break;
		default:
			throw new Exception("Damage Type not found for damage type of: " + damageType);
		}

		return GetLocationText(fileName, zone, damageLevel, subZoneRoll);

	}

	public static MeleeHitLocationData GetLocationText(String fileName, int zone, int damageLevel, int subZoneRoll)
			throws IOException {
		String zoneName = "";
		int bloodLossPD = 0;
		int shockPD = 0;
		int painPoints = 0;
		boolean knockDown = false;
		int knockDownMod = 0;

		FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "/MeleeHitTables/" + fileName));
		Workbook workbook = new XSSFWorkbook(excelFile);

		Sheet sheet = workbook.getSheetAt(0);

		int rowCount = sheet.getPhysicalNumberOfRows();

		for (int i = 0; i < rowCount; i++) {
			Row row = sheet.getRow(i);

			boolean zoneFound = false;
			Cell zoneCell = row.getCell(0);
			if (zoneCell != null && zoneCell.getCellType() == CellType.NUMERIC) {
				int zoneValue = (int) zoneCell.getNumericCellValue();
				if (zone == zoneValue) {
					zoneFound = true;
				}
			}

			if (zoneFound) {
				Row paddingRow = sheet.getRow(i + 1);

				for (int j = i + 2; j < rowCount; j++) {
					Row subZoneRow = sheet.getRow(j);
					boolean foundSubZone = false;

					Cell subZoneCell = subZoneRow.getCell(1);
					subZoneCell.setCellType(CellType.STRING);
					if (subZoneCell != null && subZoneCell.getCellType() == CellType.STRING) {
						String zones = subZoneCell.getStringCellValue();
						List<String> zoneNumbers = Arrays.asList(zones.split(";"));
						if (zoneNumbers.contains(String.valueOf(subZoneRoll))) {
							zoneName = subZoneRow.getCell(2).getStringCellValue();

							String zoneDamageLevel = subZoneRow.getCell(2 + damageLevel).getStringCellValue();
							List<String> zoneDamageLevelValues = Arrays.asList(zoneDamageLevel.split(";"));

							bloodLossPD = Integer.parseInt(zoneDamageLevelValues.get(0));
							shockPD = Integer.parseInt(zoneDamageLevelValues.get(1));
							painPoints = Integer.parseInt(zoneDamageLevelValues.get(2));

							String knockDownValue = zoneDamageLevelValues.get(3);
							knockDown = knockDownValue.equals("Y");
							knockDownMod = Integer.parseInt(zoneDamageLevelValues.get(4));

							foundSubZone = true;
						}
					}

					if (foundSubZone) {
						break;
					}
				}

				break;
			}
		}

		workbook.close();
		return new MeleeHitLocationData(zoneName, bloodLossPD, shockPD, painPoints, knockDown, knockDownMod);
	}

	public static int GetAvRow(int armorValue, String fileName) throws Exception {

		FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "/MeleeHitTables/" + fileName));
		Workbook workbook = new XSSFWorkbook(excelFile);

		Sheet sheet = workbook.getSheetAt(0);

		int rowCount = sheet.getPhysicalNumberOfRows();

		for (int i = 0; i < rowCount; i++) {
			Row row = sheet.getRow(i);

			Cell cell = row.getCell(0);
			if (cell != null && cell.getCellType() == CellType.NUMERIC) {
				int cellArmorValue = (int) cell.getNumericCellValue();
				if (armorValue >= cellArmorValue) {
					workbook.close();
					return i;
				}
			}
		}

		workbook.close();
		throw new Exception("GetAvRow for av: " + armorValue + ", File Name: " + fileName); // Indicate that the damage
																							// column is not found
	}

	public static int GetDamageColumn(int avRow, int damagePoints, String fileName) throws Exception {

		FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "/MeleeHitTables/" + fileName));
		Workbook workbook = new XSSFWorkbook(excelFile);

		Sheet sheet = workbook.getSheetAt(0);

		int rowCount = sheet.getPhysicalNumberOfRows();

		for (int i = 0; i < rowCount; i++) {
			Row row = sheet.getRow(i);

			if (i == avRow) {
				for (int j = 27; j >= 1; j--) {
					Cell cell = row.getCell(j);
					if (cell != null && cell.getCellType() == CellType.NUMERIC) {
						int damageValue = (int) cell.getNumericCellValue();
						if (damagePoints >= damageValue) {
							workbook.close();
							return j;
						}
					}

					if (j == 1) {
						workbook.close();
						return 1;
					}
				}
			}
		}

		workbook.close();
		throw new Exception("Damage column not found for av row: " + avRow + ", Damage Points: " + damagePoints
				+ ", File Name: " + fileName); // Indicate that the damage column is not found
	}

	public static Pair<Integer, Boolean> GetPdFromCell(String damageCell) {
		int physicalDamage = 0;
		boolean disabled = false;

		if (damageCell.length() == 1) {
			physicalDamage = Integer.parseInt(damageCell);
		} else if (damageCell.length() == 2) {
			if (Character.isDigit(damageCell.charAt(1))) {
				physicalDamage = Integer.parseInt(damageCell);
			} else {
				if (damageCell.charAt(1) == 'D') {
					disabled = true;
					physicalDamage = Character.getNumericValue(damageCell.charAt(0));
				} else {
					if (damageCell.charAt(1) == 'H') {
						physicalDamage = Character.getNumericValue(damageCell.charAt(0)) * 100;
					} else if (damageCell.charAt(1) == 'K') {
						physicalDamage = Character.getNumericValue(damageCell.charAt(0)) * 1000;
					} else if (damageCell.charAt(1) == 'T') {
						physicalDamage = Character.getNumericValue(damageCell.charAt(0)) * 10000;
					} else if (damageCell.charAt(1) == 'X') {
						physicalDamage = Character.getNumericValue(damageCell.charAt(0)) * 100000;
					} else if (damageCell.charAt(1) == 'M') {
						physicalDamage = Character.getNumericValue(damageCell.charAt(0)) * 1000000;
					}
				}
			}
		} else if (damageCell.length() == 3) {
			if (Character.isDigit(damageCell.charAt(1))) {
				damageCell = damageCell.substring(0, damageCell.length() - 1);
				disabled = true;
				physicalDamage = Integer.parseInt(damageCell);
			} else {
				if (damageCell.charAt(1) == 'H') {
					physicalDamage = Character.getNumericValue(damageCell.charAt(0)) * 100;
				} else if (damageCell.charAt(1) == 'K') {
					physicalDamage = Character.getNumericValue(damageCell.charAt(0)) * 1000;
				} else if (damageCell.charAt(1) == 'T') {
					physicalDamage = Character.getNumericValue(damageCell.charAt(0)) * 10000;
				} else if (damageCell.charAt(1) == 'X') {
					physicalDamage = Character.getNumericValue(damageCell.charAt(0)) * 100000;
				} else if (damageCell.charAt(1) == 'M') {
					physicalDamage = Character.getNumericValue(damageCell.charAt(0)) * 1000000;
				}
				disabled = true;
			}
		}

		return new Pair<>(physicalDamage, disabled);
	}

}
