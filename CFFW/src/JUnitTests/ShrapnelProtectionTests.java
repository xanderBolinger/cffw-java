package JUnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Explosion.Shrap.ShrapnelProtection;
import Shoot.Shoot;
import Trooper.Trooper;
import Trooper.generateSquad;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class ShrapnelProtectionTests {

	Trooper target;

	@Before
	public void initTesting() throws InterruptedException {
		generateSquad squad = new generateSquad("Clone Trooper Phase 1", "Riflesquad");
		target = squad.getSquad().get(0);
	}

	@After
	public void finishTesting() throws InterruptedException {
		DiceRoller.clearTestValues();
	}

	@Test
	public void testUnitTest() {
		assertEquals(1, 1);
	}
	
	@Test
	public void protectedCoverTest() {
		target.inCover = true;
		DiceRoller.addTestValue(21);
		assertEquals(true, ShrapnelProtection.trooperProtectedFromShrapnel(target, 10, 0, false) == true);
	}

	
	@Test
	public void protectedOpenTest() {
		target.inCover = false;
		DiceRoller.addTestValue(1);
		assertEquals(true, ShrapnelProtection.trooperProtectedFromShrapnel(target, 0, 0, false) == false);
	}
	
}
