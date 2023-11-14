package Conflict;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import Actions.Spot;
import HexGrid.HexGrid.DeployedUnit;
import Shoot.Shoot;
import Spot.Utility.SpotUtility;
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
	
	static int spotUnit = 0;
	
	public static void Spot(ArrayList<Unit> selectedUnits) {
		SpotUtility.clearSpotted();
		GameWindow.gameWindow.conflictLog.addNewLine("Spotting...");
		
		spotUnit = 0;
		
		SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

			@Override
			protected Void doInBackground() throws Exception {
				ExecutorService es = Executors.newFixedThreadPool(16);
				
				for(var unit : selectedUnits) {
					
					es.submit(() -> {
					
						for(var trooper : unit.individuals) {
							if(!trooper.canAct(GameWindow.gameWindow.game))
								continue;
							/*try {
								TimeUnit.MILLISECONDS.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}*/
							performSpotTest(trooper, unit);
							//System.out.println("spot");
						}
						spotUnit++;
						System.out.println("Spot Unit: "+spotUnit);
					
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
				System.out.println("spot finished");
				GameWindow.gameWindow.conflictLog.addQueuedText();
				SpotUtility.printSpottedTroopers();

			}

		};

		worker.execute();
		JOptionPane.showConfirmDialog(null, "Cancel", "Cancel Spot task?", JOptionPane.DEFAULT_OPTION);
		
	}
	
	static void performSpotTest(Trooper trooper, Unit trooperUnit) {

		var gameWindow = GameWindow.gameWindow;
		boolean spotted = false; 
		
		for (Unit targetUnit : trooperUnit.lineOfSight) {

			Spot spotAction = new Spot(gameWindow, trooperUnit, targetUnit, trooper,
					"60 Degrees", gameWindow.visibility, gameWindow.initiativeOrder,
					gameWindow);

			spotAction.displayResultsQueue(gameWindow, spotAction);

			// Set results in trooper
			trooper.spotted.add(spotAction);
			spotted = true;
		}
		
		if(!spotted)
			return;
		
		if (GameWindow.gameWindow.game.getPhase() == 1)
			trooper.spentPhase1++;
		else
			trooper.spentPhase2++;
		

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
							GameWindow.gameWindow.conflictLog.addNewLineToQueue("Shot Results: " + shoot.shotResults);
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
