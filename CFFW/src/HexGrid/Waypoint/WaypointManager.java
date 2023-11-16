package HexGrid.Waypoint;

import Conflict.GameWindow;

public class WaypointManager {

	public static boolean addWaypoints;
	
	public static void moveUnits() {
		var gw = GameWindow.gameWindow;
		var initOrder = gw.initiativeOrder;
		
		for(var unit : initOrder) {
			if(unit.waypointData.waypoints.size() == 0 && !unit.speed.equals("None")) {
				unit.speed = "None";
				unit.seekCover(gw.findHex(unit.X, unit.Y), gw);
				continue;
			} else if(unit.waypointData.waypoints.size() == 0) {
				continue;
			}
			
			var wp = unit.waypointData.waypoints.get(0);
			
			unit.move(gw, wp.x, wp.y, null);
			
			if(unit.X != wp.x || unit.Y != wp.y)
				continue;
			
			unit.waypointData.waypoints.remove(0);
			
			if(unit.waypointData.waypoints.size() != 0) {
				unit.speed = unit.waypointData.waypoints.get(0).waypointSpeed;
				unit.seekCover(gw.findHex(unit.X, unit.Y), gw);
			}
			
		}
		
	}
	
}
