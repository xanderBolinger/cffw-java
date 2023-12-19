package HexGrid.Roads;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Conflict.GameWindow;
import CorditeExpansion.Cord;

public class RoadManager implements Serializable {

	public List<RoadSegment> segments;
	private List<RoadSegment> selectedSegments;
	
	public RoadManager() {
		selectedSegments = new ArrayList<RoadSegment>();
		segments = new ArrayList<RoadSegment>();
		
		var segment = new RoadSegment();
		
		var r1 = new Road(new Cord(0,0), false, false);
		var r2 = new Road(new Cord(0,1), false, false);
		var r3 = new Road(new Cord(1,1), false, false);
		
		segment.addRoad(r1);
		segment.addRoad(r2);
		segment.addRoad(r3);
		
		//segments.add(segment);
	}
	
	public void addRoad(int xCord, int yCord, boolean highway, boolean river) {
		if(selectedSegments()) {
			for(var s : selectedSegments) {
				s.addRoad(new Road(new Cord(xCord, yCord), highway, river));
			}
			return;
		}
		
		
		var segments = getRoadSegmentFromCord(xCord, yCord, false);
		
		if(segments.size() == 0)
			return;
		System.out.println("found segment add road");
		
		for(var segment : segments) {
			segment.addRoad(new Road(new Cord(xCord, yCord), highway, river));
		}
		
	}
	
	public void addSegment(int xCord, int yCord, boolean highway, boolean river) {
		if(getRoadSegmentFromCord(xCord, yCord,  true).size() != 0)
			return;
		
		var segment = new RoadSegment();
		var r = new Road(new Cord(xCord, yCord), highway, river);
		segment.addRoad(r);
		segments.add(segment);
		selectedSegments = new ArrayList<RoadSegment>();
		selectedSegments.add(segment);
		HexGridRoadUtility.shadeRoad(r);
		
		System.out.println("add segment confirm");
	}
	
	
	
	public void removeSegment(int xCord, int yCord) {
		var segments = getRoadSegmentFromCord(xCord, yCord,  true);
		if(segments.size() == 0)
			return;
		
		for(var s : segments) {
			this.segments.remove(s);
			for(var r : s.getSegment()) {
				HexGridRoadUtility.removeShadeRoad(r);
			}
		}
	}
	
	public void removeRoad(int xCord, int yCord) {
		
		var roads = getRoadsFromCord(xCord, yCord);
		
		for(var r : roads) {
			for(var s : segments) {
				
				if(s.getSegment().contains(r) && 
						(s.roadIsAtEnd(r, false) || s.roadIsAtEnd(r, true))) {
					s.getSegment().remove(r);
					HexGridRoadUtility.removeShadeRoad(r);
				}
				
			}
		}
		
	}
	
	public List<RoadSegment> getRoadSegmentFromCord(int x, int y, boolean clicked) {
		
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
	
	private List<Road> getRoadsFromCord(int x, int y) {
		var listRoad = new ArrayList<Road>();
		var segments = getRoadSegmentFromCord(x,y,true);
		
		for(var s : segments) {
			for(var r : s.getSegment()) {
				if(r.point.xCord == x && r.point.yCord == y)
					listRoad.add(r);
			}
		}
		
		return listRoad;
	}
	
	public boolean selectedSegments() {
		return selectedSegments.size() > 0;
	}

	public boolean containsSelectedSegment(RoadSegment segment) {
			return selectedSegments.contains(segment);
	}
	
	public void clearSelectedSegments() {
		
		for(var s : selectedSegments) {
			for(var r : s.getSegment())
				HexGridRoadUtility.removeShadeRoad(r);
		}
		
		selectedSegments.clear();;
	}
	
	public void setSelectedSegments(List<RoadSegment> segments) {
		clearSelectedSegments();
		for(var s : segments)
			selectedSegments.add(s);
	}

	public List<RoadSegment> getSelectedSegments() {
		
		var segments = new ArrayList<RoadSegment>();
		
		for(var s : selectedSegments)
			segments.add(s);
		
		return segments;
	}
	
}
