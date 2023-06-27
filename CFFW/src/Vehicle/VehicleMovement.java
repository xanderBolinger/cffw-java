package Vehicle;

import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;

public class VehicleMovement {

	public static HullDownStatus MoveForward(HullDownStatus status) throws Exception {

		switch (status) {
		case HIDDEN:
			return HullDownStatus.HULL_DOWN;
		case HULL_DOWN:
			return HullDownStatus.TURRET_DOWN;
		case TURRET_DOWN:
			return HullDownStatus.TURRET_DOWN;
		default:
			throw new Exception("Status not found for status: " + status);
		}

	}

	public static HullDownStatus MoveBackward(HullDownStatus status) throws Exception {

		switch (status) {
		case HIDDEN:
			return HullDownStatus.HIDDEN;
		case HULL_DOWN:
			return HullDownStatus.HIDDEN;
		case TURRET_DOWN:
			return HullDownStatus.HULL_DOWN;
		default:
			throw new Exception("Status not found for status: " + status);
		}

	}

	public static void FindWay(int clearLaneChance, int manueverLaneChance, int noLaneChance) {

	}

}
