package HexGrid.Waypoint;

import java.awt.Graphics2D;

public class HexGridWaypointUtility {

	
	
	public static void drawCursor(Graphics2D g2) {
		int x = HexGrid.HexGrid.mouseX, y = HexGrid.HexGrid.mouseY;
		
		if(x < 0 || y < 0)
			return;
		
		DrawWaypoints.drawYellowSquare(g2, x, y);
		
	}
	
}
