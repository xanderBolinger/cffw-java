package Spot.Utility;

import java.util.ArrayList;
import java.util.Random;

import Conflict.GameWindow;
import HexGrid.CalculateLOS;
import Hexes.Building;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.PCUtility;

public class SpotModifiers {

	
	// Gets the concealment level and assigns the value to it
	public static int getConcealmentMod(int concealment) {
		int mod = 0;

		if (concealment == 1) {
			mod = 1;
		} else if (concealment == 2) {
			mod = 3;
		} else if (concealment == 3) {
			mod = 4;
		} else if (concealment == 4) {
			mod = 5;
		} else if (concealment == 5) {
			mod = 8;
		} else if(concealment  >= 5) {
			mod = 8;
		}

		return mod;

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

	public static int getConcealmentMod(Trooper spotter, ArrayList<Unit> spotableUnits, ArrayList<Trooper> spotableTroopers) {
		int concealment = CalculateLOS.getConcelamentValue(spotter.returnTrooperUnit(GameWindow.gameWindow), spotableUnits.get(0));
		int concealmentMod = getConcealmentMod(concealment);
		if (concealmentMod < Building.buildingConcealmentMod
				&& Building.majorityEmbarked(GameWindow.gameWindow, spotableTroopers)) {
			concealmentMod = Building.buildingConcealmentMod;
		}
		return concealmentMod;
	}

	public static int getSpeedModTarget(ArrayList<Unit> spotableUnits) {
		int speedModTarget = 0;
		for (var unit : spotableUnits) {
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
			mod = +3;
		} else if (speed.equals("Crawl")) {
			mod = +1;
		} else if (speed.equals("Rush")) {
			mod = +5;
		}

		return mod;
	}

	public static double getPcSize(ArrayList<Trooper> spotableTroopers) {
		double PCSize = 0;
		for (Trooper targetTrooper : spotableTroopers) {

			PCSize += PCUtility.getStanceModifiedSize(targetTrooper.stance, targetTrooper.PCSize, targetTrooper.inCover);

		}

		PCSize /= spotableTroopers.size();

		return PCSize;
	}

	public static int getTargetSizeMod(double PCSize) {

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
}
