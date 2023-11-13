package HexGrid.Waypoint;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.poi.util.SystemOutLogger;

import Conflict.GameWindow;
import Unit.Unit;

public class WaypointData implements Serializable {

	public ArrayList<Waypoint> waypoints;
	
	public WaypointData() {
		waypoints = new ArrayList<Waypoint>();
	}
	
	public void addWaypoint(Waypoint waypoint, Unit unit) {
		if(!canAddWaypoint(waypoint)) {
			
			System.out.println("could not add waypoint");
			return;
		}
		System.out.println("Add waypoint");
		waypoints.add(waypoint);
		
		if(waypoints.size()==1) {
			unit.speed = waypoints.get(0).waypointSpeed;
			var gw = GameWindow.gameWindow;
			unit.seekCover(gw.findHex(unit.X, unit.Y), gw);
		}
		
	}
	
	public void clearWaypoints(Unit unit) {
		waypoints.clear();
		unit.speed = "None";
		var gw = GameWindow.gameWindow;
		unit.seekCover(gw.findHex(unit.X, unit.Y), gw);
	}
	
	private boolean canAddWaypoint(Waypoint waypoint) {
		for(var p : waypoints) 
			if(p.compareTo(waypoint))
				return false;
		return true;
	}
	
}
