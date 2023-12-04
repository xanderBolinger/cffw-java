package HexGrid.Roads;

import java.io.Serializable;
import java.util.ArrayList;

import Conflict.GameWindow;

public class RoadSegment implements Serializable {

	ArrayList<Road> segment;
	
	public RoadSegment() {
		segment = new ArrayList<Road>();
	}
	
	public ArrayList<Road> getSegment() {
		return segment;
	}
	
	
	
	public void addRoad(Road road) {
		if((!roadNearEnd(road, true) && !roadNearEnd(road, false))
				|| alreadyContainsRoad(road)) {
			return;
		}
		
		System.out.println("confirm add road");
		
		if(roadNearEnd(road, true))
			segment.add(0, road);
		else
			segment.add(road);
		
	}
	
	private boolean roadNearEnd(Road road, boolean segmentStart) {
		if(segment.size() == 0)
			return true;
		
		return segmentStart ? GameWindow.hexDif(segment.get(0).point.xCord, 
				segment.get(0).point.yCord, 
				road.point.xCord, road.point.yCord) == 1: 
					GameWindow.hexDif(segment.get(segment.size()-1).point.xCord, 
						segment.get(segment.size()-1).point.yCord, 
						road.point.xCord, road.point.yCord) == 1;
		
	}
	
	public boolean roadIsAtEnd(Road road, boolean segmentStart) {
		if(segment.size() == 0)
			return false;
		
		return segmentStart ? road.point.compare(segment.get(0).point) 
				: road.point.compare(segment.get(segment.size()-1).point);
	}
	
	private boolean alreadyContainsRoad(Road road) {
		
		for(var r : segment) {
			if(r.point.compare(road.point))
				return true;
		}
		
		return false;
	}
	
}
