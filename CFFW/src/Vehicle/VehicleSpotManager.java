package Vehicle;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import HexGrid.CalculateLOS;
import HexGrid.HexDirectionUtility;
import Spot.Utility.SpotActionResults;
import Spot.Utility.SpotModifiers;
import Spot.Utility.SpotUtility;
import Spot.Utility.SpotVisibility;
import Trooper.Trooper;
import Vehicle.Data.CrewMember.CrewAction;
import Vehicle.Data.CrewPosition;
import Vehicle.Data.PositionSpotData;
import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;

public class VehicleSpotManager {

	public static void vehicleSpotChecks() {
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Vehicle Spot Tests: ");

		for(var vic : GameWindow.gameWindow.game.vehicleManager.getVehicles()) {
			
			for(var spotVic : vic.losVehicles) {
				if(vic.spottedVehicles.contains(spotVic) 
						|| (!spotVic.movementData.location.compare(vic.movementData.location) 
							&& 
								(spotVic.movementData.hullDown() && spotVic.movementData.hullDownStatus == HullDownStatus.HIDDEN) 
								||
								vic.movementData.hullDown() && vic.movementData.hullDownStatus == HullDownStatus.HIDDEN))
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
			if(!position.getFieldOfView().contains(direction) || !position.occupied() || position.crewMemeber.currentAction != CrewAction.SPOT)
				continue;
			if(!spotter.spottedVehicles.contains(target))
				if(spotVehicleRoll(spotter, target, position))
					spotter.spottedVehicles.add(target);
		}
		
	}
	
	private static boolean spotVehicleRoll(Vehicle spotter, Vehicle target, CrewPosition spotterPosition) {
		var spotterCord = spotter.movementData.location;
		var targetCord = target.movementData.location;
		var xCord = targetCord.xCord;
		var yCord = targetCord.yCord;
		var scanArea = "60 Degrees";
		
		var spotterTrooper = spotterPosition.crewMemeber.crewMember;
		
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
		var PCSize = spotterCord.compare(targetCord) == true || target.movementData.hullDown() == false ? target.spotData.hullSize + target.spotData.turretSize : 
			target.movementData.hullDownStatus == HullDownStatus.HULL_DOWN || target.movementData.hullDownStatus == HullDownStatus.PARTIAL_HULL_DOWN ?
					target.spotData.turretSize : target.spotData.turretSize / 2;
		int targetSizeMod = SpotModifiers.getTargetSizeMod(PCSize);
		var dayWeatherMod = !GameWindow.gameWindow.visibility.contains("Night") 
				&& !GameWindow.gameWindow.visibility.contains("Dusk") ? SpotVisibility.getWeatherMod(GameWindow.gameWindow.visibility) 
						: 0;
		var thermalMod = getThermalMod(spotter, target, spotterPosition);
		
		var spotterTrooperMagnification = SpotVisibility.getMagnificationMod(spotterTrooper);
		var vehiclePositionMagnification = SpotVisibility.getMagnificationMod(spotterPosition.spotData.magnification);
		
		var magnificationMod = spotterTrooperMagnification > vehiclePositionMagnification ? spotterTrooperMagnification : vehiclePositionMagnification;
		var nightWeatherMod = getNightTimeMods(spotter, target, spotterPosition.spotData);
		var firedMod = GameWindow.gameWindow.visibility.toLowerCase().contains("night") ? -15 : -12;
		var camoMod = target.spotData.camo;
		var stealthFieldMod = !target.spotData.fired ? target.spotData.stealthField : 0;
		int visibilityMod = dayWeatherMod + thermalMod + magnificationMod + nightWeatherMod + firedMod + camoMod + stealthFieldMod;

		var SLM = SpotUtility.getSlm(speedModTarget, speedModSpotter, concealmentMod, rangeMod, visibilityMod, skillMod,
				targetSizeMod, fortMod);

		try {
			SpotActionResults results = SpotUtility.getResults(size, scanArea, SLM,0);
			var successesRoll = results.successRoll;
			var targetNumber = results.targetNumber;

			var resultsString = "\nSpot Roll Between: "+spotter.getVehicleCallsign()+" to "+target.getVehicleCallsign()
					+ ", ALM Size: " + PCSize + ", Target Unit Speed(MHPT): " + target.movementData.speed
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
	
	public static int getNightTimeMods(Vehicle spotter, Vehicle target, PositionSpotData spotData) {
		int visibilityMod;

		var weather = GameWindow.gameWindow.visibility;
		
		int nightVisionEffectiveness = spotData.nightVisionGen;

		if (weather.contains("Night") && nightVisionEffectiveness > 0) {
			var mod = SpotVisibility.getWeatherMod(weather) - nightVisionEffectiveness;
			visibilityMod = mod > 0 ? 
					mod : 2;
		} else {
			visibilityMod = SpotVisibility.getWeatherMod(weather);
		}

		return visibilityMod;
	}
	
	private static int getThermalMod(Vehicle spotter, Vehicle target, CrewPosition position) {
		
		var spotterTrooper = position.crewMemeber.crewMember;
		var spotData = position.spotData;
		
		int thermalMod = spotterTrooper.thermalVision && 5 > spotData.thermalMod 
				? 5 : spotData.thermalMod;
		
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
