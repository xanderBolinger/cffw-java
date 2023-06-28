package Vehicle.HullDownPositions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import CorditeExpansion.Cord;
import UtilityClasses.DiceRoller;
import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;

public class HullDownPositionRecords {

	
	public Map<Cord, HullDownPosition> positions;
	
	public HullDownPositionRecords() {
        positions = new HashMap<>();
    }

    public void generateHullDownPositions(int width, int height) {

        for (int x = 0; x < width; x += 10) {
            for (int y = 0; y < height; y += 10) {
                
                int positionCount = DiceRoller.roll(1, 3);
                
                for(int i = 0; i < positionCount; i++) {
                	int randomX = x + DiceRoller.roll(0, 10);
                    int randomY = y + DiceRoller.roll(0, 10);
                	
                	int capacityRoll = DiceRoller.roll(1, 3);
                	
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
                	
                	positions.put(new Cord(randomX, randomY), newPosition);
                }
               
            }
        }
    }
	
	
}
