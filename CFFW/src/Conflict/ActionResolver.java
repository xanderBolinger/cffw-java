package Conflict;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingWorker;
import Shoot.Shoot;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class ActionResolver {

	static ArrayList<Trooper> getTroopers(ArrayList<Unit> selectedUnits) {
		var troopers = new ArrayList<Trooper>();
		
		
		for(var unit : selectedUnits) 
			for(var t : unit.individuals)
				troopers.add(t);
		
		return troopers;
		
	}
	
	public static void Suppress(ArrayList<Unit> selectedUnits, ArrayList<Unit> targetUnits) {
		
		SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

			@Override
			protected Void doInBackground() throws Exception {

				InjuryLog.InjuryLog.addAlreadyInjured();
				
				ExecutorService es = Executors.newFixedThreadPool(16);

				for(var shooterUnit : selectedUnits) {
					
					es.submit(() -> {
						for(var shooter : shooterUnit.individuals) {
							
							if(!shooter.canAct(GameWindow.gameWindow.game))
								continue;
							if (GameWindow.gameWindow.game.getPhase() == 1)
								shooter.spentPhase1++;
							else
								shooter.spentPhase2++;
							var unit = targetUnits.get(DiceRoller.roll(0, targetUnits.size()-1));
							Shoot shoot = new Shoot(shooterUnit, unit, shooter, unit.individuals.get(0), shooter.wep,0);
							shoot.aimTime = shooter.combatActions-1;
							shoot.recalc();
							shoot.suppressiveFire(shoot.wep.suppressiveROF+ DiceRoller.roll(1, shoot.wep.suppressiveROF/4));
						}
					});
					
				}
				
				es.shutdown();
				
				try {
				  es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				return null;
			}

			@Override
			protected void done() {
				
				InjuryLog.InjuryLog.printResultsToLog();
				GameWindow.gameWindow.conflictLog.addQueuedText();

			}

		};

		worker.execute();
		
	}
	
}
