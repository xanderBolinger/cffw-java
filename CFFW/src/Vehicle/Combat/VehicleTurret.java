package Vehicle.Combat;

import java.io.Serializable;

public class VehicleTurret implements Serializable {
	public String turretName;
	public int facingDirection;
	public int facingWidth;
	public int rotationSpeedPerPhaseDegrees;
	public boolean canRotate;

	public VehicleTurret(String turretName, int facingWidth, int rotationSpeedPerPhaseDegrees,
			boolean canRotate) {
		this.turretName = turretName;
		this.facingDirection = 0;
		this.facingWidth = facingWidth;
		this.rotationSpeedPerPhaseDegrees = rotationSpeedPerPhaseDegrees;
		this.canRotate = canRotate;
	}
}
