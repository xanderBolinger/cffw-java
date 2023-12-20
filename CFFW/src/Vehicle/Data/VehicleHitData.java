package Vehicle.Data;

public class VehicleHitData {

	int hullSize;
	int turretSize;
	
	public VehicleHitData(int hullSize, int turretSize) {
		this.hullSize = hullSize;
		this.turretSize = turretSize;
	}
	
	public int getHullSize() {
		return hullSize;
	}

	public int getTurretSize() {
		return turretSize;
	}
	
}
