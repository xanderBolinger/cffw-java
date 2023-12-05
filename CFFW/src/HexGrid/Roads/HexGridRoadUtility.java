package HexGrid.Roads;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;

import CeHexGrid.Colors;
import Conflict.GameWindow;
import CorditeExpansion.Cord;
import HexGrid.HexGridShadeHexes;

public class HexGridRoadUtility {
	
	public static void rightClickHex(int xCord, int yCord) {
		
		var rm = GameWindow.gameWindow.game.roadManager;
		
		var segment  = rm.getRoadSegmentFromCord(xCord, yCord, true);
		
		if(segment.size() == 0 && rm.selectedSegments()) {
			
			for(var s : rm.getSelectedSegments()) {
				for(var r : s.getSegment()) {
					removeShadeRoad(r);
				}
			}
			
			System.out.println("Unselect segment");
			rm.clearSelectedSegments();
			
		} else {
			
			for(var s : segment) {
				for(var r : s.getSegment()) {
					shadeRoad(r);
				}
			}
			
			
			rm.setSelectedSegments(segment);
			System.out.println("Select segment");
		}
		
		
	}
	
	public static void removeShadeRoad(Road r) {
		HexGridShadeHexes.shadedHexes.remove(new Point2D.Double(r.point.xCord, r.point.yCord));
	}
	
	public static void shadeRoad(Road r) {
		var point = new Point2D.Double(r.point.xCord,
				r.point.yCord);
		
		if(HexGridShadeHexes.shadedHexes.contains(point))
			return;
		
		HexGridShadeHexes.shadedHexes.add(point);
	}
	
	public static void leftClickHex(int xCord, int yCord,
			boolean addRoad, boolean newSegment, boolean highway,
			boolean removeRoad, boolean removeSegment, boolean river) {
		
		System.out.println("Left click hex add road");
		var rm = GameWindow.gameWindow.game.roadManager;
		if(newSegment) {
			rm.addSegment(xCord, yCord, highway, river);
		} else if(addRoad) {
			System.out.println("add road");
			rm.addRoad(xCord, yCord, highway, river);
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
		g2.setColor(road.river ? Colors.WATER_BLUE : road.highway ? Colors.HIGHWAY : Colors.PATH);
		g2.setStroke(road.highway || road.river ? new BasicStroke(4f) : new BasicStroke(2.5f));
		g2.draw(line);
	}
}
