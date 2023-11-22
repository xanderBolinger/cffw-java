package Vehicle.Utilities;

import Conflict.GameWindow;
import Vehicle.Vehicle;

public class VehicleDataUtility {

	public enum CrewPositionType {
		
		GUNNER,DRIVER,LOADER,COMMANDER
		
	}

	public static String getSide(Vehicle vehicle) {
		
		for(var company : GameWindow.gameWindow.companies) {
			if(company.vehicles.contains(vehicle))
				return company.getSide();
		}
		
		return "";
		
	}
	
}
