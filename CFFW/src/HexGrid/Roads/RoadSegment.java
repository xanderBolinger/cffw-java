package HexGrid.Roads;

import java.io.Serializable;
import java.util.ArrayList;

public class RoadSegment implements Serializable {

	ArrayList<Road> segment;
	
	public RoadSegment() {
		segment = new ArrayList<Road>();
	}
	
	public ArrayList<Road> getSegment() {
		return segment;
	}
	
	public void addRoad(Road road) {
		// if segment size > 1 
		// road first cord, equals last cord in last road in segment 
		// new road == 1 dist from last road
		
		segment.add(road);
		
	}
	
}
