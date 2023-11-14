package Actions.Resolver;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingWorker;

import Conflict.GameWindow;
import Conflict.InjuryLog;
import Spot.Utility.SpotUtility;
import Unit.Unit;

abstract class ActionToResolve {

	public void ResolveAction(ArrayList<Unit> selectedUnits, ArrayList<Unit> targetUnits) {
		
		SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

			@Override
			protected Void doInBackground() throws Exception {
				ExecutorService es = Executors.newFixedThreadPool(16);
				
				for(var unit : selectedUnits) {
					
					es.submit(() -> {
					
						processUnit(unit,targetUnits);
					
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
				GameWindow.gameWindow.conflictLog.addQueuedText();
				InjuryLog.InjuryLog.printResultsToLog();
				SpotUtility.printSpottedTroopers();
			}

		};

		worker.execute();
		
	}
	
	protected abstract void processUnit(Unit unit, ArrayList<Unit> units);
	
}
