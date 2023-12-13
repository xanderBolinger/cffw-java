package Vehicle.Spot;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import HexGrid.CalculateLOS;
import HexGrid.HexDirectionUtility;
import Spot.Utility.SpotActionResults;
import Spot.Utility.SpotModifiers;
import Spot.Utility.SpotUtility;
import Spot.Utility.SpotVisibility;
import Trooper.Trooper;
import Vehicle.Data.CrewMember.CrewAction;
import Vehicle.Vehicle;
import Vehicle.Data.CrewPosition;
import Vehicle.Data.PositionSpotData;
import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;

public class VehicleSpotManager {

	public static void vehicleSpotChecks() {
		GameWindow.gameWindow.conflictLog.addNewLineToQueue("Vehicle Spot Tests: ");

		for(var vic : GameWindow.gameWindow.game.vehicleManager.getVehicles()) {
			
			for(var spotVic : vic.getLosVehicles()) {
				if(vic.getSpottedVehicles().contains(spotVic) 
						|| VehicleSpotCalculator.hullDownRelativeHidden(vic,spotVic))
					continue;
			
				VehicleSpotCalculator.spotVehicle(vic, spotVic);
			}
			
			for(var spotUnit : vic.getLosUnits()) {
				VehicleSpotCalculator.spotInfantry(vic, spotUnit);
			}
				
			
		}
		
	}
	
	
	
}
