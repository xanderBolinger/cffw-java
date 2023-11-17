package Actions.Resolver;

import java.util.ArrayList;

import Actions.Spot;
import Conflict.GameWindow;
import Conflict.InjuryLog;
import Shoot.Shoot;
import Spot.Utility.SpotUtility;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class SpotResolver extends ActionToResolve {
	public static int spotUnit = 0;
	
	@Override
	protected void done() {
		GameWindow.gameWindow.conflictLog.addQueuedText();
		SpotUtility.printSpottedTroopers();
	}
	
	@Override
	protected void processUnit(Unit spottingUnit, ArrayList<Unit> targetUnits, boolean freeAction) {
		for(var trooper : spottingUnit.individuals) {
			if(!trooper.canAct(GameWindow.gameWindow.game))
				continue;
			/*try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			performSpotTest(trooper, spottingUnit, freeAction);
			//System.out.println("spot");
		}
		spotUnit++;
		System.out.println("Spot Unit: "+spotUnit);
	}

	
	void performSpotTest(Trooper trooper, Unit trooperUnit, boolean freeAction) {

		var gameWindow = GameWindow.gameWindow;
		boolean spotted = false; 
		
		for (Unit targetUnit : trooperUnit.lineOfSight) {

			Spot spotAction = new Spot(gameWindow, trooperUnit, targetUnit, trooper,
					freeAction ? "180 Degrees" : "60 Degrees", gameWindow.visibility, gameWindow.initiativeOrder,
					gameWindow);

			spotAction.displayResultsQueue(gameWindow, spotAction);

			// Set results in trooper
			trooper.spotted.add(spotAction);
			spotted = true;
		}
		
		if(!spotted || freeAction)
			return;
		
		
		if (GameWindow.gameWindow.game.getPhase() == 1)
			trooper.spentPhase1++;
		else
			trooper.spentPhase2++;
		

	}

	
}
