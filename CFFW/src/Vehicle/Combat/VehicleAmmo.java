package Vehicle.Combat;

import java.io.Serializable;
import java.util.ArrayList;

public class VehicleAmmo implements Serializable {
	
	public enum VehicleAmmoType {
		FAST,SLOW
	}
	
	String ammoName;
	ArrayList<Integer> balisticAccuracy;
	ArrayList<Integer> palm;
	
	VehicleAmmoType ammoType;
	
	public VehicleAmmo(String ammoName, VehicleAmmoType ammoType, 
			ArrayList<Integer> balisticAccuracy, ArrayList<Integer> palm) {
		this.ammoName = ammoName;
		this.balisticAccuracy = balisticAccuracy;
		this.palm = palm;
		this.ammoType = ammoType;
	}

	public VehicleAmmoType getAmmoType() {
		return ammoType;
	}
	
	public String getAmmoName() {
		return ammoName;
	}
	
	public int getBalisticAccuracy(int rangeHexes) {
		return balisticAccuracy.get(VehicleAmmoRangeIndex.getRangeIndex(rangeHexes));
	}
	
	public int getPalm(int rangeHexes) {
		return palm.get(VehicleAmmoRangeIndex.getRangeIndex(rangeHexes));
	}
	
}