package Vehicle;

import java.util.ArrayList;
import java.util.List;

import HexGrid.HexDirectionUtility.HexDirection;
import Trooper.Trooper;
import Vehicle.Data.CrewCompartment;
import Vehicle.Data.CrewMember;
import Vehicle.Data.CrewPosition;
import Vehicle.Data.ShieldGenerator;
import Vehicle.Utilities.VehicleDataUtility.CrewPositionType;

public class Vehicle {

	String vehicleCallsign;
	String vehicleTypeName;
	List<CrewCompartment> crewCompartments;
	ShieldGenerator shieldGenerator;

	public Vehicle(String vehicleTypeName, List<CrewCompartment> crewCompartments) {
		this.vehicleTypeName = vehicleTypeName;
		this.crewCompartments = crewCompartments;
	}

	public void AddShieldGenerator(ShieldGenerator shieldGenerator) {
		this.shieldGenerator = shieldGenerator;
	}
	
	public ShieldGenerator getShieldGenerator() {
		return shieldGenerator;
	}
	
	public CrewCompartment getCrewCompartment(String compartmentName) throws Exception {
		
		for(var compartment : crewCompartments) {
			if(compartment.getCompartmentName().equals(compartmentName))
				return compartment;
		}
		
		throw new Exception("Crew compartment not found: "+compartmentName);
	}
	
	public CrewPosition getCrewPosition(String positionName) throws Exception {
		
		for(var compartment : crewCompartments) {
			if(compartment.containsPosition(positionName)) 
				return compartment.getCrewPosition(positionName);
		}
		
		throw new Exception("Crew position not found: "+positionName);
	}
	
	public void setVehicleCallsign(String callsign) {
		vehicleCallsign = callsign;
	}

	public String getVehicleCallsign() {
		return vehicleCallsign;
	}

	public String getVehicleType() {
		return vehicleTypeName;
	}


}
