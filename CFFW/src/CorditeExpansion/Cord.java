package CorditeExpansion;

public class Cord {
	public int xCord; 
	public int yCord; 
	
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
	
	@Override
	public String toString() {
		return xCord+","+yCord;
	}
	
}
