package HexGrid;

import java.util.ArrayList;
import java.util.List;

import CeHexGrid.Chit.Facing;
import Conflict.GameWindow;
import CorditeExpansion.Cord;
import HexGrid.HexDirectionUtility.HexDirection;
import Hexes.Hex;
import Vehicle.Vehicle;

public class HexDirectionUtility {

	private static boolean debug = false;
	
	public static void testTargetDirections(Vehicle vehicle) {
		if(!debug)
			return;
		
		var cs = vehicle.getVehicleCallsign();
		for(var victor : GameWindow.gameWindow.game.vehicleManager.getVehicles()) {
			if(victor.compareTo(vehicle))
				continue;
			
			var dir = getHexSideFacingTarget(vehicle.movementData.location, victor.movementData.location);
			System.out.println("Direction, "+cs+"("+vehicle.movementData.location.toString()+") to target: "
			+victor.getVehicleCallsign()+"("+victor.movementData.location.toString()+"), direction: "+dir);
			
		}
		
	}
	
	private static HexDirection getHexSideFacingTargetBaseCases(Cord start, Cord target, int startDistance) {
		
		if(startDistance == 0) {
			return HexDirection.A;
		} else if(startDistance == 1) {
			
			var adjacentTiles = getHexNeighbourCords(start);
			
			for(int i = 0; i < 6; i++) {
				if(adjacentTiles.get(i).compare(target)) {
					if(i == 0)
						return HexDirection.A;
					if(i == 1)
						return HexDirection.B;
					if(i == 2)
						return HexDirection.C;
					if(i == 3)
						return HexDirection.D;
					if(i == 4)
						return HexDirection.E;
					if(i == 5)
						return HexDirection.F;
				}
				
			}
			
		}
		
		return null;
		
	}
	
	public static HexDirection getHexSideFacingTarget(Cord start, Cord target) {
		
		var startDistance = HexGridUtility.distance(start, target);
		var baseCase = getHexSideFacingTargetBaseCases(start, target, startDistance);
		if(baseCase != null) {
			return baseCase;
		}
		
		
		HexDirection closestDir = HexDirection.A;
		var closestDist = getDistanceInDirection(start, target, startDistance, HexDirection.A);
		
		for(var dir : HexDirection.getDirections()) {
			var newDist = getDistanceInDirection(start, target, startDistance, dir);
			
			if(newDist < closestDist) {
				closestDist = newDist;
				closestDir = dir;
			}
			
		}

		return closestDir;
		
		// if x2 < x1 and y2 == y1 than A
		// if getDistanceInDirection(A) < getDistanceInDirection(B) than A elif getDistanceInDirection(A) > getDistanceInDirection(B) than B, otherwise A/B
		// if x2 <= x1 , y2 > y1 than B
		
		// if x2 >= x1 and y2 > y1 than C 
		
		// if x2 > x1 and y2 == y1 than D
		
		// if x2 >= x1 and y2 < y1 than E
		
		// if x2 <= x1 and y2 < y1 thanF
		
		//throw new Exception("Direction not found for cords: ("+start.toString()+") to ("+target.toString()+")");
	}
	
	public static int getDistanceInDirection(Cord start, Cord target, int distance, HexDirection dir) {
		
		Cord newCord = start;
		boolean clockwise = true;
		for(int i = 0; i < distance; i++) {
			newCord = getHexInDirection(dir, newCord,clockwise);
			clockwise = !clockwise;
		}
		
		return HexGridUtility.distance(target, newCord);
	}
	
	/*private static HexDirection getDirectionBetweenTwoDirections(Cord start, int startDistance,
			HexDirection dirOne, HexDirection dirTwo, HexDirection dirInbetween) {
		if(getDistanceInDirection(start, startDistance, dirOne) < getDistanceInDirection(start, startDistance, dirTwo))
			return dirOne;
		else if(getDistanceInDirection(start, startDistance, dirOne) > getDistanceInDirection(start, startDistance, dirTwo))
			return dirTwo;
		else
			return dirInbetween;
	}*/
	
