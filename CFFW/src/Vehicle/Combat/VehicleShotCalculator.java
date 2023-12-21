package Vehicle.Combat;

import Conflict.GameWindow;
import Spot.Utility.SpotVisibility;
import UtilityClasses.DiceRoller;
import Vehicle.Vehicle;
import Vehicle.Combat.VehicleAmmo.VehicleAmmoType;
import Vehicle.Data.CrewPosition;
import Vehicle.Spot.VehicleSpotCalculator;

public class VehicleShotCalculator {

	public static void fireVehicleTurret(Vehicle vehicle, CrewPosition crewPosition, 
			VehicleTurret turret, int ammoIndex) {
		if(turret.vehicleAimTarget == null) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(vehicle.getVehicleCallsign() +
					" Turret: "+turret.toString()+", could not fire no aim target.");
			return;
		} else if(turret.fired) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(vehicle.getVehicleCallsign() +
					" Turret: "+turret.toString()+", could not fire already fired or loading.");
			return;
		}

		var target = turret.vehicleAimTarget;
		turret.fired = true;
		turret.timeSpentReloading = 0;
		vehicle.spotData.fired = true;
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

		var speed = target.getTargetSpeedInHexesPerTurn();
		
		int movingTargetValue = getMovingTargetAccuracy(target, rangeHexes, ammo.ammoType,
				turret.movingTargetAccuracyMod, speed);
		
		int movingShooterValue = vehicle.movementData.speed == 0 ? 
				VehicleMovingShooterAccuracy.getMovingShooterAccuracy(
						rangeHexes, Math.abs(vehicle.movementData.speed)) + turret.movingShooterAccuracyMod
				: Integer.MAX_VALUE;
		
		int eal = alm + sizeAlm;
		
		if(movingTargetValue < alm && movingTargetValue < balisticAccuracy 
				&& movingTargetValue < movingShooterValue)
			eal = movingTargetValue + sizeAlm;
		else if(balisticAccuracy < alm && balisticAccuracy < movingTargetValue
				&& balisticAccuracy < movingShooterValue)
			eal = balisticAccuracy + sizeAlm;
		else if(movingShooterValue < alm && movingShooterValue < balisticAccuracy &&
				movingShooterValue < movingTargetValue)
			eal = movingShooterValue + sizeAlm;
		
		var odds = VehicleOddsOfHitting.getOddsOfHitting(eal);
		
		var roll = DiceRoller.roll(0, 99);
		
		String oddsResults = "Shooter "+crewPosition.crewMemeber.crewMember.name+
				", SL: " + sl + ", Aim Value: " + aimValue + ", Range Hexes: " + rangeHexes +
				", Range ALM: " +rangeAlm+", BA: "+balisticAccuracy+", Size ALM: "+sizeAlm
				+", ALM: " + alm + (movingTargetValue != Integer.MAX_VALUE 
					? ", MTA: " + movingTargetValue : "") + ", Target Speed: " + speed + 
				(movingShooterValue != Integer.MAX_VALUE 
				? ", MSTA: " + movingShooterValue : "") + ", Shooter Speed: "+ vehicle.movementData.speed
				+", EAL: "+eal+", Odds: " + odds+", Roll: " + roll;
		
		resolveShot(roll, odds, vehicle, turret, oddsResults);
	}
	
	
	private static int getMovingTargetAccuracy(VehicleAimTarget target, int rangeHexes, 
			VehicleAmmoType ammoType,
			int movingTargetModifier,
			int speed) {
		
		if(speed == 0)
			return Integer.MAX_VALUE;
		
		return VehicleMovingTargetAccuracy.getMovingTargetAccuracy(ammoType, rangeHexes, Math.abs(speed))
				+ movingTargetModifier;
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
