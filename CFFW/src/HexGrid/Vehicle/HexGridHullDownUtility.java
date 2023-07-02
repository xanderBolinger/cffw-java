package HexGrid.Vehicle;

import java.awt.Color;
import java.awt.Graphics2D;

import Conflict.GameWindow;
import Vehicle.HullDownPositions.HullDownPosition;

public class HexGridHullDownUtility {

	
	public static void showHullDownPositions(Graphics2D g2) {
		if(GameWindow.gameWindow.game.vehicleManager.hullDownPositions == null ||
				GameWindow.gameWindow.game.vehicleManager.hullDownPositions.positions == null)
			return;
		
		
		var positions = GameWindow.gameWindow.game.vehicleManager.hullDownPositions.positions.keySet();
		var hexMap = GameWindow.gameWindow.hexGrid.panel.hexMap;
		
		for(var pos : positions) {
			var hex = hexMap.get(pos.xCord).get(pos.yCord);
			g2.setColor(Color.RED);
			g2.drawString("HD", 
					(int) (hex.xpoints[0] - (hex.getBounds().width * 0.5)),
					(int) (hex.ypoints[0] + (hex.getBounds().height * 0.3)));
		}
		
		
	}
	
	public static HullDownPosition getHullDownPosition(int x, int y) {
		var positions = GameWindow.gameWindow.game.vehicleManager.hullDownPositions.positions.keySet();
		
		for(var pos : positions) {
			
			if(x == pos.xCord && y == pos.yCord) {
				return GameWindow.gameWindow.game.vehicleManager.hullDownPositions.positions.get(pos);
			}
			
		}
		
		return null;
	}
	
	
}
