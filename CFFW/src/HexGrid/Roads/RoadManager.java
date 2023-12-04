package HexGrid.Roads;

import java.io.Serializable;
import java.util.ArrayList;
import CorditeExpansion.Cord;

public class RoadManager implements Serializable {

	public ArrayList<RoadSegment> segments;
	
	public RoadManager() {
		segments = new ArrayList<RoadSegment>();
		
		var segment = new RoadSegment();
		
		var r1 = new Road(new Cord(0,0), false);
		var r2 = new Road(new Cord(0,1), false);
		var r3 = new Road(new Cord(1,1), false);
		
		segment.addRoad(r1);
		segment.addRoad(r2);
		segment.addRoad(r3);
		
		segments.add(segment);
	}
	
	public void addRoad(int xCord, int yCord, boolean highway) {
		
		var segment = getRoadSegmentFromCord(xCord, yCord);
		
		if(segment == null)
			return;
		
		segment.addRoad(new Road(new Cord(xCord, yCord), highway));
	}
	
	public void addSegment(int xCord, int yCord, boolean highway) {
		
	}
	
	
	private RoadSegment getRoadSegmentFromCord(int x, int y) {
		
		for(var seg : segments) {
			for(var road : seg.getSegment()) {
				if(road.point.xCord == x && road.point.yCord == y)
					return seg;
			}
		}
		
		return null;
	}
	
}
