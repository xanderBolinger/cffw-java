package Vehicle.Combat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Vehicle.Combat.VehicleAmmo.VehicleAmmoType;

public class VehicleMovingShooterAccuracy {

	private ArrayList<DataRow> table;
	
	private static VehicleMovingShooterAccuracy instance;
	
	private VehicleMovingShooterAccuracy() {
		createData();
	}
	
	private void createData() {
		table = new ArrayList<DataRow>();
		table.add(new DataRow(1, Arrays.asList(-6,-9,-10,-11,-13,-14,-16,-17,
				-18,-18,-19,-20,-21,-22,-23,-24,-24)));
		
		table.add(new DataRow(2, Arrays.asList(-11,-13,-14,-15,-17,-18,-20,-21,
				-21,-22,-23,-24,-25,-26,-27,-27,-28)));
		
		table.add(new DataRow(3, Arrays.asList(-14,-15,-17,-18,-19,-21,-22,-23,
				-23,-24,-25,-26,-27,-28,-28,-29,-30)));
		
		table.add(new DataRow(4, Arrays.asList(-16,-17,-18,-19,-20,-22,-23,-24,
				-24,-25,-26,-26,-28,-29,-29,-30,-31)));
		
		table.add(new DataRow(5, Arrays.asList(-17,-18,-19,-20,-21,-23,-24,-24,
				-25,-26,-26,-28,-29,-29,-30,-31,-31)));
		
		table.add(new DataRow(6, Arrays.asList(-18,-19,-20,-21,-22,-23,-24,-25,
				-26,-26,-27,-28,-29,-29,-30,-31,-32)));
		
		table.add(new DataRow(7, Arrays.asList(-19,-20,-21,-22,-23,-24,-25,-26,
				-26,-27,-28,-28,-29,-30,-31,-31,-32)));
		
		table.add(new DataRow(8, Arrays.asList(-20,-21,-22,-22,-24,-24,-25,-26,
				-27,-27,-28,-28,-29,-30,-31,-32,-32)));
		
		table.add(new DataRow(10, Arrays.asList(-22,-22,-23,-24,-25,-25,-26,-27,
				-28,-28,-29,-29,-30,-31,-32,-32,-33)));
		
		table.add(new DataRow(12, Arrays.asList(-23,-24,-24,-25,-26,-26,-27,-28,
				-28,-29,-29,-30,-31,-32,-32,-33,-33)));
		
	}
	
	private static VehicleMovingShooterAccuracy getInstance() {
		if(instance == null)
			instance = new VehicleMovingShooterAccuracy();
		return instance;
	}
	
	public static int getMovingShooterAccuracy(int rangeHexes, int speed) {
		try {
			var index = VehicleMovingShooterRangeIndex.getRangeIndex(rangeHexes);
			var instance = getInstance(); 
			
			for(int i = 0; i < instance.table.size(); i++) {
				var row = instance.table.get(i);
				if(speed <= row.hexesPerTurn || i == instance.table.size() - 1)
					return row.movingShooterAccuacyValues.get(index);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	private class DataRow {
		
		int hexesPerTurn;
		List<Integer> movingShooterAccuacyValues;
		
		private DataRow(int hexesPerTurn, List<Integer> list) {
			this.hexesPerTurn = hexesPerTurn;
			movingShooterAccuacyValues = list;
		}
		
	}
	
}

