package Melee.Tests;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.util.Pair;
import org.junit.Test;

import Items.Optic;
import Items.Weapons;
import Melee.MeleeHitLocation;
import Melee.MeleeHitLocation.MeleeDamageType;
import UtilityClasses.DiceRoller;
import Items.Optic.OpticType;

public class MeleeHitLocationTests {

	@Test
	public void hitLocationBenchmarkTest() {
		
		/*try {
			var rslts = MeleeHitLocation.GetHitLocationResults(MeleeDamageType.CUTTING, DiceRoller.roll(1, 26), DiceRoller.roll(1, 5), DiceRoller.roll(1, 7), DiceRoller.roll(0, 30));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
        ExecutorService executor = Executors.newFixedThreadPool(16);

        long startTime = System.currentTimeMillis();

        // Execute the code block 100 times
        for (int i = 0; i < 100; i++) {
            executor.execute(() -> {
                try {
                    /*var rslts = MeleeHitLocation.GetHitLocationResults(MeleeDamageType.CUTTING, 
                    		DiceRoller.roll(1, 26), DiceRoller.roll(1, 5), DiceRoller.roll(1, 7), DiceRoller.roll(0, 30));*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        // Shut down the ExecutorService
       //executor.shutdown();

       try {
            // Wait for all tasks to complete or timeout after 1 hour
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Execution time: " + executionTime + " milliseconds");
	}
	
	@Test
	public void pcHitLocation() {
		
		try {
			var pair = MeleeHitLocation.GetPDText(0, 10, "Skull", MeleeDamageType.BLUNT);
			assertEquals("1K", pair.getFirst());
			assertEquals("Skull", pair.getSecond());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void avColumnTest() {
		try {
			var row = MeleeHitLocation.GetAvRow(0, "PcDamageTableBlunt.xlsx");
			var col = MeleeHitLocation.GetDamageColumn(row, 10, "PcDamageTableBlunt.xlsx");
			assertEquals(8,col);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getLocationTextTest() {
		var locationData = MeleeHitLocation.GetLocationText("MeleeDamageTablesBlunt.xlsx", 1, 1, 1);
		assertEquals("Foot", locationData.zoneName);
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
