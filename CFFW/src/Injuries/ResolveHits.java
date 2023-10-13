package Injuries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Artillery.Artillery.Shell;
import Conflict.ConflictLog;
import Conflict.Game;
import Conflict.GameWindow;
import Conflict.OpenTrooper;
import Items.Weapons;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class ResolveHits implements Serializable {

	private Trooper trooper;
	private Unit targetUnit;
	private Unit shooterUnit;
	private int distanceCol;
	private int hits;
	public Weapons weapon;
	private Shell shell;
	private ConflictLog log = GameWindow.gameWindow != null ? GameWindow.gameWindow.conflictLog : null;
	private GameWindow gameWindow;
	private Injuries manualInjury;
	private String path = System.getProperty("user.dir") + "\\";

	public boolean calledShot;
	public boolean isHardBodyArmor;
	public ArrayList<Integer> calledShotBounds = new ArrayList<>();
	public int distanceToTarget;

	public ResolveHits(Trooper target, int distance, Shell shell, ConflictLog log, GameWindow window, Unit u) {
		this.targetUnit = u;
		this.trooper = target;
		this.distanceCol = distance;
		this.shell = shell;
		this.log = log;
		this.gameWindow = window;

	}

	public ResolveHits(Trooper target, int distanceCol, Weapons grenade, ConflictLog conflictLog, Unit targetUnit,
			Unit unit, boolean grenadeThrow, GameWindow gameWindow) {
		if (!grenadeThrow)
			return;

		this.trooper = target;
		this.hits = 1;
		this.distanceCol = distanceCol;
		this.weapon = grenade;
		this.log = conflictLog;
		this.targetUnit = targetUnit;
		this.shooterUnit = unit;
		this.gameWindow = gameWindow;

	}

	public ResolveHits(Trooper target, int incommingHits, Weapons weapon, ConflictLog log, Unit targetUnit,
			Unit shooterUnit, GameWindow gameWindow) {
		this.trooper = target;
		this.hits = incommingHits;
		this.weapon = weapon;
		this.log = log;
		this.targetUnit = targetUnit;
		this.shooterUnit = shooterUnit;
		this.gameWindow = gameWindow;
	}

	public ResolveHits(Trooper target) {

		this.trooper = target;

	}

	public ResolveHits(Trooper target, Weapons weapon) {

		this.trooper = target;
		this.weapon = weapon;

	}

	public Injuries getPCHitsManual(int pen, int dc, int of) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(path + "hittable.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			org.apache.poi.ss.usermodel.Sheet worksheet = workbook.getSheetAt(0);

			// System.out.println("PC Hits Pass");

			String targetStatus = "";

			if (of == 1) {
				targetStatus = "Fire";
			} else {
				targetStatus = "Open";
			}

			Random rand = new Random();
			int roll = rand.nextInt(100);
			// System.out.println("PC Hit Roll: "+roll);
			int hitRow = 0;
			String locationName = "";
			// System.out.println("Target Status: "+targetStatus);
			if (targetStatus.equals("Open")) {
				// System.out.println("Pass PC Hit Open");
				for (int i = 4; i < 43; i++) {

					Row row = worksheet.getRow(i);
					Double location = row.getCell(1).getNumericCellValue();

					if (roll <= location) {
						hitRow = i;
						break;
					}

				}

			} else {
				// System.out.println("Pass PC Hit Fire");
				for (int i = 4; i < 20; i++) {

					Row row = worksheet.getRow(i);
					Double location = row.getCell(0).getNumericCellValue();

					if (roll <= location) {
						hitRow = i;
						break;
					}

				}

			}

			// System.out.println("Hit Row PC: "+hitRow);
			locationName = worksheet.getRow(hitRow).getCell(2).getStringCellValue();

			if (trooper.personalShield != null) {

				boolean protectedZone = false;

				if (targetStatus.equals("Open")) {

					for (ArrayList<Integer> zone : trooper.personalShield.protectedZonesOpen) {

						if (roll >= zone.get(0) && roll <= zone.get(1)) {
							protectedZone = true;
							break;
						}

					}

				} else {

					for (ArrayList<Integer> zone : trooper.personalShield.protectedZones) {

						if (roll >= zone.get(0) && roll <= zone.get(1)) {
							protectedZone = true;
							break;
						}

					}
				}

				if (protectedZone) {
					int tempDC = dc;
					dc -= trooper.personalShield.currentShieldStrength;

					trooper.personalShield.currentShieldStrength -= tempDC;

					if (trooper.personalShield.currentShieldStrength < 0)
						trooper.personalShield.currentShieldStrength = 0;

					if (dc < 1) {

						log.addNewLine("Shot stopped by personal shields, remaining shield strength: "
								+ trooper.personalShield.currentShieldStrength);
						workbook.close();
						return null;
					}
				}

			}

			// System.out.println("dc: "+dc);
			int dcCol = 3;

			int protectionFactor = 0;
			boolean open = false;
			if (targetStatus.equals("Open")) {
				open = true;
				protectionFactor = getProtectionFactorOpen(roll, true);
			} else {
				protectionFactor = getProtectionFactor(roll, false);
			}
			// System.out.println("PF: "+protectionFactor);
			int glancingRoll = rand.nextInt(10);
			isHardBodyArmor = trooper.armor.isHardBodyArmor(roll, open);
			if (isHardBodyArmor) {
				glancingRoll--;
			}
			int glancingCol = 0;
			int glancingRow = 0;
			FileInputStream excelFile2 = new FileInputStream(new File(path + "pftable.xlsx"));
			Workbook pfWorkbook = new XSSFWorkbook(excelFile2);
			org.apache.poi.ss.usermodel.Sheet pfWorksheet = pfWorkbook.getSheetAt(0);

			for (Cell myCell : pfWorksheet.getRow(1)) {
				if (myCell.getCellType() == CellType.NUMERIC) {
					if (glancingRoll == myCell.getNumericCellValue()) {
						glancingCol = myCell.getColumnIndex();
					}
				}

			}

			// System.out.println("glancingRoll: "+glancingRoll);
			// System.out.println("glancingCol: "+glancingCol);

			for (int i = 2; i < 20; i++) {
				if (protectionFactor <= pfWorksheet.getRow(i).getCell(0).getNumericCellValue()) {
					glancingRow = i;
					break;
				}
			}
			// System.out.println("glancingRow: "+glancingRow);
			// Expected row is 7
			double ePF = pfWorksheet.getRow(glancingRow).getCell(glancingCol).getNumericCellValue();
			// System.out.println("ePF: "+ePF);
			// System.out.println("PEN: "+pen);
			double epen = pen - ePF;

			if (epen < 0.5) {
				pfWorkbook.close();
				workbook.close();
				log.addNewLineToQueue("NO PEN. Stopped by armor. EPEN: " + epen + ", Effective PF: " + ePF);
				return null;

			} else {
				log.addNewLineToQueue("INJURY, EPEN:" + epen + ", Effective PF: " + ePF);
			}

			int damageCol = 0;

			for (Cell myCell : worksheet.getRow(1)) {

				if (myCell == null || myCell.getCellType() == CellType.BLANK) {
					continue;
				}

				if (dc == (int) myCell.getNumericCellValue()) {
					dcCol = myCell.getColumnIndex();
					break;
				}
			}

			int epenCount = 0;

			if (dc > 4 && dc < 8) {
				epenCount = 5;
			} else if (dc > 8) {
				epenCount = 4;
			} else {
				epenCount = 7;
			}

			if (epen > 10)
				epen = 10;

			// System.out.println("epenCount: "+epenCount);
			// System.out.println("dcCol: "+dcCol);
			// System.out.println("Epen: "+epen);
			for (int i = 0; i < epenCount; i++) {
				// System.out.println("Epen Cell:
				// "+worksheet.getRow(3).getCell(dcCol+i).getNumericCellValue());

				if (epen <= worksheet.getRow(3).getCell(dcCol + i).getNumericCellValue()) {
					damageCol = dcCol + i;
					break;
				}

			}

			// Size 1, flat number
			// Size 2, if second char int, flat number
			// Size 2, if second char str, check str convert to flat number or set disabled
			// Size 3, get second char convert to flat number, set disabled

			// System.out.println("Hit Row: "+hitRow);
			// System.out.println("DMG Col: "+damageCol);
			Cell cell = worksheet.getRow(hitRow).getCell(damageCol);

			String damageCell = "";

			if (cell.getCellType() == CellType.STRING) {
				damageCell = worksheet.getRow(hitRow).getCell(damageCol).getStringCellValue();
			} else {
				damageCell = Integer.toString((int) worksheet.getRow(hitRow).getCell(damageCol).getNumericCellValue());
			}

			// System.out.println("damageCell: "+damageCell);

			int physicalDamage = 0;
			Boolean disabled = false;
			if (damageCell.length() == 1) {
				// System.out.println("CharAt1: "+damageCell.charAt(0));
				physicalDamage = Integer.parseInt(damageCell);
			} else if (damageCell.length() == 2) {
				// System.out.println("CharAt1: "+damageCell.charAt(0));
				// System.out.println("CharAt2: "+damageCell.charAt(1));
				if (Character.isDigit(damageCell.charAt(1))) {
					physicalDamage = Integer.parseInt(damageCell);
				} else {
					if (damageCell.charAt(1) == 'D') {
						disabled = true;
						physicalDamage = Integer.parseInt(damageCell.substring(0, 1));
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
				// System.out.println("CharAt1:
				// "+Character.getNumericValue(damageCell.charAt(0)));
				// System.out.println("CharAt2: "+damageCell.charAt(1));
				// System.out.println("CharAt3: "+damageCell.charAt(2));
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

			// System.out.println("Location: "+locationName);
			// System.out.println("physicalDamage: "+physicalDamage);
			// System.out.println("disabled: "+disabled);

			pfWorkbook.close();
			workbook.close();

			if (weapon != null && !weapon.energyWeapon) {
				int bloodLossPd = (int) getBloodLossPD(epen, dc, locationName);
				physicalDamage += bloodLossPd;
			}

			return new Injuries(physicalDamage, locationName, disabled, weapon);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public Injuries getPCHitsManual(int pen, int dc, int of, int hitLocationLower, int hitLocationUpper,
			int secondHitLocationLower, int secondHitLocationUpper, ConflictLog log) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(path + "hittable.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			org.apache.poi.ss.usermodel.Sheet worksheet = workbook.getSheetAt(0);

			// System.out.println("PC Hits Pass");

			String targetStatus = "";

			if (of == 1) {
				targetStatus = "Fire";
			} else {
				targetStatus = "Open";
			}

			Random rand = new Random();
			int roll = rand.nextInt(100);

			if (hitLocationLower > 0 && hitLocationUpper > 0 && secondHitLocationLower > 0
					&& secondHitLocationUpper > 0) {

				while (roll < hitLocationLower || roll > hitLocationUpper && roll < secondHitLocationLower
						|| roll > secondHitLocationUpper) {
					roll = rand.nextInt(100) + 1;
				}

			} else if (hitLocationLower > 0 && hitLocationUpper > 0) {
				while (roll < hitLocationLower || roll > hitLocationUpper) {
					roll = rand.nextInt(100) + 1;
				}
			}

			roll--;
			// System.out.println("PC Hit Roll: "+roll);
			int hitRow = 0;
			String locationName = "";
			// System.out.println("Target Status: "+targetStatus);
			if (targetStatus.equals("Open")) {
				// System.out.println("Pass PC Hit Open");
				for (int i = 4; i < 43; i++) {

					Row row = worksheet.getRow(i);
					Double location = row.getCell(1).getNumericCellValue();

					if (roll <= location) {
						hitRow = i;
						break;
					}

				}

			} else {
				// System.out.println("Pass PC Hit Fire");
				for (int i = 4; i < 20; i++) {

					Row row = worksheet.getRow(i);
					Double location = row.getCell(0).getNumericCellValue();

					if (roll <= location) {
						hitRow = i;
						break;
					}

				}

			}

			// System.out.println("Hit Row PC: "+hitRow);
			locationName = worksheet.getRow(hitRow).getCell(2).getStringCellValue();

			int range = GameWindow.hexDif(targetUnit, shooterUnit) * 10;
			int rangeCol = 7;

			ArrayList<Integer> ranges = new ArrayList<Integer>();
			ranges.add(10);
			ranges.add(20);
			ranges.add(40);
			ranges.add(70);
			ranges.add(100);
			ranges.add(200);
			ranges.add(300);
			ranges.add(400);

			for (int i = 0; i < ranges.size(); i++) {
				if (range <= ranges.get(i)) {
					rangeCol = i;
					break;
				}

			}

			pen = shieldTest(rangeCol, roll, pen, targetStatus);
			if (pen < 1) {
				workbook.close();
				// log.addNewLineToQueue("Damage Stopped By Shield");
				return null;
			}

			if (ionTest(rangeCol)) {
				workbook.close();
				// log.addNewLineToQueue("Ion Damage");
				return null;
			}

			// System.out.println("dc: "+dc);
			int dcCol = 3;

			int protectionFactor = 0;
			boolean open = false;
			if (targetStatus.equals("Open")) {
				open = true;
				protectionFactor = getProtectionFactorOpen(roll, true);
			} else {
				protectionFactor = getProtectionFactor(roll, false);
			}
			// System.out.println("PF: "+protectionFactor);
			int glancingRoll = rand.nextInt(10);
			isHardBodyArmor = trooper.armor.isHardBodyArmor(roll, open);
			if (isHardBodyArmor) {
				glancingRoll--;
			}
			int glancingCol = 0;
			int glancingRow = 0;
			FileInputStream excelFile2 = new FileInputStream(new File(path + "pftable.xlsx"));
			Workbook pfWorkbook = new XSSFWorkbook(excelFile2);
			org.apache.poi.ss.usermodel.Sheet pfWorksheet = pfWorkbook.getSheetAt(0);

			for (Cell myCell : pfWorksheet.getRow(1)) {
				if (myCell.getCellType() == CellType.NUMERIC) {
					if (glancingRoll == myCell.getNumericCellValue()) {
						glancingCol = myCell.getColumnIndex();
					}
				}

			}

			// System.out.println("glancingRoll: "+glancingRoll);
			// System.out.println("glancingCol: "+glancingCol);

			for (int i = 2; i < 20; i++) {
				if (protectionFactor <= pfWorksheet.getRow(i).getCell(0).getNumericCellValue()) {
					glancingRow = i;
					break;
				}
			}
			// System.out.println("glancingRow: "+glancingRow);
			// Expected row is 7
			double ePF = pfWorksheet.getRow(glancingRow).getCell(glancingCol).getNumericCellValue();
			if (protectionFactor < 1)
				ePF = 0;
			// System.out.println("ePF: "+ePF);
			// System.out.println("PEN: "+pen);

			double epen = pen - ePF;

			if (epen < 0.5) {
				pfWorkbook.close();
				workbook.close();
				log.addNewLineToQueue("NO PEN. Stopped by armor. EPEN: " + epen + ", Effective PF: " + ePF);
				return null;

			} else {
				log.addNewLineToQueue("INJURY, EPEN:" + epen + ", Effective PF: " + ePF);
			}

			int damageCol = 0;

			for (Cell myCell : worksheet.getRow(1)) {

				if (myCell == null || myCell.getCellType() == CellType.BLANK) {
					continue;
				}

				if (dc == (int) myCell.getNumericCellValue()) {
					dcCol = myCell.getColumnIndex();
					break;
				}
			}

			int epenCount = 0;

			if (dc > 4 && dc < 8) {
				epenCount = 5;
			} else if (dc > 8) {
				epenCount = 4;
			} else {
				epenCount = 7;
			}

			if (epen > 10)
				epen = 10;

			// System.out.println("epenCount: "+epenCount);
			// System.out.println("dcCol: "+dcCol);
			// System.out.println("Epen: "+epen);
			for (int i = 0; i < epenCount; i++) {
				// System.out.println("Epen Cell:
				// "+worksheet.getRow(3).getCell(dcCol+i).getNumericCellValue());

				if (epen <= worksheet.getRow(3).getCell(dcCol + i).getNumericCellValue()) {
					damageCol = dcCol + i;
					break;
				}

			}

			// Size 1, flat number
			// Size 2, if second char int, flat number
			// Size 2, if second char str, check str convert to flat number or set disabled
			// Size 3, get second char convert to flat number, set disabled

			// System.out.println("Hit Row: "+hitRow);
			// System.out.println("DMG Col: "+damageCol);
			Cell cell = worksheet.getRow(hitRow).getCell(damageCol);

			String damageCell = "";

			if (cell.getCellType() == CellType.STRING) {
				damageCell = worksheet.getRow(hitRow).getCell(damageCol).getStringCellValue();
			} else {
				damageCell = Integer.toString((int) worksheet.getRow(hitRow).getCell(damageCol).getNumericCellValue());
			}

			// System.out.println("damageCell: "+damageCell);

			int physicalDamage = 0;
			Boolean disabled = false;
			if (damageCell.length() == 1) {
				// System.out.println("CharAt1: "+damageCell.charAt(0));
				physicalDamage = Integer.parseInt(damageCell);
			} else if (damageCell.length() == 2) {
				// System.out.println("CharAt1: "+damageCell.charAt(0));
				// System.out.println("CharAt2: "+damageCell.charAt(1));
				if (Character.isDigit(damageCell.charAt(1))) {
					physicalDamage = Integer.parseInt(damageCell);
				} else {
					if (damageCell.charAt(1) == 'D') {
						disabled = true;
						physicalDamage = Integer.parseInt(damageCell.substring(0, 1));
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
				// System.out.println("CharAt1:
				// "+Character.getNumericValue(damageCell.charAt(0)));
				// System.out.println("CharAt2: "+damageCell.charAt(1));
				// System.out.println("CharAt3: "+damageCell.charAt(2));
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

			// System.out.println("Location: "+locationName);
			// System.out.println("physicalDamage: "+physicalDamage);
			// System.out.println("disabled: "+disabled);

			pfWorkbook.close();
			workbook.close();

			if (!weapon.energyWeapon) {
				int bloodLossPd = (int) getBloodLossPD(epen, dc, locationName);
				physicalDamage += bloodLossPd;
			}

			dc10KOTest(dc, locationName);
			return new Injuries(physicalDamage, locationName, disabled, weapon);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public void performCalculationsArtillery(Game game) {

		int pen = shell.pen.get(distanceCol);
		int dc = shell.dc.get(distanceCol);
		int bc = shell.bc.get(distanceCol);
		String bshc = shell.bshc.get(distanceCol);

		log.addNewLine("\nHit Concussive Damage, " + trooper.number + " " + trooper.name + ", PD: " + bc);
		Injuries concussion = new Injuries(shell.bc.get(distanceCol), "Concussion", false, null);

		if (trooper.personalShield != null) {

			// Full body shield
			if (trooper.personalShield.protectedZones.get(0).get(0) == 0
					&& trooper.personalShield.protectedZones.get(0).get(1) == 100) {
				concussion = null;
			} else {

				trooper.personalShield.currentShieldStrength -= concussion.pd / 100;
				if (trooper.personalShield.currentShieldStrength < 0)
					trooper.personalShield.currentShieldStrength = 0;

			}

		}

		if (concussion != null) {
			trooper.injured(log, concussion, game, gameWindow);
			trooper.calculateInjury(gameWindow, log);
		}

		int hits = 0;

		if (bshc.charAt(0) == '*') {
			hits += Integer.parseInt(bshc.substring(1));
		} else {
			Random rand = new Random();
			int roll = rand.nextInt(100) + 1;
			if (roll <= Integer.parseInt(bshc)) {
				hits++;
			} else {
				log.addNewLine("No shrapnel hit");
			}

		}

		if (hits == 0) {
			return;
		}

		for (int i = 0; i < hits; i++) {

			// Gets shield loss
			if (trooper.currentShields > 0) {
				Random rand = new Random();
				int roll = rand.nextInt(100) + 1;

				if (roll <= trooper.shieldChance) {
					if (pen > trooper.currentShields) {
						pen -= trooper.currentShields;
						trooper.currentShields = 0;

					} else {
						trooper.currentShields -= pen;
						pen = 0;
					}

				}

			}

			if (pen > 0) {

				// Applies hit from trooper
				log.addNewLine("\n Shrap Hit, " + trooper.number + " " + trooper.name);
				Injuries injury = getPCHitsArtillery(pen, dc);
				if (injury == null)
					continue;

				if (injury.pd > 0) {
					trooper.injured(log, injury, game, gameWindow);
					trooper.calculateInjury(gameWindow, log);

					// Gets status, and puts status in log line
					log.addToLine(", Number: " + trooper.number + "; Current PD: " + 0 + "; Additional PD: " + injury.pd
							+ ", Location: " + injury.location + ", Disabled: " + injury.disabled);
				} else {
					log.addToLine(", stopped by armor");
				}

			} else {
				log.addNewLine("\n Shrap Hit, " + trooper.name + ", Number: " + trooper.number + "; Current Sheilds: "
						+ trooper.currentShields);

			}

		}

	}

	public void performCalculationsLauncher(Game game) {

		int pen = weapon.pen.get(distanceCol);
		int dc = weapon.dc.get(distanceCol);
		int bc = weapon.bc.get(distanceCol);
		String bshc = weapon.bshc.get(distanceCol);

		log.addNewLine("\nHit Concussive Damage, " + trooper.number + " " + trooper.name + ", PD: " + bc);
		Injuries concussion = new Injuries(weapon.bc.get(distanceCol), "Concussion", false, weapon);

		if (trooper.personalShield != null) {

			// Full body shield
			if (trooper.personalShield.protectedZones.get(0).get(0) == 0
					&& trooper.personalShield.protectedZones.get(0).get(1) == 100) {
				concussion = null;
			} else {

				trooper.personalShield.currentShieldStrength -= concussion.pd / 100;
				if (trooper.personalShield.currentShieldStrength < 0)
					trooper.personalShield.currentShieldStrength = 0;

			}

		}

		if (concussion != null) {
			trooper.injured(log, concussion, game, gameWindow);
			trooper.calculateInjury(gameWindow, log);
		}

		if (weapon.ionWeapon)
			ionDamage = weapon.ionDamage.get(distanceCol);

		if (ionTest(distanceCol)) {
			return;
		}

		int hits = 0;

		if (bshc.charAt(0) == '*') {
			hits += Integer.parseInt(bshc.substring(1));
		} else {
			Random rand = new Random();
			int roll = rand.nextInt(100) + 1;
			if (roll <= Integer.parseInt(bshc)) {
				hits++;
			} else {
				log.addNewLine("No shrapnel hit");
			}

		}

		if (hits == 0) {
			return;
		}

		for (int i = 0; i < hits; i++) {

			// Gets shield loss
			if (trooper.currentShields > 0) {
				Random rand = new Random();
				int roll = rand.nextInt(100) + 1;

				if (roll <= trooper.shieldChance) {
					if (pen > trooper.currentShields) {
						pen -= trooper.currentShields;
						trooper.currentShields = 0;

					} else {
						trooper.currentShields -= pen;
						pen = 0;
					}

				}

			}

			if (pen > 0) {

				// Applies hit from trooper
				log.addNewLine("\n Shrap Hit, " + trooper.number + " " + trooper.name);
				Injuries injury = getPCHitsGrenade(pen, dc, distanceCol);
				if (injury == null)
					continue;

				if (injury.pd > 0) {
					trooper.injured(log, injury, game, gameWindow);
					trooper.calculateInjury(gameWindow, log);

					// Gets status, and puts status in log line
					log.addToLine(", Number: " + trooper.number + "; Current PD: " + 0 + "; Additional PD: " + injury.pd
							+ ", Location: " + injury.location + ", Disabled: " + injury.disabled);
				} else {
					log.addToLine(", stopped by armor");
				}

			} else {
				log.addNewLine("\n Shrap Hit, " + trooper.name + ", Number: " + trooper.number + "; Current Sheilds: "
						+ trooper.currentShields);

			}

		}

	}

	public void performCalculationsGrenade(Game game) {

		log.addNewLine("\nHit Concussive Damage, " + trooper.number + " " + trooper.name + ", PD: "
				+ weapon.bc.get(distanceCol));
		Injuries concussion = new Injuries(weapon.bc.get(distanceCol), "Concussion", false, weapon);

		if (trooper.personalShield != null) {

			// Full body shield
			if (trooper.personalShield.protectedZones.get(0).get(0) == 0
					&& trooper.personalShield.protectedZones.get(0).get(1) == 100) {
				concussion = null;
			} else {

				trooper.personalShield.currentShieldStrength -= concussion.pd / 100;
				if (trooper.personalShield.currentShieldStrength < 0)
					trooper.personalShield.currentShieldStrength = 0;

			}

		}

		if (concussion != null) {
			trooper.injured(log, concussion, game, gameWindow);
			trooper.calculateInjury(gameWindow, log);
		}

		if (weapon.ionWeapon)
			ionDamage = weapon.ionDamage.get(distanceCol);

		if (ionTest(distanceCol)) {
			return;
		}

		int pen = weapon.pen.get(distanceCol);

		int hits = 0;

		String bshc = weapon.bshc.get(distanceCol);

		if (bshc.charAt(0) == '*') {
			hits += Integer.parseInt(bshc.substring(1));
		} else {
			Random rand = new Random();
			int roll = rand.nextInt(100) + 1;
			if (roll <= Integer.parseInt(bshc)) {
				hits++;
			} else {
				log.addNewLine("No shrapnel hit");
			}

		}

		if (hits == 0) {
			return;
		}

		for (int i = 0; i < hits; i++) {

			// Gets shield loss
			if (trooper.currentShields > 0) {
				Random rand = new Random();
				int roll = rand.nextInt(100) + 1;

				if (roll <= trooper.shieldChance) {
					if (pen > trooper.currentShields) {
						pen -= trooper.currentShields;
						trooper.currentShields = 0;

					} else {
						trooper.currentShields -= pen;
						pen = 0;
					}

				}

			}

			if (pen > 0) {

				int dc = weapon.dc.get(distanceCol);

				// Applies hit from trooper
				log.addNewLine("\n Shrap Hit, " + trooper.number + " " + trooper.name);
				Injuries injury = getPCHitsGrenade(pen, dc, distanceCol);
				if (injury == null)
					continue;

				if (injury.pd > 0) {
					trooper.injured(log, injury, game, gameWindow);
					trooper.calculateInjury(gameWindow, log);

					// Gets status, and puts status in log line
					log.addToLine(", Number: " + trooper.number + "; Current PD: " + 0 + "; Additional PD: " + injury.pd
							+ ", Location: " + injury.location + ", Disabled: " + injury.disabled);
				} else {
					log.addToLine(", stopped by armor");
				}

			} else {
				log.addNewLine("\n Shrap Hit, " + trooper.name + ", Number: " + trooper.number + "; Current Sheilds: "
						+ trooper.currentShields);

			}

		}

	}

	public void performCalculations(Game game, ConflictLog log) {

		// System.out.println("Performed Calculations");

		log.addNewLineToQueue(
				"Resolving " + hits + " hits on " + targetUnit.callsign + ":: " + trooper.number + " " + trooper.name);

		for (int i = 0; i < hits; i++) {
			log.addNewLineToQueue("Hit: " + (i + 1));

			int range;
			if (targetUnit == null || shooterUnit == null) {
				range = distanceToTarget; 
			} else {
				range = GameWindow.hexDif(targetUnit, shooterUnit) * 10;
			}

			// System.out.println("range: "+range);

			int rangeCol = 7;

			ArrayList<Integer> ranges = new ArrayList<Integer>();
			ranges.add(10);
			ranges.add(20);
			ranges.add(40);
			ranges.add(70);
			ranges.add(100);
			ranges.add(200);
			ranges.add(300);
			ranges.add(400);

			for (int x = 0; x < ranges.size(); x++) {

				if (range <= ranges.get(x)) {
					rangeCol = x;
					break;
				}

			}

			int pen = weapon.pen.get(rangeCol);
			int dc = weapon.dc.get(rangeCol);

			//if (!weapon.energyWeapon && dc < 10 && isHardBodyArmor) {
			//	dc++;
			//}

			if (pen > 0 && dc > 0) {

				log.addNewLineToQueue("PEN:" + pen + ", DC: " + dc);

				// Applies hit from trooper
				log.addNewLineToQueue("\nHit, " + trooper.name);
				Injuries injury = null;
				int of = 0;

				if (trooper.inCover || trooper.stance.equals("Prone"))
					of = 1;

				if (calledShot) {
					// System.out.println("PC Hits Manual");
					injury = getPCHitsManual(pen, dc, of, calledShotBounds.get(0), calledShotBounds.get(1),
							calledShotBounds.get(2), calledShotBounds.get(3), log);
				} else {
					// System.out.println("PC Hits");
					injury = getPCHits(pen);
				}

				if (injury != null && injury.pd > 0) {

					// System.out.println("Injury");

					if (calledShot)
						log.addToLineInQueue(", Current PD: " + trooper.physicalDamage + "; Additional PD: " + injury.pd
								+ ", Location: " + injury.location + ", Disabled: " + injury.disabled + " Called Shot: "
								+ calledShotBounds.get(0) + ", " + calledShotBounds.get(1) + ", "
								+ calledShotBounds.get(2) + ", " + calledShotBounds.get(3));
					else
						log.addToLineInQueue(", Current PD: " + trooper.physicalDamage + "; Additional PD: " + injury.pd
								+ ", Location: " + injury.location + ", Disabled: " + injury.disabled);

					trooper.injured(log, injury, game, gameWindow);
					trooper.calculateInjury(gameWindow, log);

					// Gets status, and puts status in log line

				} else if (injury != null && injury.pd <= 0) {
					log.addToLineInQueue(", PD Zero or less");
				}

			} else {
				log.addNewLineToQueue("No damage, DC and/or PEN less than 0");

			}
			
		}

	}

	public Injuries getPCHits(int pen) {

		try {

			// System.out.println("Pass 0");

			FileInputStream excelFile = new FileInputStream(new File(path + "hittable.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			org.apache.poi.ss.usermodel.Sheet worksheet = workbook.getSheetAt(0);

			// System.out.println("PC Hits Pass");

			// System.out.println("PC Target Concealment: "+targetUnit.concealment);
			String targetStatus = "";

			if (trooper.inCover || trooper.stance.equals("Prone")) {
				targetStatus = "Fire";
			} else {
				targetStatus = "Open";
			}

			Random rand = new Random();
			int roll = rand.nextInt(100);
			// System.out.println("PC Hit Roll: "+roll);
			int hitRow = 0;
			String locationName = "";
			// System.out.println("Target Status: "+targetStatus);
			if (targetStatus.equals("Open")) {
				// System.out.println("Pass PC Hit Open");
				for (int i = 4; i < 43; i++) {

					Row row = worksheet.getRow(i);
					Double location = row.getCell(1).getNumericCellValue();

					if (roll <= location) {
						hitRow = i;
						break;
					}

				}

			} else {
				// System.out.println("Pass PC Hit Fire");
				for (int i = 4; i < 20; i++) {

					Row row = worksheet.getRow(i);
					Double location = row.getCell(0).getNumericCellValue();

					if (roll <= location) {
						hitRow = i;
						break;
					}

				}

			}

			// System.out.println("Pass 1");

			// System.out.println("Hit Row PC: "+hitRow);
			locationName = worksheet.getRow(hitRow).getCell(2).getStringCellValue();

			int range;
			if(targetUnit == null || shooterUnit == null) {
				range = distanceToTarget;
			} else {
				range = GameWindow.hexDif(targetUnit, shooterUnit) * 10;
			}
			
			
			//System.out.println("Range: "+range);
			int rangeCol = 7;

			ArrayList<Integer> ranges = new ArrayList<Integer>();
			ranges.add(10);
			ranges.add(20);
			ranges.add(40);
			ranges.add(70);
			ranges.add(100);
			ranges.add(200);
			ranges.add(300);
			ranges.add(400);

			for (int i = 0; i < ranges.size(); i++) {
				if (range <= ranges.get(i)) {
					rangeCol = i;
					break;
				}

			}

			int dc = weapon.dc.get(rangeCol);

			pen = shieldTest(rangeCol, roll, pen, targetStatus);
			if (pen < 1) {
				workbook.close();
				// System.out.println("Shield return");
				return null;
			}

			if (ionTest(rangeCol)) {
				workbook.close();
				System.out.println("Ion return");
				return null;
			}

			// System.out.println("Pass 1-1");

			// System.out.println("dc: "+dc);
			int dcCol = 3;

			int protectionFactor = 0;
			boolean open = false;
			if (targetStatus.equals("Open")) {
				// System.out.println("Pass PF 1");
				open = true;
				protectionFactor = getProtectionFactorOpen(roll, true);
			} else {
				// System.out.println("Pass PF 2");
				protectionFactor = getProtectionFactor(roll, false);
			}
			// System.out.println("PF: "+protectionFactor);
			int glancingRoll = rand.nextInt(10);
			isHardBodyArmor = trooper.armor.isHardBodyArmor(roll, open);
			if (isHardBodyArmor) {
				glancingRoll--;
			}
			int glancingCol = 0;
			int glancingRow = 0;
			FileInputStream excelFile2 = new FileInputStream(new File(path + "pftable.xlsx"));
			Workbook pfWorkbook = new XSSFWorkbook(excelFile2);
			org.apache.poi.ss.usermodel.Sheet pfWorksheet = pfWorkbook.getSheetAt(0);

			for (Cell myCell : pfWorksheet.getRow(1)) {
				if (myCell.getCellType() == CellType.NUMERIC) {
					if (glancingRoll == myCell.getNumericCellValue()) {
						glancingCol = myCell.getColumnIndex();
					}
				}

			}

			// System.out.println("Pass 2");

			// System.out.println("glancingRoll: "+glancingRoll);
			// System.out.println("glancingCol: "+glancingCol);

			for (int i = 2; i < 20; i++) {
				if (protectionFactor <= pfWorksheet.getRow(i).getCell(0).getNumericCellValue()) {
					glancingRow = i;
					break;
				}
			}
			// System.out.println("glancingRow: "+glancingRow);
			// Expected row is 7
			double ePF = pfWorksheet.getRow(glancingRow).getCell(glancingCol).getNumericCellValue();
			if (protectionFactor < 1)
				ePF = 0;

			// System.out.println("ePF: "+ePF);
			double epen = pen - ePF;

			// System.out.println("Pass 3");

			if (epen < 0.5) {
				pfWorkbook.close();
				workbook.close();
				// System.out.println("No Pen");
				log.addNewLineToQueue("NO PEN. Stopped by armor. EPEN: " + epen + ", Effective PF: " + ePF);
				return null;

			} else {
				// System.out.println("Injury");
				log.addNewLineToQueue("INJURY, EPEN:" + epen + ", Effective PF: " + ePF);
			}

			int damageCol = 0;

			for (Cell myCell : worksheet.getRow(1)) {

				if (myCell == null || myCell.getCellType() == CellType.BLANK) {
					continue;
				}

				if (dc == (int) myCell.getNumericCellValue()) {
					dcCol = myCell.getColumnIndex();
					break;
				}
			}

			int epenCount = 0;

			if (dc > 4 && dc < 8) {
				epenCount = 5;
			} else if (dc > 8) {
				epenCount = 4;
			} else {
				epenCount = 7;
			}

			if (epen > 10)
				epen = 10;

			// System.out.println("epenCount: "+epenCount);
			// System.out.println("dcCol: "+dcCol);
			// System.out.println("Epen: "+epen);
			for (int i = 0; i < epenCount; i++) {
				// System.out.println("Epen Cell:
				// "+worksheet.getRow(3).getCell(dcCol+i).getNumericCellValue());

				if (epen <= worksheet.getRow(3).getCell(dcCol + i).getNumericCellValue()) {
					damageCol = dcCol + i;
					break;
				}

			}

			// Size 1, flat number
			// Size 2, if second char int, flat number
			// Size 2, if second char str, check str convert to flat number or set disabled
			// Size 3, get second char convert to flat number, set disabled

			// System.out.println("Hit Row: "+hitRow);
			// System.out.println("DMG Col: "+damageCol);
			Cell cell = worksheet.getRow(hitRow).getCell(damageCol);

			String damageCell = "";

			if (cell.getCellType() == CellType.STRING) {
				damageCell = worksheet.getRow(hitRow).getCell(damageCol).getStringCellValue();
			} else {
				damageCell = Integer.toString((int) worksheet.getRow(hitRow).getCell(damageCol).getNumericCellValue());
			}

			// System.out.println("damageCell: "+damageCell);

			int physicalDamage = 0;
			Boolean disabled = false;
			if (damageCell.length() == 1) {
				// System.out.println("CharAt1: "+damageCell.charAt(0));
				physicalDamage = Integer.parseInt(damageCell);
			} else if (damageCell.length() == 2) {
				// System.out.println("CharAt1: "+damageCell.charAt(0));
				// System.out.println("CharAt2: "+damageCell.charAt(1));
				if (Character.isDigit(damageCell.charAt(1))) {
					physicalDamage = Integer.parseInt(damageCell);
				} else {
					if (damageCell.charAt(1) == 'D') {
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
					}

				}

			} else if (damageCell.length() == 3) {
				// System.out.println("CharAt1:
				// "+Character.getNumericValue(damageCell.charAt(0)));
				// System.out.println("CharAt2: "+damageCell.charAt(1));
				// System.out.println("CharAt3: "+damageCell.charAt(2));
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

			// System.out.println("Location: "+locationName);
			// System.out.println("physicalDamage: "+physicalDamage);
			// System.out.println("disabled: "+disabled);

			excelFile.close();
			excelFile2.close();
			pfWorkbook.close();
			workbook.close();

			if (!weapon.energyWeapon) {
				int bloodLossPd = (int) getBloodLossPD(epen, dc, locationName);
				physicalDamage += bloodLossPd;
			}

			dc10KOTest(dc, locationName);
			return new Injuries(physicalDamage, locationName, disabled, weapon);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public void dc10KOTest(int dc, String location) {
		if (dc == 10 && !location.toLowerCase().contains("glance") && !location.toLowerCase().contains("head")) {
			int dc10Roll = DiceRoller.roll(0, 99);
			if (dc10Roll <= 60) {
				trooper.conscious = false;
			}
			String rslts = "DC 10 KO Test, TN 60, Roll: " + dc10Roll;
			if (dc10Roll <= 60)
				rslts += "::" + "unconscious".toUpperCase();
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(rslts + "\n");
		}
	}

	public Injuries getPCHitsArtillery(int pen, int dc) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(path + "hittable.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			org.apache.poi.ss.usermodel.Sheet worksheet = workbook.getSheetAt(0);

			// System.out.println("PC Hits Pass");

			// System.out.println("PC Target Concealment: "+targetUnit.concealment);
			String targetStatus = "";

			if (!targetUnit.concealment.equals("No Concealment ") && !targetUnit.concealment.equals("Level 1")) {
				targetStatus = "Fire";
			} else {
				targetStatus = "Open";
			}

			Random rand = new Random();
			int roll = rand.nextInt(100);
			// System.out.println("PC Hit Roll: "+roll);
			int hitRow = 0;
			String locationName = "";
			// System.out.println("Target Status: "+targetStatus);
			if (targetStatus.equals("Open")) {
				// System.out.println("Pass PC Hit Open");
				for (int i = 4; i < 43; i++) {

					Row row = worksheet.getRow(i);
					Double location = row.getCell(1).getNumericCellValue();

					if (roll <= location) {
						hitRow = i;
						break;
					}

				}

			} else {
				// System.out.println("Pass PC Hit Fire");
				for (int i = 4; i < 20; i++) {

					Row row = worksheet.getRow(i);
					Double location = row.getCell(0).getNumericCellValue();

					if (roll <= location) {
						hitRow = i;
						break;
					}

				}

			}

			// System.out.println("Hit Row PC: "+hitRow);
			locationName = worksheet.getRow(hitRow).getCell(2).getStringCellValue();

			// System.out.println("dc: "+dc);
			int dcCol = 3;

			int protectionFactor = 0;
			boolean open = false;
			if (targetStatus.equals("Open")) {
				open = true;
				protectionFactor = getProtectionFactorOpen(roll, true);
			} else {
				protectionFactor = getProtectionFactor(roll, false);
			}
			// System.out.println("PF: "+protectionFactor);
			int glancingRoll = rand.nextInt(10);
			isHardBodyArmor = trooper.armor.isHardBodyArmor(roll, open);
			if (isHardBodyArmor) {
				glancingRoll--;
			}
			int glancingCol = 0;
			int glancingRow = 0;
			FileInputStream excelFile2 = new FileInputStream(new File(path + "pftable.xlsx"));
			Workbook pfWorkbook = new XSSFWorkbook(excelFile2);
			org.apache.poi.ss.usermodel.Sheet pfWorksheet = pfWorkbook.getSheetAt(0);

			for (Cell myCell : pfWorksheet.getRow(1)) {
				if (myCell.getCellType() == CellType.NUMERIC) {
					if (glancingRoll == myCell.getNumericCellValue()) {
						glancingCol = myCell.getColumnIndex();
					}
				}

			}

			// System.out.println("glancingRoll: "+glancingRoll);
			// System.out.println("glancingCol: "+glancingCol);

			for (int i = 2; i < 20; i++) {
				if (protectionFactor <= pfWorksheet.getRow(i).getCell(0).getNumericCellValue()) {
					glancingRow = i;
					break;
				}
			}
			// System.out.println("glancingRow: "+glancingRow);
			// Expected row is 7
			double ePF = pfWorksheet.getRow(glancingRow).getCell(glancingCol).getNumericCellValue();
			// System.out.println("ePF: "+ePF);
			double epen = pen - ePF;

			if (epen < 0.5) {
				pfWorkbook.close();
				workbook.close();
				return new Injuries(0, locationName, false, weapon);

			}

			int damageCol = 0;

			for (Cell myCell : worksheet.getRow(1)) {

				if (myCell == null || myCell.getCellType() == CellType.BLANK) {
					continue;
				}

				if (dc == (int) myCell.getNumericCellValue()) {
					dcCol = myCell.getColumnIndex();
					break;
				}
			}

			int epenCount = 0;

			if (dc > 4 && dc < 8) {
				epenCount = 5;
			} else if (dc > 8) {
				epenCount = 4;
			} else {
				epenCount = 7;
			}

			if (epen > 10)
				epen = 10;

			// System.out.println("epenCount: "+epenCount);
			// System.out.println("dcCol: "+dcCol);
			// System.out.println("Epen: "+epen);
			for (int i = 0; i < epenCount; i++) {
				// System.out.println("Epen Cell:
				// "+worksheet.getRow(3).getCell(dcCol+i).getNumericCellValue());

				if (epen <= worksheet.getRow(3).getCell(dcCol + i).getNumericCellValue()) {
					damageCol = dcCol + i;
					break;
				}

			}

			// Size 1, flat number
			// Size 2, if second char int, flat number
			// Size 2, if second char str, check str convert to flat number or set disabled
			// Size 3, get second char convert to flat number, set disabled

			// System.out.println("Hit Row: "+hitRow);
			// System.out.println("DMG Col: "+damageCol);
			Cell cell = worksheet.getRow(hitRow).getCell(damageCol);

			String damageCell = "";

			if (cell.getCellType() == CellType.STRING) {
				damageCell = worksheet.getRow(hitRow).getCell(damageCol).getStringCellValue();
			} else {
				damageCell = Integer.toString((int) worksheet.getRow(hitRow).getCell(damageCol).getNumericCellValue());
			}

			// System.out.println("damageCell: "+damageCell);

			int physicalDamage = 0;
			Boolean disabled = false;
			if (damageCell.length() == 1) {
				// System.out.println("CharAt1: "+damageCell.charAt(0));
				physicalDamage = Integer.parseInt(damageCell);
			} else if (damageCell.length() == 2) {
				// System.out.println("CharAt1: "+damageCell.charAt(0));
				// System.out.println("CharAt2: "+damageCell.charAt(1));
				if (Character.isDigit(damageCell.charAt(1))) {
					physicalDamage = Integer.parseInt(damageCell);
				} else {
					if (damageCell.charAt(1) == 'D') {
						disabled = true;
						physicalDamage = Integer.parseInt(damageCell.substring(0, 1));
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
				// System.out.println("CharAt1:
				// "+Character.getNumericValue(damageCell.charAt(0)));
				// System.out.println("CharAt2: "+damageCell.charAt(1));
				// System.out.println("CharAt3: "+damageCell.charAt(2));
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

			// System.out.println("Location: "+locationName);
			// System.out.println("physicalDamage: "+physicalDamage);
			// System.out.println("disabled: "+disabled);

			pfWorkbook.close();
			workbook.close();

			physicalDamage += (int) getBloodLossPD(epen, dc, locationName);

			return new Injuries(physicalDamage, locationName, disabled, weapon);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	// Works for grenade and launcher
	public Injuries getPCHitsGrenade(int pen, int dc, int rangeCol) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(path + "hittable.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			org.apache.poi.ss.usermodel.Sheet worksheet = workbook.getSheetAt(0);

			// System.out.println("PC Hits Pass");

			// System.out.println("PC Target Concealment: "+targetUnit.concealment);
			String targetStatus = "";

			if (!targetUnit.concealment.equals("No Concealment ") && !targetUnit.concealment.equals("Level 1")) {
				targetStatus = "Fire";
			} else {
				targetStatus = "Open";
			}

			Random rand = new Random();
			int roll = rand.nextInt(100);
			// System.out.println("PC Hit Roll: "+roll);
			int hitRow = 0;
			String locationName = "";
			// System.out.println("Target Status: "+targetStatus);
			if (targetStatus.equals("Open")) {
				// System.out.println("Pass PC Hit Open");
				for (int i = 4; i < 43; i++) {

					Row row = worksheet.getRow(i);
					Double location = row.getCell(1).getNumericCellValue();

					if (roll <= location) {
						hitRow = i;
						break;
					}

				}

			} else {
				// System.out.println("Pass PC Hit Fire");
				for (int i = 4; i < 20; i++) {

					Row row = worksheet.getRow(i);
					Double location = row.getCell(0).getNumericCellValue();

					if (roll <= location) {
						hitRow = i;
						break;
					}

				}

			}

			// System.out.println("Hit Row PC: "+hitRow);
			locationName = worksheet.getRow(hitRow).getCell(2).getStringCellValue();
			pen = shieldTest(rangeCol, roll, pen, targetStatus);

			if (pen < 1) {
				workbook.close();
				return null;
			}

			// System.out.println("dc: "+dc);
			int dcCol = 3;

			int protectionFactor = 0;
			boolean open = false;
			if (targetStatus.equals("Open")) {
				open = true;
				protectionFactor = getProtectionFactorOpen(roll, true);
			} else {
				protectionFactor = getProtectionFactor(roll, false);
			}

			// System.out.println("PF: "+protectionFactor);
			int glancingRoll = rand.nextInt(10);
			isHardBodyArmor = trooper.armor.isHardBodyArmor(roll, open);
			if (isHardBodyArmor) {
				glancingRoll--;
			}
			int glancingCol = 0;
			int glancingRow = 0;
			FileInputStream excelFile2 = new FileInputStream(new File(path + "pftable.xlsx"));
			Workbook pfWorkbook = new XSSFWorkbook(excelFile2);
			org.apache.poi.ss.usermodel.Sheet pfWorksheet = pfWorkbook.getSheetAt(0);

			for (Cell myCell : pfWorksheet.getRow(1)) {
				if (myCell.getCellType() == CellType.NUMERIC) {
					if (glancingRoll == myCell.getNumericCellValue()) {
						glancingCol = myCell.getColumnIndex();
					}
				}

			}

			// System.out.println("glancingRoll: "+glancingRoll);
			// System.out.println("glancingCol: "+glancingCol);

			for (int i = 2; i < 20; i++) {
				if (protectionFactor <= pfWorksheet.getRow(i).getCell(0).getNumericCellValue()) {
					glancingRow = i;
					break;
				}
			}
			// System.out.println("glancingRow: "+glancingRow);
			// Expected row is 7
			double ePF = pfWorksheet.getRow(glancingRow).getCell(glancingCol).getNumericCellValue();
			if (protectionFactor < 1)
				ePF = 0;
			// System.out.println("ePF: "+ePF);
			double epen = pen - ePF;

			if (epen < 0.5) {
				pfWorkbook.close();
				workbook.close();
				return new Injuries(0, locationName, false, weapon);

			}

			int damageCol = 0;

			for (Cell myCell : worksheet.getRow(1)) {

				if (myCell == null || myCell.getCellType() == CellType.BLANK) {
					continue;
				}

				if (dc == (int) myCell.getNumericCellValue()) {
					dcCol = myCell.getColumnIndex();
					break;
				}
			}

			int epenCount = 0;

			if (dc > 4 && dc < 8) {
				epenCount = 5;
			} else if (dc > 8) {
				epenCount = 4;
			} else {
				epenCount = 7;
			}

			if (epen > 10)
				epen = 10;

			// System.out.println("epenCount: "+epenCount);
			// System.out.println("dcCol: "+dcCol);
			// System.out.println("Epen: "+epen);
			for (int i = 0; i < epenCount; i++) {
				// System.out.println("Epen Cell:
				// "+worksheet.getRow(3).getCell(dcCol+i).getNumericCellValue());

				if (epen <= worksheet.getRow(3).getCell(dcCol + i).getNumericCellValue()) {
					damageCol = dcCol + i;
					break;
				}

			}

			// Size 1, flat number
			// Size 2, if second char int, flat number
			// Size 2, if second char str, check str convert to flat number or set disabled
			// Size 3, get second char convert to flat number, set disabled

			// System.out.println("Hit Row: "+hitRow);
			// System.out.println("DMG Col: "+damageCol);
			Cell cell = worksheet.getRow(hitRow).getCell(damageCol);

			String damageCell = "";

			if (cell.getCellType() == CellType.STRING) {
				damageCell = worksheet.getRow(hitRow).getCell(damageCol).getStringCellValue();
			} else {
				damageCell = Integer.toString((int) worksheet.getRow(hitRow).getCell(damageCol).getNumericCellValue());
			}

			// System.out.println("damageCell: "+damageCell);

			int physicalDamage = 0;
			Boolean disabled = false;
			if (damageCell.length() == 1) {
				// System.out.println("CharAt1: "+damageCell.charAt(0));
				physicalDamage = Integer.parseInt(damageCell);
			} else if (damageCell.length() == 2) {
				// System.out.println("CharAt1: "+damageCell.charAt(0));
				// System.out.println("CharAt2: "+damageCell.charAt(1));
				if (Character.isDigit(damageCell.charAt(1))) {
					physicalDamage = Integer.parseInt(damageCell);
				} else {
					if (damageCell.charAt(1) == 'D') {
						disabled = true;
						physicalDamage = Integer.parseInt(damageCell.substring(0, 1));
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
				// System.out.println("CharAt1:
				// "+Character.getNumericValue(damageCell.charAt(0)));
				// System.out.println("CharAt2: "+damageCell.charAt(1));
				// System.out.println("CharAt3: "+damageCell.charAt(2));
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

			// System.out.println("Location: "+locationName);
			// System.out.println("physicalDamage: "+physicalDamage);
			// System.out.println("disabled: "+disabled);

			pfWorkbook.close();
			workbook.close();

			physicalDamage += (int) getBloodLossPD(epen, dc, locationName);
			dc10KOTest(dc, locationName);
			return new Injuries(physicalDamage, locationName, disabled, weapon);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public int getProtectionFactor(int roll, boolean open) {

		int PF = 0;

		if (trooper.armor.getProtected(roll))
			PF = trooper.armor.getBPF(roll, open);

		// System.out.println("PF: "+PF);
		return PF;

	}

	public int getProtectionFactorOpen(int roll, boolean open) {

		int PF = 0;
		// System.out.println("Roll: "+roll);
		if (trooper.armor.getProtectedOpen(roll)) {
			PF = trooper.armor.getBPF(roll, open);
		}

		// System.out.println("PF1: "+PF);
		return PF;

	}



	int ionDamage = 0;

	public int shieldTest(int rangeCol, int roll, int pen, String targetStatus) {

		if (weapon.ionWeapon)
			ionDamage = weapon.ionDamage.get(rangeCol);

		if (trooper.personalShield != null) {

			boolean protectedZone = false;

			if (targetStatus.equals("Open")) {

				for (ArrayList<Integer> zone : trooper.personalShield.protectedZonesOpen) {

					if (roll >= zone.get(0) && roll <= zone.get(1)) {
						protectedZone = true;
						break;
					}

				}

			} else {

				for (ArrayList<Integer> zone : trooper.personalShield.protectedZones) {

					if (roll >= zone.get(0) && roll <= zone.get(1)) {
						protectedZone = true;
						break;
					}

				}
			}

			if (protectedZone && !weapon.ionWeapon) {
				int tempPen = pen;
				pen -= trooper.personalShield.currentShieldStrength;

				trooper.personalShield.currentShieldStrength -= tempPen;

				if (trooper.personalShield.currentShieldStrength < 0)
					trooper.personalShield.currentShieldStrength = 0;

				if (pen < 1) {

					log.addNewLineToQueue("Shot stopped by personal shields, remaining shield strength: "
							+ trooper.personalShield.currentShieldStrength);
					return pen;
				} else {
					log.addNewLineToQueue("Shot penetrated shield.");
				}

			} else if (protectedZone && weapon.ionWeapon) {
				int tempIonDamage = ionDamage;
				ionDamage -= trooper.personalShield.currentShieldStrength;

				trooper.personalShield.currentShieldStrength -= tempIonDamage / 10;

				if (trooper.personalShield.currentShieldStrength < 0)
					trooper.personalShield.currentShieldStrength = 0;

				if (ionDamage < 1) {

					log.addNewLineToQueue("Shot stopped by personal shields, remaining shield strength: "
							+ trooper.personalShield.currentShieldStrength);
					return pen;
				} else {
					log.addNewLineToQueue("Ion Shot penetrated shield.");
				}
			}

		}

		return pen;

	}

	public boolean ionTest(int rangeCol) {
		// System.out.println("Ion Test, weapon: "+weapon.name);

		// System.out.println("Range Col: "+rangeCol);

		if (ionDamage <= 0)
			return false;

		trooper.ionDamage += ionDamage;

		System.out.println("Ion Damage: " + ionDamage);

		if (weapon.ionWeapon) {
			if (ionDamage / 5 > trooper.KO && !trooper.entirelyMechanical) {
				log.addNewLine("Ion damage stunned organic trooper.");
				trooper.stunned(gameWindow.game, log);
			} else if (!trooper.entirelyMechanical) {
				log.addNewLine("Ion damage failed to stun organic trooper.");
			} else if (trooper.entirelyMechanical) {
				System.out.println("Entirely Mechanical");
				int TN = 0;
				if (trooper.ionDamage > 0) {

					if (trooper.ionDamage > trooper.KO * 5) {
						TN = 60;
						trooper.P2--;
					} else if (trooper.ionDamage > trooper.KO * 4) {
						TN = 26;
					} else if (trooper.ionDamage > trooper.KO * 3) {
						TN = 13;
					} else if (trooper.ionDamage > trooper.KO * 2) {
						TN = 12;
					} else {
						TN = 0;
					}

				}

				int roll2 = new Random().nextInt(100) + 1;
				if (roll2 < TN) {
					trooper.conscious = false;
					log.addToLineInQueue(" Mechanical Trooper Rendered Unconscious Through " + ionDamage
							+ " Ion Damage. Total Ion Damage: " + trooper.ionDamage);
					// Apply death
					trooper.incapacitationTime = 4800;

					Unit returnedTrooperUnit = trooper.returnTrooperUnit(gameWindow);
					int unitSize = returnedTrooperUnit.getSize();
					int moraleLoss = 100 / unitSize;
					if (returnedTrooperUnit.organization - 5 < 1) {
						returnedTrooperUnit.organization = 0;
					} else {
						returnedTrooperUnit.organization -= 5;
					}

					if (returnedTrooperUnit.moral - (moraleLoss / 2) <= 0) {
						returnedTrooperUnit.moral = 0;
					} else {
						returnedTrooperUnit.moral -= moraleLoss / 2;
					}

				} else {
					log.addToLineInQueue(" Mechanical Trooper Suffered " + ionDamage + " Ion Damage. Total Ion Damage: "
							+ trooper.ionDamage);
				}

			}

			return true;

		}

		return false;
	}

	public Injuries getPCHitsMelee(int pen, int dc) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(path + "hittable.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			org.apache.poi.ss.usermodel.Sheet worksheet = workbook.getSheetAt(0);

			// System.out.println("PC Hits Pass");

			// System.out.println("PC Target Concealment: "+targetUnit.concealment);
			String targetStatus = "";

			if (trooper.inCover) {
				targetStatus = "Fire";
			} else {
				targetStatus = "Open";
			}

			Random rand = new Random();
			int roll = rand.nextInt(100);
			// System.out.println("PC Hit Roll: "+roll);
			int hitRow = 0;
			String locationName = "";
			// System.out.println("Target Status: "+targetStatus);
			if (targetStatus.equals("Open")) {
				// System.out.println("Pass PC Hit Open");
				for (int i = 4; i < 43; i++) {

					Row row = worksheet.getRow(i);
					Double location = row.getCell(1).getNumericCellValue();

					if (roll <= location) {
						hitRow = i;
						break;
					}

				}

			} else {
				// System.out.println("Pass PC Hit Fire");
				for (int i = 4; i < 20; i++) {

					Row row = worksheet.getRow(i);
					Double location = row.getCell(0).getNumericCellValue();

					if (roll <= location) {
						hitRow = i;
						break;
					}

				}

			}

			// System.out.println("Hit Row PC: "+hitRow);
			locationName = worksheet.getRow(hitRow).getCell(2).getStringCellValue();

			// System.out.println("dc: "+dc);

			if (dc > 10)
				dc = 10;

			int dcCol = 3;

			int protectionFactor = 0;
			boolean open = false;
			if (targetStatus.equals("Open")) {
				open = true;
				protectionFactor = getProtectionFactorOpen(roll, true);
			} else {
				protectionFactor = getProtectionFactor(roll, false);
			}

			// System.out.println("PF: "+protectionFactor);
			int glancingRoll = rand.nextInt(10);
			isHardBodyArmor = trooper.armor.isHardBodyArmor(roll, open);
			if (isHardBodyArmor) {
				glancingRoll--;
			}
			int glancingCol = 0;
			int glancingRow = 0;
			FileInputStream excelFile2 = new FileInputStream(new File(path + "pftable.xlsx"));
			Workbook pfWorkbook = new XSSFWorkbook(excelFile2);
			org.apache.poi.ss.usermodel.Sheet pfWorksheet = pfWorkbook.getSheetAt(0);

			for (Cell myCell : pfWorksheet.getRow(1)) {
				if (myCell.getCellType() == CellType.NUMERIC) {
					if (glancingRoll == myCell.getNumericCellValue()) {
						glancingCol = myCell.getColumnIndex();
					}
				}

			}

			// System.out.println("glancingRoll: "+glancingRoll);
			// System.out.println("glancingCol: "+glancingCol);

			for (int i = 2; i < 20; i++) {
				if (protectionFactor <= pfWorksheet.getRow(i).getCell(0).getNumericCellValue()) {
					glancingRow = i;
					break;
				}
			}
			// System.out.println("glancingRow: "+glancingRow);
			// Expected row is 7
			double ePF = pfWorksheet.getRow(glancingRow).getCell(glancingCol).getNumericCellValue();
			// System.out.println("ePF: "+ePF);
			double epen = pen - ePF;

			if (epen < 0.5) {
				pfWorkbook.close();
				workbook.close();
				return new Injuries(0, locationName, false, weapon);

			}

			int damageCol = 0;

			for (Cell myCell : worksheet.getRow(1)) {

				if (myCell == null || myCell.getCellType() == CellType.BLANK) {
					continue;
				}

				if (dc == (int) myCell.getNumericCellValue()) {
					dcCol = myCell.getColumnIndex();
					break;
				}
			}

			int epenCount = 0;

			if (dc > 4 && dc < 8) {
				epenCount = 5;
			} else if (dc > 8) {
				epenCount = 4;
			} else {
				epenCount = 7;
			}

			if (epen > 10)
				epen = 10;

			// System.out.println("epenCount: "+epenCount);
			// System.out.println("dcCol: "+dcCol);
			// System.out.println("Epen: "+epen);
			for (int i = 0; i < epenCount; i++) {
				// System.out.println("Epen Cell:
				// "+worksheet.getRow(3).getCell(dcCol+i).getNumericCellValue());

				if (epen <= worksheet.getRow(3).getCell(dcCol + i).getNumericCellValue()) {
					damageCol = dcCol + i;
					break;
				}

			}

			// Size 1, flat number
			// Size 2, if second char int, flat number
			// Size 2, if second char str, check str convert to flat number or set disabled
			// Size 3, get second char convert to flat number, set disabled

			// System.out.println("Hit Row: "+hitRow);
			// System.out.println("DMG Col: "+damageCol);
			Cell cell = worksheet.getRow(hitRow).getCell(damageCol);

			String damageCell = "";

			if (cell.getCellType() == CellType.STRING) {
				damageCell = worksheet.getRow(hitRow).getCell(damageCol).getStringCellValue();
			} else {
				damageCell = Integer.toString((int) worksheet.getRow(hitRow).getCell(damageCol).getNumericCellValue());
			}

			// System.out.println("damageCell: "+damageCell);

			int physicalDamage = 0;
			Boolean disabled = false;
			if (damageCell.length() == 1) {
				// System.out.println("CharAt1: "+damageCell.charAt(0));
				physicalDamage = Integer.parseInt(damageCell);
			} else if (damageCell.length() == 2) {
				// System.out.println("CharAt1: "+damageCell.charAt(0));
				// System.out.println("CharAt2: "+damageCell.charAt(1));
				if (Character.isDigit(damageCell.charAt(1))) {
					physicalDamage = Integer.parseInt(damageCell);
				} else {
					if (damageCell.charAt(1) == 'D') {
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
					}

				}

			} else if (damageCell.length() == 3) {
				// System.out.println("CharAt1:
				// "+Character.getNumericValue(damageCell.charAt(0)));
				// System.out.println("CharAt2: "+damageCell.charAt(1));
				// System.out.println("CharAt3: "+damageCell.charAt(2));
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

			// System.out.println("Location: "+locationName);
			// System.out.println("physicalDamage: "+physicalDamage);
			// System.out.println("disabled: "+disabled);

			excelFile.close();
			excelFile2.close();
			pfWorkbook.close();
			workbook.close();

			physicalDamage += (int) getBloodLossPD(epen, dc, locationName);

			return new Injuries(physicalDamage, locationName, disabled, weapon);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public double getBloodLossPD(double epen, int dc, String locationName) {
		if (trooper.entirelyMechanical) {
			return 0;
		} else {

			for (int i = 0; i < trooper.mechanicalZones.size(); i++) {
				if (trooper.mechanicalZones.get(i).equals(locationName))
					return 0;
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

		return bloodLossPD;

	}

	// Used in melee attack
	public void performCalculations(Game game, int str) {
		Random rand = new Random();

		// Loops through each hit
		for (int i = 0; i < hits; i++) {

			int dc = weapon.dc.get(0) + str;
			int pen = weapon.pen.get(0);

			// Gets shield loss
			if (trooper.currentShields > 0) {

				int roll = rand.nextInt(100) + 1;

				if (roll <= trooper.shieldChance) {
					if (pen > trooper.currentShields) {
						pen -= trooper.currentShields;
						trooper.currentShields = 0;

					} else {
						trooper.currentShields -= pen;
						pen = 0;
					}

				}

			}

			if (pen > 0) {

				// Applies hit from trooper
				log.addNewLine("\n Melee Hit, " + trooper.name);
				Injuries injury = getPCHitsMelee(pen, dc);
				if (injury == null)
					return;

				if (injury.pd > 0) {
					trooper.injured(log, injury, game, gameWindow);
					trooper.calculateInjury(gameWindow, log);

					// Gets status, and puts status in log line
					log.addToLine(", Number: " + trooper.number + "; Current PD: " + 0 + "; Additional PD: " + injury.pd
							+ ", Location: " + injury.location + ", Disabled: " + injury.disabled);
				} else {
					log.addToLine(", stopped by armor");
				}

			} else {
				log.addNewLine("\nMele Hit, " + trooper.name + ", Number: " + trooper.number + "; Current Sheilds: "
						+ trooper.currentShields);

			}

		}

		// System.out.println("Pass calculating");

	}

	public String hitLocation() {
		int locationRoll = 0;
		String hitLocation = "";

		locationRoll += d6();
		locationRoll += d6();
		locationRoll += d6();

		if (locationRoll <= 5) {
			hitLocation = "head";
		} else if (locationRoll <= 9) {
			hitLocation = "legs";
		} else if (locationRoll <= 11) {
			hitLocation = "arms";
		} else if (locationRoll <= 16) {
			hitLocation = "torso";
		} else if (locationRoll <= 18) {
			hitLocation = "vitals";
		}

		return hitLocation;
	}

	public Trooper returnTarget() {
		return trooper;
	}

	public int d10() {
		Random rand = new Random();
		int d10 = rand.nextInt(10) + 1;
		return d10;
	}

	public int d6() {
		Random rand = new Random();
		int d6 = rand.nextInt(6) + 1;
		return d6;
	}

}
