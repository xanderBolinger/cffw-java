package Vehicle.Combat;

import Conflict.GameWindow;
import Vehicle.Vehicle;
import Vehicle.Combat.VehicleAmmo.VehicleAmmoType;
import Vehicle.Data.CrewPosition;

public class VehicleShotCalculator {

	public static void fireVehicleTurret(Vehicle vehicle, CrewPosition crewPosition, 
			VehicleTurret turret, int ammoIndex, int shotsFired) {
		if(turret.vehicleAimTarget == null) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(vehicle.getVehicleCallsign() +
					" Turret: "+turret.toString()+", could not fire no aim target.");
			return;
		} else if(turret.fired) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(vehicle.getVehicleCallsign() +
					" Turret: "+turret.toString()+", could not fire already fired or loading.");
			return;
		}

		var odds = new VehicleOddsOfHitting(vehicle, crewPosition, turret, ammoIndex, shotsFired);
		
		odds.roll();
		
		resolveShot(odds, vehicle, turret);
	}
	
	
	public static int getMovingTargetAccuracy(VehicleAimTarget target, int rangeHexes, 
			VehicleAmmoType ammoType,
			int movingTargetModifier,
			int speed) {
		
		if(speed == 0)
			return Integer.MAX_VALUE;
		
		return VehicleMovingTargetAccuracy.getMovingTargetAccuracy(ammoType, rangeHexes, Math.abs(speed))
				+ movingTargetModifier;
	}
	
	private static void resolveShot(VehicleOddsOfHitting odds, Vehicle vehicle, VehicleTurret
			turret) {
		
		odds.getHits();
		
		if(odds.shotsHit > 0) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(vehicle.getVehicleCallsign()
					+" shot: "+turret.toString()+", "+odds.getOddsResults()+", HIT(s): " +odds.shotsHit);
		} else {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(vehicle.getVehicleCallsign()
					+" shot: "+turret.toString()+ odds.getOddsResults()+", Miss. " );
		}
	}
	
}
