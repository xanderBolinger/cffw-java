package Shoot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import CorditeExpansion.Cord;
import HexGrid.HexDirectionUtility;
import Items.PCAmmo;
import Items.Weapons;
import Unit.Unit;

public class AdjacentHexHits {

	public Map<Cord, AdjacentHexHitsData>  hits;
	
	Cord mainCord;
	List<Cord> adjacentCords;
	
	public AdjacentHexHits(Unit targetUnit) {
		hits = new HashMap<Cord, AdjacentHexHitsData>();
		mainCord = new Cord(targetUnit.X, targetUnit.Y);
		adjacentCords = HexDirectionUtility.getHexNeighbours(mainCord);
	}
	
	public class AdjacentHexHitsData {
		PCAmmo pcAmmo;
		Weapons weapon;
		int hits;
		Cord cord;
		
		public AdjacentHexHitsData(Weapons weapon, PCAmmo pcAmmo, Cord cord) {
			this.weapon = weapon;
			this.pcAmmo = pcAmmo;
			this.cord = cord;
			hits = 1;
		}
		
	}
	
	public void resolveHits() {
		
	}
	
}



