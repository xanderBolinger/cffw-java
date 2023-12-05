package Explosion.Shrap;

import Conflict.GameWindow;
import Trooper.Trooper;
import UtilityClasses.DiceRoller;

public class ShrapnelProtection {
    private static ShrapnelProtection instance;

    private int[][] openRanges;
    private int[][] coverRanges;
    private int[][] fortificationTwo;
    private int[][] fortificationThreeFour;
    private int[] range;
    
    private ShrapnelProtection() {
    	
    	this.range = new int [] {0,1,2,3,4,5,6,8,10,12,14,16,18,20};
    	
        this.openRanges = new int[][]{
                {0, -1},
                {2, -1},
                {4, 0},
                {6, 1},
                {9, 1},
                {11, 2},
                {13, 2},
                {17, 3},
                {21, 4},
                {23, 5},
                {27, 7},
                {31, 8},
                {34, 9},
                {37, 10}
        };

        this.coverRanges = new int[][]{
                {2, 0},
                {4, 2},
                {9, 4},
                {14, 6},
                {18, 9},
                {23, 11},
                {27, 13},
                {34, 17},
                {41, 21},
                {47, 23},
                {53, 27},
                {57, 31},
                {61, 34},
                {65, 37}
        };
        
        this.fortificationTwo = new int[][]{
            {9, 2},
            {14, 4},
            {18, 9},
            {23, 14},
            {35, 18},
            {44, 23},
            {61, 27},
            {77, 34},
            {88, 41},
            {92, 47},
            {95, 53},
            {99, 57},
            {99, 61},
            {99, 65}
        };
        
        this.fortificationThreeFour = new int[][]{
        	{11, 9},
            {20, 14},
            {31, 18},
            {47, 23},
            {61, 35},
            {77, 44},
            {88, 61},
            {92, 77},
            {95, 88},
            {99, 92},
            {99, 95},
            {99, 99},
            {99, 99},
            {99, 99}
        };

    }

    private static ShrapnelProtection getInstance() {
        if (instance == null) {
            instance = new ShrapnelProtection();
        }
        return instance;
    }
    
    private int getRangeIndex(int range) throws Exception {
    	if(range > 20)
    		range = 20;
    	
    	int count = 0;
    	for(var i : this.range) {
    		if(range <= i)
    			return count;
    		count++;
    	}
    	
    	
    	throw new Exception("Range out of range or not found for val: "+range);
    }
    
    private int getStanceIndex(Trooper trooper) {
    	if((trooper.stance.equals("Prone") || trooper.inCover) && trooper.PCSize < 2)
    		return 0;
    	else 
    		return 1;
    }
    
    private boolean protectedResult(int range, int stanceIndex, int[][] list, boolean printToLog) throws Exception {
    	var rangeIndex = getRangeIndex(range);
    	var protectedRoll = DiceRoller.roll(0, 99);
    	var tn = list[rangeIndex][stanceIndex];
    	var pro = protectedRoll <= tn;
    	
    	if(printToLog) 
    		GameWindow.gameWindow.conflictLog.addToLineInQueue("range index "+rangeIndex
    				+", stance index "+stanceIndex+ ", protected roll "+protectedRoll+" tn "+tn+", protected: "+pro);
    	
    	return pro;
    }
    
    public static boolean trooperProtectedFromShrapnel(Trooper trooper, int range, int fortificationLevel, boolean printToLog) {
    	
    	var instance = getInstance();
    	
    	try {
			var stanceIndex = instance.getStanceIndex(trooper);
			
			if(trooper.inCover && trooper.HD && fortificationLevel < 2) {
				if(printToLog)
	    			GameWindow.gameWindow.conflictLog.addNewLineToQueue(", shrap protection in cover hd (equal to ft2): ");
	    		return instance.protectedResult(range, stanceIndex, instance.fortificationTwo, printToLog);
			} else if(trooper.inCover && trooper.HD && fortificationLevel >= 2) {
				if(printToLog)
	    			GameWindow.gameWindow.conflictLog.addNewLineToQueue(", shrap protection in ft2+ cover hd (equal to ft3-4): ");
	    		return instance.protectedResult(range, stanceIndex, instance.fortificationThreeFour, printToLog);
			} else if(trooper.inCover && fortificationLevel >= 3) {
				if(printToLog)
	    			GameWindow.gameWindow.conflictLog.addNewLineToQueue(", shrap protection in ft3+): ");
	    		return instance.protectedResult(range, stanceIndex, instance.fortificationThreeFour, printToLog);
			}
		    else if(trooper.inCover && fortificationLevel >= 2) {
				if(printToLog)
	    			GameWindow.gameWindow.conflictLog.addNewLineToQueue(", shrap protection in ft2): ");
	    		return instance.protectedResult(range, stanceIndex, instance.fortificationTwo, printToLog);
			}
			else if(trooper.inCover) {
				if(printToLog)
	    			GameWindow.gameWindow.conflictLog.addNewLineToQueue(", shrap protection in cover: ");
	    		return instance.protectedResult(range, stanceIndex, instance.coverRanges, printToLog);
	    	} else {
	    		if(printToLog)
	    			GameWindow.gameWindow.conflictLog.addNewLineToQueue(", shrap protection out of cover: ");
	    		
	    		return instance.protectedResult(range, stanceIndex, instance.openRanges, printToLog);
	    	}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
    }

}
