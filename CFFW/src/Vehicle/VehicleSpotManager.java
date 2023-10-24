package Vehicle;

import Conflict.GameWindow;
import HexGrid.HexDirectionUtility;

public class VehicleSpotManager {

	public static void vehicleSpotChecks() {
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Vehicle Spot Tests: ");

		for(var vic : GameWindow.gameWindow.game.vehicleManager.getVehicles()) {
			
			for(var spotVic : vic.losVehicles) {
				if(vic.spottedVehicles.contains(spotVic))
					continue;
				spotVehicle(vic, spotVic);
			}
			
		}
		
	}
	
	private static void spotVehicle(Vehicle spotter, Vehicle target) {
		var spotterCord = spotter.movementData.location;
		var targetCord = target.movementData.location;
		var direction = HexDirectionUtility.getHexSideFacingTarget(spotterCord, targetCord);
		
		for(var position : spotter.getCrewPositions()) {
			if(!position.getFieldOfView().contains(direction))
				continue;

			if(!spotter.spottedVehicles.contains(target))
				spotter.spottedVehicles.add(target);
			
		}
		
	}
	
	private static boolean spotVehicleRoll(Vehicle spotter, Vehicle target) {
		return false;
	}
	
}
