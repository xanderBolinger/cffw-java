package Unit;

import java.util.Random;

import Conflict.GameWindow;
import UtilityClasses.DiceRoller;

public class AdvanceTimeUnit {

	public static void advanceTimeUnit(Unit unit) {
		
		var gameWindow = GameWindow.gameWindow;
		var conflictLog = gameWindow.conflictLog;
		var game = gameWindow.game;
		
		
		// Loops through all individuals, increases time passed for wounds
		// Checks unit morale, forces individuals to hunker down that are subject to the
		// morale failure
		unit.getCommandValue();
		var commandMod = -1 * (5-unit.commandValue);
		
		for (int x = 0; x < unit.getSize(); x++) {

			var trooper = unit.getTroopers().get(x);
			
			if(!trooper.alive || !trooper.conscious)
				continue;
			
			var fighterSkill = trooper.getSkill("Fighter");
			var skillMod = -1 * (10-trooper.sl);
			
			if (unit.moral < 30) {
				var roll = DiceRoller.roll(0, 99);
				var modifiedRoll = roll + skillMod * 5 + commandMod * 5;
				if (modifiedRoll > fighterSkill && !trooper.entirelyMechanical) {

					if (trooper.inCover) {
						trooper.hunkerDown(gameWindow);
						conflictLog.addNewLineToQueue(GameWindow.gameWindow.getLogHead(trooper)
								+ " hunkers down. Morale too low.");
					}

				}

			}

			if (unit.suppression > 10) {
				var roll = DiceRoller.roll(0, 99);
				var modifiedRoll = roll + skillMod * 5 + commandMod * 5;

				if (modifiedRoll > fighterSkill && !trooper.entirelyMechanical) {

					if (trooper.inCover) {
						trooper.hunkerDown(gameWindow);
						conflictLog.addNewLineToQueue(GameWindow.gameWindow.getLogHead(trooper)
								+ " hunkers down. SUPPRESSED.");
					} else {

						if (game.getPhase() == 1) {
							trooper.spentPhase1++;
						} else {
							trooper.spentPhase2++;
						}

						conflictLog.addNewLineToQueue(GameWindow.gameWindow.getLogHead(trooper)
								+  " cowers. SUPPRESSED.");
					}

				}

			}

			trooper.advanceTime(gameWindow, conflictLog);
		}


		modifySuppressionAndOrganization(unit);

	}
	
	private static void modifySuppressionAndOrganization(Unit unit) {
		var mod = averageSkillLevelModifier(unit);
		if (unit.suppression - 5 + mod > 0) {
			unit.suppression -= 5 + mod;
		} else {
			unit.suppression = 0;
		}

		if (unit.lineOfSight.size() == 0) {
			unit.organization += unit.commandValue
					+ (unit.moral / 20) + mod;
		}

		if (unit.organization > 100) {
			unit.organization = 100;
		}
	}
	
	private static int averageSkillLevelModifier(Unit unit) {
		
		int avgSl = 0;
		
		for(var trooper : unit.getTroopers()) {
			avgSl += trooper.sl;
		}
		
		avgSl /= unit.individuals.size();
		
		return -1*(10-avgSl);
	}
	
	
}
