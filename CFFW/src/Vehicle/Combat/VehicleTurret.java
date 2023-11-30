package Vehicle.Combat;

import java.io.Serializable;

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
	
	public VehicleTurret(String turretName, int facingWidth, int rotationSpeedPerPhaseDegrees,
			boolean canRotate, int minFacing, int maxFacing) {
		this.turretName = turretName;
		this.facingDirection = 0;
		this.facingWidth = facingWidth;
		this.rotationSpeedPerPhaseDegrees = rotationSpeedPerPhaseDegrees;
		this.canRotate = canRotate;
		this.minFacing = minFacing;
		this.maxFacing = maxFacing;
	}
}
