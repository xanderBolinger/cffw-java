package ShipCreation;

import Ship.Component.ComponentType;
import Ship.Ship;

public interface ShipTemplate {

	public void createShip();
	public void description();
	public void components();
	public void hardPoints();
	public void hitTable();
	public void electronics();
	public void fuel();
	public void pivot();
	public void roll();
}
