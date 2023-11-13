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
	
	public static ArrayList<Trooper> spottedTroopers = new ArrayList<Trooper>();
	
	public static void clearSpotted() {
		spottedTroopers.clear();		
	}
	
	public static void printSpottedTroopers() {
		
		String spotted = "\nTotal Spotted: ";
		
		for(var trooper : spottedTroopers) {
			spotted += trooper.returnTrooperUnit(GameWindow.gameWindow).callsign
					+" "+trooper.number+" "+trooper.name
					+ (trooper.compareTo(spottedTroopers.get(spottedTroopers.size()-1))
							? "" : ", ");
		}
		
		GameWindow.gameWindow.conflictLog.addNewLine(spotted+"\n");
	}
	
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

	

	public static int getSlm(int speedModTarget, int speedModSpotter, int concealmentMod, int rangeMod,
			int visibilityMod, int skillMod, int targetSizeMod, int fortMod) {

		return speedModTarget + speedModSpotter + concealmentMod + rangeMod + visibilityMod - skillMod + targetSizeMod + fortMod;
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

		if(size <= 2)
			return spottingChance;
		
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
				if (spottingChance <= worksheet.getRow(i).getCell(0).getNumericCellValue()) {
					row = i;
					break;
				}

			}
			
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

	public static SpotActionResults getResults(int size, String scanArea, int SLM, int suppression) throws Exception {

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

		if (passes > 0) {
			Random rand = new Random();

			int roll;
			boolean duplicate = false;

			if (passes > spotableTroopers.size()) {
				passes = spotableTroopers.size();
			}

			for (int i = 0; i < passes; i++) {
				roll = rand.nextInt(size);
				if (rolls.contains(roll) || spotableTroopers.get(roll).unspottable
						|| successesRoll >= targetNumber || !spotableTroopers.get(roll).alive
						|| !spotableTroopers.get(roll).conscious) {
					duplicate = true;
				} else {
					rolls.add(roll);
				}

				ArrayList<Integer> loopCounter = new ArrayList<Integer>();

				while (duplicate) {
					int roll2 = rand.nextInt(size);

					if (rolls.contains(roll2) || spotableTroopers.get(roll2).unspottable
							|| successesRoll >= targetNumber || !spotableTroopers.get(roll2).alive
							|| !spotableTroopers.get(roll2).conscious) {
						duplicate = true;
					} else {
						rolls.add(roll2);
						duplicate = false;
					}

					if (loopCounter.size() >= size) {
						break;
					}

				}
			}

		}

		// Sets spotted individuals and unit
		for (int i = 0; i < rolls.size(); i++) {
			var t = spotableTroopers.get(rolls.get(i));
			spottedIndividuals.add(t);
			spottedTroopers.add(t);
			spottedUnits.add(spotableTroopers.get(rolls.get(i)).returnTrooperUnit(initativeOrder));

		}

	}

	public static int addSpottedIndividual(int roll, ArrayList<Trooper> targets, ArrayList<Integer> rolls,
			int targetNumber, int successesRoll) {
		if (rolls.contains(roll) || targets.get(roll).HD || targets.get(roll).unspottable
				|| successesRoll >= targetNumber || !targets.get(roll).alive
				|| !targets.get(roll).conscious) {
			return -1;
		} else {
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
