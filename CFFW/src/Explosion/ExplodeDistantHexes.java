package Explosion;

import java.util.ArrayList;
import java.util.HashMap;

import Conflict.GameWindow;
import Items.PCAmmo;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class ExplodeDistantHexes {

	public static void explodeDistantHexes(PCAmmo pcAmmo, int x, int y, Explosion explosion) {
		
		if(pcAmmo.rangeList.length <= 0)
			return;
		
		// max ordnance range 10 hexes
		// max explosive range 8 hexes 
		int maxRange = getMaxRange(pcAmmo);
		var targets = getTargets(x,y,maxRange);

		for(var target : targets.keySet()) {
			
			for(var trooper : target.individuals) {
				
				var range = DiceRoller.roll(0, 10) + targets.get(target);
				
				if(range > maxRange)
					continue;
				
				explosion.explodeTrooper(trooper, range);
				
			}
			
		}
		
	}
	
	private static int getMaxRange(PCAmmo pcAmmo) {
		
		if(pcAmmo.ordnance) {
			
			if(pcAmmo.rangeList.length == 0)
				return 10;
			else 
				return pcAmmo.rangeList[pcAmmo.rangeList.length-1];
			
		} else {
			
			if(pcAmmo.rangeList.length == 0)
				return 8;
			else 
				return pcAmmo.rangeList[pcAmmo.rangeList.length-1];
			
		}
		
	}
	
	private static HashMap<Unit, Integer> getTargets(int x, int y, int maxRangeTwoYardHexes) {
		HashMap<Unit,Integer> targetUnits = new HashMap<Unit,Integer>();
		var initOrder = GameWindow.gameWindow.initiativeOrder;
		
		for(var unit : initOrder) {
			
			var dist = GameWindow.dist(x, y, unit.X, unit.Y);
			
			if(dist == 0 || dist*10 > maxRangeTwoYardHexes)
				continue;
			
			targetUnits.put(unit,dist);
			
		}
		
		
		return targetUnits;
		
	}
	
}
