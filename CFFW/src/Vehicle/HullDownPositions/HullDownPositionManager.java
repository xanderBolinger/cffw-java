package Vehicle.HullDownPositions;

import java.io.Serializable;
import Hexes.Hex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import HexGrid.HexDirectionUtility;
import UtilityClasses.DiceRoller;
import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;
import HexGrid.HexDirectionUtility.HexDirection;;

public class HullDownPositionManager implements Serializable {

	
	public Map<Cord, HullDownPosition> positions;
	
	
	public HullDownPositionManager() {
        positions = new HashMap<>();
    }

    public void generateHullDownPositions(int width, int height) {
    	
    	ArrayList<Cord> usedCords = new ArrayList<Cord>();
    	
        for (int x = 0; x < width - 8; x += 8) {
            for (int y = 0; y < height - 8; y += 8) {
                if(x >= width || y >= height)
                	return;
            	
                int positionCount = DiceRoller.roll(1, 5);
                
                for(int i = 0; i < positionCount; i++) {
                	int randomX = x + DiceRoller.roll(0, 8);
                    int randomY = y + DiceRoller.roll(0, 8);
                	
                	int capacityRoll = DiceRoller.roll(1, 4);
                	
                	HullDownPosition newPosition = new HullDownPosition(HullDownStatus.HIDDEN, HullDownStatus.PARTIAL_HULL_DOWN);
                	newPosition.capacity = capacityRoll;
                	
                	int typeRoll = DiceRoller.roll(1,4);
                	
                	if(typeRoll == 1) {
                		newPosition.minimumHullDownStatus = HullDownStatus.TURRET_DOWN;
                	} else if(typeRoll == 2) {
                		newPosition.minimumHullDownStatus = HullDownStatus.HULL_DOWN;
                	} else if(typeRoll == 3) {
                		newPosition.minimumHullDownStatus = HullDownStatus.PARTIAL_HULL_DOWN;
                	}
                	var cord = new Cord(randomX, randomY);
                	if(usedCords.contains(cord))
                		continue;
                	positions.put(cord, newPosition);
                	usedCords.add(cord);
                	
                	if(GameWindow.gameWindow.findHex(randomX, randomY) != null)
                		GameWindow.gameWindow.findHex(randomX, randomY).coverPositions += capacityRoll;
                	
                }
               
            }
        }
        
        generateFromElevationChanges();
    }
	
    private void generateFromElevationChanges() {
    	
    	for(var hex : GameWindow.gameWindow.hexes) {
    		var cord = new Cord(hex.xCord, hex.yCord);
    		var neighbours = HexDirectionUtility.getHexNeighbours(cord);
    		
    		var elevationDifferences = new ArrayList<Hex>();
    		
    		for(var neighbour : neighbours) {
    			if(hex.elevation > neighbour.elevation) {
    				elevationDifferences.add(neighbour);
    			}
    		}
    		
    		var hd = new HullDownPosition(HullDownStatus.HIDDEN,
    				HullDownStatus.PARTIAL_HULL_DOWN);
    		hd.capacity += 4;
    		hd.protectedDirections.clear();

    		
    		for(var diff : elevationDifferences) {
    			var side = HexDirectionUtility.getHexSideFacingTarget(new Cord(diff.xCord, diff.yCord),cord
    					);
    			
    			hd.protectedDirections.add(side);
    			hd.protectedDirections.add(HexDirectionUtility.getFaceInDirection(side, false));
    			hd.protectedDirections.add(HexDirectionUtility.getFaceInDirection(side, true));
    		}
    		
    		if(hd.protectedDirections.size() > 0) {
    			positions.put(cord, hd);
    		}
    		
    	}
    	
    }
    
	
}
