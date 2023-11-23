package Spot.Utility;

import java.util.ArrayList;

import Conflict.GameWindow;
import Trooper.Trooper;
import Unit.Unit;
import Vehicle.Vehicle;
import Vehicle.VehicleSpotCalculator;
import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;
import CorditeExpansion.Cord;

public class InfantrySpotVehicleCalculator {
	
	public static void vehicleSpotCheck(Trooper trooper) {
		var initiativeOrder = GameWindow.gameWindow.initiativeOrder;
		var spotterUnit = trooper.returnTrooperUnit(initiativeOrder);
		var spotterCord = new Cord(spotterUnit.X, spotterUnit.Y);
		for(var vic : GameWindow.gameWindow.vehicleCombatWindow.vehicles) {
			var hullDown = VehicleSpotCalculator.hullDownRelative(spotterCord, vic);
			
			if((hullDown && vic.movementData.hullDownStatus == HullDownStatus.HIDDEN )
					|| spotterUnit.spottedVehicles.contains(vic))
				continue;
			
			var spotted = spotCheckInfantryToVehicle(trooper, spotterCord, spotterUnit,
					vic, hullDown);

			if(spotted)
				spotterUnit.spottedVehicles.add(vic);
			
		}
	}
	
	
	private static boolean spotCheckInfantryToVehicle(Trooper spotter, Cord spotterCord, 
			Unit spotterUnit, Vehicle targetVehicle, boolean hullDown) {
		
		
		// Size of target unit
		int size = 1;

		
		
		// Speed
		int speedModTarget = VehicleSpotCalculator.getSpeedModifierTarget(targetVehicle);
		
		int speedModSpotter = SpotModifiers.getSpeedModSpotter(spotterUnit.speed);		
		
		
		var targetCord = targetVehicle.movementData.location;
		
		// Concealment
		int concealmentMod = VehicleSpotCalculator.getConcealmentMod(spotterCord,
				targetCord);

		// Behavior 
		// Accounted for through difference in target size from prone to standing
		
		// Fortifications 
		int fortMod = SpotModifiers.getFortificationMod(targetCord.xCord, targetCord.yCord);
		
		// Range
		int rangeMod = SpotModifiers.getRangeMod(spotterCord, targetCord);

		// Skill
		int skillMod = SpotModifiers.getSkillMod(spotter);

		// Calculation
		var PCSize = VehicleSpotCalculator.getSizeMod(hullDown, spotterCord, targetCord, targetVehicle);
		
		int targetSizeMod = SpotModifiers.getTargetSizeMod(PCSize);

		var distanceYards =  GameWindow.hexDif(targetCord.xCord, targetCord.yCord, spotterUnit) * 20;
		var visibilityMod = 0;
		var thermalMod = SpotVisibility.getThermalModAgainstVehicle(spotter, distanceYards, targetVehicle);
		var magnificationMod = SpotVisibility.getMagnificationMod(spotter);
		var vis = GameWindow.gameWindow.visibility;
		var weatherMod = SpotVisibility.getWeatherMod(vis);
		
		int nightVisionEffectiveness = SpotVisibility.getNightVisionEffectiveness(spotter);
		var nightTimeMod = -(weatherMod < nightVisionEffectiveness ? 
				weatherMod : nightVisionEffectiveness);
		var tracerMod =  targetVehicle.spotData.fired ? vis.toLowerCase().contains("night") ? -15 : -12 : 0;
		var camoMod = targetVehicle.spotData.camo;
		var stealthField = !targetVehicle.spotData.fired ? targetVehicle.spotData.stealthField : 0;

		
		var SLM = SpotUtility.getSlm(speedModTarget, speedModSpotter, concealmentMod, rangeMod, visibilityMod, skillMod,
				targetSizeMod, fortMod);

		int suppression = spotterUnit != null ? spotterUnit.suppression : 0;
		
		try {
			SpotActionResults results = SpotUtility.getResults(size, "60 Degrees", SLM, suppression);
			var successesRoll = results.successRoll;
			var targetNumber = results.targetNumber;
			var success = results.success;
			var spotActionSpottingChance = results.spotActionSpottingChance;
			var spotActionDiceRoll = results.spotActionDiceRoll;
			int passes = results.success;

			var resultsString = "\nSpot Roll Between: "+GameWindow.getLogHead(spotter)+" to "+targetVehicle.getVehicleCallsign()
			+ ", ALM Size: " + PCSize + ", Target Unit Speed(MHPT): " + targetVehicle.movementData.speed
			+ ", Spotter Unit Speed: " + spotterUnit.speed
			+ ", Hex Range: " + GameWindow.hexDif(spotterCord.xCord, spotterCord.yCord, targetCord.xCord, targetCord.yCord) + "\n"
			+ "\n PC Spot Modifiers: "
			+ "Skill Test Mod: " + skillMod + ", Target Size Mod: " + targetSizeMod + ", Target Speed Mod: "
			+ speedModTarget + ", Spotter Speed Mod: " + speedModSpotter + ", Concealment Mod: "
			+ concealmentMod +", Fortification Mod: "+fortMod +", Range Mod: " + rangeMod + 
			", Visibility Mod: " + visibilityMod +"(Thermal: "+thermalMod+", Magnification: "+magnificationMod
			+", Night Weather: "+nightTimeMod+", Day Weather: "+weatherMod+", Fired: "+tracerMod
			+", Camo: "+camoMod+", Stealth Field: "+stealthField+")" +"\n"
			+ "SLM: " + SLM+", TN: "+targetNumber+", Roll: "+successesRoll+", Suppression: "+suppression;
			
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(resultsString);
			
			if(success >= 1) {
				GameWindow.gameWindow.conflictLog.addNewLineToQueue(
						GameWindow.getLogHead(spotter)+" spotted ["+targetVehicle.getVehicleCallsign()+"]");
				return true;
			}
			
			/*
			 * for(int i = 0; i < spottedIndividuals.size();i ++) {
			 * //System.out.println("\n Added Trooper 2: "+spottedIndividuals.get(i).
			 * number+" "+spottedIndividuals.get(i).name); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
}
