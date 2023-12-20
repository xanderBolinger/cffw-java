package Vehicle.Combat;

import java.util.ArrayList;
import java.util.Arrays;

public class VehicleAmmoRangeIndex {

	private ArrayList<Integer> rangeList;
	
	private static VehicleAmmoRangeIndex instance;
	
	private VehicleAmmoRangeIndex() {
		createData();
	}
	
	private void createData() {
		rangeList = new ArrayList<Integer>(Arrays.asList(
				1,4,15,20,30,40,50,60,70,80,90,100,120,140,160,180,200
				));
	}

    private static VehicleAmmoRangeIndex getInstance() {
        if (instance == null) {
            instance = new VehicleAmmoRangeIndex();
        }
        return instance;
    }
	
    public static int getRangeIndex(int rangeHexes) {
    	var instance = getInstance();
    	
    	for(int i = 0; i < instance.rangeList.size(); i++) {
    		
    		var range = instance.rangeList.get(i);
    		
    		if(rangeHexes <= range)
    			return i;
    	}
    	
    	return -1;
    }
    
}
