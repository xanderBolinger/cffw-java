package Vehicle.Data;

import java.util.ArrayList;
import java.util.List;

import HexGrid.HexDirectionUtility.HexDirection;
import Trooper.Trooper;

public class CrewCompartment {

	String compartmentName;
	List<CrewPosition> crewPositions;
	
	boolean shielded; 
	ShieldGenerator shieldGenerator;
	
	public CrewCompartment(String compartmentName, List<CrewPosition> crewPositions) {
		this.compartmentName = compartmentName;
		this.crewPositions = crewPositions;
	}
	
	public void AddShieldGenerator(ShieldGenerator shieldGenerator) {
		shielded = true;
		this.shieldGenerator = shieldGenerator;
	}
	
	public ShieldGenerator getShieldGenerator() {
		return shieldGenerator;
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
	
	public List<Trooper> getTroopers() {
		var troopers = new ArrayList<Trooper>();
		
		for(var position : crewPositions) {
			
			if(position.crewMemeber != null && position.crewMemeber.crewMember != null)
				troopers.add(position.crewMemeber.crewMember);
			
		}
	
		return troopers; 
	}

	public CrewPosition getCrewPosition(String positionName) throws Exception {
		
		for(var pos : crewPositions) {
			if(positionName.equals(pos.getPositionName()))
				return pos;
		}
		
		
		throw new Exception("Position not found in compartment: "+compartmentName+", position: "+positionName);
	}
	
	public int getCrewCount() {
		return crewPositions.size();
	}
	
}
