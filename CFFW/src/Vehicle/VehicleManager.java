package Vehicle;

import java.io.Serializable;

import Conflict.GameWindow;
import Vehicle.Data.CrewMember.Action;

public class VehicleManager implements Serializable {

	public int turn = 1;
	
	public int phase = 1; 
	
	public void nextTurn() {
		turn = turn == 1 ? 2 : 1; 
		
		for(var company : GameWindow.gameWindow.companies) {
			for(var vic : company.vehicles) {
				VehicleMovement.moveVehicle(vic);
			}
		}
		
		GameWindow.gameWindow.conflictLog.addNewLine("Next Vehicle Turn: "+turn);
		
	}
	
	public void nextPhase() {
		
		phase = phase + 1 > 4 ? 1 : phase + 1;
		
		for(var company : GameWindow.gameWindow.companies) {
			for(var vic : company.vehicles) {
				
				for(var pos : vic.getCrewPositions()) {
					if(pos.crewMemeber == null)
						continue; 
					pos.crewMemeber.currentAction = Action.SPOT;
				}
				
			}
		}
		
		if(phase == 1)
			nextTurn();
		
		GameWindow.gameWindow.conflictLog.addNewLine("Next Vehicle Phase: "+phase);
		
	}
	
}
