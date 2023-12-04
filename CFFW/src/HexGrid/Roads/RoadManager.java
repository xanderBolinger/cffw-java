package HexGrid.Roads;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Conflict.GameWindow;
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
		
		var segments = getRoadSegmentFromCord(xCord, yCord, false);
		
		if(segments.size() == 0)
			return;
		System.out.println("found segment add road");
		
		for(var segment : segments) {
			segment.addRoad(new Road(new Cord(xCord, yCord), highway));
		}
		
	}
	
	public void addSegment(int xCord, int yCord, boolean highway) {
		if(getRoadSegmentFromCord(xCord, yCord,  true).size() != 0)
			return;
		
		var segment = new RoadSegment();
		segment.addRoad(new Road(new Cord(xCord, yCord), highway));
		segments.add(segment);
		System.out.println("add segment confirm");
	}
	
	
	private List<RoadSegment> getRoadSegmentFromCord(int x, int y, boolean clicked) {
		
		var roadSegments = new ArrayList<RoadSegment>();
		
		for(var seg : segments) {
			for(var road : seg.getSegment()) {
				if(((road.point.xCord == x && road.point.yCord == y) && clicked)
						|| (!clicked && GameWindow.hexDif(x, y, 
								road.point.xCord, road.point.yCord) == 1))
					roadSegments.add(seg);
			}
		}
		
		return roadSegments;
	}
	
}
