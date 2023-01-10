package Ship;

import Ship.Component.ComponentType;

public class HitLocation {

	public enum LocationType {
		HARDPOINT, COMPONENT, ELECTRONICS, FUEL
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

	
	@Override
	public String toString() {

		switch (locationType) {
		case HARDPOINT:
			return "HARDPOINT Index: "+hardPointIndex + " [" + armor + "]";
		case COMPONENT:
			return  "" + componentType + " [" + armor + "]";
		case ELECTRONICS:
			return "ELECTRONICS" + " [" + armor + "]";
		case FUEL:
			return "FUEL" + " [" + armor + "]";
		}

		return "EMPTY HIT LOCATION";
	}
	
	public String toString(int hitLocationNumber) {

		switch (locationType) {
		case HARDPOINT:
			return hitLocationNumber + " HARDPOINT Index: "+hardPointIndex + " [" + armor + "]";
		case COMPONENT:
			return hitLocationNumber + " " + componentType + " [" + armor + "]";
		case ELECTRONICS:
			return hitLocationNumber + " ELECTRONICS" + " [" + armor + "]";
		case FUEL:
			return hitLocationNumber + " FUEL" + " [" + armor + "]";
		}

		return "EMPTY HIT LOCATION";
	}

}
