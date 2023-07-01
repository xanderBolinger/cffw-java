package Actions;

import Unit.Unit;
import Trooper.Trooper;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Conflict.GameWindow;
import HexGrid.CalculateLOS;
import Hexes.Building;
import Hexes.Hex;
import Items.Weapons;
import Spot.Utility.SpotActionResults;
import Spot.Utility.SpotModifiers;
import Spot.Utility.SpotUtility;
import Spot.Utility.SpotVisibility;

public class Spot implements Serializable {
	// Unit spotted unit
	public Unit spottedUnit;
	// Spotting unit belonging to the spotter
	public Unit spotterUnit;

	// Spotted Units
	public ArrayList<Unit> spottedUnits = new ArrayList<Unit>();

	// Units spotted individuals
	public ArrayList<Trooper> spottedIndividuals = new ArrayList<Trooper>();

	public int success;
	public int successesRoll;
	public int targetNumber;
	public double PCSize;
	public int SLM;

	public String visibilityModifications = "";
	public String resultsString = "";
	public int spotActionSpottingChance;
	public int spotActionDiceRoll;

	private transient Unit canSpotSpotterUnit;

	// Basic constructor
	public Spot() {

	}

	// Gets speed
	// Gets concealment
	// Gets behavior
	// Gets skill diff
	// Makes calculations
	// Margin 10
	// Stores spotted individuals in the spotted modifier array
	public Spot(Unit spotterUnit, int xCord, int yCord, Trooper spotter, String scanArea, String weather,
			ArrayList<Unit> initiativeOrder, GameWindow game) {
		// System.out.println("Multi Unit Hex Spot");

		this.spotterUnit = spotterUnit;
		this.spottedUnit = null;

		// Gets target units
		ArrayList<Unit> spotableUnits = SpotUtility.getTargetUnitsInHex(spotterUnit, initiativeOrder, xCord, yCord);

		if (spotableUnits.size() <= 0) {
			return;
		}

		performSpotAction(spotableUnits, spotter, initiativeOrder, weather, xCord, yCord, scanArea);
	}

	// Gets speed
	// Gets concealment
	// Gets behavior
	// Gets skill diff
	// Makes calculations
	// Margin 10
	// Stores spotted individuals in the spotted modifier array
	public Spot(GameWindow gameWindow, Unit spotterUnit, Unit targetUnit, Trooper spotter, String scanArea,
			String weather, ArrayList<Unit> initOrder, GameWindow game) {

		// Sets spotted unit
		this.spottedUnit = targetUnit;
		this.spotterUnit = spotterUnit;
		performSpotAction(new ArrayList<Unit>(Arrays.asList(targetUnit)), spotter, initOrder, weather, targetUnit.X, targetUnit.Y, scanArea);
		// System.out.println("Spot Passing 4");

	}

	private void performSpotAction(ArrayList<Unit> spotableUnits, Trooper spotter, ArrayList<Unit> initiativeOrder, String weather,
			int xCord, int yCord, String scanArea) {
		// Size of target unit
		int size = SpotUtility.getTargetUnitSize(spotterUnit, spotableUnits);

		// Speed
		int speedModTarget = SpotModifiers.getSpeedModTarget(spotableUnits);
		int speedModSpotter = SpotModifiers.getSpeedModSpotter(spotter.returnTrooperUnit(initiativeOrder).speed);		
		
		ArrayList<Trooper> spotableTroopers = SpotUtility.getTargetTroopers(spotterUnit, spotableUnits);

		// Concealment
		int concealmentMod = SpotModifiers.getConcealmentMod(spotableUnits, spotableTroopers);

		// Behavior

		// Range
		int rangeMod = SpotModifiers.getRangeMod(spotableUnits.get(0), spotterUnit);

		// Skill
		int skillMod = SpotModifiers.getSkillMod(spotter);

		// Calculation
		PCSize = SpotModifiers.getPcSize(spotableTroopers);
		int targetSizeMod = SpotModifiers.getTargetSizeMod(PCSize, spotableTroopers);

		int visibilityMod = SpotVisibility.getVisibilityMod(spotter, spotterUnit, 
				weather, xCord, yCord, spotableUnits);

		visibilityModifications = SpotVisibility.visibilityModifications;
		
		SLM = SpotUtility.getSlm(speedModTarget, speedModSpotter, concealmentMod, rangeMod, visibilityMod, skillMod,
				targetSizeMod);

		try {
			SpotActionResults results = SpotUtility.getResults(size, scanArea, SLM);
			this.successesRoll = results.successRoll;
			this.targetNumber = results.targetNumber;
			this.success = results.success;
			this.spotActionSpottingChance = results.spotActionSpottingChance;
			this.spotActionDiceRoll = results.spotActionDiceRoll;
			int passes = results.success;

			SpotUtility.findSpottedTroopers(spottedIndividuals, spottedUnits, passes, spotableTroopers, spotableUnits,
					size, successesRoll, targetNumber, spotableUnits);

			resultsString += "Target Size: " + size + ", Average PC Size: " + PCSize + ", Target Unit Speed: " + "N/A"
					+ ", Spotter Unit Speed: " + "N/A" + ", Target Concealment: " + spotableUnits.get(0).concealment
					+ ", Hex Range: " + GameWindow.hexDif(spotableUnits.get(0), spotterUnit) + "\n"
					+ "Visibility Modifications: " + visibilityModifications + "\n PC Spot Modifiers: "
					+ "Skill Test Mod: " + skillMod + ", Target Size Mod: " + targetSizeMod + ", Target Speed Mod: "
					+ speedModTarget + ", Spotter Speed Mod: " + speedModSpotter + ", Concealment Mod: "
					+ concealmentMod + ", Range Mod: " + rangeMod + ", Visibility Mod: " + visibilityMod + "\n"
					+ "SLM: " + SLM;

			/*
			 * for(int i = 0; i < spottedIndividuals.size();i ++) {
			 * //System.out.println("\n Added Trooper 2: "+spottedIndividuals.get(i).
			 * number+" "+spottedIndividuals.get(i).name); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// Returns true if the trooper can be spotted
	public boolean canSpot(Trooper trooper, int successesRoll, int spottingDiff, int targetNumber) {
		boolean sameHex = false;
		if (canSpotSpotterUnit != null) {
			sameHex = canSpotSpotterUnit.X == trooper.returnTrooperUnit(GameWindow.gameWindow).X
					&& canSpotSpotterUnit.Y == trooper.returnTrooperUnit(GameWindow.gameWindow).Y;
		}

		if ((trooper.HD && !sameHex) || trooper.unspottable || successesRoll - spottingDiff >= targetNumber
				|| !trooper.alive || !trooper.conscious) {
			return false;
		} else
			return true;

	}


	

	public void displayResultsQueue(GameWindow gameWindow, Spot spotAction) {

		// Print results
		gameWindow.conflictLog.addNewLineToQueue("Spotted: \n" + "Results: " + resultsString + "\n" + "Target Number: "
				+ spotActionSpottingChance + " Roll: " + spotActionDiceRoll + "\nSuccess: " + spotAction.success + "\n"
				+ spotAction.spottedIndividuals.toString());

	}

	public void displayResults(GameWindow gameWindow, Spot spotAction) {

		// Print results
		gameWindow.conflictLog.addNewLine("Spotted: \n" + "Results: " + resultsString + "\n" + "Target Number: "
				+ spotActionSpottingChance + " Roll: " + spotActionDiceRoll + "\nSuccess: " + spotAction.success + "\n"
				+ spotAction.spottedIndividuals.toString());

	}

}
