package Vehicle.HullDownPositions;

import java.io.Serializable;
import java.util.ArrayList;

import HexGrid.HexDirectionUtility;
import HexGrid.HexDirectionUtility.HexDirection;
import UtilityClasses.DiceRoller;

public class HullDownPosition implements Serializable {

	public enum HullDownStatus {
		HIDDEN,TURRET_DOWN,HULL_DOWN,PARTIAL_HULL_DOWN
	}
	
	public enum HullDownDecision {
		NOTHING,INCH_FORWARD,INCH_BACKWARD,ENTER,EXIT
	}

	public HullDownStatus minimumHullDownStatus;
	public HullDownStatus maximumHullDownStatus;
	public int capacity;
	public int occupants;
	public ArrayList<HexDirection> protectedDirections;
	
	public HullDownPosition(HullDownStatus minimumHullDownStatus, HullDownStatus maximumHullDownStatus) {
		this.minimumHullDownStatus = minimumHullDownStatus;
		this.maximumHullDownStatus = maximumHullDownStatus;
		protectedDirections = new ArrayList<HexDirection>();

		addProtectedDirections();
	}
	
	private void addProtectedDirections() {
		var directions = HexDirection.getDirections();
		
		var directionsCovered = DiceRoller.roll(1, 12);
		
		var dir = directions.get(DiceRoller.roll(0, directions.size()-1));
		addProtectedDir(dir);
		for(int i = 0; i < directionsCovered; i++) {
			var dir1 = HexDirectionUtility.getFaceInDirection(dir, false);
			addProtectedDir(dir1);
			var dir2 = HexDirectionUtility.getFaceInDirection(dir, true);
			addProtectedDir(dir2);
			
			dir = DiceRoller.roll(0, 1) == 0 ? dir1 : dir2;
		}
	}
	
	private void addProtectedDir(HexDirection dir) {
		if(!protectedDirections.contains(dir))
			protectedDirections.add(dir);
	}
	
	@Override
	public String toString() {
		String dirs = "";
		
		for(var dir : protectedDirections)
			dirs += dir.toString() + (protectedDirections.get(protectedDirections.size()-1) == dir
			? "]" : ", ");
		
		return "Hull Down ("+minimumHullDownStatus+", "+maximumHullDownStatus+"): "+occupants
				+"/"+capacity+", protected directions: ["+dirs;
	}
	
}
