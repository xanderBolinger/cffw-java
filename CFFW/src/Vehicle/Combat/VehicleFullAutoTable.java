package Vehicle.Combat;

import java.util.ArrayList;
import java.util.Arrays;

public class VehicleFullAutoTable {

	private ArrayList<Integer> patternRof;
	private ArrayList<Integer> patternAlm;
	private ArrayList<ArrayList<String>> resultRows;
	
	private static VehicleFullAutoTable instance;
	
	private VehicleFullAutoTable() {
		createData();
	}
	
	private void createData() {
		patternRof = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,12,18,36,54,72));
		patternAlm = new ArrayList<Integer>(Arrays.asList(20,21,22,23,24,25,26,27,28,29,30,
				32,34,36,38,40,42,44));
		
		resultRows = new ArrayList<ArrayList<String>>();
		
	}

	private static VehicleFullAutoTable getInstance() {
		if(instance == null)
			instance = new VehicleFullAutoTable();
		return instance;
	}
	
	
	
}
