package Melee;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import UtilityClasses.ExcelUtility;

public class MeleeHitLocation {

	public static MeleeHitLocationData GetLocationText(String fileName, int zone, int damageLevel, int subZoneRoll) {
		String zoneName = "";
		int bloodLossPD = 0;
		int shockPD = 0;
		int painPoints = 0;
		boolean knockDown = false;
		int knockDownMod = 0;

		try (Workbook workbook = new XSSFWorkbook(ExcelUtility.path+"/MeleeHitTables/"+fileName)) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new MeleeHitLocationData(zoneName, bloodLossPD, shockPD, painPoints, knockDown, knockDownMod);
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
