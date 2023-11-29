package Vehicle;

import java.io.Serializable;

import CeHexGrid.Chit;
import CeHexGrid.Chit.Facing;
import Conflict.GameWindow;
import HexGrid.CalculateLOS;
import HexGrid.HexDirectionUtility;
import HexGrid.HexDirectionUtility.HexDirection;
import Vehicle.Data.VehicleMovementData;
import Vehicle.HullDownPositions.HullDownPosition.HullDownDecision;
import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;

public class VehicleMovement implements Serializable {

	
	public static void moveVehicle(Vehicle vehicle) {
		
		var md = vehicle.movementData;
		
		if(vehicle.smokeData.trailingSmokeActive && md.speed == 0) {
			vehicle.smokeData.deployTrailingSmoke();
		}
		
		for(int i = 0; i < md.speed; i++) {
			var cord = HexDirectionUtility.getHexInDirection(md.facing, md.location, md.movedClockwise);
			var hex = GameWindow.gameWindow.findHex(cord.xCord, cord.yCord);
			md.movedClockwise = !md.movedClockwise;
			if(hex == null)
				continue;

			md.enterHex(hex);
		}

		updateHullDown(md);
		
		updateChit(vehicle);
		
		CalculateLOS.calcVehicles(vehicle);
		
		updateTurretFacing(vehicle);
		
	}
	
	private static void updateTurretFacing(Vehicle vehicle) {
		for(var turret : vehicle.turretData.turrets) {
			
			turret.facingDirection = turret.nextFacing;
			
		}
	}
	
	private static void updateHullDown(VehicleMovementData md) {
		if(md.hullDownDecision == null)
			return;
		
		switch(md.hullDownDecision) {
		case ENTER:
			md.enterHullDownPosition(md.selectedHullDownPosition);
			break;
		case EXIT:
			md.exitHullDownPosition();
			break;
		case INCH_BACKWARD:
			md.inchBack();
			break;
		case INCH_FORWARD:
			md.inchForward();
			break;
		case NOTHING:
			break;
		default:
			break;
		}
		
		md.hullDownDecision = HullDownDecision.NOTHING;
	}
	
	private static void updateChit(Vehicle vehicle) {
		if(GameWindow.gameWindow == null || GameWindow.gameWindow.game == null || GameWindow.gameWindow.game.chits == null) {
			return;
		}
		
		try {
			var chit = findChit(vehicle.identifier);
			var md = vehicle.movementData;
			chit.facing = convertFacing(md.facing);
			chit.xCord = md.location.xCord;
			chit.yCord = md.location.yCord;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	private static Chit findChit(String identifier) throws Exception {
		
		var chits = GameWindow.gameWindow.game.chits;
		for(var c : chits) {
			if(c.labeled && c.chitIdentifier.equals(identifier))
				return c;
		}
		
		throw new Exception("Chit not found for vic callsign: "+identifier);
	}

	private static Facing convertFacing(HexDirection facing) throws Exception {
		
		for(var f : Facing.values()) {
			if(f.toString().equals(facing.toString()))
				return f;
		}
		
		
		throw new Exception("Converted chit facing not found for hex direction: "+facing);
	}
	
	public static HullDownStatus moveForwardHD(HullDownStatus status, HullDownStatus maximumHullDownStatus) throws Exception {
		if(status == maximumHullDownStatus)
			return status;
		
		switch (status) {
		case HIDDEN:
			return HullDownStatus.TURRET_DOWN;
		case TURRET_DOWN:
			return HullDownStatus.HULL_DOWN;
		case HULL_DOWN:
			return HullDownStatus.PARTIAL_HULL_DOWN;
		case PARTIAL_HULL_DOWN:
			return HullDownStatus.PARTIAL_HULL_DOWN;
		default:
			throw new Exception("Status not found for status: " + status);
		}

	}

	public static HullDownStatus moveBackwardHD(HullDownStatus status, HullDownStatus minimumHullDownStatus) throws Exception {
		if(status == minimumHullDownStatus)
			return status;
		
		switch (status) {
		case HIDDEN:
			return HullDownStatus.HIDDEN;
		case HULL_DOWN:
			return HullDownStatus.TURRET_DOWN;
		case TURRET_DOWN:
			return HullDownStatus.HIDDEN;
		case PARTIAL_HULL_DOWN:
			return HullDownStatus.HULL_DOWN;
		default:
			throw new Exception("Status not found for status: " + status);
		}

	}

	
}
