package UtilityClasses;

import Actions.Spot;
import Conflict.GameWindow;
import Trooper.Trooper;
import Unit.Unit;

public class HexGridUtility {
	
	public enum ShownType {
		BLUFOR,OPFOR,BOTH
	}
	
	
	
	
	public static boolean canShow(ShownType shownType, Unit targetUnit) {
		
		if(shownTypeEqualsSide(shownType, targetUnit.side))
			return true; 
		
		// If the unit is within LOS of a shown unit
		if(canShowLos(shownType, targetUnit)) 
			return true; 
		
		
		return false; 
	}
	
	public static boolean shownTypeEqualsSide(ShownType shownType, String side) {

		if(shownType == ShownType.BOTH) {
			return true; 
		} else if(side.equals("BLUFOR") && shownType == ShownType.BLUFOR) {
			return true; 
		} else if(side.equals("OPFOR") && shownType == ShownType.OPFOR) {
			return true; 
		} 
		
		
		return false;
	}
	
	public static ShownType getShownTypeFromSide(String side) {
		if(side.equals("BLUFOR")) 
			return ShownType.BLUFOR; 
		else 
			return ShownType.OPFOR;
	}
	
	
	public static boolean spottedSomeTrooper(Unit spotterUnit, Unit targetUnit) {
		
		for(Trooper trooper : spotterUnit.individuals) {
			
			for(Spot spot : trooper.spotted) {
				
				for(Trooper spottedTrooper : spot.spottedIndividuals) {
					
					if(targetUnit.individuals.contains(spottedTrooper))
						return true; 
					
				}
				
			}
			
		}
		
		return false; 
	}
	
	public static boolean canShowLos(ShownType shownType, Unit targetUnit) {
		
		for(Unit unit : GameWindow.gameWindow.initiativeOrder) {
			
			if(unit.side.equals("BLUFOR") && shownType != ShownType.BLUFOR) {
				continue; 
			} else if(unit.side.equals("OPFOR") && shownType != ShownType.OPFOR) {
				continue; 
			} 
			
			if(unit.lineOfSight.contains(targetUnit) && spottedSomeTrooper(unit, targetUnit))
				return true;
			
		}
		
		return false; 
	}
	
}
