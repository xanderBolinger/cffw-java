package HexGrid.Waypoint;

import java.io.Serializable;
import java.util.ArrayList;

public class WaypointData implements Serializable {

	public ArrayList<Waypoint> waypoints;
	
	public WaypointData() {
		waypoints = new ArrayList<Waypoint>();
	}
	
	public void addWaypoint(Waypoint waypoint) {
		if(!canAddWaypoint(waypoint))
			return;
		waypoints.add(waypoint);
	}
	
	private boolean canAddWaypoint(Waypoint waypoint) {
		for(var p : waypoints) 
			if(p.compareTo(waypoint))
				return false;
		return true;
	}
	
}
