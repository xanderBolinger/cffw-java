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
		var rm = GameWindow.gameWindow.game.roadManager;
		if(newSegment) {
			rm.addSegment(xCord, yCord, highway);
		} else if(addRoad) {
			System.out.println("add road");
			rm.addRoad(xCord, yCord, highway);
		} else if(removeSegment) {
			rm.removeSegment(xCord, yCord);
		} else if(removeRoad) {
			rm.removeRoad(xCord, yCord);
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
		g2.setColor(road.highway ? Colors.HIGHWAY : Colors.PATH);
		g2.setStroke(road.highway ? new BasicStroke(4f) : new BasicStroke(2.5f));
		g2.draw(line);
	}
}
