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
		if((segment.size() > 1 && 
				(GameWindow.hexDif(segment.get(segment.size()-1).point.xCord, 
						segment.get(segment.size()-1).point.yCord, 
						road.point.xCord, road.point.yCord) != 1))
				|| alreadyContainsRoad(road)) {
			return;
		}
		
		System.out.println("confirm add road");
		segment.add(road);
		
	}
	
	private boolean alreadyContainsRoad(Road road) {
		
		for(var r : segment) {
			if(r.point.compare(road.point))
				return true;
		}
		
		return false;
	}
	
}
