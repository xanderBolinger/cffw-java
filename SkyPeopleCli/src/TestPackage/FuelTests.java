package TestPackage;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import Ship.Ship;
import Ship.Ship.ShipType;

public class FuelTests {

	@Test
	public void venatorFuel() {
		Ship ship = new Ship(ShipType.VENATOR);
		
		assertEquals(144, ship.fuel.remainingFuel());
		
		ship.fuel.destroyFuel();
		
		assertEquals(136, ship.fuel.remainingFuel());
		
		ship.fuel.spendFuel(15);
		
		assertEquals(121, ship.fuel.remainingFuel());
	}
	
}
