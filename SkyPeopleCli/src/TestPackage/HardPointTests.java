package TestPackage;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Ship.Ship;
import Ship.Ship.ShipType;
import Ship.Weapon.WeaponType;

public class HardPointTests {

	Ship venator; 
	
	@Before
	public void before() {
		venator = new Ship(ShipType.VENATOR);
	}
	
	@Test
	public void venatorHardPointTests() {
		
		assertEquals(WeaponType.HEAVY_TURBO_LASER, venator.hardPoints.get(0).weapons.get(0).weaponType);
		assertEquals(1, venator.hardPoints.get(0).fireArcIndex);
		assertEquals(WeaponType.HEAVY_TURBO_LASER, venator.hardPoints.get(1).weapons.get(0).weaponType);
		assertEquals(2, venator.hardPoints.get(1).fireArcIndex);
		assertEquals(WeaponType.HEAVY_PROTON_TORPEDO, venator.hardPoints.get(2).weapons.get(0).weaponType);
		assertEquals(3, venator.hardPoints.get(2).fireArcIndex);
		assertEquals(WeaponType.DECK_GUN, venator.hardPoints.get(3).weapons.get(0).weaponType);
		assertEquals(4, venator.hardPoints.get(3).fireArcIndex);
		assertEquals(WeaponType.DECK_GUN, venator.hardPoints.get(4).weapons.get(0).weaponType);
		assertEquals(5, venator.hardPoints.get(4).fireArcIndex);
	}
	
}
