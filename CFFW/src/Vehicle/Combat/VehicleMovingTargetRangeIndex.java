package Vehicle.Combat;

import java.util.ArrayList;
import java.util.Arrays;

import Vehicle.Combat.VehicleAmmo.VehicleAmmoType;

public class VehicleMovingTargetRangeIndex {

	private ArrayList<Integer> rangeIndexFast;
	
	private ArrayList<Integer> rangeIndexSlow;
	
	private static VehicleMovingTargetRangeIndex instance;
	
	private VehicleMovingTargetRangeIndex() {
		createData();
	}
	
	private void createData() {
		rangeIndexFast = new ArrayList<Integer>(Arrays.asList(
				20,30,40,50,60,80,100,120,130,160,180,200,220,260
				));
		rangeIndexSlow = new ArrayList<Integer>(Arrays.asList(
				13,18,22,27,31,40,49,58,63,76,85,94,100,120
			));
	}
	
	private static VehicleMovingTargetRangeIndex getInstance() {
		if(instance == null) {
			instance = new VehicleMovingTargetRangeIndex();
		}
		
		return instance;
	}
	
	public static int getRangeIndex(VehicleAmmoType ammoType, int rangeHexes) throws Exception {
		var list = getRangeList(ammoType);
		
		for(int i = 0; i < list.size(); i++) {
			if(rangeHexes <= list.get(i))
				return i;
		}
		
		throw new Exception("Range index not found for range hexes: "+rangeHexes);
	}
	
	private static ArrayList<Integer> getRangeList(VehicleAmmoType ammoType) throws Exception {
		switch(ammoType) {
		
		case FAST:
			return getInstance().rangeIndexFast; 
		case SLOW:
			return getInstance().rangeIndexSlow;
		
		}
		
		throw new Exception("Ammo Type not found for type: "+ammoType);
	}
	
}
