package HexGrid.Fortifications;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Conflict.GameWindow;
import Fortifications.Fortification;
import Vehicle.HullDownPositions.HullDownPosition;

public class HexGridFortificationsUtility {

	public static void showFortifications(Graphics2D g2) {
		if(GameWindow.gameWindow.game.fortifications.fortifications == null)
			return;
		
		var positions = GameWindow.gameWindow.game.fortifications.fortifications.keySet();
		var hexMap = GameWindow.gameWindow.hexGrid.panel.hexMap;
		
		for(var pos : positions) {
			var hex = hexMap.get(pos.xCord).get(pos.yCord);
			g2.setColor(Color.GREEN);
			g2.drawString("Ft", 
					(int) (hex.xpoints[0] - (hex.getBounds().width * 0.15)),
					(int) (hex.ypoints[0] + (hex.getBounds().height * 0.3)));
		}
		
	}
	
	public static ArrayList<Fortification> getFortifications(int x, int y) {
		if( GameWindow.gameWindow == null ||  GameWindow.gameWindow.game == null ||  GameWindow.gameWindow.game.fortifications == null)
			return new ArrayList<Fortification>();
		
		var positions = GameWindow.gameWindow.game.fortifications.fortifications.keySet();
		
		ArrayList<Fortification> fortifications = new ArrayList<Fortification>();
		
		for(var pos : positions) {
			
			if(x == pos.xCord && y == pos.yCord) {
				fortifications.add(GameWindow.gameWindow.game.fortifications.fortifications.get(pos));
			}
			
		}
		
		return fortifications;
	}
}
