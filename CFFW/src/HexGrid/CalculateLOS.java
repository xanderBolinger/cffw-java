package HexGrid;

import java.util.ArrayList;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import Hexes.Hex;
import Unit.Unit;

public class CalculateLOS {

	public static void calc(Unit unit, Unit targetUnit) {
		ArrayList<Cord> hexes = TraceLine.GetHexes(new Cord(unit.X, unit.Y), new Cord(targetUnit.X, targetUnit.Y), GameWindow.gameWindow.hexGrid.panel);
		
		if(hexes.size() <= 2) {
			unit.lineOfSight.add(targetUnit);
			return;
		}
		
		if(getConcealment(unit, targetUnit, true) >= 5)
			return;
		
		if(!unit.lineOfSight.contains(targetUnit))
			unit.lineOfSight.add(targetUnit);
		
		if(!targetUnit.lineOfSight.contains(unit))
			targetUnit.lineOfSight.add(unit);
		
	}
	
	private static int getConcealment(Unit unit, Unit targetUnit, boolean lineOfSight) {
		ArrayList<Cord> hexes = TraceLine.GetHexes(new Cord(unit.X, unit.Y), new Cord(targetUnit.X, targetUnit.Y), GameWindow.gameWindow.hexGrid.panel);

		Cord spotterCord;
		Cord targetCord; 
		
		if(hexes.size() > 2) {
			spotterCord = hexes.remove(0);
			targetCord = lineOfSight ? hexes.remove(hexes.size()-1) : 
				hexes.get(hexes.size()-1);
		} else {
			return 0;
		}
		// Calculations assumes spotter is at x 1 
		// target x is always greater than spotter therefore spotter is x1 and y1 while target is x2 and y2 
		// The number of hexes in between target and spotter plus 1 gets target x 
		// The denominator has an extra plus one because we are getting the index of each hex in the array
		
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
			
			double slopeToCurrentHex = (currentHexElevation+1 - (double)spotterElevation) / ((double)hexes.indexOf(hex)+2.0 - 1.0);
			double slopeToBrush = (currentHexElevation+1 - (double)spotterElevation) / ((double)hexes.indexOf(hex)+2.0 - 1.0);
			double slopeToTrees = (currentHexElevation+3 - (double)spotterElevation) / ((double)hexes.indexOf(hex)+2.0 - 1.0);
			double slopeToBuilding = (currentHexElevation+buildingFloors+1 - (double)spotterElevation) / ((double)hexes.indexOf(hex)+2.0 - 1.0);
			
			if(((spotterElevation > targetElevation && slopeToCurrentHex >= slopeToTarget) || (spotterElevation < targetElevation && slopeToCurrentHex < slopeToTarget)
					) && currentHexElevation+1 != targetElevation) {
				concealment = 5;
				break;
			}
			
			int brushConcealment = 0;
			int treeConcealment = GameWindow.gameWindow.game.smoke.getConcealment(hex);
			// "Light Forest", "Medium Forest", "Heavy Forest", "Brush", "Heavy Brush", "Light Rock", "Medium Rock", "Heavy Rock", "Light Urban Sprawl", "Dense Urban Sprawl", "Rubble", "Small Depression", "Large Depression"
			for(var feature : foundHex.features) {
				if(feature.featureType.equals("Light Forest"))
					treeConcealment += 1;
				else if( feature.featureType.equals("Medium Forest"))
					treeConcealment += 2;
				else if(feature.featureType.equals("Heavy Forest"))
					treeConcealment += 3;
			}
			brushConcealment = foundHex.concealment - treeConcealment;
			
			
			if(slopeToTrees >= slopeToTarget)
				concealment += treeConcealment;
			if(slopeToBrush >= slopeToTarget)
				concealment += brushConcealment;
			if(slopeToBuilding >= slopeToTarget && buildings) {
				concealment = 5;
				break;
			}
			
			/*if(targetElevation >= spotterElevation) {
				
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
			}*/
		
			
		}
		
		return concealment;
	}
	
	public static int getConcelamentValue(Unit unit, Unit targetUnit) {
				
		return getConcealment(unit, targetUnit, false);
	}
	
	public static int getConcealmentAlm(Unit shooterUnit, Unit targetUnit) {
		if(GameWindow.gameWindow == null)
			return 0;
		
		ArrayList<Cord> hexes = TraceLine.GetHexes(new Cord(shooterUnit.X, shooterUnit.Y), new Cord(targetUnit.X, targetUnit.Y), GameWindow.gameWindow.hexGrid.panel);
		
		if(hexes.size() != 1)
			hexes.remove(0);
		
		int alm = 0; 
		
		for(Cord hex : hexes) {
			Hex foundHex = GameWindow.gameWindow.findHex(hex.xCord, hex.yCord);
			if(foundHex == null)
				continue; 
			alm += GameWindow.gameWindow.game.smoke.getAlm(hex);
		}
		
		alm -= getConcealment(shooterUnit, targetUnit, false);
		
		if(alm < -14)
			alm = -14; 
		
		return alm;
	} 
	
	
}
