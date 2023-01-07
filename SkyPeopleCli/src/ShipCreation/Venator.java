package ShipCreation;

import Ship.Fuel;
import Ship.Ship;

public class Venator implements ShipTemplate {

	public Ship ship; 
	
	public Venator(Ship ship) {
		this.ship = ship;
		createShip();
	}
	
	@Override
	public void createShip() {
		fuel();
	}

	@Override
	public void description() {
		
	}

	@Override
	public void componens() {
		
	}

	@Override
	public void hardPoints() {
		
	}

	@Override
	public void hitTable() {
		
	}

	@Override
	public void electronics() {
		
	}

	@Override
	public void fuel() {
		Fuel fuel = new Fuel();
		
	}

	@Override
	public void pivot() {
		
	}

	@Override
	public void roll() {
		
	}

}
