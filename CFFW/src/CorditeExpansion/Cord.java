package CorditeExpansion;

import CeHexGrid.Chit.Facing;

public class Cord {
	public int xCord; 
	public int yCord; 
	
	public Facing facing;
	
	public Cord(int xCord, int yCord) {
		this.xCord = xCord;
		this.yCord = yCord;
	}

	public boolean compare(Cord newCord) {
		if(xCord == newCord.xCord && yCord == newCord.yCord) {
			return true; 
		} else {
			return false;
		}
	}
	
	public void setFacing(boolean clockwise) {
		
		if(facing == null)
			facing = Facing.A;
		
		if(clockwise) {
			facing = Facing.turnClockwise(facing);
		} else {
			facing = Facing.turnCounterClockwise(facing);
		}
		
	
	}
	
	
	
	@Override
	public String toString() {
		return xCord+","+yCord;
	}
	
}
