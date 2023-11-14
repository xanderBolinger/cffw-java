package Actions.Resolver;

import java.util.ArrayList;

import Conflict.GameWindow;
import Shoot.Shoot;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class SuppressResolver extends ActionToResolve {

	@Override
	protected void processUnit(Unit shooterUnit, ArrayList<Unit> targetUnits) {
		for(var shooter : shooterUnit.individuals) {
			
			if(!shooter.canAct(GameWindow.gameWindow.game))
				continue;
			
			var unit = getTargetUnit(shooterUnit, targetUnits);
			
			if(unit == null)
				return;
			
			Shoot shoot = new Shoot(shooterUnit, unit, shooter, unit.individuals.get(0), shooter.wep,0);
			shoot.aimTime = shooter.combatActions-1;
			shoot.recalc();
			shoot.suppressiveFire(shoot.wep.suppressiveROF+ DiceRoller.roll(1, shoot.wep.suppressiveROF/4));
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Shot Results: " + shoot.shotResults);
			
			
			if (GameWindow.gameWindow.game.getPhase() == 1)
				shooter.spentPhase1++;
			else
				shooter.spentPhase2++;
		}		
	}

	Unit getTargetUnit(Unit shooterUnit, ArrayList<Unit> targetUnits) {
		
		ArrayList<Unit> validTargets = new ArrayList<Unit>();
		
		for(var t: targetUnits) {
			if(shooterUnit.lineOfSight.contains(t))
				validTargets.add(t);
		}
		
		if(validTargets.size() <= 0)
			return null;
		
		
		return validTargets.get(DiceRoller.roll(0, validTargets.size()-1));
	}
	
	
}
