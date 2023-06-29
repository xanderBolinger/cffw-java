package Vehicle.UnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Items.PersonalShield;
import Items.PersonalShield.ShieldType;
import Trooper.Trooper;
import Vehicle.Damage.VehicleCollision;
import Vehicle.Damage.VehicleCollision.CollisionHitLocation;
import Vehicle.Data.VehicleMovementData;
import Vehicle.HullDownPositions.HullDownPosition;
import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;

public class VehicleMovementTests {

	
	@Test
	public void EnterHullDownPositionTest() {
		
		var data = new VehicleMovementData();
		var hullDownPosition = new HullDownPosition(HullDownStatus.HIDDEN, HullDownStatus.PARTIAL_HULL_DOWN);
		
		data.enterHullDownPosition(hullDownPosition);
		assertEquals(hullDownPosition, data.hullDownPosition);
		assertEquals(HullDownStatus.HIDDEN, data.hullDownStatus);
	}
	
	@Test 
	public void MovementHullDownTest() {
		
		var data = new VehicleMovementData();
		var hullDownPosition = new HullDownPosition(HullDownStatus.HIDDEN, HullDownStatus.PARTIAL_HULL_DOWN);
		
		data.enterHullDownPosition(hullDownPosition);
		
		data.inchBack();
		
		assertEquals(HullDownStatus.HIDDEN, data.hullDownStatus);
		
		data.inchForward();
		
		assertEquals(HullDownStatus.TURRET_DOWN, data.hullDownStatus);
		
		data.inchForward();
		data.exitHullDownPosition();
		assertEquals(true, data.hullDown());
		assertEquals(HullDownStatus.HULL_DOWN, data.hullDownStatus);
		
		data.inchForward();
		
		assertEquals(HullDownStatus.PARTIAL_HULL_DOWN, data.hullDownStatus);
		
		data.inchForward();
		
		assertEquals(HullDownStatus.PARTIAL_HULL_DOWN, data.hullDownStatus);
		
		data.inchBack();
		
		assertEquals(HullDownStatus.HULL_DOWN, data.hullDownStatus);
		
		data.inchBack();
		
		assertEquals(HullDownStatus.TURRET_DOWN, data.hullDownStatus);
		
		data.inchBack();
		
		assertEquals(HullDownStatus.HIDDEN, data.hullDownStatus);
		
		data.exitHullDownPosition();
		assertEquals(false, data.hullDown());
		
		
		data.enterHullDownPosition(hullDownPosition);
		data.inchForward();
		data.inchForward();
		data.inchForward();
		data.exitHullDownPosition();
		assertEquals(false, data.hullDown());
	}
	
	@Test
	public void obsticleChanceTest() {
		
		assertEquals(44, VehicleCollision.hiddenObstacleCollisionChance(4));
		
	}
	
	@Test
	public void slowDownMagnitudeTest() {
		var id  = VehicleCollision.getRapidSlowdownImpactDamage(7);
		assertEquals(true, id == 6 || id == 7);
	}
	
	@Test
	public void getHitLocationTest() {
		var location = VehicleCollision.getHitLocationZoneOpen(CollisionHitLocation.Legs);
		assertEquals(true, location <= 99 && location >= 57);
		location = VehicleCollision.getHitLocationZoneOpen(CollisionHitLocation.Arms);
		assertEquals(true, location <= 16 && location >= 8);
		location = VehicleCollision.getHitLocationZoneOpen(CollisionHitLocation.Body);
		assertEquals(true, location <= 56 && location >= 18);
		location = VehicleCollision.getHitLocationZoneOpen(CollisionHitLocation.Head);
		assertEquals(true, location <= 7 && location >= 0);
	}
	
	
	@Test
	public void physicalDamageTest() {
		Trooper trooper = new Trooper();
		trooper.personalShield = new PersonalShield(ShieldType.MKIIBubbleShield);
	
		int col = VehicleCollision.getDamageCol(trooper, 11, 0);
		assertEquals(9, col);
		
		int pd = VehicleCollision.getInjuryPhysicalDamage(9, CollisionHitLocation.Arms);
		assertEquals(72, pd);
		pd = VehicleCollision.applyShieldProtection(trooper, pd, 0);
		assertEquals(193, trooper.personalShield.currentShieldStrength);
		assertEquals(true, pd <= 0);
		
	}
	
}
