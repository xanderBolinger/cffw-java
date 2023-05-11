package HexGrid;

import java.util.ArrayList;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import Hexes.Hex;
import Unit.Unit;

public class CalculateLOS {

	public static void calc(Unit unit, Unit targetUnit) {
		
		ArrayList<Cord> hexes = TraceLine.GetHexes(new Cord(unit.X, unit.Y), new Cord(targetUnit.X, targetUnit.Y), GameWindow.gameWindow.hexGrid.panel);

		Cord spotterCord;
		Cord targetCord; 
		
		if(hexes.size() > 2) {
			spotterCord = hexes.remove(0);
			targetCord = hexes.remove(hexes.size()-1);
		} else {
			
			
			unit.lineOfSight.add(targetUnit);
			return;
		}
		
		int spotterElevation = GameWindow.gameWindow.findHex(spotterCord.xCord, spotterCord.yCord).elevation;
		int targetElevation = GameWindow.gameWindow.findHex(targetCord.xCord, targetCord.yCord).elevation;
		
		int concealment = 0; 
		
		for(Cord hex : hexes) {
			Hex foundHex = GameWindow.gameWindow.findHex(hex.xCord, hex.yCord);
			if(foundHex == null)
				continue; 
			
			double slopeToTarget = ((double)targetElevation - (double)spotterElevation) / ((double)hexes.size() + 2.0 - 1.0);
			double slopeToObstacle = ((double)GameWindow.gameWindow.findHex(hex.xCord, hex.yCord).elevation - (double)spotterElevation) / ((double)hexes.indexOf(hex)+2.0 - 1.0);
			
			if(slopeToObstacle < slopeToTarget)
				continue;

			concealment += foundHex.concealment;
			concealment += GameWindow.gameWindow.game.smoke.getConcealment(hex);
			
		}
		
		if(concealment >= 5)
			return;
		
		unit.lineOfSight.add(targetUnit);
		
	}
	
	public static int getConcelamentValue(Unit unit, Unit targetUnit) {
		
		ArrayList<Cord> hexes = TraceLine.GetHexes(new Cord(unit.X, unit.Y), new Cord(targetUnit.X, targetUnit.Y), GameWindow.gameWindow.hexGrid.panel);
		
		int concealment = 0; 
		if(hexes.size() > 2) {
			hexes.remove(0);
		} else if(hexes.size() == 1) {
			Cord hex = hexes.get(0);
			Hex foundHex = GameWindow.gameWindow.findHex(hex.xCord, hex.yCord);
			concealment += foundHex.concealment;
			concealment += GameWindow.gameWindow.game.smoke.getConcealment(hex);
		} else {
			hexes.remove(0);
			Cord hex = hexes.get(0);
			Hex foundHex = GameWindow.gameWindow.findHex(hex.xCord, hex.yCord);
			concealment += foundHex.concealment;
			concealment += GameWindow.gameWindow.game.smoke.getConcealment(hex);
			return concealment;
		}
		
		
		for(Cord hex : hexes) {
			Hex foundHex = GameWindow.gameWindow.findHex(hex.xCord, hex.yCord);
			if(foundHex == null)
				continue; 
			concealment += foundHex.concealment;
			concealment += GameWindow.gameWindow.game.smoke.getConcealment(hex);
			
		}
		
		return concealment;
	}
	
	public static int getConcealmentAlm(Unit shooterUnit, Unit targetUnit) {
		if(GameWindow.gameWindow == null)
			return 0;
		
		ArrayList<Cord> hexes = TraceLine.GetHexes(new Cord(shooterUnit.X, shooterUnit.Y), new Cord(targetUnit.X, targetUnit.Y), GameWindow.gameWindow.hexGrid.panel);
		
		if(hexes.size() != 1)
			hexes.remove(0);
		
		int alm = 0; 
		int concealment = 0; 
		
		for(Cord hex : hexes) {
			Hex foundHex = GameWindow.gameWindow.findHex(hex.xCord, hex.yCord);
			if(foundHex == null)
				continue; 
			concealment += foundHex.concealment;
			alm += GameWindow.gameWindow.game.smoke.getAlm(hex);
		}
		
		alm -= concealment * 2;
		
		if(alm < -14)
			alm = -14; 
		
		return alm;
	} 
	
	
}
