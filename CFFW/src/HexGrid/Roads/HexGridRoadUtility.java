package HexGrid.Roads;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Polygon;

import CeHexGrid.Colors;
import Conflict.GameWindow;
import CorditeExpansion.Cord;

public class HexGridRoadUtility {
	
	public static void leftClickHex(int xCord, int yCord,
			boolean addRoad, boolean newSegment, boolean highway,
			boolean removeRoad, boolean removeSegment) {
		
		System.out.println("Left click hex add road");
		
		if(newSegment) {
			
		} else if(addRoad) {
			
		} 
		
	}
	
	public static void drawRoads(Graphics2D g2) {
		
		for(var segment : GameWindow.gameWindow.game.roadManager.segments) {
			
			Cord lastRoad = null;
			
			for(var road : segment.getSegment()) {
				if(lastRoad == null) {
					lastRoad = road.point;
					continue;
				}
				
				drawRoad(g2, lastRoad, road);
				lastRoad = road.point;
			}
			
		}		
		
	}
	
	private static void drawRoad(Graphics2D g2, Cord lastRoad, Road road) {
		var hexGrid = GameWindow.gameWindow.hexGrid;
		var p1 = hexGrid.panel.getHexCenter(lastRoad.xCord, lastRoad.yCord);
		var p2 = hexGrid.panel.getHexCenter(road.point.xCord, road.point.yCord);
		var line = new Polygon();
		line.addPoint(p1[0], p1[1]);
		line.addPoint(p2[0], p2[1]);
		g2.setColor(Colors.PATH);
		g2.setStroke(new BasicStroke(2f));
		g2.draw(line);
	}
}