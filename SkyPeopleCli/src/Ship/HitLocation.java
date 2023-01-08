package Ship;

import Ship.Component.ComponentType;

public class HitLocation {

	public enum LocationType {
		HARDPOINT,COMPONENT,ELECTRONICS,FUEL
	}
	public ComponentType componentType;
	public LocationType locationType;
	public int armor;
	public int hardPointIndex;
	
	public HitLocation(ComponentType componentType, int armor) {
		this.armor = armor; 
		this.componentType = componentType;
		this.locationType = LocationType.COMPONENT;
	}
	
	public HitLocation(LocationType locationType, int armor) {
		this.locationType = locationType;
		this.armor = armor;
	}
	
	public HitLocation(int hardPointIndex, int armor) {
		this.hardPointIndex = hardPointIndex;
		this.armor = armor;
		locationType = LocationType.HARDPOINT;
	}
	
}
