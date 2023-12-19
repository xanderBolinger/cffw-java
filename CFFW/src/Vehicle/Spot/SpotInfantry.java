package Vehicle.Spot;

import java.util.ArrayList;

import Conflict.GameWindow;
import Spot.Utility.SpotActionResults;
import Spot.Utility.SpotModifiers;
import Spot.Utility.SpotUtility;
import Spot.Utility.SpotVisibility;
import Trooper.Trooper;
import Unit.Unit;
import Vehicle.Vehicle;
import Vehicle.Data.CrewPosition;
import Vehicle.Data.PositionSpotData;
import CorditeExpansion.Cord;

public class SpotInfantry {

	public static void spotInfantry(Vehicle spotter, Unit targetUnit, 
			CrewPosition spotterPosition) {
		ArrayList<Trooper> spotableTroopers = getTargetTroopers(spotter,
				spotter.movementData.location, targetUnit);
		var passes = spotVehicleRollSuccesses(spotter, targetUnit, spotterPosition, spotableTroopers);
		
		ArrayList<Trooper> spottedTroopers = new ArrayList<Trooper>();
		
		try {
			while(SpotUtility.findingSpottedTroopers)
				Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		SpotUtility.findSpottedTroopers(spottedTroopers, passes, spotableTroopers);
		
		for(var t : spottedTroopers) {
			if(!spotterPosition.spottedTroopers.contains(t))
				spotterPosition.spottedTroopers.add(t);
		}
		
		String spottedTroopersString = spotter.getVehicleCallsign()+" spotted troopers: [";
		
		for(var t : spottedTroopers)
			spottedTroopersString += GameWindow.getLogHead(t) 
				+ (spottedTroopers.get(spottedTroopers.size()-1).compareTo(t) ? "" : ", ");
		
		spottedTroopersString += "]";
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue(spottedTroopersString);
		
	}
	
	private static int spotVehicleRollSuccesses(Vehicle spotter, Unit targetUnit, 
			CrewPosition spotterPosition, ArrayList<Trooper> spotableTroopers) {
		var spotterCord = spotter.movementData.location;
		var targetCord = new Cord(targetUnit.X, targetUnit.Y);
		var xCord = targetUnit.X;
		var yCord = targetUnit.Y;
		var scanArea = "60 Degrees";
		
		var spotterTrooper = spotterPosition.crewMemeber.crewMember;
		
		// Size of target unit
		//int size = SpotUtility.getTargetUnitSize(spotterUnit, spotableUnits);
		int size = targetUnit.individuals.size();
		
		// Speed
		int speedModTarget = SpotModifiers.getSpeedModTarget(targetUnit);
		int speedModSpotter = VehicleSpotCalculator.getSpeedModifierSpotter(spotter);		

		// Concealment
		int concealmentMod = VehicleSpotCalculator.getConcealmentMod(spotterCord, targetCord, spotterPosition.elevationAboveVehicle,0);
		
		// Fortifications 
		int fortMod = SpotModifiers.getFortificationMod(xCord, yCord);
		
		// Range
		int rangeMod = SpotModifiers.getRangeMod(spotterCord, targetCord);

		// Skill
		int skillMod = SpotModifiers.getSkillMod(spotterTrooper);

		// Calculation
		var PCSize = SpotModifiers.getPcSize(spotableTroopers);
		
		var vis = GameWindow.gameWindow.visibility;
		int targetSizeMod = SpotModifiers.getTargetSizeMod(PCSize);
		var dayWeatherMod = !vis.contains("Night") 
				&& !vis.contains("Dusk") ? SpotVisibility.getWeatherMod(vis) 
						: 0;
		var thermalMod = getThermalMod(spotter, targetUnit, spotterPosition);
		
		var spotterTrooperMagnification = SpotVisibility.getMagnificationMod(spotterTrooper);
		var vehiclePositionMagnification = SpotVisibility.getMagnificationMod(spotterPosition.spotData.magnification);
		
		var magnificationMod = spotterTrooperMagnification > vehiclePositionMagnification ? spotterTrooperMagnification : vehiclePositionMagnification;
		
		var nightWeatherMod = getNightTimeMods(spotter, targetUnit, spotterPosition.spotData);
		
		var firedMod = getTracerMod(targetUnit);
		
		var armorMod = armorModifier(targetUnit);
		
		var camoMod = camoMod(targetUnit);
		var stealthFieldMod = firedMod == 0 ? stealthFieldMod(targetUnit) : 0;
		
		var thermalEquipped = VehicleSpotCalculator.isThermalEquipped(spotter, spotterPosition);
		var smokeMod = SpotVisibility.getSmokeModifier(thermalEquipped,
				spotterCord, targetCord);
		
		int visibilityMod = smokeMod + armorMod + dayWeatherMod + thermalMod + magnificationMod + nightWeatherMod + firedMod + camoMod + stealthFieldMod;

		var SLM = SpotUtility.getSlm(speedModTarget, speedModSpotter, concealmentMod, rangeMod, visibilityMod, skillMod,
				targetSizeMod, fortMod);

		try {
			SpotActionResults results = SpotUtility.getResults(size, scanArea, SLM,0);
			var successesRoll = results.successRoll;
			var targetNumber = results.targetNumber;
			

			var resultsString = "\nSpot Roll Between: "+spotter.getVehicleCallsign()+" to "+targetUnit.callsign
					+ ", ALM Size: " + PCSize + ", Target Unit Speed: " + targetUnit.speed
					+ ", Spotter Unit Speed(MHPT): " + spotter.movementData.speed
					+ ", Hex Range: " + GameWindow.hexDif(spotterCord.xCord, spotterCord.yCord, targetCord.xCord, 
							targetCord.yCord) + "\n"
					+ "\n PC Spot Modifiers: "
					+ "Skill Test Mod: " + skillMod + ", Target Size Mod: " + targetSizeMod + ", Target Speed Mod: "
					+ speedModTarget + ", Spotter Speed Mod: " + speedModSpotter + ", Concealment Mod: "
					+ concealmentMod +", Fortification Mod: "+fortMod +", Range Mod: " + rangeMod + 
					", Visibility Mod: " + visibilityMod +"(Thermal: "+thermalMod+", Magnification: "+magnificationMod
					+", Smoke: "+smokeMod+" (thermal equipped: "+thermalEquipped+")"
					+", Night Weather: "+nightWeatherMod+", Day Weather: "+dayWeatherMod+", Fired: "+firedMod
					+", Camo: "+camoMod+", Stealth Field: "+stealthFieldMod+"), Armor Mod: "+armorMod +"\n"
					+ "SLM: " + SLM+", TN: "+targetNumber+", Roll: "+successesRoll;
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(resultsString);
			
			return results.success;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	private static int getTracerMod(Unit targetUnit) {

		for (var trooper : targetUnit.individuals) {
			if (trooper.firedTracers) {
				var val = GameWindow.gameWindow.visibility.toLowerCase().contains("night") ? -15 : -12;
				return val;
			}
		}

		return 0;
	}
	
	private static int getThermalMod(Vehicle spotter, Unit targetUnit, CrewPosition position) {
		
		var spotterTrooper = position.crewMemeber.crewMember;
		var spotData = position.spotData;
		
		int thermalMod = spotterTrooper.thermalVision && 5 > spotData.thermalMod 
				? 5 : spotData.thermalMod;
		
		if(SpotVisibility.isUnitThermalShrouded(targetUnit))
			return 0;
		
		return -thermalMod;
		
	}
	
	private static ArrayList<Trooper> getTargetTroopers(Vehicle spotter, Cord spotterCord, Unit targetUnit) {
		ArrayList<Trooper> targetTroopers = new ArrayList<Trooper>();

		boolean sameHex = spotterCord.xCord == targetUnit.X 
				&& spotterCord.yCord == targetUnit.Y;
		if (targetUnit.getSize() > 0) {
			for (Trooper trooper : targetUnit.getTroopers()) {
				if(spotter.getSpottedTroopers().contains(trooper))
					continue;
				if (sameHex && trooper.alive && trooper.conscious) {
					targetTroopers.add(trooper);
					continue;
				}

				if (!trooper.HD && trooper.alive && trooper.conscious)
					targetTroopers.add(trooper);

			}

		}

		return targetTroopers;
	}
	
	private static int getNightTimeMods(Vehicle spotter, Unit targetUnit, PositionSpotData spotData) {
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

		boolean targetsUsingflashLight = false;
		boolean targetsUsingIRlaser = false;

		for (Trooper trooper : targetUnit.individuals) {
			if (trooper.weaponIRLaserOn || trooper.weaponLaserOn)
				targetsUsingIRlaser = true;
			if (trooper.weaponLightOn)
				targetsUsingflashLight = true;
		}

		if (targetsUsingflashLight) {
			visibilityMod -= 5;
		} else if(targetsUsingIRlaser && nightVisionEffectiveness > 0) {
			visibilityMod -= 5;
		}
		
		return visibilityMod;
	}
	
	private static int armorModifier(Unit targetUnit) {
		int mod = 0;
		int count = 0;

		for (var trooper : targetUnit.individuals) {
			mod += trooper.armor != null ? trooper.armor.visibilityModifier : 0;
			count++;
		}

		mod /= count;

		return mod;
	}
	
	private static int camoMod(Unit targetUnit) {
		int mod = 0;
		int count = 0;

		for (var trooper : targetUnit.individuals) {
			mod += trooper.spottingDifficulty;
			for (var item : trooper.inventory.getItemsArray()) {
				if (item.camouflage)
					mod += item.camoMod;
			}

			count++;
		}

		mod /= count;

		return mod;
	}
	
	public static int stealthFieldMod(Unit targetUnit) {
		int mod = 0;
		int count = 0;

		for (var trooper : targetUnit.getTroopers()) {
			for (var item : trooper.inventory.getItemsArray()) {
				if (!item.cloakField || (!targetUnit.speed.equals("None") && item.movementDisabled)) {
					count++;
					continue;
				}

				count++;
				mod += item.cloakSlm / (trooper.firedWeapons ? 2 : 1);
			}
		}

		mod /= count;
		return mod;
	}
	
	
}
