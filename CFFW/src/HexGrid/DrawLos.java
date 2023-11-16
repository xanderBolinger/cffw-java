package HexGrid;

import java.awt.Graphics2D;
import java.util.ArrayList;

import HexGrid.HexGrid.DeployedUnit;
import Unit.Unit;

public class DrawLos {

	public static void drawLos(Graphics2D g2, ArrayList<DeployedUnit> deployedUnits) {
		
		for(var unit : deployedUnits) {
			
			drawLosForUnit(g2, unit.unit);
			
		}
		
		
	}
	
	static void drawLosForUnit(Graphics2D g2, Unit unit) {
		
	}
	
	static void drawLineBetweenUnits() {
		
	}
	
	
}
