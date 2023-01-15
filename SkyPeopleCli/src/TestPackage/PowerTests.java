package TestPackage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Ship.Ship;
import Ship.Component.ComponentType;
import Ship.Ship.ShipType;

public class PowerTests {

	
	@Test
	public void powerHeatTest() {
		
		Ship venator = new Ship(ShipType.VENATOR);
		
		
		venator.power -= 23; 
		
		assertEquals(true, venator.power == 337.0);
		
		venator.generationStep();
		//System.out.println("heat: "+venator.heat);
		assertEquals(true, venator.heat == 15.0);
		assertEquals(true, venator.power == 352);
		
		venator.generationStep();
		
		assertEquals(true, venator.heat == 23.0);
		assertEquals(true, venator.power == 360);
		
		venator.destroyComponent(ComponentType.BATTERY);
		
		assertEquals(true, venator.power == 352.0);
		
	}
	
}
