package Shoot;

import java.util.ArrayList;
import Conflict.GameWindow;
import CorditeExpansion.Cord;
import Explosion.Explosion;
import Injuries.ResolveHits;
import Items.PCAmmo;
import Items.Weapons;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class AdjacentHexHitData {
		Unit shooterUnit;
		Unit targetUnit;
		PCAmmo pcAmmo;
		Weapons weapon;
		int hits;
		Cord cord;
		ArrayList<Unit> units;
		
		public AdjacentHexHitData(Weapons weapon, PCAmmo pcAmmo, Unit shooterUnit, Unit targetUnit) {
			this.weapon = weapon;
			this.pcAmmo = pcAmmo;
			this.cord = new Cord(targetUnit.X, targetUnit.Y);
			this.shooterUnit = shooterUnit;
			this.targetUnit = targetUnit;
			hits = 1;
			units = GameWindow.gameWindow.getUnitsInHexExcludingSide("", cord.xCord, cord.yCord);
		}
		
		public void resolve() {

			if(pcAmmo != null) {
				Explosion explosion = new Explosion(pcAmmo);
				explosion.explodeHex(cord.xCord, cord.yCord, "");
				return;
			}

			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Adjacent Hex Hits: "+hits);
			
			for(Unit unit : units) {
				suppressiveHit(unit);
			}
			
		}
		
		public void directHit(Unit targetUnit) {
			var target = targetUnit.individuals.get(DiceRoller.roll(0, targetUnit.individuals.size()-1));
			ResolveHits resolveHits = new ResolveHits(target, hits, weapon,
					GameWindow.gameWindow != null ? GameWindow.gameWindow.conflictLog : null, targetUnit, shooterUnit,
					GameWindow.gameWindow);

			if (GameWindow.gameWindow != null)
				resolveHits.performCalculations(GameWindow.gameWindow.game, GameWindow.gameWindow.conflictLog);
		}
		
		public void suppressiveHit(Unit unit) {
			if(hits > 1)
				hits /= 2;
			
			applyFortificationModifiers();
			
			if (unit.suppression + hits < 100) {
				unit.suppression += hits > 3 ? hits / 3 : 1;
			} else {
				unit.suppression = 100;
			}

			if (unit.organization - hits > 0) {
				unit.organization -= hits > 3 ? hits / 3 : 1;
			} else {
				unit.organization = 0;
			}

			for(int i = 0; i < hits; i++) {
				if(DiceRoller.roll(0, 99) <= 0)
					directHit(unit);
			}
			
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