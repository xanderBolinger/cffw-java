package Vehicle.Combat;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import Trooper.Trooper;
import Unit.Unit;
import Vehicle.Vehicle;

public class VehicleAimTarget {

	public int timeSpentAiming;
	public boolean fired;
	Trooper trooper;
	Unit unit;
	Vehicle vehicle;
	Cord hexCord;
	
	public VehicleAimTarget(Cord cord) {
		this.hexCord = cord;
	}
	
	public VehicleAimTarget(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public VehicleAimTarget(Unit unit, Trooper trooper) {
		this.unit = unit;
		this.trooper = trooper;
	}
	
	public Cord getTargetCord() {
		if(unit != null)
			return new Cord(unit.X, unit.Y);
		else if(vehicle != null)
			return vehicle.movementData.location;
		else 
			return hexCord;
	}
	
	@Override
	public String toString() {
		String rslt = "";
		
		if(unit != null && trooper != null) {
			rslt = GameWindow.getLogHead(trooper);
		} else if(vehicle != null) {
			rslt = vehicle.getVehicleCallsign();
		} else if(hexCord != null) {
			rslt = "Hex Cord ("+hexCord.toString()+")";
		}
 		
		
		return rslt + ", Aim Time: "+timeSpentAiming;
	}
	
}
