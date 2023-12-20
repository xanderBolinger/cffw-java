package Vehicle.Combat;

import java.io.Serializable;
import java.util.ArrayList;

public class VehicleAmmo implements Serializable {
	
	public enum VehicleAmmoType {
		FAST,SLOW
	}
	
	String ammoName;
	ArrayList<Integer> balisticAccuracy;
	VehicleAmmoType ammoType;
	
	public VehicleAmmo(String ammoName, ArrayList<Integer> balisticAccuracy, VehicleAmmoType ammoType) {
		this.ammoName = ammoName;
		this.balisticAccuracy = balisticAccuracy;
		this.ammoType = ammoType;
	}

	public VehicleAmmoType getAmmoType() {
		return ammoType;
	}
	
	public String getAmmoName() {
		return ammoName;
	}
	
	public int getBalisticAccuracy(int rangeHexes) {
		return VehicleAmmoRangeIndex.getRangeIndex(rangeHexes);
	}
	
}
