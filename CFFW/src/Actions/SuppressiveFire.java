package Actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import Conflict.GameWindow;
import Hexes.Building;
import Items.Weapons;
import Trooper.Trooper;
import Unit.Unit;

public class SuppressiveFire implements Serializable {

	private int hits = 0;
	private int TN = 0;
	private int shots = 0;

	public SuppressiveFire(GameWindow gameWindow, int RWS, int bonus, Weapons weapon, int shots, Trooper target, Trooper shooter, Unit targetUnit, Unit shooterUnit) {
		int rangeInYards = hexDif(targetUnit, shooterUnit) * GameWindow.hexSize;
		int rangeMod = getRangeMod(rangeInYards);
		int concealmentMod = getConcealmentMod(targetUnit);
		
		if(Building.majorityEmbarked(gameWindow, targetUnit.individuals))
			concealmentMod = 0; 
		
		int getSpeedMod = getSpeedMod(targetUnit);
		
		bonus = bonus - shooterUnit.suppression;
		bonus += weapon.weaponBonus;
		rangeMod = rangeMod / 2; 
		concealmentMod = concealmentMod / 2; 
		
		if(shooter.disabledArms > 0) {
			RWS = RWS / 2;
		} else if (shooter.disabledArms > 1) {
			RWS = RWS / 3; 
		}
		
		this.TN = RWS + bonus + rangeMod + concealmentMod + getSpeedMod + 20;
		this.shots = shots;
		
		//System.out.println("\n\nCalculating Shots Suppressive Fire \n\n");
		//System.out.println("range in yards: "+rangeInYards);
		//System.out.println("Range Mod: "+rangeMod);
		//System.out.println("Concealment Mod: "+concealmentMod);
		//System.out.println("Get Speed Mod: "+getSpeedMod);
		calculateShots();
	}

	// Finds the differance between the two locations
	public int hexDif(Unit targetUnit, Unit shooterUnit) {
		double xDif = Math.abs(targetUnit.X - shooterUnit.X);
		double yDif = Math.abs(targetUnit.Y - shooterUnit.Y);
		
		xDif *= xDif; 
		yDif *= yDif; 
		
		return (int) Math.floor(Math.sqrt((xDif + yDif)));
	}

	// Takes range in yards and returns GURPS percentage penalty
	public int getRangeMod(int rangeInYards) {
		int rangeMod = 0;

		ArrayList<ArrayList<Integer>> rangeMods = new ArrayList<ArrayList<Integer>>(20);
		for (int i = 1; i <= 20; i++) {
			rangeMods.add(new ArrayList<Integer>());
		}

		// 3 yards to 15
		rangeMods.get(0).add(0, 3);
		rangeMods.get(1).add(0, 5);
		rangeMods.get(2).add(0, 7);
		rangeMods.get(3).add(0, 10);
		rangeMods.get(4).add(0, 15);

		// 20 to 100 yards
		rangeMods.get(5).add(0, 20);
		rangeMods.get(6).add(0, 30);
		rangeMods.get(7).add(0, 50);
		rangeMods.get(8).add(0, 70);
		rangeMods.get(9).add(0, 100);

		// 150 to 700 yards
		rangeMods.get(10).add(0, 150);
		rangeMods.get(11).add(0, 200);
		rangeMods.get(12).add(0, 300);
		rangeMods.get(13).add(0, 500);
		rangeMods.get(14).add(0, 700);

		// 1000 to 5000 yards
		rangeMods.get(15).add(0, 1000);
		rangeMods.get(16).add(0, 1500);
		rangeMods.get(17).add(0, 2000);
		rangeMods.get(18).add(0, 3000);
		rangeMods.get(19).add(0, 5000);

		// Penalties 
		
		// 3 yards to 15
		rangeMods.get(0).add(1, -1);
		rangeMods.get(1).add(1, -2);
		rangeMods.get(2).add(1, -3);
		rangeMods.get(3).add(1, -4);
		rangeMods.get(4).add(1, -5);

		// 20 to 100 yards
		rangeMods.get(5).add(1, -6);
		rangeMods.get(6).add(1, -7);
		rangeMods.get(7).add(1, -8);
		rangeMods.get(8).add(1, -9);
		rangeMods.get(9).add(1, -10);

		// 150 to 700 yards
		rangeMods.get(10).add(1, -11);
		rangeMods.get(11).add(1, -12);
		rangeMods.get(12).add(1, -13);
		rangeMods.get(13).add(1, -14);
		rangeMods.get(14).add(1, -15);

		// 1000 to 5000 yards
		rangeMods.get(15).add(1, -16);
		rangeMods.get(16).add(1, -17);
		rangeMods.get(17).add(1, -18);
		rangeMods.get(18).add(1, -19);
		rangeMods.get(19).add(1, -20);

		
		rangeMod = search(rangeMods, rangeInYards);
		rangeMod = rangeMod * 5; 
		
		return rangeMod;
	}

	// Gets concealment penalty
	public int getConcealmentMod(Unit targetUnit) {
		int mod = 0;
		String concealment = targetUnit.concealment;
		if (concealment.equals("Level 1")) {
			mod = -10;
		} else if (concealment.equals("Level 2")) {
			mod = -20;
		} else if (concealment.equals("Level 3")) {
			mod = -30;
		} else if (concealment.equals("Level 4")) {
			mod = -40;
		} else if (concealment.equals("Level 5")) {
			mod = -50;
		}

		return mod;
	}

	// Gets speed penalty
	public int getSpeedMod(Unit targetUnit) {
		int mod = 0;
		String speed = targetUnit.speed;
		if (speed.equals("Walk")) {
			mod = -5;
		} else if (speed.equals("Crawl")) {
			mod = 0;
		} else if (speed.equals("Rush")) {
			mod = -10;
		}

		return mod;
	}

	// Get target number
	public int getTN() {
		return TN;
	}

	// Get hits
	public int getHits() {
		return hits;
	}

	// Get hits
	public void calculateShots() {
		Random rand = new Random();
		for (int i = 0; i < shots; i++) {
			int roll = rand.nextInt(100) + 1;
			if (roll <= TN) {
				hits++;
			}
		}
	}

	// Searches an array of a table
	// Returns the item crossrefereanced in the table
	public int search(ArrayList<ArrayList<Integer>> arr, int target) {
		int value = 0;
		//System.out.println("Target: " + target);
		//System.out.println("Array: " + arr.toString());
		int temp1;
		int temp2;
		for (int i = 1; i <= arr.size() - 1; i++) {
			temp1 = arr.get(i).get(0);
			temp2 = arr.get(i - 1).get(0);
			if (target <= temp1 && target >= temp2) {
				value = arr.get(i - 1).get(1);
				//System.out.println("Value: " + value);
			}

		}

		return value;

	}
	
}
