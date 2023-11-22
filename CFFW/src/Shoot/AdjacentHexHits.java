package Shoot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import CorditeExpansion.Cord;
import HexGrid.HexDirectionUtility;
import Items.PCAmmo;
import Items.Weapons;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class AdjacentHexHits {

	public Map<Cord, AdjacentHexHitData>  hits;
	
	Cord mainCord;
	List<Cord> adjacentCords;
	
	Unit shooterUnit;
	Unit targetUnit;
	
	public AdjacentHexHits(Unit shooterUnit, Unit targetUnit) {
		this.shooterUnit = shooterUnit;
		this.targetUnit = targetUnit;
		hits = new HashMap<Cord, AdjacentHexHitData>();
		mainCord = new Cord(targetUnit.X, targetUnit.Y);
		adjacentCords = HexDirectionUtility.getHexNeighbourCords(mainCord);
	}
	
	public void addHit(Weapons weapon, PCAmmo pcAmmo) {
		Cord cord = adjacentCords.get(DiceRoller.roll(0, adjacentCords.size()-1));
		
		if(!hits.containsKey(cord)) {
			hits.put(cord, new AdjacentHexHitData(weapon, pcAmmo, shooterUnit, targetUnit));
			return;
		}
		
		var hit = hits.get(cord);
		hit.hits++;
	
	}
	
	public void resolveHits() {
		for(var hit : hits.values())
			hit.resolve();
	}
	
}



