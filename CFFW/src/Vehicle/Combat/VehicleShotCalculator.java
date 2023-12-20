package Vehicle.Combat;

import Conflict.GameWindow;
import Spot.Utility.SpotVisibility;
import UtilityClasses.DiceRoller;
import Vehicle.Vehicle;
import Vehicle.Data.CrewPosition;
import Vehicle.Spot.VehicleSpotCalculator;

public class VehicleShotCalculator {

	public static void fireVehicleTurret(Vehicle vehicle, CrewPosition crewPosition, 
			VehicleTurret turret, int ammoIndex) {
		if(turret.vehicleAimTarget == null) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(vehicle.getVehicleCallsign() +
					" Turret: "+turret.toString()+", could not fire no aim target.");
			return;
		} else if(turret.vehicleAimTarget.fired) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(vehicle.getVehicleCallsign() +
					" Turret: "+turret.toString()+", could not fire already fired.");
			return;
		}

		var target = turret.vehicleAimTarget;
		target.fired = true;
		var ammo = turret.ammunitionTypes.get(ammoIndex);
		int rangeHexes = turret.getRangeToTargetIn20YardHexes(vehicle);
		
		int sl = crewPosition.crewMemeber.crewMember.sl;
		int aimValue = turret.getAimValue();
		int rangeAlm = VehicleRangeAlm.getAlmForRange(rangeHexes);
		var nightWeatherMod = VehicleSpotCalculator.getNightTimeMods(vehicle, crewPosition.spotData);
		var smokeMod = SpotVisibility.getSmokeModifier(
				VehicleSpotCalculator.isThermalEquipped(vehicle, crewPosition),
				vehicle.movementData.location, target.getTargetCord());
		var visibilityAlm = -nightWeatherMod - smokeMod;
		var alm = sl + aimValue + rangeAlm + visibilityAlm; 

		int sizeAlm = target.getTargetSizeAlm(vehicle);
		int balisticAccuracy = ammo.getBalisticAccuracy(rangeHexes);
		
		var eal = balisticAccuracy < alm ? balisticAccuracy + sizeAlm : alm + sizeAlm;
		
		var odds = VehicleOddsOfHitting.getOddsOfHitting(eal);
		
		var roll = DiceRoller.roll(0, 99);
		
		String oddsResults = "Shooter "+crewPosition.crewMemeber.crewMember.name+
				", SL: " + sl + ", Aim Value: " + aimValue + ", Range Hexes: " + rangeHexes +
				", Range ALM: " +rangeAlm+", BA: "+balisticAccuracy+", Size ALM: "+sizeAlm
				+", ALM: " + alm+", EAL: "+eal+", Odds: " + odds+", Roll: " + roll;
		
		resolveShot(roll, odds, vehicle, turret, oddsResults);
	}
	
	private static void resolveShot(int roll, int odds, Vehicle vehicle, VehicleTurret
			turret, String oddsResults) {
		
		if(roll <= odds) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(vehicle.getVehicleCallsign()
					+" shot: "+turret.toString()+", HIT. " + oddsResults);
		} else {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(vehicle.getVehicleCallsign()
					+" shot: "+turret.toString()+", Miss. " + oddsResults);
		}
	}
	
}
