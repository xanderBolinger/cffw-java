package Vehicle.Combat;

import java.io.Serializable;
import java.util.ArrayList;

import Conflict.GameWindow;
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
	
	ArrayList<Integer> aimValues;
	
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
	
	public int getAimValue() {
		return vehicleAimTarget.timeSpentAiming >= aimValues.size() ? 
				aimValues.get(aimValues.size()-1) : aimValues.get(vehicleAimTarget.timeSpentAiming);
	}
	
	public int getRangeToTargetIn20YardHexes(Vehicle vic) {
		var vicLocation = vic.movementData.location;
		
		var targetCord = vehicleAimTarget.getTargetCord();
		
		return GameWindow.hexDif(vicLocation.xCord, vicLocation.yCord,
				targetCord.xCord, targetCord.yCord);
	}
	
}
