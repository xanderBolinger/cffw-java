package HexGrid.Waypoint;

import java.awt.Graphics2D;

import Conflict.GameWindow;
import CorditeExpansion.Cord;

public class HexGridWaypointUtility {

	public static void addWaypoint(Cord clickedHex) {
		
		
	}
	
	public static void draw(Graphics2D g2) {
		var p = GameWindow.gameWindow.hexGrid.panel;
		
		if(WaypointManager.addWaypoints)
			HexGridWaypointUtility.drawCursor(g2);
		
		//var pos1 = p.getHexCenter(0, 0);
		//var pos2 = p.getHexCenter(2, 2);
		//DrawWaypoints.drawLine(g2,pos1[0],pos1[1],pos2[0],pos2[1]);
		
	}
	
	public static void drawCursor(Graphics2D g2) {
		int x = HexGrid.HexGrid.mouseX, y = HexGrid.HexGrid.mouseY;
		
		if(x < 0 || y < 0)
			return;
		
		DrawWaypoints.drawYellowSquare(g2, x, y);
		
	}
	
}
