package Shoot;

import java.util.ArrayList;
import Conflict.GameWindow;
import CorditeExpansion.Cord;
import Injuries.Explosion;
import Items.PCAmmo;
import Items.Weapons;
import Unit.Unit;

public class AdjacentHexHitData {
		
		PCAmmo pcAmmo;
		Weapons weapon;
		int hits;
		Cord cord;
		ArrayList<Unit> units;
		public AdjacentHexHitData(Weapons weapon, PCAmmo pcAmmo, Cord cord) {
			this.weapon = weapon;
			this.pcAmmo = pcAmmo;
			this.cord = cord;
			hits = 1;
			units = GameWindow.gameWindow.getUnitsInHexExcludingSide("", cord.xCord, cord.yCord);
		}
		
		public void resolve() {

			if(pcAmmo != null) {
				Explosion explosion = new Explosion(pcAmmo);
				explosion.explodeHex(cord.xCord, cord.yCord, "");
				return;
			}

			for(Unit unit : units) {
				suppressiveHit(unit);
			}
			
		}
		
		public void suppressiveHit(Unit unit) {
			if(hits > 1)
				hits /= 2;
			
			applyFortificationModifiers();
			
			if (unit.suppression + hits < 100) {
				unit.suppression += hits > 1 ? hits / 3 : 1;
			} else {
				unit.suppression = 100;
			}

			if (unit.organization - hits > 0) {
				unit.organization -= hits > 1 ? hits / 3 : 1;
			} else {
				unit.organization = 0;
			}

			if(hits % 2 == 0)
				hits = 0; 
			else 
				hits = 1;
		}
		
		private void applyFortificationModifiers() {
			var level = GameWindow.gameWindow.game.fortifications.getTrenchesLevel(cord);
			if(level == 2) {
				hits /= 2;
			} else if(level >= 3) {
				hits /= 3;
			}
		}
		
	}