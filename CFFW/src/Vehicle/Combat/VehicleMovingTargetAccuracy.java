package Vehicle.Combat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Vehicle.Combat.VehicleAmmo.VehicleAmmoType;

public class VehicleMovingTargetAccuracy {

	private ArrayList<DataRow> table;
	
	private static VehicleMovingTargetAccuracy instance;
	
	private VehicleMovingTargetAccuracy() {
		createData();
	}
	
	private void createData() {
		table = new ArrayList<DataRow>();
		table.add(new DataRow(1, Arrays.asList(3,1,0,-2,-3,-6,-9,-12,-13,-17,-20,-23,-26,-33)));
		
		table.add(new DataRow(2, Arrays.asList(2,1,-1,-2,-4,-7,-11,-14,-16,-21,-24,-27,-30,-38)));
		
		table.add(new DataRow(3, Arrays.asList(2,0,-2,-3,-5,-9,-13,-16,-18,-24,-28,-32,-34,-44)));
		
		table.add(new DataRow(4, Arrays.asList(2,0,-2,-4,-6,-10,-14,-19,-21,-27,-31,-36,-39,-49)));
		
		table.add(new DataRow(5, Arrays.asList(1,-1,-3,-5,-7,-12,-16,-21,-23,-30,-35,-40,-43,-55)));
		
		table.add(new DataRow(6, Arrays.asList(1,-1,-4,-6,-9,-13,-18,-23,-26,-33,-39,-44,-48,-60)));
		
		table.add(new DataRow(7, Arrays.asList(1,-2,-5,-7,-10,-15,-20,-26,-28,-37,-42,-48,-52,-65)));
		
		table.add(new DataRow(8, Arrays.asList(0,-2,-5,-8,-11,-17,-22,-28,-31,-40,-46,-52,-56,-71)));
		
		table.add(new DataRow(10, Arrays.asList(0,-3,-7,-10,-13,-20,-26,-33,-36,-46,-53,-60,-65,-82)));
		
		table.add(new DataRow(12, Arrays.asList(-1,-5,-8,-12,-15,-23,-30,-37,-41,-53,-60,-68,-74,-93)));
		
	}
	
	private static VehicleMovingTargetAccuracy getInstance() {
		if(instance == null)
			instance = new VehicleMovingTargetAccuracy();
		return instance;
	}
	
	public static int getMovingTargetAccuracy(VehicleAmmoType ammoType, int rangeHexes, int speed) {
		try {
			var index = VehicleMovingTargetRangeIndex.getRangeIndex(ammoType, rangeHexes);
			var instance = getInstance(); 
			
			for(int i = 0; i < instance.table.size(); i++) {
				var row = instance.table.get(i);
				if(speed <= row.hexesPerTurn || i == instance.table.size() - 1)
					return row.movingTargetAccuacyValues.get(index);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	private class DataRow {
		
		int hexesPerTurn;
		List<Integer> movingTargetAccuacyValues;
		
		private DataRow(int hexesPerTurn, List<Integer> list) {
			this.hexesPerTurn = hexesPerTurn;
			movingTargetAccuacyValues = list;
		}
		
	}
	
}

