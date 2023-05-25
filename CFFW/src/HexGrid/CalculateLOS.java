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
		
		// Calculations assumes spotter is at x 1 
		// target x is always greater than spotter therefore spotter is x1 and y1 while target is x2 and y2 
		// The number of hexes in between target and spotter plus 1 gets target x 
		
		int spotterElevation = GameWindow.gameWindow.findHex(spotterCord.xCord, spotterCord.yCord).elevation+1;
		int targetElevation = GameWindow.gameWindow.findHex(targetCord.xCord, targetCord.yCord).elevation+1;
		double slopeToTarget = ((double)targetElevation - (double)spotterElevation) / ((double)hexes.size() + 1.0 - 1.0);
		
		int concealment = 0; 
		
		for(Cord hex : hexes) {
			Hex foundHex = GameWindow.gameWindow.findHex(hex.xCord, hex.yCord);
			if(foundHex == null)
				continue; 
						
			double currentHexElevation = (double)GameWindow.gameWindow.findHex(hex.xCord, hex.yCord).elevation;
			
			boolean buildings = false;
			int buildingFloors = 0;
			
			for(var building : foundHex.buildings) {
				buildings = true;
				if(building.floors.size() > buildingFloors)
					buildingFloors = building.floors.size();
			}
			
			double slopeToBrush = (currentHexElevation - (double)spotterElevation) / ((double)hexes.indexOf(hex)+1.0 - 1.0);
			double slopeToTrees = (currentHexElevation+2 - (double)spotterElevation) / ((double)hexes.indexOf(hex)+1.0 - 1.0);
			double slopeToBuilding = (currentHexElevation+buildingFloors+1 - (double)spotterElevation) / ((double)hexes.indexOf(hex)+1.0 - 1.0);
			
			int brushConcealment = 0;
			int treeConcealment = GameWindow.gameWindow.game.smoke.getConcealment(hex);
			// "Light Forest", "Medium Forest", "Heavy Forest", "Brush", "Heavy Brush", "Light Rock", "Medium Rock", "Heavy Rock", "Light Urban Sprawl", "Dense Urban Sprawl", "Rubble", "Small Depression", "Large Depression"
			for(var feature : foundHex.features) {
				if(feature.featureType == "Light Forest")
					treeConcealment += 1;
				else if( feature.featureType == "Medium Forest")
					treeConcealment += 2;
				else if(feature.featureType == "Heavy Forest")
					treeConcealment += 3;
			}
			brushConcealment = foundHex.concealment - treeConcealment;
			
			
			if(targetElevation >= spotterElevation) {
				
				if(slopeToTrees >= slopeToTarget)
					concealment += treeConcealment;
				if(slopeToBrush >= slopeToTarget)
					concealment += brushConcealment;
				if(slopeToBuilding >= slopeToTarget && buildings) {
					concealment = 5;
					break;
				}
			} else {
				if(slopeToTrees <= slopeToTarget)
					concealment += treeConcealment;
				if(slopeToBrush <= slopeToTarget)
					concealment += brushConcealment;
				if(slopeToBuilding <= slopeToTarget && buildings) {
					concealment = 5;
					break;
				}
			}
		
			
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
