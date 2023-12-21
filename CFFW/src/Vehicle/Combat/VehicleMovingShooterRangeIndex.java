package Vehicle.Combat;

import java.util.ArrayList;
import java.util.Arrays;

import Vehicle.Combat.VehicleAmmo.VehicleAmmoType;

public class VehicleMovingShooterRangeIndex {

	private ArrayList<Integer> rangeIndex;
	
	private static VehicleMovingShooterRangeIndex instance;
	
	private VehicleMovingShooterRangeIndex() {
		createData();
	}
	
	private void createData() {
		rangeIndex = new ArrayList<Integer>(Arrays.asList(
				4,10,15,20,30,40,50,60,70,80,90,100,120,140,160,180,200
			));
	}
	
	private static VehicleMovingShooterRangeIndex getInstance() {
		if(instance == null) {
			instance = new VehicleMovingShooterRangeIndex();
		}
		
		return instance;
	}
	
	public static int getRangeIndex(int rangeHexes) throws Exception {
		var list = getInstance().rangeIndex;
		
		for(int i = 0; i < list.size(); i++) {
			if(rangeHexes <= list.get(i))
				return i;
			else if(i == list.size() - 1)
				return i;
		}
		
		throw new Exception("Range index not found for range hexes: "+rangeHexes);
	}
	
}