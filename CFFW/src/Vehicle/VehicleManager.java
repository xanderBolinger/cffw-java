package Vehicle;

import java.io.Serializable;
import java.util.ArrayList;

import Conflict.GameWindow;
import Vehicle.Data.CrewMember.CrewAction;
import Vehicle.HullDownPositions.HullDownPositionManager;
import Vehicle.Utilities.VehicleDataUtility;

public class VehicleManager implements Serializable {

	public int turn = 1;
	
	public int phase = 1; 
	
	public HullDownPositionManager hullDownPositions;
	
	public VehicleManager() {
		
	}
	
	public boolean opforVehicle(Vehicle vehicle) {
		for(var company : GameWindow.gameWindow.companies)
			for(var vic : company.vehicles)
				if(vic.compareTo(vehicle))
					return company.getSide().equals("OPFOR");
		return false;
	}
	
	public ArrayList<Vehicle> getVehicles() {
		
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		for(var company : GameWindow.gameWindow.companies) {
			for(var vic : company.vehicles) {
				if(VehicleDataUtility.containsVehicle(vic, vehicles))
					continue;
				vehicles.add(vic);
			}
			
		}
		
		return vehicles; 
		
	}
	
	public void generate() {
		hullDownPositions = new HullDownPositionManager();
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
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Next Vehicle Turn: "+turn);
		
	}
	
	public void nextPhase() {
		
		phase = phase + 1 > 4 ? 1 : phase + 1;
		
		for(var company : GameWindow.gameWindow.companies) {
			for(var vic : company.vehicles) {
				
				for(var pos : vic.getCrewPositions()) {
					if(pos.crewMemeber == null)
						continue; 
					pos.crewMemeber.currentAction = CrewAction.SPOT;
				}
				
				VehicleMovement.updateTurretFacing(vic);
				
			}
		}
		
		if(phase == 1)
			nextTurn();
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Next Vehicle Phase: "+phase);
		
	}
	
}
