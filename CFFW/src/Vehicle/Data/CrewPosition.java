package Vehicle.Data;

import java.util.ArrayList;
import java.util.List;

import HexGrid.HexDirectionUtility.HexDirection;
import Vehicle.Utilities.VehicleDataUtility.CrewPositionType;

public class CrewPosition {

	String positionName;
	CrewMember crewMemeber;
	List<CrewPositionType> positionTypes;
	List<HexDirection> fieldOfView;
	
	public CrewPosition(String positionName, CrewMember crewMember, List<CrewPositionType> positionTypes, List<HexDirection> fieldOfView) {
		this.positionName = positionName;
		this.crewMemeber = crewMember;
		this.positionTypes = positionTypes;
		this.fieldOfView = fieldOfView;
	}
	
	public String getPositionName() {
		return positionName;
	}

	public List<HexDirection> getFieldOfView() {
		return fieldOfView;
	}
	
}
