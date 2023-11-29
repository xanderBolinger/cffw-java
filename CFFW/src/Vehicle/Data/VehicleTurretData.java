package Vehicle.Data;

import java.io.Serializable;
import java.util.ArrayList;

import Vehicle.Combat.VehicleTurret;

public class VehicleTurretData implements Serializable {
	
	public ArrayList<VehicleTurret> turrets;
	
	public VehicleTurretData() {
		turrets = new ArrayList<VehicleTurret>();
	}
	
}
