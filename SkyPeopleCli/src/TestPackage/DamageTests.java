package TestPackage;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Mechanics.BeamAttack;
import Mechanics.DamageAllocation;
import Mechanics.DamageAllocation.HitSide;
import Ship.HardPoint;
import Ship.Ship;
import Ship.Ship.ShipType;
import Ship.Weapon;
import Ship.Weapon.FireType;
import Ship.Weapon.WeaponType;

public class DamageTests {

	Ship venator; 
	
	@Before
	public void before() {
		venator = new Ship(ShipType.VENATOR);
		venator.shieldStrength = 0;
	}
	
	@Test 
	public void shieldDamage() throws Exception {
		
		venator.shieldStrength = 500; 
		
		Weapon weapon = venator.hardPoints.get(0).weapons.get(0);
		BeamAttack.beamAttack(venator, weapon, 8, 4, 6, HitSide.NOSE);
		
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
	
	@Test
	public void allocateDamage() throws Exception {
		DamageAllocation.allocateDamage(1000, venator, HitSide.TOP);
	}
	
	@Test
	public void destroyHardPoint() {
		
		HardPoint hardpoint = new HardPoint(6,1);
		hardpoint.addWeapon(WeaponType.HEAVY_TURBO_LASER, FireType.TWIN);
		hardpoint.addWeapon(WeaponType.HEAVY_TURBO_LASER, FireType.TWIN);

		
		hardpoint.destroyWeapon();
		
		for(Weapon weapon : hardpoint.weapons) {
			if(weapon.destroyed) {
				assertEquals(true, weapon.destroyed);				
			}
		}
		
	}
	
	@Test
	public void beamAttack() throws Exception {
		
		Weapon weapon = venator.hardPoints.get(0).weapons.get(0);
		BeamAttack.beamAttack(venator, weapon, 8, 4, 6, HitSide.NOSE);
		
		
		System.out.println(venator.toString());
	}
	
	@Test
	public void hitCountNose() throws Exception {
		
		int counter = 0;
		while(!venator.destroyed) {
			Weapon weapon = venator.hardPoints.get(0).weapons.get(0);
			BeamAttack.beamAttack(venator, weapon, 8, 4, 6, HitSide.NOSE);
			counter++;
		}
	
		System.out.println("Nose Hit Count: "+counter);
		
	}
	
	@Test
	public void hitCountAft() throws Exception {
		
		int counter = 0;
		while(!venator.destroyed) {
			Weapon weapon = venator.hardPoints.get(0).weapons.get(0);
			BeamAttack.beamAttack(venator, weapon, 8, 4, 6, HitSide.AFT);
			counter++;
		}
	
		System.out.println("Aft Hit Count: "+counter);
		
	}

	@Test
	public void hitCountPort() throws Exception {
		
		int counter = 0;
		while(!venator.destroyed) {
			Weapon weapon = venator.hardPoints.get(0).weapons.get(0);
			BeamAttack.beamAttack(venator, weapon, 8, 4, 6, HitSide.PORT);
			counter++;
		}
	
		System.out.println("Port Hit Count: "+counter);
		
	}
	
	@Test
	public void getHitSide() {
		
		assertEquals(null, DamageAllocation.getHitSide("asdf"));
		assertEquals(HitSide.NOSE, DamageAllocation.getHitSide("NOSE"));
		assertEquals(HitSide.AFT, DamageAllocation.getHitSide("AFT"));
		assertEquals(HitSide.PORT, DamageAllocation.getHitSide("PORT"));
		assertEquals(HitSide.STARBOARD, DamageAllocation.getHitSide("STARBOARD"));
		assertEquals(HitSide.TOP, DamageAllocation.getHitSide("TOP"));
		assertEquals(HitSide.BOTTOM, DamageAllocation.getHitSide("BOTTOM"));
		
		
	}
	
}
