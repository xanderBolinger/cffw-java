package Vehicle;

import java.io.Serializable;

import Conflict.GameWindow;
import HexGrid.HexDirectionUtility;
import Vehicle.HullDownPositions.HullDownPosition.HullDownDecision;
import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;

public class VehicleMovement implements Serializable {

	
	public static void moveVehicle(Vehicle vehicle) {
		
		var md = vehicle.movementData;
		
		for(int i = 0; i < md.speed; i++) {
			var cord = HexDirectionUtility.getHexInDirection(md.facing, md.location, md.movedClockwise);
			var hex = GameWindow.gameWindow.findHex(cord.xCord, cord.yCord);
			md.movedClockwise = !md.movedClockwise;
			if(hex == null)
				continue;

			md.enterHex(hex);
		}

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
