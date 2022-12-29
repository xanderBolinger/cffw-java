package JUnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Items.Optic;
import Items.Optic.OpticType;
import Items.Weapons;

public class OpticTests {

	
	@Test
	public void redDotTest() {
		
		Weapons a310 = new Weapons().findWeapon("A310");
		Optic redDot = new Optic(OpticType.REDDOT);
		redDot.applyOptic(a310);

		assertEquals(true, a310.aimTime.get(0) == -18);
		assertEquals(false, a310.aimTime.get(0) == -19);
		assertEquals(true, a310.aimTime.get(1) == -10);
		assertEquals(true, a310.aimTime.get(2) == -8);
		assertEquals(true, a310.aimTime.get(3) == -5);
		assertEquals(true, a310.aimTime.get(4) == -4);
		assertEquals(true, a310.aimTime.get(5) == -3);
		assertEquals(true, a310.aimTime.get(6) == -2);
		assertEquals(true, a310.aimTime.get(7) == -1);
		assertEquals(true, a310.aimTime.get(8) == 0);
		assertEquals(true, a310.aimTime.get(9) == 1);
		assertEquals(true, a310.aimTime.get(10) == 2);
	}
	
}
