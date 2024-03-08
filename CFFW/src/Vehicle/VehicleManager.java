package Vehicle;

import java.io.Serializable;
import java.util.ArrayList;

import Conflict.GameWindow;
import Hexes.Feature;
import Hexes.Hex;
import Vehicle.Combat.VehicleAimUtility;
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
				if(vic.getVehicleDisabled())
					continue;
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
				VehicleAimUtility.resolveAimTargets(vic);
			}
		}
		
		if(phase == 1)
			nextTurn();
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Next Vehicle Phase: "+phase);
		
	}
	
	public static void createCoverPosition(
			Hex hex, Vehicle vehicle) {
		var newCoverPositions = (vehicle.spotData.hullSize + vehicle.spotData.turretSize) / 5;
		hex.features.add(new Feature(vehicle.getVehicleType()+" Hull", newCoverPositions));
		var unitsInHex = GameWindow.gameWindow.getUnitsInHex("", hex.xCord, hex.yCord);
		hex.setTotalPositions();
		for(var unit : unitsInHex)
			unit.seekCover(hex, GameWindow.gameWindow);
	}
	
	public static void removeCoverPosition(Hex hex, Vehicle vehicle) {
		
		var featureName = vehicle.getVehicleType()+" Hull";
		
		Feature feature = null;
		
		for(var f : hex.features) {
			if(f.featureType.equals(featureName)) {
				feature = f;
				break;
			}
		}
		
		hex.features.remove(feature);
		hex.setTotalPositions();
		
		var unitsInHex = GameWindow.gameWindow.getUnitsInHex("", hex.xCord, hex.yCord);
		for(var unit : unitsInHex)
			unit.seekCover(hex, GameWindow.gameWindow);
		
	}
	
}
