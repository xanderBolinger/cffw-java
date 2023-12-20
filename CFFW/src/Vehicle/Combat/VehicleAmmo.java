package Vehicle.Combat;

import java.io.Serializable;
import java.util.ArrayList;

public class VehicleAmmo implements Serializable {
	
	String ammoName;
	ArrayList<Integer> balisticAccuracy;
	
	public VehicleAmmo(String ammoName, ArrayList<Integer> balisticAccuracy) {
		this.ammoName = ammoName;
		this.balisticAccuracy = balisticAccuracy;
	}
	
	public String getAmmoName() {
		return ammoName;
	}
	
	public int getBalisticAccuracy(int rangeHexes) {
		return VehicleAmmoRangeIndex.getRangeIndex(rangeHexes);
	}
	
}
