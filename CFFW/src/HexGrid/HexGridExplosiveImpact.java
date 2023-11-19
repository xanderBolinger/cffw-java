package HexGrid;

import java.util.ArrayList;

import Conflict.GameWindow;
import Hexes.Feature;
import Hexes.Hex;
import Items.PCAmmo;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class HexGridExplosiveImpact {

	
	public static void explosiveImpact(PCAmmo pcAmmo, int x, int y, ArrayList<Unit> targetUnits) {
		
		var hex = GameWindow.gameWindow.findHex(x, y);
		
		createCoverPosition(pcAmmo, targetUnits, hex);
		defoliateHex(hex, pcAmmo, x, y);
		
	}
	
	private static void defoliateHex(Hex hex, PCAmmo pcAmmo, int x, int y) {
		if(hex.concealment <= 0)
			return;
		
		String results = "Defoliate Impact("+x+","+y
				+"): "+pcAmmo.name+", ";
		
		if(pcAmmo.defoliateChance == 100) {
			hex.concealment -= pcAmmo.defoliateMagnitude;
			results += pcAmmo.defoliateMagnitude;
		}
		else {
			var defoliateRoll = DiceRoller.roll(0, 99);
			if(defoliateRoll <= pcAmmo.defoliateChance) {
				hex.concealment--;
				results += "1, ";
			}
			results += "defoliate chance: "+pcAmmo.defoliateChance+", roll: "+defoliateRoll;
		}
		
		if(hex.concealment < 0)
			hex.concealment = 0;
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue(results);
		
	}
	
	private static void createCoverPosition(PCAmmo pcAmmo, ArrayList<Unit> targetUnits,
			Hex hex) {
		
		var newCoverPositions = DiceRoller.roll(pcAmmo.impactCraterMin, pcAmmo.impactCrafterMax);
		
		if(newCoverPositions > 0) {
			hex.features.add(new Feature("Impact Crater", newCoverPositions));
			
			for(var unit : targetUnits)
				unit.seekCover(hex, GameWindow.gameWindow);
			
		}
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Impact crater cover positions: "+newCoverPositions);
	}
	
	public static void burnHex(int x, int y, int magnitude) {
		
	}
	
	public static void burnHexesOvertime() {
		
	}
	
}
