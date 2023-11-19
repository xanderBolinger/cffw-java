package HexGrid;

import java.awt.Graphics2D;
import java.util.ArrayList;

import CeHexGrid.Colors;
import Conflict.GameWindow;
import CorditeExpansion.Cord;
import java.awt.geom.Point2D;

public class HexGridShadeHexes {

	public static ArrayList<Point2D> shadedHexes = new ArrayList<Point2D>();
		
	final static int maxShadedHexes = 1000;
	
	public static void shadeHexes(Graphics2D g2, int screenWidth, int screenHeight) {
		var panel = GameWindow.gameWindow.hexGrid.panel;
		var hexMap = panel.hexMap;
		int count = 0;
		for(var cord : shadedHexes) {
			if(cord.getX() >= hexMap.size() 
					|| cord.getY() >= hexMap.size() 
					|| cord.getX() < 0 || cord.getY() < 0)
				continue;
			
			var hex = hexMap.get((int)cord.getX()).get((int)cord.getY());
			if(!HexGrid.OnScreen(hex, screenWidth, screenHeight))
				continue;
			var xpoint = hex.xpoints[0];
			var ypoint = hex.ypoints[0];
			if(xpoint > screenWidth && ypoint > screenHeight)
				break;
			
			count++;
			panel.shadeHex(g2, hex, Colors.SOFT_BLUE);
		
			if(count >= maxShadedHexes)
				return;
		}
		
	}
	
	
}
