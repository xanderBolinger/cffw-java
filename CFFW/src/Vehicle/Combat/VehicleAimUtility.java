package Vehicle.Combat;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import HexGrid.HexDirectionUtility;
import HexGrid.HexDirectionUtility.HexDirection;
import Unit.Unit;
import Vehicle.Vehicle;
import Vehicle.Utilities.VehicleDataUtility;

public class VehicleAimUtility {

	
	public static void resolveAimTargets(Vehicle vic) {
		
		for(var turret : vic.turretData.turrets) {
			if(turret.vehicleAimTarget != null && !turret.vehicleAimTarget.fired) {
				turret.vehicleAimTarget.timeSpentAiming++;
				GameWindow.gameWindow.conflictLog.addNewLineToQueue(vic.getVehicleCallsign() + " turret "+turret.turretName
						+" increased aim time.");
			}
		}
		
	}
	
	public static boolean turretFacingTarget(VehicleTurret turret, Vehicle spotter, Cord targetCord) {
		var facing = HexDirectionUtility.getHexSideFacingTarget(spotter.movementData.location, targetCord);
		return turretContainsFacing(turret, spotter, facing);
	}
	
	public static boolean turretFacingTarget(VehicleTurret turret, Vehicle spotter, Unit target) {
		var facing = HexDirectionUtility.getHexSideFacingTarget(spotter.movementData.location, new Cord(target.X, target.Y));

		return turretContainsFacing(turret, spotter, facing);
	}
	
	public static boolean turretFacingTarget(VehicleTurret turret, Vehicle spotter, Vehicle target) {		
		var facing = HexDirectionUtility.getHexSideFacingTarget(spotter.movementData.location, target.movementData.location);

		return turretContainsFacing(turret, spotter, facing);
	}
	
	private static boolean turretContainsFacing(VehicleTurret turret, Vehicle spotter, HexDirection facing) {
		
		//System.out.println("turret contains facing check, facing: "+facing);
		
		var turretFacing = VehicleDataUtility.getTurretFacing(turret, spotter);
		
		//System.out.println("turret facing: "+turretFacing);
		
		if(turretFacing == facing) {
			//System.out.println("return true 1");
			return true;
		}
		
		var facingWidth = Math.abs(turret.facingWidth/20);
		if(facingWidth < 0)
			facingWidth = 1;
		
		var lastFacing = turretFacing;
		
		for(int i = 0; i < facingWidth; i++) {
			var leftFacing = HexDirectionUtility.getFaceInDirection(lastFacing, false);
			//System.out.println("left facing: "+leftFacing);
			if(leftFacing == facing) {
				//System.out.println("return true 2");
				return true;
			}
			lastFacing = leftFacing;
		}
		
		lastFacing = turretFacing;
		
		for(int i = 0; i < facingWidth; i++) {
			var rightFacing = HexDirectionUtility.getFaceInDirection(lastFacing, true);
			//System.out.println("right facing: "+rightFacing);
			if(rightFacing == facing) {
				//System.out.println("return true 3");
				return true;
			}
			lastFacing = rightFacing;
		}
		
		//System.out.println("return false");
		return false;
		
	}
	
}