	public enum HexDirection {
		A,AB,B,BC,C,CD,D,DE,E,EF,F,FA;
		
		public static Facing getFacing(HexDirection dir) {
			switch(dir) {
			
			case A: 
				return Facing.A;
			case AB:
				return Facing.AB;
			case B:
				return Facing.B;
			case BC:
				return Facing.BC;
			case C:
				return Facing.C;
			case CD:
				return Facing.CD;
			case D:
				return Facing.D;
			case DE:
				return Facing.DE;
			case E:
				return Facing.E;
			case EF:
				return Facing.EF;
			case F:
				return Facing.F;
			case FA:
				return Facing.FA;
			default:
				return null;
			
			}
		}
		
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
		
		var distance = getDistanceDirections(facing1, facing2, true) < getDistanceDirections(facing1, facing2, false) 
				? getDistanceDirections(facing1, facing2, true) : getDistanceDirections(facing1, facing2, false);

		return distance > 1 && distance <= 4;
	}

	public static boolean rear(HexDirection facing1, HexDirection facing2) {
		
		var distance = getDistanceDirections(facing1, facing2, true) < getDistanceDirections(facing1, facing2, false) 
				? getDistanceDirections(facing1, facing2, true) : getDistanceDirections(facing1, facing2, false);

		return distance == 0 || distance == 1;
	}
	
	private static int getDistanceDirections(HexDirection facing1, HexDirection facing2, boolean clockwise) {
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
	
	public static HexDirection getFaceInDirection(HexDirection facing, boolean clockwise, int numberOfFacesAway) {
		
		var lastFace = facing;
		
		for(int i = 0; i < numberOfFacesAway; i++) {
			lastFace = getFaceInDirection(lastFace, clockwise);
		}
		
		return lastFace;
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
			return clockwise ? HexDirection.C : HexDirection.B;
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
	
	public static List<Hex> getHexNeighbours(Cord pos) {
		 List<Hex> neighbours = new ArrayList<Hex>();
		 
		 var cords = getHexNeighbourCords(pos);
		 
		 for(var cord : cords) {
			 var hex = GameWindow.gameWindow.findHex(cord.xCord, cord.yCord);
			 if(hex != null)
				 neighbours.add(hex);
		 }
		 
		 
		 return neighbours;
	} 
	
	public static List<Cord> getHexNeighbourCords(Cord pos) {
        List<Cord> neighbors = new ArrayList<>();

        // A
        neighbors.add(new Cord(pos.xCord - 1, pos.yCord));

        // B
        if (pos.yCord % 2 != 0)
            neighbors.add(new Cord(pos.xCord, pos.yCord + 1));
        else
            neighbors.add(new Cord(pos.xCord-1, pos.yCord + 1));

        // C
        if (pos.yCord % 2 != 0)
            neighbors.add(new Cord(pos.xCord+1, pos.yCord + 1));
        else
            neighbors.add(new Cord(pos.xCord, pos.yCord + 1));

        // D
        neighbors.add(new Cord(pos.xCord + 1, pos.yCord));

        // E
        if (pos.yCord % 2 != 0)
            neighbors.add(new Cord(pos.xCord + 1, pos.yCord - 1));
        else
            neighbors.add(new Cord(pos.xCord, pos.yCord - 1));

        // F
        if (pos.yCord % 2 != 0)
            neighbors.add(new Cord(pos.xCord, pos.yCord - 1));
        else
            neighbors.add(new Cord(pos.xCord-1, pos.yCord - 1));

        return neighbors;
    }

	public static Cord getHexInDirection(HexDirection dir, Cord startPos, int distance) 
		{
		
		var lastCord = startPos;
		boolean clockwise = true;
		
		for(int i = 0; i < distance; i++) {
			lastCord = getHexInDirection(dir, lastCord, clockwise);
			clockwise = !clockwise;
		}
		
		return lastCord;
	}
	
    public static Cord getHexInDirection(HexDirection dir, Cord pos) {
        List<Cord> neighbors = getHexNeighbourCords(pos);

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
        List<Cord> neighbors = getHexNeighbourCords(pos);

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
