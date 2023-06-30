package CorditeExpansion;

import java.io.Serializable;
import java.util.ArrayList;

import CeHexGrid.Chit.Facing;

public class Cord implements Serializable {
	public int xCord;
	public int yCord;

	public Facing facing;

	public Cord(int xCord, int yCord) {
		this.xCord = xCord;
		this.yCord = yCord;
	}

	public boolean compare(Cord newCord) {
		if (xCord == newCord.xCord && yCord == newCord.yCord) {
			return true;
		} else {
			return false;
		}
	}

	public void setFacing(boolean clockwise) {

		if (facing == null)
			facing = Facing.A;

		if (clockwise) {
			facing = Facing.turnClockwise(facing);
		} else {
			facing = Facing.turnCounterClockwise(facing);
		}

	}

	public static boolean containsHex(int[] cords, ArrayList<Cord> hexes) {
		for(Cord c : hexes) {
			if(cords[0] == c.xCord && cords[1] == c.yCord)
				return true;
		}
		
		return false;
	}
	
	public static boolean containsHex(Cord cord, ArrayList<Cord> hexes) {
		for(Cord c : hexes) {
			if(cord.xCord == c.xCord && cord.yCord == c.yCord)
				return true;
		}
		
		return false;
	}

	@Override
	public String toString() {
		return xCord + "," + yCord;
	}

}
