package HexGrid;

import java.awt.Graphics2D;
import java.util.ArrayList;

import CeHexGrid.Colors;
import Conflict.GameWindow;
import CorditeExpansion.Cord;

public class HexGridShadeHexes {

	public static ArrayList<Cord> shadedHexes = new ArrayList<Cord>();
	
	public static void shadeHexes(Graphics2D g2) {
		var panel = GameWindow.gameWindow.hexGrid.panel;
		var hexMap = panel.hexMap;
		
		for(var cord : shadedHexes) {
			if(cord.xCord >= hexMap.size() 
					|| cord.yCord >= hexMap.size() 
					|| cord.xCord < 0 || cord.yCord < 0)
				continue;
			
			panel.shadeHex(g2, hexMap.get(cord.xCord).get(cord.yCord), Colors.SOFT_BLUE);
		}
		
	}
	
	
}
