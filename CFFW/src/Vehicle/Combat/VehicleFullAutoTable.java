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
		
		var row1 = new ArrayList<String>(Arrays.asList("6*","9*","*12","*15","*18","*21","*24","*27","*30","*30",
	            "*36","*52","*100","*200","*200"));
	    var row2 = new ArrayList<String>(Arrays.asList("*5","*7","*9","*11","*14","*16","*18","*21","*23","*27",
	            "*41","*82","*100","*200"));
	    var row3 = new ArrayList<String>(Arrays.asList("*3","*5","*7","*9","*10","*12","*14","*16","*17","*21",
	            "*31","*63","*94","*100"));
	    var row4 = new ArrayList<String>(Arrays.asList("*3","*4","*5","*7","*8","*9","*11","*12","*13","*16",
	            "*24","*49","*73","*97"));
	    var row5 = new ArrayList<String>(Arrays.asList("*2","*2","*3","*4","*5","*6","*7","*8","*9","*10",
	            "*14","*29","*43","*58"));
	    var row6 = new ArrayList<String>(Arrays.asList("*2","*2","*3","*4","*5","*6","*6","*7","*8","*10",
	            "*14","*29","*43","*58"));
	    var row7 = new ArrayList<String>(Arrays.asList("*1","*2","*2","*3","*4","*4","*5","*6","*6","*7",
	            "*11","*22","*34","*45"));
	    var row8 = new ArrayList<String>(Arrays.asList("95","*1","*2","*2","*3","*3","*4","*4","*5","*6",
	            "*9","*17","*26","*34"));
	    var row9 = new ArrayList<String>(Arrays.asList("73","*1","*1","*2","*2","*3","*3","*3","*4","*4",
	            "*7","*13","*20","*27"));
	    var row10 = new ArrayList<String>(Arrays.asList("56","84","*1","*1","*2","*2","*2","*3","*3","*3",
	            "*5","*10","*15","*20"));
	    var row11 = new ArrayList<String>(Arrays.asList("43","64","86","*1","*1","*2","*2","*2","*2","*3",
	            "*4","*8","*12","*16"));
	    var row12 = new ArrayList<String>(Arrays.asList("25","37","50","63","76","89","*1","*1","*1","*2",
	            "*2","*5","*7","*9"));
	    var row13 = new ArrayList<String>(Arrays.asList("14","21","29","36","44","51","59","66","74","89",
	            "*1","*3","*4","*5"));
	    var row14 = new ArrayList<String>(Arrays.asList("8","12","16","21","25","29","34","38","42","51",
	            "77","*2","*2","*3"));
	    var row15 = new ArrayList<String>(Arrays.asList("4","7","9","12","14","17","19","22","24","29",
	            "44","89","*1","*2"));
	    var row16 = new ArrayList<String>(Arrays.asList("2","3","5","6","8","9","11","12","13","16","25",
	            "51","77","*1"));
	    var row17 = new ArrayList<String>(Arrays.asList("1","2","2","3","4","5","6","7","7","9","14","29",
	            "44","59"));
	    var row18 = new ArrayList<String>(Arrays.asList("0","0","1","1","2","2","3","3","4","5","8","16",
	            "25","34"));
		resultRows.add(row1);
		resultRows.add(row2);
		resultRows.add(row3);
		resultRows.add(row4);
		resultRows.add(row5);
		resultRows.add(row6);
		resultRows.add(row7);
		resultRows.add(row8);
		resultRows.add(row9);
		resultRows.add(row10);
		resultRows.add(row11);
		resultRows.add(row12);
		resultRows.add(row13);
		resultRows.add(row14);
		resultRows.add(row15);
		resultRows.add(row16);
		resultRows.add(row17);
		resultRows.add(row18);
	}

	private static VehicleFullAutoTable getInstance() {
		if(instance == null)
			instance = new VehicleFullAutoTable();
		return instance;
	}

	public static String getFullAutoString(int rof, int palm) {
		var instance = getInstance();
		int rofIndex = getIndex(rof, instance.patternRof);
		int palmIndex = getIndex(palm, instance.patternAlm);
		
		return instance.resultRows.get(palmIndex).get(rofIndex);
	}
	
	private static int getIndex(int target, ArrayList<Integer> list) {
		for(int i = 0; i < list.size(); i++) 
			if(target <= list.get(i)) 
				return i;
		
		return -1;
	}
	
}
