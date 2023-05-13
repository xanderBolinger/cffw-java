package Vehicle.Data;

import java.util.ArrayList;
import java.util.List;

import HexGrid.HexDirectionUtility.HexDirection;

public class CrewCompartment {

	String compartmentName;
	List<CrewPosition> crewPositions;
	
	boolean shielded; 
	ShieldGenerator shieldGenerator;
	
	public CrewCompartment(String compartmentName, List<CrewPosition> crewPositions) {
		this.compartmentName = compartmentName;
		this.crewPositions = crewPositions;
	}
	
	public String getCompartmentName() {
		return compartmentName;
	}

	public boolean containsPosition(String positionName) {
		
		for(var pos : crewPositions) {
			if(positionName.equals(pos.getPositionName()))
				return true;
		}
		
		return false;
	}

	public CrewPosition getCrewPosition(String positionName) throws Exception {
		
		for(var pos : crewPositions) {
			if(positionName.equals(pos.getPositionName()))
				return pos;
		}
		
		
		throw new Exception("Position not found in compartment: "+compartmentName+", position: "+positionName);
	}
	
}
