package Actions.Resolver;

import java.util.ArrayList;

import Conflict.GameWindow;
import Conflict.InjuryLog;
import Shoot.Shoot;
import Spot.Utility.SpotUtility;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class SuppressResolver extends ActionToResolve {

	@Override
	protected void actionDone() {
		InjuryLog.InjuryLog.printResultsToLog();
		GameWindow.gameWindow.conflictLog.addQueuedText();
	}
	
	@Override
	protected void processUnit(Unit shooterUnit, ArrayList<Unit> targetUnits, boolean freeAction) {
		for(var shooter : shooterUnit.individuals) {
			
			if(!shooter.canAct(GameWindow.gameWindow.game))
				continue;
			
			var unit = getTargetUnit(shooterUnit, targetUnits);
			
			if(unit == null)
				return;
			
			System.out.println("Shooter unit: "+shooterUnit.callsign+", target unit: "+unit.callsign);
			
			Shoot shoot = new Shoot(shooterUnit, unit, shooter, null
					, shooter.wep,0);
			shoot.aimTime = shooter.combatActions-1;
			shoot.recalc();
			shoot.suppressiveFire(shoot.wep.suppressiveROF+ DiceRoller.roll(1, shoot.wep.suppressiveROF/4));
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Shot Results: " + shoot.shotResults);
			
			if(freeAction)
				continue;
			
			if (GameWindow.gameWindow.game.getPhase() == 1)
				shooter.spentPhase1++;
			else
				shooter.spentPhase2++;
		}		
	}

	Unit getTargetUnit(Unit shooterUnit, ArrayList<Unit> targetUnits) {
		
		ArrayList<Unit> validTargets = new ArrayList<Unit>();
		
		for(var t: targetUnits) {
			if(shooterUnit.lineOfSight.contains(t) && !t.side.equals(shooterUnit.side))
				validTargets.add(t);
		}
		
		if(validTargets.size() <= 0)
			return null;
		
		
		return validTargets.get(DiceRoller.roll(0, validTargets.size()-1));
	}
	
	
}
