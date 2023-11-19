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
		
		System.out.println("Explode distant hexes");
		
		// max ordnance range 10 hexes
		// max explosive range 8 hexes 
		int maxRange = getMaxRange(pcAmmo);
		var targets = getTargets(x,y,maxRange);

		for(var target : targets.keySet()) {
			boolean explodedTarget = false;
			
			for(var trooper : target.individuals) {
				
				var range = DiceRoller.roll(-5, 5) + targets.get(target) * 10;
				System.out.println("Trooper range: "+trooper.name+", Range: "+range);
				if(range > maxRange)
					continue;
				
				explosion.explodeTrooper(trooper, range);
				explodedTarget = true;
			}
			
			
			if(!explodedTarget)
				continue;
			
			target.organization -= 5;
			target.suppression += 5;
			if(target.suppression > 100)
				target.suppression = 100;
			if(target.organization < 0)
				target.organization = 0;
			
		}
		
	}
	
	private static int getMaxRange(PCAmmo pcAmmo) {
		
		if(pcAmmo.ordnance) {
				return pcAmmo.rangeList[pcAmmo.rangeList.length-1];
			
		} else {
				return pcAmmo.rangeList[pcAmmo.rangeList.length-1];
		}
		
	}
	
	private static HashMap<Unit, Integer> getTargets(int x, int y, int maxRangeTwoYardHexes) {
		HashMap<Unit,Integer> targetUnits = new HashMap<Unit,Integer>();
		var initOrder = GameWindow.gameWindow.initiativeOrder;
		System.out.println("Get targets, max range: "+maxRangeTwoYardHexes);
		for(var unit : initOrder) {
			
			var dist = GameWindow.dist(x, y, unit.X, unit.Y);
			
			if(dist == 0 || dist*10 > maxRangeTwoYardHexes)
				continue;
			
			System.out.println("Add target: "+unit.callsign+", dist: "+dist);
			targetUnits.put(unit,dist);
			
		}
		
		
		return targetUnits;
		
	}
	
}
