package HexGrid;

import java.util.ArrayList;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import Hexes.Hex;
import Unit.Unit;

public class CalculateLOS {

	public static void calc(Unit unit, Unit targetUnit) {
		
		ArrayList<Cord> hexes = TraceLine.GetHexes(new Cord(unit.X, unit.Y), new Cord(targetUnit.X, targetUnit.Y), GameWindow.gameWindow.hexGrid.panel);

		if(hexes.size() > 2) {
			hexes.remove(0);
			hexes.remove(hexes.size()-1);
		}
		
		int concealment = 0; 
		
		for(Cord hex : hexes) {
			Hex foundHex = GameWindow.gameWindow.findHex(hex.xCord, hex.yCord);
			if(foundHex == null)
				continue; 
			concealment += foundHex.concealment;
		}
		
		if(concealment >= 5)
			return;
		
		unit.lineOfSight.add(targetUnit);
		
	}
	
}
