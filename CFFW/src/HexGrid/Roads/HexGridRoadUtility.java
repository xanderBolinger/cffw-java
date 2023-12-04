package HexGrid.Roads;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Polygon;

import CeHexGrid.Colors;
import Conflict.GameWindow;

public class HexGridRoadUtility {
	
	public static void drawRoads(Graphics2D g2) {
		
		for(var segment : GameWindow.gameWindow.game.roadManager.segments) {
			
			for(var road : segment.getSegment()) {
				drawRoad(g2, road);
			}
			
		}		
		
	}
	
	
	private static void drawRoad(Graphics2D g2, Road road) {
		var hexGrid = GameWindow.gameWindow.hexGrid;
		var p1 = hexGrid.panel.getHexCenter(road.point1.xCord, road.point1.yCord);
		var p2 = hexGrid.panel.getHexCenter(road.point2.xCord, road.point2.yCord);
		var line = new Polygon();
		line.addPoint(p1[0], p1[1]);
		line.addPoint(p2[0], p2[1]);
		g2.setColor(Colors.PATH);
		g2.setStroke(new BasicStroke(2f));
		g2.draw(line);
	}
}
