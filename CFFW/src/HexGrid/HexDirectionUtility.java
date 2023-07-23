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

		public static List<HexDirection> getNormalDirections() {
			
			List<HexDirection> directions = new ArrayList<>();
			
			for (HexDirection dir : HexDirection.values()) {
				switch (dir) {
	            case A:
	                directions.add(dir);
	                break;
	            case B:
	            	directions.add(dir);
	                break;
	            case C:
	            	directions.add(dir);
	                break;
	            case D:
	            	directions.add(dir);
	                break;
	            case E:
	            	directions.add(dir);
	                break;
	            case F:
	            	directions.add(dir);
	                break;
	        }
			}
			
			return directions;
			
		}
		
		
	}
	
	
	public static boolean flanking(HexDirection facing1, HexDirection facing2) {
		
		var distance = getDistance(facing1, facing2, true) < getDistance(facing1, facing2, false) 
				? getDistance(facing1, facing2, true) : getDistance(facing1, facing2, false);

		return distance > 1 && distance <= 4;
	}

	public static boolean rear(HexDirection facing1, HexDirection facing2) {
		
		var distance = getDistance(facing1, facing2, true) < getDistance(facing1, facing2, false) 
				? getDistance(facing1, facing2, true) : getDistance(facing1, facing2, false);

		return distance == 0 || distance == 1;
	}
	
	private static int getDistance(HexDirection facing1, HexDirection facing2, boolean clockwise) {
		var nextDir = facing1;
		int distance = 0;
		while(true) {
			var dir = getFaceInDirection(nextDir, clockwise);
			if(dir == facing2)
				break;
			distance++;
			nextDir = dir;
		}
		return distance;
	}
	
	public static HexDirection getFaceInDirection(HexDirection facing, boolean clockwise) {
		
		switch(facing) {
		
		case A: 
			return clockwise ? HexDirection.AB : HexDirection.FA;
		case AB:
			return clockwise ? HexDirection.B : HexDirection.A;
		case B:
			return clockwise ? HexDirection.BC : HexDirection.AB;
		case BC:
			return clockwise ? HexDirection.C : HexDirection.BC;
		case C:
			return clockwise ? HexDirection.CD : HexDirection.BC;
		case CD:
			return clockwise ? HexDirection.D : HexDirection.C;
		case D:
			return clockwise ? HexDirection.DE : HexDirection.CD;
		case DE:
			return clockwise ? HexDirection.E : HexDirection.D;
		case E:
			return clockwise ? HexDirection.EF : HexDirection.DE;
		case EF:
			return clockwise ? HexDirection.F : HexDirection.E;
		case F:
			return clockwise ? HexDirection.FA : HexDirection.EF;
		case FA:
			return clockwise ? HexDirection.A : HexDirection.F;
		default:
			break;
		
		}
		
		return null;
		
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

	
    public static Cord getHexInDirection(HexDirection dir, Cord pos, boolean clockwise) {
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
            case AB:
            	return !clockwise ? neighbors.get(0) : neighbors.get(1);
            case BC:
            	return !clockwise ? neighbors.get(1) : neighbors.get(2);
            case CD:
            	return !clockwise ? neighbors.get(2) : neighbors.get(3);
            case DE:
            	return !clockwise ? neighbors.get(3) : neighbors.get(4);
            case EF:
            	return !clockwise ? neighbors.get(4) : neighbors.get(5);
            case FA:
            	return !clockwise ? neighbors.get(5) : neighbors.get(0);
            default:
                throw new IllegalArgumentException("Hex direction not found, dir: " + dir + ", pos: " + pos);
        }
    }
	
	
	
}
