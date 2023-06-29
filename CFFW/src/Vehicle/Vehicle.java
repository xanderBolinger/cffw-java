package Vehicle;

import java.util.ArrayList;
import java.util.List;

import CorditeExpansion.Cord;
import HexGrid.HexDirectionUtility.HexDirection;
import Trooper.Trooper;
import Unit.Unit;
import Vehicle.Data.CrewCompartment;
import Vehicle.Data.CrewMember;
import Vehicle.Data.CrewPosition;
import Vehicle.Data.ShieldGenerator;
import Vehicle.Data.VehicleMovementData;
import Vehicle.Utilities.VehicleDataUtility.CrewPositionType;

public class Vehicle {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	String vehicleCallsign;
	String vehicleTypeName;
	String vehicleClass;
	boolean repulsorCraft;
	boolean disabled;
	List<CrewCompartment> crewCompartments;
	ShieldGenerator shieldGenerator;
	public String identifier;
	
	public VehicleMovementData movementData;
	
	public Vehicle() {} // empty constructor for testing 

	public Vehicle(String vehicleTypeName, List<CrewCompartment> crewCompartments) {
		this.vehicleTypeName = vehicleTypeName;
		this.crewCompartments = crewCompartments;
		this.identifier = identifier();
	}

	public void AddShieldGenerator(ShieldGenerator shieldGenerator) {
		this.shieldGenerator = shieldGenerator;
	}
	
	public ShieldGenerator getShieldGenerator() {
		return shieldGenerator;
	}
	
	public List<Trooper> getTroopers() {
		var troopers = new ArrayList<Trooper>();
		
		for(var compartment : crewCompartments) {
			
			for(var individual : compartment.getTroopers()) 
				troopers.add(individual);
			
		}
		
		return troopers; 
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

	public int getTroopCapacity() {
		int capacity = 0; 
		
		for(var compartment : crewCompartments) {
			capacity += compartment.getCrewCount();
		}
		
		return capacity;
	}

	String identifier() {
		int count = 10;
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	// Compares identifers of unit 
	public boolean compareTo(Vehicle otherUnit) {
		if(this.identifier.equals(otherUnit.identifier)) {
			return true; 
		} else {
			return false; 
		}
	}

	public boolean getRepulsorCraft() {
		return repulsorCraft;
	}
	
	public String getVehicleClass() {
		return vehicleClass;
	}
	
	public boolean getVehicleDisabled() {
		return disabled;
	}
	
}
