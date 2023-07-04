package Fortifications;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import Fortifications.Fortification.FortificationType;
import HexGrid.Fortifications.HexGridFortificationsUtility;
import Vehicle.HullDownPositions.HullDownPosition;

public class FortificationRecords implements Serializable {

	
	
	// Trench Fortifications come in five levels 
	// Level 1: fox holes, provides cover positions, 10 cover 
	// Level 2: light trenches, reduction in blast damage twice the amount of cover, 15 cover, halves incoming suppression
	// Level 3: heavy trenches, additional reduction in blast damage, hunkered down individuals in this type of hex are immune to shrapnel, +1 SLM camo, 20 cover, incoming suppression / 3
	// Level 4: individuals hunkered down in this hex are immune to grenades, +3 SLM camo 
	
	// Ice Box: 
	// Option clear concealment of a hex 
	// Increased concealment 
	
	public Map<Cord, Fortification> fortifications;
	
	
	public FortificationRecords() {
		fortifications = new HashMap<>();
    }
	
	public int getTrenchesLevel(Cord cord) {
		if(fortifications == null) {
			return 0;
		}
		
		int level = 0;
		
		var fortifications = HexGridFortificationsUtility.getFortifications(cord.xCord, cord.yCord);
		
		for(var fortification : fortifications) {
			if(fortification.fortificationType == FortificationType.TRENCHES) {
				level = fortification.level;
			}
		}
		
		return level; 
	}
	
	
	public void addTrench(Cord cord, int level) {
		var forts = HexGridFortificationsUtility.getFortifications(cord.xCord, cord.yCord);
		int index = -1;
		int i = 0;
		for(var fort : forts) {
			if(fort.fortificationType == FortificationType.TRENCHES) {
				index = i;
				break;
			}
			i++; 
		}
		
		var hex = GameWindow.gameWindow.findHex(cord.xCord, cord.yCord);
		
		if(index != -1) {
			var oldFort = forts.remove(index);
			if(oldFort.level == 1) {
				hex.coverPositions -= 10;
			} else if(oldFort.level == 2) {
				hex.coverPositions -= 15;
			} else if(oldFort.level == 3 || oldFort.level == 4) {
				hex.coverPositions -= 20;
			}
		}
		
		if(level == 1) {
			hex.coverPositions += 10;
		} else if(level == 2) {
			hex.coverPositions += 15;
		} else if(level == 3 || level == 4) {
			hex.coverPositions += 20;
		}
		
		fortifications.put(cord, new Fortification(level));
		GameWindow.gameWindow.conflictLog.addNewLine("Set Trenches at ("+cord+"), Level: "+level);
	}
	
}
