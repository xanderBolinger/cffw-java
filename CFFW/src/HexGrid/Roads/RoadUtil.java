package HexGrid.Roads;

import Conflict.GameWindow;
import Hexes.Hex;

public class RoadUtil {

	public static boolean hexIsPath(Hex hex) {
		var road = GameWindow.gameWindow.game.roadManager
				.getRoadSegmentFromCord(hex.xCord, hex.yCord, true);
		
		if(road.size() > 0) {
			var s = road.get(0);
			
			if(s.getSegment().size() > 0) {
				var r = s.getSegment().get(0);
				
				if(!r.river && !r.highway) {
					return true;
				} 
				
			}
			
		}
		
		return false;
	}
	
	public static boolean hexIsHighway(Hex hex) {
		var road = GameWindow.gameWindow.game.roadManager
				.getRoadSegmentFromCord(hex.xCord, hex.yCord, true);
		
		if(road.size() > 0) {
			var s = road.get(0);
			
			if(s.getSegment().size() > 0) {
				var r = s.getSegment().get(0);
				
				if(!r.river && r.highway) {
					return true;
				} 
				
			}
			
		}
		
		return false;
	}
	
	
}
