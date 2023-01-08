package TestPackage;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import Ship.Component.ComponentType;
import Ship.HitLocation;
import Ship.Ship;
import Ship.Ship.ShipType;

public class HitTableTests {

	@Test
	public void venatorHitTableTest() {
		
		
		Ship ship = new Ship(ShipType.VENATOR);
		
		assertEquals(10, ship.hitTable.nose.size());
		assertEquals(10, ship.hitTable.aft.size());
		assertEquals(10, ship.hitTable.port.size());
		assertEquals(10, ship.hitTable.starboard.size());
		assertEquals(10, ship.hitTable.top.size());
		assertEquals(10, ship.hitTable.bottom.size());
		assertEquals(10, ship.hitTable.core.size());
		
		ComponentType[] types = ComponentType.values();
		
		ArrayList<ComponentType> usedTypes = new ArrayList<>();
		
		for(HitLocation location : ship.hitTable.nose) {
			usedTypes.add(location.componentType);
		}
		
		
	}
	
	
}
