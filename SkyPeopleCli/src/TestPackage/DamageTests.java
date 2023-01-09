package TestPackage;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Mechanics.DamageAllocation;
import Mechanics.DamageAllocation.HitSide;
import Ship.Ship;
import Ship.Ship.ShipType;

public class DamageTests {

	Ship venator; 
	
	@Before
	public void before() {
		venator = new Ship(ShipType.VENATOR);
	}
	
	@Test
	public void destroyFuelTest() {
		assertEquals(144, venator.fuel.remainingFuel());
		
		DamageAllocation.destroyLocation(venator, venator.hitTable.aft.get(8));
		
		assertEquals(136, venator.fuel.remainingFuel());
		
	}

	@Test
	public void destroyElectronicsTest() {
		
		DamageAllocation.destroyLocation(venator, venator.hitTable.core.get(8));
		assertEquals(true, venator.electronics.cells.get(0).destroyed);
		assertEquals(false, venator.electronics.cells.get(1).destroyed);
		DamageAllocation.destroyLocation(venator, venator.hitTable.core.get(8));
		assertEquals(true, venator.electronics.cells.get(1).destroyed);
		DamageAllocation.destroyLocation(venator, venator.hitTable.core.get(8));
		assertEquals(true, venator.electronics.cells.get(2).destroyed);
		DamageAllocation.destroyLocation(venator, venator.hitTable.core.get(8));
		assertEquals(true, venator.electronics.cells.get(3).destroyed);
		DamageAllocation.destroyLocation(venator, venator.hitTable.core.get(8));
		assertEquals(true, venator.electronics.cells.get(4).destroyed);
	}

	@Test
	public void applyInitialHit() {
		assertEquals(10, DamageAllocation.applyInitialHit(30, 9, 11));
		assertEquals(0, DamageAllocation.applyInitialHit(30, 9, 21));
	}
	
	@Test
	public void applyHit() {
		assertEquals(144, venator.fuel.remainingFuel());
		
		assertEquals(1, DamageAllocation.applyHit(8, 1, venator, venator.hitTable.aft.get(8)));
		
		assertEquals(136, venator.fuel.remainingFuel());
	}
	
	@Test
	public void getHitLocation() throws Exception {
		
		assertEquals(venator.hitTable.nose.get(0), 
				DamageAllocation.getHitLocation(1, venator, HitSide.NOSE));
		assertEquals(venator.hitTable.aft.get(0), 
				DamageAllocation.getHitLocation(1, venator, HitSide.AFT));
		assertEquals(venator.hitTable.port.get(0), 
				DamageAllocation.getHitLocation(1, venator, HitSide.PORT));
		assertEquals(venator.hitTable.starboard.get(0), 
				DamageAllocation.getHitLocation(1, venator, HitSide.STARBOARD));
		assertEquals(venator.hitTable.top.get(0), 
				DamageAllocation.getHitLocation(1, venator, HitSide.TOP));
		assertEquals(venator.hitTable.bottom.get(0), 
				DamageAllocation.getHitLocation(1, venator, HitSide.BOTTOM));
		assertEquals(venator.hitTable.core.get(0), 
				DamageAllocation.getHitLocation(1, venator, HitSide.CORE));
		
	}
	
	@Test
	public void hitSideCylce() {
		
		assertEquals(HitSide.CORE, DamageAllocation.cycleHitSide(HitSide.NOSE, HitSide.NOSE));
		assertEquals(HitSide.AFT, DamageAllocation.cycleHitSide(HitSide.NOSE, HitSide.CORE));
		assertEquals(HitSide.NONE, DamageAllocation.cycleHitSide(HitSide.NOSE, HitSide.AFT));
		
		assertEquals(HitSide.CORE, DamageAllocation.cycleHitSide(HitSide.AFT, HitSide.AFT));
		assertEquals(HitSide.NOSE, DamageAllocation.cycleHitSide(HitSide.AFT, HitSide.CORE));
		assertEquals(HitSide.NONE, DamageAllocation.cycleHitSide(HitSide.AFT, HitSide.NOSE));
		
		assertEquals(HitSide.CORE, DamageAllocation.cycleHitSide(HitSide.PORT, HitSide.PORT));
		assertEquals(HitSide.STARBOARD, DamageAllocation.cycleHitSide(HitSide.PORT, HitSide.CORE));
		assertEquals(HitSide.NONE, DamageAllocation.cycleHitSide(HitSide.PORT, HitSide.STARBOARD));
		
		assertEquals(HitSide.CORE, DamageAllocation.cycleHitSide(HitSide.STARBOARD, HitSide.STARBOARD));
		assertEquals(HitSide.PORT, DamageAllocation.cycleHitSide(HitSide.STARBOARD, HitSide.CORE));
		assertEquals(HitSide.NONE, DamageAllocation.cycleHitSide(HitSide.STARBOARD, HitSide.PORT));
		
		assertEquals(HitSide.CORE, DamageAllocation.cycleHitSide(HitSide.TOP, HitSide.TOP));
		assertEquals(HitSide.BOTTOM, DamageAllocation.cycleHitSide(HitSide.TOP, HitSide.CORE));
		assertEquals(HitSide.NONE, DamageAllocation.cycleHitSide(HitSide.TOP, HitSide.BOTTOM));
		
		assertEquals(HitSide.CORE, DamageAllocation.cycleHitSide(HitSide.BOTTOM, HitSide.BOTTOM));
		assertEquals(HitSide.TOP, DamageAllocation.cycleHitSide(HitSide.BOTTOM, HitSide.CORE));
		assertEquals(HitSide.NONE, DamageAllocation.cycleHitSide(HitSide.BOTTOM, HitSide.TOP));
	}
	
	
}
