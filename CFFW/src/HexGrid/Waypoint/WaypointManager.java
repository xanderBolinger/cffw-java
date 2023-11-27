package HexGrid.Waypoint;

import Conflict.GameWindow;
import Hexes.Hex;
import Unit.Unit;

public class WaypointManager {

	public static boolean addWaypoints;
	
	public static void moveUnits() {
		var gw = GameWindow.gameWindow;
		var initOrder = gw.initiativeOrder;
		
		try {
			for(var unit : initOrder) {
				if(unit.waypointData.waypoints.size() == 0 && !unit.speed.equals("None")) {
					unit.speed = "None";
					unit.seekCover(gw.findHex(unit.X, unit.Y), gw);
					continue;
				} else if(unit.waypointData.waypoints.size() == 0) {
					continue;
				}
				
				
				System.out.println("Move Unit 1: "+unit.callsign);
				
				unit.spentMP = 0;
				if(unit.speed.equals("Rush")) {
					for(int i = 0; i < 5; i++) {
						moveUnit(unit);
						if(unit.spentMP >= 5 || unit.waypointData.waypoints.size() == 0)
							break;
					}
				} else 
					moveUnit(unit);
				
			}
		} catch (Exception e) {e.printStackTrace();}
		
		
		
	}
	
	private static void moveUnit(Unit unit) {
		
		System.out.println("Move Unit 2: "+unit.callsign);
		
		var gw = GameWindow.gameWindow;
		var wp = unit.waypointData.waypoints.get(0);
		unit.move(gw, wp.x, wp.y, null);
		var hex = gw.findHex(unit.X, unit.Y);
		
		if(unit.X != wp.x || unit.Y != wp.y)
			return;

		unit.spentMP += getMediumOrHeavyWoods(hex) ? 2 : 1;
		
		unit.waypointData.waypoints.remove(0);
		
		if(unit.waypointData.waypoints.size() != 0) {
			unit.speed = unit.waypointData.waypoints.get(0).waypointSpeed;
			unit.seekCover(hex, gw);
		}
	}
	
	private static boolean getMediumOrHeavyWoods(Hex hex) {
		
		for(var feature : hex.features)
			if(feature.featureType.equals("Medium Forest") 
					|| feature.featureType.equals("Heavy Forest"))
				return true;
		
		return false;
	}
	
}
