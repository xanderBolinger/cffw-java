package Vehicle.Utilities;

import java.util.ArrayList;

import Conflict.GameWindow;
import HexGrid.HexDirectionUtility;
import HexGrid.HexDirectionUtility.HexDirection;
import Vehicle.Vehicle;
import Vehicle.Combat.VehicleTurret;

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
	
	public static HexDirection getTurretFacing(VehicleTurret turret, Vehicle selectedVehicle) {
		var facing = HexDirectionUtility.getFaceInDirection(selectedVehicle.movementData.facing, 
				turret.facingDirection >= 0, 
				Math.abs(turret.facingDirection/20));
		return facing;
	}
	
}
