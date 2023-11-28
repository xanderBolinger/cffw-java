package Vehicle.Utilities;

import java.util.ArrayList;

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
	
	public static boolean containsVehicle(Vehicle vehicle, ArrayList<Vehicle> vehicles) {
		
		for(var vic : vehicles) 
			if(vic.compareTo(vehicle))
				return true;
		
		
		return false;
	}
	
}
