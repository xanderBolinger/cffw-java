package Vehicle.Combat;

import java.io.Serializable;
import java.util.ArrayList;

import Vehicle.Vehicle;
import Vehicle.Data.CrewPosition;

public class VehicleTurret implements Serializable {
	public String turretName;
	public int facingDirection;
	public int facingWidth;
	public int rotationSpeedPerPhaseDegrees;
	public int minFacing;
	public int maxFacing;
	public boolean canRotate;
	public int nextFacing;
	public VehicleAimTarget vehicleAimTarget;
	
	public ArrayList<CrewPosition> crewPositions;
	
	
	public VehicleTurret(String turretName, int facingWidth, int rotationSpeedPerPhaseDegrees,
			boolean canRotate, int minFacing, int maxFacing) {
		this.turretName = turretName;
		this.facingDirection = 0;
		this.facingWidth = facingWidth;
		this.rotationSpeedPerPhaseDegrees = rotationSpeedPerPhaseDegrees;
		this.canRotate = canRotate;
		this.minFacing = minFacing;
		this.maxFacing = maxFacing;
		crewPositions = new ArrayList<CrewPosition>();
	}
	
	public void addPosition(String positionName, Vehicle vic) throws Exception {
		crewPositions.add(vic.getCrewPosition(positionName));
	}
	
	public int getTurretElevation() {
		return crewPositions.get(0).elevationAboveVehicle;
	}
	
}
