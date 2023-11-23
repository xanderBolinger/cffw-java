package HexGrid.Waypoint;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Conflict.GameWindow;

public class HexGridWaypointUtility {

	public static void addWaypoint(int[] clickedHex) {
		var unit = GameWindow.gameWindow.hexGrid.panel.selectedUnit.unit;
		var lastWaypoint = unit.waypointData.waypoints.size() > 0 ?
				unit.waypointData.waypoints.get(unit.waypointData.waypoints.size()-1) : null;
		var distanceFromLastWaypoint = GameWindow.dist(clickedHex[0], clickedHex[1],
				lastWaypoint == null ?  unit.X : lastWaypoint.x, 
						lastWaypoint == null ? unit.Y : lastWaypoint.y);
		if(lastWaypoint != null)
			System.out.println("Distance from last waypoint: "+distanceFromLastWaypoint
					+", X: "+lastWaypoint.x+
					", Y: "+lastWaypoint.y);
		else 
			System.out.println("Last waypoint is null");
		if((clickedHex[0] == unit.X && clickedHex[1] == unit.Y)
				|| distanceFromLastWaypoint > 1) {
			System.out.println("returning, dist from last: "+distanceFromLastWaypoint+", "
					+ "Clicked X: "+clickedHex[0]+", Clicked Y: "+clickedHex[1]+
					", Unit X: "+unit.X+", Unit Y: "+unit.Y);
			return;
		}
		
		System.out.println("Add waypoint");
		unit.waypointData.addWaypoint(new Waypoint(clickedHex[0], clickedHex[1], 
				GameWindow.gameWindow.hexGrid.comboBoxRouteSpeed.getSelectedItem().toString()),unit);
	}
	
	public static void draw(Graphics2D g2) {
		
		if(WaypointManager.addWaypoints)
			HexGridWaypointUtility.drawCursor(g2);
		
		if(GameWindow.gameWindow.hexGrid.panel.selectedUnit != null)
			drawRoute(g2);
	}
	
	private static void drawRoute(Graphics2D g2) {
		var grid = GameWindow.gameWindow.hexGrid.panel;
		var unit = grid.selectedUnit.unit;
		var waypointData = unit.waypointData;
		
		boolean first = true;
		
		ArrayList<int[]> centers = new ArrayList<int[]>();
		var startingCenter = grid.getHexCenter(unit.X, unit.Y);
		for(var waypoint : waypointData.waypoints) {
			var center = grid.getHexCenter(waypoint.x, waypoint.y);
			
			centers.add(center);
			
			if(first) {
				DrawWaypoints.drawLine(g2, startingCenter[0], startingCenter[1], 
						center[0], center[1]);
			} else {
				var lastCenter = centers.get(centers.size()-2);
				DrawWaypoints.drawLine(g2, lastCenter[0], lastCenter[1], 
						center[0], center[1]);
			}
			
			first = false;
		}
		
		for(var wp : waypointData.waypoints) {
			var center = grid.getHexCenter(wp.x, wp.y);
			DrawWaypoints.drawYellowSquare(g2, center[0], center[1], true, 
					String.valueOf(wp.waypointSpeed.charAt(0)));
		} 
		
	}
	
	
	private static void drawCursor(Graphics2D g2) {
		int x = HexGrid.HexGrid.mouseX, y = HexGrid.HexGrid.mouseY;
		
		if(x < 0 || y < 0)
			return;
		
		DrawWaypoints.drawYellowSquare(g2, x, y, false, String.valueOf(GameWindow.gameWindow.hexGrid.comboBoxRouteSpeed
				.getSelectedItem().toString().charAt(0)));
		
	}
	
}
