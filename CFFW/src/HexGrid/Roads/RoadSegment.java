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
		if(segment.size() > 1 && 
				(segment.get(segment.size()-1).point.compare(road.point) || 
						GameWindow.hexDif(segment.get(segment.size()-1).point.xCord, 
								segment.get(segment.size()-1).point.yCord, 
								road.point.xCord, road.point.yCord) != 1)) {
			return;
		}
		
		System.out.println("confirm add road");
		segment.add(road);
		
	}
	
}
