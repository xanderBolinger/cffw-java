package Ship;

import Ship.Component.ComponentType;

public class HitLocation {

	public enum LocationType {
		HARDPOINT,COMPONENT,ELECTRONICS,FUEL
	}
	public ComponentType componentType;
	public int hardPointIndex;
	public LocationType locationType;
	public int armor;
}
