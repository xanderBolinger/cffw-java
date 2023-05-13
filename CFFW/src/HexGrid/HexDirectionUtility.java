package HexGrid;

import java.util.ArrayList;
import java.util.List;

import HexGrid.HexDirectionUtility.HexDirection;

public class HexDirectionUtility {

	public enum HexDirection {
		A,AB,B,BC,C,CD,D,DE,E,EF,F,FA;

		public static List<HexDirection> getDirections() {
			
			List<HexDirection> directions = new ArrayList<>();
			
			for (HexDirection dir : HexDirection.values())
				directions.add(dir);
			
			return directions;
			
		}
		
		public static HexDirection getMapDirectionFromFacing() {
			return HexDirection.A;
		}
		
	}
	
	
	
	
	
	
	
}
