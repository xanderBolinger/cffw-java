package HexGrid.Roads;

import java.io.Serializable;
import java.util.ArrayList;
import CorditeExpansion.Cord;

public class RoadManager implements Serializable {

	public ArrayList<RoadSegment> segments;
	
	public RoadManager() {
		segments = new ArrayList<RoadSegment>();
		
		var segment = new RoadSegment();
		
		var r1 = new Road(new Cord(0,0), new Cord(0,1), false);
		var r2 = new Road(new Cord(0,1), new Cord(1,1), false);
		var r3 = new Road(new Cord(1,1), new Cord(1,2), false);
		
		segment.addRoad(r1);
		segment.addRoad(r2);
		segment.addRoad(r3);
	}
	
}
