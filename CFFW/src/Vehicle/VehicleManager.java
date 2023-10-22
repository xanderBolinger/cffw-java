package Vehicle;

import java.io.Serializable;
import java.util.ArrayList;

import Conflict.GameWindow;
import Vehicle.Data.CrewMember.Action;
import Vehicle.HullDownPositions.HullDownPositionRecords;

public class VehicleManager implements Serializable {

	public int turn = 1;
	
	public int phase = 1; 
	
	public HullDownPositionRecords hullDownPositions;
	
	public VehicleManager() {
		
	}
	
	public ArrayList<Vehicle> getVehicles() {
		
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		for(var company : GameWindow.gameWindow.companies)
			for(var vic : company.vehicles)
				vehicles.add(vic);
		
		return vehicles; 
		
	}
	
	public void generate() {
		hullDownPositions = new HullDownPositionRecords();
		hullDownPositions.generateHullDownPositions(GameWindow.gameWindow.hexCols, GameWindow.gameWindow.hexRows);
	}
	
	public void nextTurn() {
		turn = turn == 1 ? 2 : 1; 
		
		for(var company : GameWindow.gameWindow.companies) {
			for(var vic : company.vehicles) {
				VehicleMovement.moveVehicle(vic);
				vic.movementData.changedFaces = 0;
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
