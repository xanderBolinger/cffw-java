package HexGrid.Waypoint;

import java.io.Serializable;

public class Waypoint implements Serializable {
	public int x;
	public int y;
	public String speedOnArival;
	
	public Waypoint(int x, int y, String speedOnArival) {
		this.x = x; 
		this.y = y;
		this.speedOnArival = speedOnArival;
	}
	
	public boolean compareTo(Waypoint waypoint) {
		return x == waypoint.x && y == waypoint.y;
	}
	
}
