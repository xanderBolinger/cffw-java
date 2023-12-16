package Vehicle.Spot;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import HexGrid.CalculateLOS;
import HexGrid.HexDirectionUtility;
import Spot.Utility.SpotActionResults;
import Spot.Utility.SpotModifiers;
import Spot.Utility.SpotUtility;
import Spot.Utility.SpotVisibility;
import Unit.Unit;
import Vehicle.Vehicle;
import Vehicle.Data.CrewPosition;
import Vehicle.Data.PositionSpotData;
import Vehicle.Data.CrewMember.CrewAction;
import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;

public class VehicleSpotCalculator {
	
	public static boolean hullDownRelativeHidden(Vehicle spotter, Vehicle target) {
		// vics in same hex
		if(target.movementData.location.compare(spotter.movementData.location) )
			return false;
		
		var spotterToTarget = hullDownRelative(spotter.movementData.location,
				target);
		System.out.println("Hulldown spotter to target: "+spotter.getVehicleCallsign()+", "+
				target.getVehicleCallsign()+", "+spotterToTarget);
		var targetToSpotter = hullDownRelative(target.movementData.location,
				spotter);
		System.out.println("Hulldown target to spotter: "+target.getVehicleCallsign()+", "+
				spotter.getVehicleCallsign()+", "+targetToSpotter);
		var spotterHidden = spotter.movementData.hullDownStatus == HullDownStatus.HIDDEN;
		System.out.println("Spotter hidden: "+spotterHidden);
		var targetHidden = target.movementData.hullDownStatus == HullDownStatus.HIDDEN;
		System.out.println("Target hideen: "+targetHidden);
		// spotter hull down in direction of 
		if(spotterToTarget && targetHidden || targetToSpotter && spotterHidden)
			return true;
		return false;
	}
	
	public static boolean hullDownRelative(Cord spotterCord, Vehicle target) {
		
		if(!target.movementData.hullDown())
			return false;
		
		var targetToSpotter = HexDirectionUtility.getHexSideFacingTarget(
				target.movementData.location, spotterCord);
		
		return target.movementData.hullDownPosition.protectedDirections.contains(targetToSpotter);
	}
	
	public static void spotVehicle(Vehicle spotter, Vehicle target) {
		var spotterCord = spotter.movementData.location;
		var targetCord = target.movementData.location;
		var direction = HexDirectionUtility.getHexSideFacingTarget(spotterCord, targetCord);
		
		for(var position : spotter.getCrewPositions()) {
			if(!position.getFieldOfView().contains(direction) || !position.occupied() || position.crewMemeber.currentAction != CrewAction.SPOT)
				continue;
			if(!position.spottedVehicles.contains(target))
				if(spotVehicleRoll(spotter, target, position))
					position.spottedVehicles.add(target);
		}
		
	}
	
	public static void spotInfantry(Vehicle spotter, Unit target) {
		var spotterCord = spotter.movementData.location;
		var targetCord = new Cord(target.X, target.Y);
		var direction = HexDirectionUtility.getHexSideFacingTarget(spotterCord, targetCord);
		
		for(var position : spotter.getCrewPositions()) {
			if(!position.getFieldOfView().contains(direction) || !position.occupied() || position.crewMemeber.currentAction != CrewAction.SPOT)
				continue;
			SpotInfantry.spotInfantry(spotter, target, position);
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
		int concealmentMod = getConcealmentMod(spotterCord, targetCord, spotterPosition.elevationAboveVehicle, target.altitude);
		
		// Fortifications 
		int fortMod = SpotModifiers.getFortificationMod(xCord, yCord);
		
		// Range
		int rangeMod = SpotModifiers.getRangeMod(spotterCord, targetCord);

		// Skill
		int skillMod = SpotModifiers.getSkillMod(spotterTrooper);

		// Calculation
		var hullDown = hullDownRelative(spotter.movementData.location, target);
		
		var PCSize = 
				getSizeMod(hullDown, spotterCord, targetCord, target);
		
		var vis = GameWindow.gameWindow.visibility;
		int targetSizeMod = SpotModifiers.getTargetSizeMod(PCSize);
		var dayWeatherMod = !vis.contains("Night") 
				&& !vis.contains("Dusk") ? SpotVisibility.getWeatherMod(vis) 
						: 0;
		var thermalMod = getThermalMod(spotter, target, spotterPosition);
		
		var spotterTrooperMagnification = SpotVisibility.getMagnificationMod(spotterTrooper);
		var vehiclePositionMagnification = SpotVisibility.getMagnificationMod(spotterPosition.spotData.magnification);
		
		var magnificationMod = spotterTrooperMagnification > vehiclePositionMagnification ? spotterTrooperMagnification : vehiclePositionMagnification;
		var nightWeatherMod = getNightTimeMods(spotter, target, spotterPosition.spotData);
		var firedMod = target.spotData.fired ? vis.toLowerCase().contains("night") ? -15 : -12 : 0;
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
					+ ", Spotter Unit Speed(MHPT): " + spotter.movementData.speed
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
	
	public static double getSizeMod(boolean hullDownRelative, Cord spotterCord,
			Cord targetCord, Vehicle target) {
		return spotterCord.compare(targetCord) == true 
				|| 
				hullDownRelative == false ? 
						target.spotData.hullSize + target.spotData.turretSize : 
			target.movementData.hullDownStatus == HullDownStatus.HULL_DOWN || target.movementData.hullDownStatus == HullDownStatus.PARTIAL_HULL_DOWN ?
					target.spotData.turretSize : target.spotData.turretSize / 2;
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
	
	public static int getConcealmentMod(Cord spotter, Cord target, int spotterElevationBonus, int targetElevationBonus) {
		int concealment = CalculateLOS.getConcealment(spotter, target, false, spotterElevationBonus, targetElevationBonus);
		return SpotModifiers.getConcealmentMod(concealment);
	}
	
	public static int getSpeedModifierTarget(Vehicle vehicle) {
		return vehicle.movementData.speed <= 0 ? 0 : vehicle.movementData.speed <= 1 ? -3 : vehicle.movementData.speed <= 2 ? -6 : -9;
	}
	
	public static int getSpeedModifierSpotter(Vehicle vehicle) {
		return vehicle.movementData.speed <= 0 ? 0 : vehicle.movementData.speed <= 1 ? 3 : vehicle.movementData.speed <= 2 ? 5 : 7;
	}
}
