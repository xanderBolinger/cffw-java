package HexGrid.Waypoint;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.poi.util.SystemOutLogger;

public class WaypointData implements Serializable {

	public ArrayList<Waypoint> waypoints;
	
	public WaypointData() {
		waypoints = new ArrayList<Waypoint>();
	}
	
	public void addWaypoint(Waypoint waypoint) {
		if(!canAddWaypoint(waypoint)) {
			
			System.out.println("could not add waypoint");
			return;
		}
		System.out.println("Add waypoint");
		waypoints.add(waypoint);
	}
	
	private boolean canAddWaypoint(Waypoint waypoint) {
		for(var p : waypoints) 
			if(p.compareTo(waypoint))
				return false;
		return true;
	}
	
}
