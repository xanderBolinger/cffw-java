package Vehicle.Combat;

import CeHexGrid.Chit;
import Conflict.GameWindow;
import Conflict.SmokeStats;
import Conflict.SmokeStats.SmokeType;
import Hexes.Feature;
import Hexes.Hex;
import Vehicle.Vehicle;
import Vehicle.VehicleManager;
import Vehicle.Utilities.VehicleHexGridUtility;

public class VehicleBrewUp {
	
	public static void knockoutVehicle(Vehicle vehicle) {
		knockoutVehicle(vehicle, false);
	}
	
	private static void knockoutVehicle(Vehicle vehicle, boolean addSmoke) {
		var vicCord = vehicle.movementData.location;
		var hex = GameWindow.gameWindow.findHex(vicCord.xCord, vicCord.yCord);
		hex.explosiveImpacts.addMarker();
		
		destroyVehicle(vehicle);
		VehicleManager.createCoverPosition(hex, vehicle);
		
		if(!addSmoke)
			return;
		
		var smokeStats = new SmokeStats(SmokeType.Mortar81mm);
		GameWindow.gameWindow.game.smoke.deploySmoke(vicCord, smokeStats);
	}
	
	public static void vehicleBrewUp(Vehicle vehicle) {
		knockoutVehicle(vehicle, true);
	}
	
	private static void destroyVehicle(Vehicle destroyVic) {
		
		var vc = GameWindow.gameWindow.vehicleCombatWindow;
		
		try {
			Chit chit = VehicleHexGridUtility.findChit(destroyVic.identifier);
			GameWindow.gameWindow.game.chits.remove(chit);
        	Chit.unselectChit();
        	destroyVic.knockedOut = true;
        	vc.unselectVehicle();
        	vc.vehicles.remove(destroyVic);
        	vc.refreshVehicleList();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

	
	
}
