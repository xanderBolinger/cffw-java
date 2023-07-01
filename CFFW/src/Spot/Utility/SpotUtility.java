package Spot.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Conflict.GameWindow;
import Hexes.Building;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class SpotUtility {
	public static int getTargetUnitSize(Unit spotterUnit, ArrayList<Unit> targets) {

		int size = 0;

		for (Unit unit : targets) {
			boolean sameHex = spotterUnit.X == unit.X && spotterUnit.Y == unit.Y;

			for (Trooper trooper : unit.individuals) {
				if (sameHex && trooper.alive && trooper.conscious) {
					size++;
					continue;
				}

				if (!trooper.HD && trooper.alive && trooper.conscious)
					size++;
			}

		}

		return size;
	}

	public static ArrayList<Trooper> getTargetTroopers(Unit spotterUnit, ArrayList<Unit> spotableUnits) {
		ArrayList<Trooper> targetTroopers = new ArrayList<Trooper>();

		for (Unit unit : spotableUnits) {
			boolean sameHex = spotterUnit.X == unit.X && spotterUnit.Y == unit.Y;
			if (unit.getSize() > 0) {
				for (Trooper trooper : unit.getTroopers()) {
					if (sameHex && trooper.alive && trooper.conscious) {
						targetTroopers.add(trooper);
						continue;
					}

					if (!trooper.HD && trooper.alive && trooper.conscious)
						targetTroopers.add(trooper);

				}

			}

		}

		return targetTroopers;
	}

	public static ArrayList<Unit> getTargetUnitsInHex(Unit spotterUnit, ArrayList<Unit> initiativeOrder, int xCord,
			int yCord) {
		ArrayList<Unit> targets = new ArrayList<Unit>();

		for (Unit unit : initiativeOrder) {

			if (!unit.side.equals(spotterUnit) && xCord == unit.X && yCord == unit.Y) {
				targets.add(unit);
			}

		}
		return targets;
	}

	public static int getSkillMod(Trooper spotter) {
		int skillTestMod = 0;

		int roll3 = new Random().nextInt(100) + 1;

		if (roll3 <= spotter.getSkill("Spot/Listen")) {
			int margin = spotter.getSkill("Spot/Listen") - roll3;

			skillTestMod = 1;

			skillTestMod += margin / 10;

		} else {
			int margin = spotter.getSkill("Spot/Listen") - roll3;
			skillTestMod--;
			skillTestMod += margin / 10;
		}

		if (skillTestMod >= 5)
			skillTestMod = 3;
		else if (skillTestMod <= -5)
			skillTestMod = -3;
		else
			skillTestMod /= 2;

		return skillTestMod;
	}

	public static int getConcealmentMod(ArrayList<Unit> spotableUnits, ArrayList<Trooper> spotableTroopers) {
		String concealment = spotableUnits.get(0).concealment;
		int concealmentMod = getConcealmentMod(concealment);
		if (concealmentMod < Building.buildingConcealmentMod
				&& Building.majorityEmbarked(GameWindow.gameWindow, spotableTroopers)) {
			concealmentMod = Building.buildingConcealmentMod;
		}
		return concealmentMod;
	}

	
	public static int getSpeedModTarget(ArrayList<Unit> spotableUnits) {
		int speedModTarget = 0;
		for(var unit : spotableUnits) {
			speedModTarget += getSpeedModTarget(unit.speed);
		}
		speedModTarget /= spotableUnits.size();
		return speedModTarget;
	}
	
	// Checks with unit speed and assigns a PC value to it
	// Crawl 1/3 hex
	// Walk 1 hex
	// Rush 2 hex
	private static int getSpeedModTarget(String speed) {
		
		
		
		int mod = 0;

		if (speed.equals("Walk")) {
			mod = -6;
		} else if (speed.equals("Crawl")) {
			mod = -1;
		} else if (speed.equals("Rush")) {
			mod = -7;
		}

		return mod;
	}

	public static int getSpeedModSpotter(String speed) {
		int mod = 0;

		if (speed.equals("Walk")) {
			mod = +2;
		} else if (speed.equals("Crawl")) {
			mod = +3;
		} else if (speed.equals("Rush")) {
			mod = +5;
		}

		return mod;
	}

	public static double getPcSize(ArrayList<Trooper> spotableTroopers) {
		double PCSize = 0;
		for (Trooper targetTrooper : spotableTroopers) {

			if (targetTrooper.stance.equals("Standing")) {
				// System.out.println("Standing");
				PCSize += targetTrooper.PCSize / 1;
			} else if (targetTrooper.stance.equals("Crouching")) {
				// System.out.println("Crouching");
				PCSize += targetTrooper.PCSize / 1.25;
			} else {
				// System.out.println("Prone");
				PCSize += targetTrooper.PCSize / 2;
			}

		}

		PCSize /= spotableTroopers.size();

		return PCSize;
	}

	public static int getTargetSizeMod(double PCSize, ArrayList<Trooper> spotableTroopers) {

		if (PCSize <= -8) {
			return 8;
		} else if (PCSize <= -3) {
			return 6;
		} else if (PCSize <= 0) {
			return 4;
		} else if (PCSize <= 2) {
			return 3;
		} else if (PCSize <= 4) {
			return 2;
		} else if (PCSize <= 6) {
			return 1;
		} else if (PCSize <= 8) {
			return 0;
		} else if (PCSize <= 10) {
			return -1;
		} else if (PCSize <= 14) {
			return -2;
		} else if (PCSize <= 20) {
			return -5;
		} else if (PCSize <= 26) {
			return -7;
		} else if (PCSize <= 32) {
			return -10;
		} else if (PCSize <= 40) {
			return -12;
		}

		return 0;
	}

	// Compares the diff to the PC range chart
	// Gets mod
	public static int getRangeMod(Unit target, Unit spotter) {
		int hexes = GameWindow.hexDif(target, spotter);

		int mod = 0;
		int yards = hexes * GameWindow.hexSize;
		int pcHexes = yards / 2;

		// System.out.println("Hexes: " + hexes + "; Yards: " + yards + "; pcHexes: " +
		// pcHexes);
		// Range array

		ArrayList<ArrayList<Integer>> rangeMods = new ArrayList<ArrayList<Integer>>(19);
		for (int i = 1; i <= 19; i++) {
			rangeMods.add(new ArrayList<Integer>());
		}

		rangeMods.get(0).add(0, 2);
		rangeMods.get(1).add(0, 3);
		rangeMods.get(2).add(0, 4);
		rangeMods.get(3).add(0, 6);
		rangeMods.get(4).add(0, 8);
		rangeMods.get(5).add(0, 11);
		rangeMods.get(6).add(0, 16);
		rangeMods.get(7).add(0, 23);
		rangeMods.get(8).add(0, 32);
		rangeMods.get(9).add(0, 45);
		rangeMods.get(10).add(0, 64);
		rangeMods.get(11).add(0, 90);
		rangeMods.get(12).add(0, 130);
		rangeMods.get(13).add(0, 180);
		rangeMods.get(14).add(0, 260);
		rangeMods.get(15).add(0, 360);
		rangeMods.get(16).add(0, 510);
		rangeMods.get(17).add(0, 720);
		rangeMods.get(18).add(0, 1020);

		rangeMods.get(0).add(1, 1);
		rangeMods.get(1).add(1, 2);
		rangeMods.get(2).add(1, 3);
		rangeMods.get(3).add(1, 4);
		rangeMods.get(4).add(1, 5);
		rangeMods.get(5).add(1, 6);
		rangeMods.get(6).add(1, 7);
		rangeMods.get(7).add(1, 8);
		rangeMods.get(8).add(1, 9);
		rangeMods.get(9).add(1, 10);
		rangeMods.get(10).add(1, 11);
		rangeMods.get(11).add(1, 12);
		rangeMods.get(12).add(1, 13);
		rangeMods.get(13).add(1, 14);
		rangeMods.get(14).add(1, 15);
		rangeMods.get(15).add(1, 16);
		rangeMods.get(16).add(1, 17);
		rangeMods.get(17).add(1, 18);
		rangeMods.get(18).add(1, 19);

		// System.out.println("range mods array: " + rangeMods.toString());

		mod = search(rangeMods, pcHexes);

		return mod;
	}

	private static int search(ArrayList<ArrayList<Integer>> arr, int target) {
		int value = 0;
		// System.out.println("Target: " + target);
		// System.out.println("Array: " + arr.toString());
		int temp1;
		int temp2;
		for (int i = 1; i <= arr.size() - 1; i++) {
			temp1 = arr.get(i).get(0);
			temp2 = arr.get(i - 1).get(0);
			if (target <= temp1 && target >= temp2) {
				value = arr.get(i).get(1);
				// System.out.println("Value: " + value);
			}

		}

		return value;

	}

	public static int searchSecond(ArrayList<ArrayList<Integer>> arr, int target) {
		target--;
		int value = 0;
		// System.out.println("Target: " + target);
		// System.out.println("Array: " + arr.toString());
		int temp1;
		int temp2;
		for (int i = 1; i <= arr.size() - 1; i++) {
			temp1 = arr.get(i).get(1);
			temp2 = arr.get(i - 1).get(1);
			if (target <= temp1 && target >= temp2) {
				value = arr.get(i).get(0);
				// System.out.println("Value: " + value);
			}

		}

		return value;

	}

	// Gets the concealment level and assigns the value to it
	public static int getConcealmentMod(String concealment) {
		int mod = 0;

		if (concealment.equals("Level 1")) {
			mod = 1;
		} else if (concealment.equals("Level 2")) {
			mod = 3;
		} else if (concealment.equals("Level 3")) {
			mod = 4;
		} else if (concealment.equals("Level 4")) {
			mod = 5;
		} else if (concealment.equals("Level 5")) {
			mod = 8;
		}

		return mod;

	}

	public static int getSlm(int speedModTarget, int speedModSpotter, int concealmentMod, int rangeMod,
			int visibilityMod, int skillMod, int targetSizeMod) {

		if (concealmentMod == 1) {
			concealmentMod = 1;
		} else if (concealmentMod == 2) {
			concealmentMod = 3;
		} else if (concealmentMod == 3) {
			concealmentMod = 5;
		} else if (concealmentMod == 4) {
			concealmentMod = 8;
		} else if (concealmentMod == 5) {
			concealmentMod = 9;
		}

		return speedModTarget + speedModSpotter + concealmentMod + rangeMod + visibilityMod - skillMod + targetSizeMod;
	}

	private static ArrayList<ArrayList<Integer>> getSpottingTable(String scanArea) {
		ArrayList<ArrayList<Integer>> spottingTable = new ArrayList<ArrayList<Integer>>(19);
		for (int i = 1; i <= 19; i++) {
			spottingTable.add(new ArrayList<Integer>());
		}

		if (scanArea.equals("60 Degrees")) {
			spottingTable.get(0).add(0, 100);
			spottingTable.get(1).add(0, 100);
			spottingTable.get(2).add(0, 100);
			spottingTable.get(3).add(0, 100);
			spottingTable.get(4).add(0, 80);
			spottingTable.get(5).add(0, 58);
			spottingTable.get(6).add(0, 40);
			spottingTable.get(7).add(0, 28);
			spottingTable.get(8).add(0, 20);
			spottingTable.get(9).add(0, 14);
			spottingTable.get(10).add(0, 10);
			spottingTable.get(11).add(0, 7);
			spottingTable.get(12).add(0, 5);
			spottingTable.get(13).add(0, 4);
			spottingTable.get(14).add(0, 3);
			spottingTable.get(15).add(0, 1);
			spottingTable.get(16).add(0, 1);
			spottingTable.get(17).add(0, 0);
			spottingTable.get(18).add(0, 0);

		} else if (scanArea.equals("180 Degrees")) {
			spottingTable.get(0).add(0, 100);
			spottingTable.get(1).add(0, 100);
			spottingTable.get(2).add(0, 83);
			spottingTable.get(3).add(0, 56);
			spottingTable.get(4).add(0, 42);
			spottingTable.get(5).add(0, 30);
			spottingTable.get(6).add(0, 21);
			spottingTable.get(7).add(0, 14);
			spottingTable.get(8).add(0, 10);
			spottingTable.get(9).add(0, 7);
			spottingTable.get(10).add(0, 5);
			spottingTable.get(11).add(0, 4);
			spottingTable.get(12).add(0, 3);
			spottingTable.get(13).add(0, 2);
			spottingTable.get(14).add(0, 1);
			spottingTable.get(15).add(0, 1);
			spottingTable.get(16).add(0, 0);
			spottingTable.get(17).add(0, 0);
			spottingTable.get(18).add(0, 0);

		} else {
			spottingTable.get(0).add(0, 100);
			spottingTable.get(1).add(0, 100);
			spottingTable.get(2).add(0, 100);
			spottingTable.get(3).add(0, 75);
			spottingTable.get(4).add(0, 60);
			spottingTable.get(5).add(0, 48);
			spottingTable.get(6).add(0, 39);
			spottingTable.get(7).add(0, 32);
			spottingTable.get(8).add(0, 28);
			spottingTable.get(9).add(0, 24);
			spottingTable.get(10).add(0, 21);
			spottingTable.get(11).add(0, 18);
			spottingTable.get(12).add(0, 16);
			spottingTable.get(13).add(0, 14);
			spottingTable.get(14).add(0, 12);
			spottingTable.get(15).add(0, 7);
			spottingTable.get(16).add(0, 4);
			spottingTable.get(17).add(0, 2);
			spottingTable.get(18).add(0, 2);
		}

		spottingTable.get(0).add(1, 1);
		spottingTable.get(1).add(1, 2);
		spottingTable.get(2).add(1, 3);
		spottingTable.get(3).add(1, 4);
		spottingTable.get(4).add(1, 5);
		spottingTable.get(5).add(1, 6);
		spottingTable.get(6).add(1, 7);
		spottingTable.get(7).add(1, 8);
		spottingTable.get(8).add(1, 9);
		spottingTable.get(9).add(1, 10);
		spottingTable.get(10).add(1, 11);
		spottingTable.get(11).add(1, 12);
		spottingTable.get(12).add(1, 13);
		spottingTable.get(13).add(1, 14);
		spottingTable.get(14).add(1, 15);
		spottingTable.get(15).add(1, 16);
		spottingTable.get(16).add(1, 17);
		spottingTable.get(17).add(1, 18);
		spottingTable.get(18).add(1, 19);

		return spottingTable;
	}

	public static int findSpottingChance(int size, int spottingChance) throws Exception {

		if (size > 24)
			size = 24;

		try {
			FileInputStream excelFile = new FileInputStream(
					new File(System.getProperty("user.dir") + "\\groupspottingtable.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			org.apache.poi.ss.usermodel.Sheet worksheet = workbook.getSheetAt(0);

			int col = 0;
			int row = 0;
			for (Cell cell : worksheet.getRow(0)) {

				if (cell.getCellType() == CellType.NUMERIC) {

					if (size <= cell.getNumericCellValue()) {
						col = cell.getColumnIndex();
						break;
					}

				}
			}

			for (int i = 1; i < 18; i++) {
				// System.out.println("spottingChance1: "+spottingChance);
				// System.out.println("CellSC:
				// "+worksheet.getRow(i).getCell(0).getNumericCellValue());
				if (spottingChance <= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
					row = i;
					break;
				}

			}
			// System.out.println("Row: "+row);
			// System.out.println("Col: "+col);
			workbook.close();
			excelFile.close();
			return (int) worksheet.getRow(row).getCell(col).getNumericCellValue();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		throw new Exception("Spotting chance not found for size: " + size + ", and spotting chance: " + spottingChance);
	}

	public static SpotActionResults getResults(int size, String scanArea, int SLM) throws Exception {

		int spottingChance = SpotUtility.searchSecond(getSpottingTable(scanArea), SLM);

		if (SLM <= 1) {
			spottingChance = 100;
		} else if (SLM > 19) {
			spottingChance = 0;
		} else {
			spottingChance = findSpottingChance(size, spottingChance);
		}

		// Roll and success calculations
		int roll = DiceRoller.roll(1, 100);
		int margin = spottingChance - roll;
		int success = 0;
		if (roll <= spottingChance) {
			success++;
		}
		success += margin / 10;

		return new SpotActionResults(roll, spottingChance, success, spottingChance, roll);
	}

	public static void findSpottedTroopers(ArrayList<Trooper> spottedIndividuals, ArrayList<Unit> spottedUnits,
			int passes, ArrayList<Trooper> spotableTroopers, ArrayList<Unit> spotableUnits, int size, int successesRoll,
			int targetNumber, ArrayList<Unit> initativeOrder) {
		ArrayList<Integer> rolls = new ArrayList<Integer>();

		// Determines spotted individuals
		if (passes > 0) {
			Random rand = new Random();

			int roll;
			boolean duplicate = false;

			if (passes > spotableTroopers.size()) {
				passes = spotableTroopers.size();
			}

			// Sets spotted individuals and unit
			for (int i = 0; i < passes; i++) {
				roll = rand.nextInt(size);
				int spottingDiff = spotableTroopers.get(roll).spottingDifficulty;
				if (spotableTroopers.get(roll).firedTracers) {
					spottingDiff -= 20;
				}

				/*
				 * System.out.println("\nRoll: "+roll); System.out.println("Rolls:");
				 */
				for (int x = 0; x < rolls.size(); x++) {
					// System.out.println("\nRoll"+x+": "+rolls.get(x));
				}

				/*
				 * System.out.println("\nNew Trooper:");
				 * System.out.println("Spotting Diff: "+spottingDiff);
				 * System.out.println("HD: "+targetTroopers.get(roll).HD);
				 * System.out.println("Unspottable: "+targetTroopers.get(roll).unspottable);
				 * System.out.println("Alive: "+targetTroopers.get(roll).alive);
				 * System.out.println("Conscious: "+targetTroopers.get(roll).conscious);
				 * System.out.println("\n");
				 */
				if (rolls.contains(roll) || spotableTroopers.get(roll).unspottable
						|| successesRoll + spottingDiff >= targetNumber || !spotableTroopers.get(roll).alive
						|| !spotableTroopers.get(roll).conscious) {
					duplicate = true;
				} else {
					rolls.add(roll);
				}
				// Checks for duplicates and if the individual can be spotted

				ArrayList<Integer> loopCounter = new ArrayList<Integer>();

				while (duplicate) {
					// System.out.println("Duplicate == true");
					int roll2 = rand.nextInt(size);
					spottingDiff = spotableTroopers.get(roll2).spottingDifficulty;
					if (spotableTroopers.get(roll2).firedTracers) {
						spottingDiff -= 20;
					}
					/*
					 * System.out.println("\nNew Duplicate:");
					 * System.out.println("Spotting Diff: "+spottingDiff);
					 * System.out.println("HD: "+targetTroopers.get(roll).HD);
					 * System.out.println("Unspottable: "+targetTroopers.get(roll).unspottable);
					 * System.out.println("Alive: "+targetTroopers.get(roll).alive);
					 * System.out.println("Conscious: "+targetTroopers.get(roll).conscious);
					 * System.out.println("\n");
					 */

					if (rolls.contains(roll2) || spotableTroopers.get(roll2).unspottable
							|| successesRoll + spottingDiff >= targetNumber || !spotableTroopers.get(roll2).alive
							|| !spotableTroopers.get(roll2).conscious) {
						duplicate = true;
					} else {
						rolls.add(roll2);
						duplicate = false;
					}

					if (loopCounter.size() >= size) {
						// System.out.println("Loop counter break.");
						break;
					}

				}
			}

		}

		// Checks for individuals possibly spotted by their tracers
		for (int i = 0; i < spotableUnits.size(); i++) {
			// System.out.println("successesRoll: "+successesRoll);
			if (spotableTroopers.get(i).firedTracers && targetNumber - successesRoll >= -20
					&& targetNumber - successesRoll < 0) {
				if (addSpottedIndividual(i, spotableTroopers, rolls, targetNumber, successesRoll) > -1) {
					spottedIndividuals.add(spotableTroopers.get(i));
				}
			}

		}

		// Sets spotted individuals and unit
		// System.out.println("\nRolls: "+rolls.size());
		for (int i = 0; i < rolls.size(); i++) {
			// targetTroopers.get(rolls.get(i)).number = rolls.get(i) + 1;
			spottedIndividuals.add(spotableTroopers.get(rolls.get(i)));

			spottedUnits.add(spotableTroopers.get(rolls.get(i)).returnTrooperUnit(initativeOrder));

		}

	}

	public static int addSpottedIndividual(int roll, ArrayList<Trooper> targets, ArrayList<Integer> rolls,
			int targetNumber, int successesRoll) {
		int spottingDiff = targets.get(roll).spottingDifficulty;
		if (targets.get(roll).firedTracers) {
			// System.out.println("Pass Tracers");
			spottingDiff -= 20;
		}

		// System.out.println("Rolls contains test: "+rolls.contains(roll));

		for (int x = 0; x < rolls.size(); x++) {
			// System.out.println("\nRoll"+x+": "+rolls.get(x));
		}

		if (rolls.contains(roll) || targets.get(roll).HD || targets.get(roll).unspottable
				|| successesRoll + spottingDiff >= targetNumber || !targets.get(roll).alive
				|| !targets.get(roll).conscious) {
			// System.out.println("Add spotted individual fail, -1, spot action smh");
			return -1;
		} else {
			// System.out.println("Pass123");
			return roll;
		}

	}

	public static void updateResults(String resultsString, int size, int PCSize, String targetUnitSpeed,
			String spotterUnitSpeed, int hexDiff, String visibilityModifications, String concealment, int skillTestMod,
			int targetSizeMod, int speedModSpotter, int speedModTarget, int concealmentMod, int rangeMod,
			int visibilityMod, int SLM) {
		resultsString += "Target Size: " + size + ", Average PC Size: " + PCSize + ", Target Unit Speed: "
				+ targetUnitSpeed + ", Spotter Unit Speed: " + spotterUnitSpeed + ", Target Concealment: " + concealment
				+ ", Hex Range: " + hexDiff + "\n" + "Visibility Modifications: " + visibilityModifications
				+ "\n PC Spot Modifiers: " + "Skill Test Mod: " + skillTestMod + ", Target Size Mod: " + targetSizeMod
				+ ", Target Speed Mod: " + speedModTarget + ", Spotter Speed Mod: " + speedModSpotter
				+ ", Concealment Mod: " + concealmentMod + ", Range Mod: " + rangeMod + ", Visibility Mod: "
				+ visibilityMod + "\n" + "SLM: " + SLM;
	}

}
