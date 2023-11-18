package Shoot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import HexGrid.HexDirectionUtility;
import Injuries.Explosion;
import Items.PCAmmo;
import Items.Weapons;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class AdjacentHexHits {

	public Map<Cord, AdjacentHexHitData>  hits;
	
	Cord mainCord;
	List<Cord> adjacentCords;
	
	public AdjacentHexHits(Unit targetUnit) {
		hits = new HashMap<Cord, AdjacentHexHitData>();
		mainCord = new Cord(targetUnit.X, targetUnit.Y);
		adjacentCords = HexDirectionUtility.getHexNeighbours(mainCord);
	}
	
	public void addHit() {
		Cord cord = adjacentCords.get(DiceRoller.roll(0, adjacentCords.size()-1));
		var hit = hits.get(cord);
		hit.hits++;
	}
	
	public void resolveHits() {
		for(var hit : hits.values())
			hit.resolve();
	}
	
}



