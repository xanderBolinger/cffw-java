package Vehicle.Combat;

import java.io.Serializable;
import java.util.ArrayList;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import HexGrid.CalculateLOS;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.PCUtility;
import Vehicle.Vehicle;
import Vehicle.Data.CrewPosition;
import Vehicle.Spot.VehicleSpotCalculator;

public class VehicleAimTarget implements Serializable {

	public int timeSpentAiming;
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
	
	public boolean hasLosToTarget(Vehicle spotterVic, ArrayList<CrewPosition> crewPosition) {

		for(var pos : crewPosition) {
			
			if(unit != null && pos.losUnits.contains(unit))
				return true;
			else if(vehicle != null && pos.losVehicles.contains(vehicle))
				return true;
			else if(hexCord != null && CalculateLOS.hasLos(spotterVic.movementData.location, hexCord, 
					pos.elevationAboveVehicle+spotterVic.altitude, 0,
					VehicleSpotCalculator.isThermalEquipped(spotterVic, pos)))
				return true;
		}
		
		
		return false;
	}
	
	public Cord getTargetCord() {
		if(unit != null)
			return unit.getCord();
		else if(vehicle != null)
			return vehicle.movementData.location;
		else 
			return hexCord;
	}
	
	public int getTargetSizeAlm(Vehicle shooterVehicle) {
		var targetVehicle = this.vehicle;
		
		if(targetVehicle != null) {
			var hullDown = VehicleSpotCalculator.hullDownRelative(shooterVehicle.movementData.location,
					targetVehicle);
			
			var PCSize = 
					VehicleSpotCalculator.getSizeMod(hullDown, shooterVehicle.movementData.location,
							targetVehicle.movementData.location, targetVehicle);
			return (int) PCSize;
		} else if(unit != null) {
			
			return PCUtility.findSizeALM(trooper.stance, trooper.PCSize, trooper.inCover);
			
		} else {
			return 20;
		}
		
	}
	
	public int getTargetSpeedInHexesPerTurn() {
		
		if(vehicle != null) {
			return vehicle.movementData.speed;
		} else if(unit != null) {
			if(unit.speed.equals("Walk"))
				return 1;
			else if(unit.speed.equals("Rush"))
				return 3;
			else 
				return 0;
		} else {
			return 0;
		}
		
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
		} else {
			return "None";
		}
		
		return rslt + ", Aim Time: "+timeSpentAiming;
	}
	
}
