package TestPackage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Ship.Weapon;
import Ship.Weapon.FireType;
import Ship.Weapon.WeaponType;

public class WeaponTest {

	@Test
	public void mediumTurboLaserTest() {
		
		Weapon weapon = new Weapon(WeaponType.MEDIUM_TURBO_LASER, FireType.SINGLE);
		
		for(int i = 0; i < 8; i++) {
			assertEquals(10, weapon.columns.get(i).damage.size());
		}
		
	}
	
	@Test
	public void mediumLaserCannon() {
		
		Weapon weapon = new Weapon(WeaponType.MEDIUM_LASER_CANNON, FireType.SINGLE);
		
		for(int i = 0; i < 4; i++) {
			assertEquals(10, weapon.columns.get(i).damage.size());
		}
		
	}
	
}
