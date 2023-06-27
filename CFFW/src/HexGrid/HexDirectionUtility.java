package HexGrid;

import java.util.ArrayList;
import java.util.List;

import CorditeExpansion.Cord;
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
	
	
	public static List<Cord> getHexNeighbours(Cord pos) {
        List<Cord> neighbors = new ArrayList<>();

        // A
        neighbors.add(new Cord(pos.xCord - 1, pos.yCord));

        // B
        if (pos.yCord % 2 != 0)
            neighbors.add(new Cord(pos.xCord - 1, pos.yCord + 1));
        else
            neighbors.add(new Cord(pos.xCord, pos.yCord + 1));

        // C
        if (pos.yCord % 2 != 0)
            neighbors.add(new Cord(pos.xCord, pos.yCord + 1));
        else
            neighbors.add(new Cord(pos.xCord + 1, pos.yCord + 1));

        // D
        neighbors.add(new Cord(pos.xCord + 1, pos.yCord));

        // E
        if (pos.yCord % 2 != 0)
            neighbors.add(new Cord(pos.xCord, pos.yCord - 1));
        else
            neighbors.add(new Cord(pos.xCord + 1, pos.yCord - 1));

        // F
        if (pos.yCord % 2 != 0)
            neighbors.add(new Cord(pos.xCord - 1, pos.yCord - 1));
        else
            neighbors.add(new Cord(pos.xCord, pos.yCord - 1));

        return neighbors;
    }

    public static Cord getHexInDirection(HexDirection dir, Cord pos) {
        List<Cord> neighbors = getHexNeighbours(pos);

        switch (dir) {
            case A:
                return neighbors.get(0);
            case B:
                return neighbors.get(1);
            case C:
                return neighbors.get(2);
            case D:
                return neighbors.get(3);
            case E:
                return neighbors.get(4);
            case F:
                return neighbors.get(5);
            default:
                throw new IllegalArgumentException("Hex direction not found, dir: " + dir + ", pos: " + pos);
        }
    }

	
	
	
	
	
}
