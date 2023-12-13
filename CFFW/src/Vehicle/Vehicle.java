package Vehicle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;

import Artillery.FireMission;
import CorditeExpansion.Cord;
import HexGrid.HexDirectionUtility.HexDirection;
import Trooper.Trooper;
import Unit.Unit;
import Vehicle.Data.CrewCompartment;
import Vehicle.Data.CrewMember;
import Vehicle.Data.CrewPosition;
import Vehicle.Data.ShieldGenerator;
import Vehicle.Data.VehicleMovementData;
import Vehicle.Data.VehicleSmokeData;
import Vehicle.Data.VehicleSpotData;
import Vehicle.Data.VehicleTurretData;
import Vehicle.Data.PositionSpotData;
import Vehicle.Utilities.VehicleDataUtility.CrewPositionType;

public class Vehicle implements Serializable {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	String vehicleCallsign;
	String vehicleTypeName;
	String vehicleClass;
	boolean repulsorCraft;
	public boolean knockedOut;
	List<CrewCompartment> crewCompartments;
	ShieldGenerator shieldGenerator;

	public ArrayList<FireMission> fireMissions;
	
	
	public String notes;
	
	public boolean active;
	public String identifier;
	
	public VehicleMovementData movementData;
	public VehicleSmokeData smokeData;
	public VehicleSpotData spotData;
	public VehicleTurretData turretData;
	
	public Vehicle() {} // empty constructor for testing 

	public Vehicle(String vehicleTypeName, List<CrewCompartment> crewCompartments, 
			VehicleSpotData spotData,
			String vehicleClass) {
		this.vehicleTypeName = vehicleTypeName;
		this.vehicleClass = vehicleClass;
		this.crewCompartments = crewCompartments;
		this.identifier = identifier();
		active = true;
		
		fireMissions = new ArrayList<FireMission>();
		this.spotData = spotData;
		turretData = new VehicleTurretData();
	}

	public void AddShieldGenerator(ShieldGenerator shieldGenerator) {
		this.shieldGenerator = shieldGenerator;
	}
	
	public ShieldGenerator getShieldGenerator() {
		return shieldGenerator;
	}
	
	public ArrayList<Trooper> getTroopers() {
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
	
	public ArrayList<CrewPosition> getCrewPositions() {
		
		ArrayList<CrewPosition> positions = new ArrayList<CrewPosition>();
		
		for(var compartment : crewCompartments) {
			for(var pos : compartment.getCrewPositions()) {
				positions.add(pos);
			}
		}
		
		return positions;
		
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

	public String getVehicleClass() {
		return vehicleClass;
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
	
	
	public boolean getVehicleDisabled() {
		return knockedOut;
	}
	
	public boolean isSpottingUnit(Unit unit) {
		for(var pos : getCrewPositions())
			if(pos.losUnits.contains(unit))
				return true;
		
		return false;
	}
	
	public void clearLos() {
		
		for(var pos : getCrewPositions()) {
			pos.losUnits.clear();
			pos.losVehicles.clear();
		}
		
	}
	
	public List<Vehicle> getLosVehicles() {
		var losVics = new ArrayList<Vehicle>();
		
		for(var pos : getCrewPositions())
			for(var vic : pos.losVehicles)
				losVics.add(vic);
		
		return losVics;
	}
	
	public List<Unit> getLosUnits() {
		var losUnits = new ArrayList<Unit>();
		
		for(var pos : getCrewPositions())
			for(var unit : pos.losUnits)
				losUnits.add(unit);
		
		return losUnits;
	}
	
	public List<Vehicle> getSpottedVehicles() {
		var spottedVics = new ArrayList<Vehicle>();
		
		for(var pos : getCrewPositions())
			for(var vic : pos.spottedVehicles)
				spottedVics.add(vic);
		
		return spottedVics;
	}
	
	public List<Trooper> getSpottedTroopers() {
		var spottedTroopers = new ArrayList<Trooper>();
		
		for(var pos : getCrewPositions())
			for(var t : pos.spottedTroopers)
				spottedTroopers.add(t);
		
		return spottedTroopers;
	}
	
	@Override
	public String toString() {
		var fm = fireMissions.size() > 0 ? "FM:: " : "";
		return vehicleCallsign+":: " +fm+ vehicleTypeName + ", Knocked-out: "+knockedOut;
	}
	
}
