package Vehicle;

import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;

public class VehicleMovement {

	public static HullDownStatus MoveForward(HullDownStatus status, HullDownStatus maximumHullDownStatus) throws Exception {
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

	public static HullDownStatus MoveBackward(HullDownStatus status, HullDownStatus minimumHullDownStatus) throws Exception {
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

	public static void FindWay(int clearLaneChance, int manueverLaneChance, int noLaneChance) {

	}

}
