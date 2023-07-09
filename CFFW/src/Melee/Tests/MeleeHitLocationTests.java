package Melee.Tests;

import static org.junit.Assert.assertEquals;

import org.apache.commons.math3.util.Pair;
import org.junit.Test;

import Items.Optic;
import Items.Weapons;
import Melee.MeleeHitLocation;
import UtilityClasses.DiceRoller;
import Items.Optic.OpticType;

public class MeleeHitLocationTests {

	@Test
	public void getLocationTextTest() {
		var locationData = MeleeHitLocation.GetLocationText("MeleeDamageTablesBlunt.xlsx", 1, 1, 1);
		assertEquals(0, locationData.bloodLossPD);
		assertEquals(40, locationData.shockPD);
		assertEquals(2, locationData.painPoints);
		assertEquals(false, locationData.knockDown);
		assertEquals(0, locationData.knockDownMod);
		
		/*final long startTime = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			var testLocationData = MeleeHitLocation.GetLocationText("MeleeDamageTablesBlunt.xlsx", DiceRoller.roll(1,7), 1, 1);
		}
		final long endTime = System.currentTimeMillis();

		System.out.println("Total execution time: " + (endTime - startTime));*/
		
	}
	
	@Test
	public void damageFromPdCellTest() {
		
		Pair<Integer, Boolean> pair = MeleeHitLocation.GetPdFromCell("5HD");
		assertEquals(500, (int)pair.getFirst());
		assertEquals(true, pair.getSecond());
		
		pair = MeleeHitLocation.GetPdFromCell("1");
		assertEquals(1, (int)pair.getFirst());
		assertEquals(false, pair.getSecond());
	}
	
}
