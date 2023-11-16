package HexGrid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;

import Conflict.GameWindow;
import HexGrid.HexGrid.DeployedUnit;
import Unit.Unit;

public class DrawLos {

	public static void drawLos(Graphics2D g2, ArrayList<DeployedUnit> deployedUnits) {
		
		for(var unit : deployedUnits) {
		
			drawLos(g2, unit);
			
		}
		
	}
	
	public static void drawLos(Graphics2D g2, DeployedUnit unit) {
		
		var center = GameWindow.gameWindow.hexGrid.panel.getHexCenter(unit.xCord, unit.yCord);
		
		drawLosForUnit(g2, unit.unit, center);
		
	}
	
	private static void drawLosForUnit(Graphics2D g2, Unit unit, int[] startPos) {
		
		for(var target : unit.lineOfSight) {
			
			var center = GameWindow.gameWindow.hexGrid.panel.getHexCenter(target.X, target.Y);
			
			var color = getLineColor(unit, target);
			
			drawLineBetweenUnits(g2, startPos, center, color);
		}
		
	}
	
	private static void drawLineBetweenUnits(Graphics2D g2, 
			int[] startPos, int[] center, Color color) {
		var losLine = new Polygon();
		losLine.addPoint(startPos[0], startPos[1]);
		losLine.addPoint(center[0], center[1]);
		
		g2.setColor(color);
		g2.setStroke(new BasicStroke(1f));
		g2.draw(losLine);
	}
	
	private static Color getLineColor(Unit unit, Unit target) {
		
		var color = Color.MAGENTA;
		
		var unitSpottingTarget = spottingUnit(unit, target);
		var targetSpottingUnit = spottingUnit(target, unit);
		
		if(unitSpottingTarget && targetSpottingUnit)
			color = Color.GREEN;
		else if(unitSpottingTarget)
			color = color.BLUE;
		else if(targetSpottingUnit)
			color = color.RED;
		
		return color;
	}
	
	private static boolean spottingUnit(Unit unit, Unit target) {
		
		for(var trooper : unit.individuals)
			for(var spot : trooper.spotted)
				for(var spottedTrooper : spot.spottedIndividuals)
					if(target.individuals.contains(spottedTrooper))
						return true;
		
		return false;
	}
	
}
