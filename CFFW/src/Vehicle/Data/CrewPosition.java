package Vehicle.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import HexGrid.HexDirectionUtility.HexDirection;
import Trooper.Trooper;
import Unit.Unit;
import Vehicle.Vehicle;
import Vehicle.Utilities.VehicleDataUtility.CrewPositionType;

public class CrewPosition implements Serializable {

	public CrewMember crewMemeber;
	public PositionSpotData spotData;
	String positionName;
	List<CrewPositionType> positionTypes;
	List<HexDirection> fieldOfView;
	
	public ArrayList<Vehicle> losVehicles;
	public ArrayList<Unit> losUnits;
	public ArrayList<Vehicle> spottedVehicles;
	public ArrayList<Trooper> spottedTroopers;
	
	public int elevationAboveVehicle;
	
	public CrewPosition(String positionName, CrewMember crewMember, 
			List<CrewPositionType> positionTypes, List<HexDirection> fieldOfView,
			PositionSpotData positionSpotData) {
		this.positionName = positionName;
		this.crewMemeber = crewMember;
		this.positionTypes = positionTypes;
		this.fieldOfView = fieldOfView;
		losVehicles = new ArrayList<Vehicle>();
		losUnits = new ArrayList<Unit>();
		spottedVehicles = new ArrayList<Vehicle>();
		spottedTroopers = new ArrayList<Trooper>();
		spotData = positionSpotData;
	}
	
	public CrewPosition(String positionName, List<CrewPositionType> positionTypes, List<HexDirection> fieldOfView) {
		this.positionName = positionName;
		this.positionTypes = positionTypes;
		this.fieldOfView = fieldOfView;
	}
	
	public String getPositionName() {
		return positionName;
	}

	public List<HexDirection> getFieldOfView() {
		return fieldOfView;
	}
	
	public boolean occupied() {
		return crewMemeber != null && crewMemeber.crewMember != null;
	}
	
}
