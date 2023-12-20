package Vehicle.Combat;

import Conflict.GameWindow;
import UtilityClasses.DiceRoller;
import Vehicle.Vehicle;
import Vehicle.Data.CrewPosition;

public class VehicleShotCalculator {

	public static void fireVehicleTurret(Vehicle vehicle, CrewPosition crewPosition, 
			VehicleTurret turret) {
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
		
		int sl = crewPosition.crewMemeber.crewMember.sl;
		int aimValue = turret.getAimValue();
		int rangeHexes = turret.getRangeToTargetIn20YardHexes(vehicle);
		int rangeAlm = VehicleRangeAlm.getAlmForRange(rangeHexes);
		int sizeAlm = turret.vehicleAimTarget.getTargetSizeAlm(vehicle);
		
		var alm = sl + aimValue + rangeAlm;
		
		var odds = VehicleOddsOfHitting.getOddsOfHitting(alm);
		
		var roll = DiceRoller.roll(0, 99);
		
		String oddsResults = "Shooter "+crewPosition.crewMemeber.crewMember.name+
				", SL: " + sl + ", Aim Value: " + aimValue + ", Range Hexes: " + rangeHexes +
				", Range ALM: " +rangeAlm+", ALM: " + alm+", Odds: " + odds+", Roll: " + roll;
		
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
