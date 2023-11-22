package HexGrid;

import java.awt.Color;
import java.awt.Graphics2D;

import Conflict.GameWindow;
import Unit.Unit;
import Vehicle.Vehicle;

public class DrawLosVehicles {
	
	public static void drawLosVehicles(Graphics2D g2) {
		
		for(var vic : GameWindow.gameWindow.vehicleCombatWindow.vehicles) {
			drawLosForVehicle(g2, vic);
		}
		
	}
	
	public static void drawLosForVehicle(Graphics2D g2, Vehicle vic) {
		var cord = vic.movementData.location;
		var center = GameWindow.gameWindow.hexGrid.panel.getHexCenter(cord.xCord, cord.yCord);
		
		for(var losVehicle : vic.losVehicles) {
			
			var targetCord = losVehicle.movementData.location;
			var targetCenter = GameWindow.gameWindow.hexGrid.panel
					.getHexCenter(targetCord.xCord, targetCord.yCord);
			var color = getLineColor(vic, losVehicle);
			DrawLos.drawLineBetweenUnits(g2, center, targetCenter, color);
		}
		
		for(var losUnit : vic.losUnits) {
			var targetCenter = GameWindow.gameWindow.hexGrid.panel
					.getHexCenter(losUnit.X, losUnit.Y);
			var color = getLineColor(vic, losUnit);
			DrawLos.drawLineBetweenUnits(g2, center, targetCenter, color);
		}
		
	}
	
	
	
	private static Color getLineColor(Vehicle vic, Unit targetUnit) {
		var color = Color.MAGENTA;
		
		var spottingTarget = spottingUnit(vic, targetUnit);
		
		if(spottingTarget)
			color = color.BLUE;
		
		return color;
	}
	
	private static boolean spottingUnit(Vehicle vic, Unit targetUnit) {
		
		for(var spottedTrooper : vic.spottedTroopers)
			if(targetUnit.individuals.contains(spottedTrooper))
				return true;
		
		return false;
	}
	
	private static Color getLineColor(Vehicle vic, Vehicle vic2) {
		
		var color = Color.MAGENTA;
		
		var vicSpottingVic2 = vic.spottedVehicles.contains(vic2);
		var vic2SpottingVic = vic2.spottedVehicles.contains(vic);
		
		if(vicSpottingVic2 && vic2SpottingVic)
			color = Color.GREEN;
		else if(vicSpottingVic2)
			color = color.BLUE;
		else if(vic2SpottingVic)
			color = color.RED;
		
		return color;
	}
	
}