package Vehicle;

import java.util.ArrayList;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import HexGrid.CalculateLOS;
import HexGrid.HexDirectionUtility;
import Spot.Utility.SpotActionResults;
import Spot.Utility.SpotModifiers;
import Spot.Utility.SpotUtility;
import Spot.Utility.SpotVisibility;
import Trooper.Trooper;
import Unit.Unit;

public class VehicleSpotManager {

	public static void vehicleSpotChecks() {
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Vehicle Spot Tests: ");

		for(var vic : GameWindow.gameWindow.game.vehicleManager.getVehicles()) {
			
			for(var spotVic : vic.losVehicles) {
				if(vic.spottedVehicles.contains(spotVic))
					continue;
				spotVehicle(vic, spotVic);
			}
			
		}
		
	}
	
	private static void spotVehicle(Vehicle spotter, Vehicle target) {
		var spotterCord = spotter.movementData.location;
		var targetCord = target.movementData.location;
		var direction = HexDirectionUtility.getHexSideFacingTarget(spotterCord, targetCord);
		
		for(var position : spotter.getCrewPositions()) {
			if(!position.getFieldOfView().contains(direction) || !position.occupied())
				continue;
			if(!spotter.spottedVehicles.contains(target))
				if(spotVehicleRoll(spotter, target, position.crewMemeber.crewMember))
					spotter.spottedVehicles.add(target);
		}
		
	}
	
	private static boolean spotVehicleRoll(Vehicle spotter, Vehicle target, Trooper spotterTrooper) {
		var spotterCord = spotter.movementData.location;
		var targetCord = target.movementData.location;
		var xCord = targetCord.xCord;
		var yCord = targetCord.yCord;
		var scanArea = "60 Degrees";
		
		// Size of target unit
		//int size = SpotUtility.getTargetUnitSize(spotterUnit, spotableUnits);
		int size = 1;
		
		// Speed
		int speedModTarget = getSpeedModifierTarget(target);
		int speedModSpotter = getSpeedModifierSpotter(spotter);		

		// Concealment
		int concealmentMod = getConcealmentMod(spotterCord, targetCord);
		
		// Fortifications 
		int fortMod = SpotModifiers.getFortificationMod(xCord, yCord);
		
		// Range
		int rangeMod = SpotModifiers.getRangeMod(spotterCord, targetCord);

		// Skill
		int skillMod = SpotModifiers.getSkillMod(spotterTrooper);

		// Calculation
		var PCSize = (double) 11;
		int targetSizeMod = SpotModifiers.getTargetSizeMod(PCSize);
		var dayWeatherMod = !GameWindow.gameWindow.visibility.contains("Night") 
				&& !GameWindow.gameWindow.visibility.contains("Dusk") ? SpotVisibility.getWeatherMod(GameWindow.gameWindow.visibility) 
						: 0;
		var thermalMod = getThermalMod(spotter, target, spotterTrooper);
		var magnificationMod = SpotVisibility.getMagnificationMod(spotter.spotData.magnification);
		var nightWeatherMod = getNightTimeMods(spotter, target);
		var firedMod = target.spotData.fired ? -7 : 0;
		var camoMod = target.spotData.camo;
		var stealthFieldMod = !target.spotData.fired ? target.spotData.stealthField : 0;
		int visibilityMod = dayWeatherMod + thermalMod + magnificationMod + nightWeatherMod + firedMod + camoMod + stealthFieldMod;

		var SLM = SpotUtility.getSlm(speedModTarget, speedModSpotter, concealmentMod, rangeMod, visibilityMod, skillMod,
				targetSizeMod, fortMod);

		try {
			SpotActionResults results = SpotUtility.getResults(size, scanArea, SLM);
			var successesRoll = results.successRoll;
			var targetNumber = results.targetNumber;

			var resultsString = "Target Size: " + size + ", Average PC Size: " + PCSize + ", Target Unit Speed: " + "N/A"
					+ ", Spotter Unit Speed(MHPT): " + target.movementData.speed
					+ ", Hex Range: " + GameWindow.hexDif(spotterCord.xCord, spotterCord.yCord, targetCord.xCord, targetCord.yCord) + "\n"
					+ "\n PC Spot Modifiers: "
					+ "Skill Test Mod: " + skillMod + ", Target Size Mod: " + targetSizeMod + ", Target Speed Mod: "
					+ speedModTarget + ", Spotter Speed Mod: " + speedModSpotter + ", Concealment Mod: "
					+ concealmentMod +", Fortification Mod: "+fortMod +", Range Mod: " + rangeMod + 
					", Visibility Mod: " + visibilityMod +"(Thermal: "+thermalMod+", Magnification: "+magnificationMod
					+", Night Weather: "+nightWeatherMod+", Day Weather: "+dayWeatherMod+", Fired: "+firedMod
					+", Camo: "+camoMod+", Stealth Field: "+stealthFieldMod+")" +"\n"
					+ "SLM: " + SLM+", TN: "+targetNumber+", Roll: "+successesRoll;
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(resultsString);
			
			if(successesRoll <= targetNumber)
				return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static int getNightTimeMods(Vehicle spotter, Vehicle target) {
		int visibilityMod;

		var weather = GameWindow.gameWindow.visibility;
		
		int nightVisionEffectiveness = spotter.spotData.nightVisionGen;

		if (weather.contains("Night") && nightVisionEffectiveness > 0) {
			var mod = SpotVisibility.getWeatherMod(weather) - nightVisionEffectiveness;
			visibilityMod = mod > 0 ? 
					mod : 2;
		} else {
			visibilityMod = SpotVisibility.getWeatherMod(weather);
		}

		return visibilityMod;
	}
	
	private static int getThermalMod(Vehicle spotter, Vehicle target, Trooper spotterTrooper) {
		
		int thermalMod = spotterTrooper.thermalVision && 5 > spotter.spotData.thermalMod 
				? 5 : spotter.spotData.thermalMod;
		
		switch(target.spotData.thermalShroud) {
		
		case Impervious:
			return 0;
		case None:
			return -thermalMod;
		case Resistant:
			return thermalMod - 4  > 0 ? -thermalMod : 0;
		default:
			break;
		
		}
		
		return 0;
		
	}
	
	private static int getConcealmentMod(Cord spotter, Cord target) {
		int concealment = CalculateLOS.getConcealment(spotter, target, false);
		return SpotModifiers.getConcealmentMod(concealment);
	}
	
	private static int getSpeedModifierTarget(Vehicle vehicle) {
		return vehicle.movementData.speed <= 0 ? 0 : vehicle.movementData.speed <= 1 ? -3 : vehicle.movementData.speed <= 2 ? -6 : -9;
	}
	
	private static int getSpeedModifierSpotter(Vehicle vehicle) {
		return vehicle.movementData.speed <= 0 ? 0 : vehicle.movementData.speed <= 1 ? 3 : vehicle.movementData.speed <= 2 ? 5 : 7;
	}
	
}
