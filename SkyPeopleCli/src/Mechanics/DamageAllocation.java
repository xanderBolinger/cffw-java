package Mechanics;

import Ship.Component.ComponentType;
import Ship.HitLocation;
import Ship.HitLocation.LocationType;
import Ship.Ship;

public class DamageAllocation {

	public enum HitSide {
		NOSE, AFT, PORT, STARBOARD, TOP, BOTTOM, CORE, NONE
	}

	public static int appliedDamage;
	public static HitSide initialHitSide;

	public static void allocateDamage(int damage, Ship ship, HitSide hitSide) throws Exception {

		ship.shieldStrength -= (double) damage;
		
		if(ship.shieldStrength < 0) {
			damage = (int) (ship.shieldStrength * -1.0);
			ship.shieldStrength = 0;
		} else {
			return;
		}
		
		System.out.println("Allocating Damage: "+damage);
		
		appliedDamage = 0;
		initialHitSide = hitSide; 
		
		int skinArmor = ship.hitTable.getSkinArmor(hitSide);
		System.out.println("Skin Armor: "+skinArmor);
		int roll = DiceRoller.d10();
		HitLocation initialLocation = getHitLocation(roll, ship, hitSide);

		System.out.println("Initial Hit Location: "+initialLocation.toString(roll));
		
		if (initialLocation.locationType != LocationType.HARDPOINT
				|| initialLocation.componentType != ComponentType.POINTDEFENSE
				|| initialLocation.componentType != ComponentType.BRIDGE) {
			damage = applyInitialHit(damage, DiceRoller.twoD10Minus(), skinArmor);
			hitSide = depthCheck(ship, hitSide);
			if (hitSide == HitSide.NONE)
				return;
		}

		if (damage > 0) {
			damage = applyHit(damage, soak(ship), ship, initialLocation);
			hitSide = depthCheck(ship, hitSide);
			if (hitSide == HitSide.NONE)
				return;
		}

		while (damage > 0) {
			int roll2 = DiceRoller.d10();
			
			HitLocation location = getHitLocation(roll2, ship, hitSide);
			System.out.println("Location: "+location.toString(roll2)+", Damage: "+damage);
			
			
			damage = applyHit(damage, soak(ship), ship, location);
			hitSide = depthCheck(ship, hitSide);
			if (hitSide == HitSide.NONE)
				return;
		}

	}
	
	public static int soak(Ship ship) {
		int soak = DiceRoller.twoD10Minus();
		
		if(soak == 0) {
			System.out.println("Soak Zero: SI Loss");
			ship.destroyComponent(ComponentType.SI);
		}
		
		return soak; 
	} 
	

	public static HitSide depthCheck(Ship ship, HitSide currentHitSide) throws Exception {


		int depth = 0;
		
		switch (initialHitSide) {

		case NOSE:
			depth = ship.hitTable.noseAftDepth;
			break;
		case AFT:
			depth = ship.hitTable.noseAftDepth;
			break;
		case PORT:
			depth = ship.hitTable.portStarboardDepth;
			break;
		case STARBOARD:
			depth = ship.hitTable.portStarboardDepth;
			break;
		case TOP:
			depth = ship.hitTable.topBottomDepth;
			break;
		case BOTTOM:
			depth = ship.hitTable.topBottomDepth;
			break;
			
		}

		
		//System.out.println("Hit Side: "+currentHitSide);
		//System.out.println("depth: "+depth);
		//System.out.println("Damage: "+appliedDamage);
		
		if(depth == 0)
			throw new Exception("depth = 0, for "+ship.shipName+", InitialSide: "+initialHitSide+", Current Side: "+currentHitSide);
		
		if(appliedDamage >= depth) {
			appliedDamage = 0;
			return cycleHitSide(initialHitSide, currentHitSide);
			
		} else {
			return currentHitSide;
		} 
		
	}
	
	public static HitSide getHitSide(String hitSide) {
		hitSide = hitSide.toLowerCase();
		
		if(hitSide.equals("nose")) {
			return HitSide.NOSE;
		} else if(hitSide.equals("aft")) {
			return HitSide.AFT;
		} else if(hitSide.equals("port")) {
			return HitSide.PORT;
		} else if(hitSide.equals("starboard")) {
			return HitSide.STARBOARD;
		} else if(hitSide.equals("top")) {
			return HitSide.TOP;
		} else if(hitSide.equals("bottom")) {
			return HitSide.BOTTOM;
		} 
			
		
		return null; 
		
	}

	public static HitSide cycleHitSide(HitSide initialHitSide, HitSide currentSide) {
		switch (initialHitSide) {

		case NOSE:
			if (currentSide == HitSide.NOSE)
				return HitSide.CORE;
			else if (currentSide == HitSide.CORE)
				return HitSide.AFT;
			else
				return HitSide.NONE;
		case AFT:
			if (currentSide == HitSide.AFT)
				return HitSide.CORE;
			else if (currentSide == HitSide.CORE)
				return HitSide.NOSE;
			else
				return HitSide.NONE;
		case PORT:
			if (currentSide == HitSide.PORT)
				return HitSide.CORE;
			else if (currentSide == HitSide.CORE)
				return HitSide.STARBOARD;
			else
				return HitSide.NONE;
		case STARBOARD:
			if (currentSide == HitSide.STARBOARD)
				return HitSide.CORE;
			else if (currentSide == HitSide.CORE)
				return HitSide.PORT;
			else
				return HitSide.NONE;
		case TOP:
			if (currentSide == HitSide.TOP)
				return HitSide.CORE;
			else if (currentSide == HitSide.CORE)
				return HitSide.BOTTOM;
			else
				return HitSide.NONE;
		default: // Bottom
			if (currentSide == HitSide.BOTTOM)
				return HitSide.CORE;
			else if (currentSide == HitSide.CORE)
				return HitSide.TOP;
			else
				return HitSide.NONE;
		}

	}

	public static int applyInitialHit(int damage, int initialSoak, int initialArmor) {
		appliedDamage += initialArmor + initialSoak;
		return damage - initialArmor - initialSoak;
	}

	public static int applyHit(int damage, int soak, Ship ship, HitLocation hitLocation) {
		appliedDamage += soak + hitLocation.armor;

		if (damage - soak - hitLocation.armor > 0) {
			destroyLocation(ship, hitLocation);
		}
		
		return damage - soak - hitLocation.armor;
	}

	public static void destroyLocation(Ship ship, HitLocation hitLocation) {
		
		System.out.println("Destroyed Location: "+hitLocation.toString());
		
		switch (hitLocation.locationType) {

		case COMPONENT:
			ship.destroyComponent(hitLocation.componentType);
			break;
		case ELECTRONICS:
			ship.electronics.destroyElectronics();
			break;
		case FUEL:
			ship.fuel.destroyFuel();
			break;
		case HARDPOINT:
			ship.hardPoints.get(hitLocation.hardPointIndex).destroyWeapon();
			break;

		}
	}

	public static HitLocation getHitLocation(int locationRoll, Ship ship, HitSide hitSide) throws Exception {

		locationRoll--;

		switch (hitSide) {

		case NOSE:
			return ship.hitTable.nose.get(locationRoll);
		case AFT:
			return ship.hitTable.aft.get(locationRoll);
		case PORT:
			return ship.hitTable.port.get(locationRoll);
		case STARBOARD:
			return ship.hitTable.starboard.get(locationRoll);
		case TOP:
			return ship.hitTable.top.get(locationRoll);
		case BOTTOM:
			return ship.hitTable.bottom.get(locationRoll);
		case CORE:
			return ship.hitTable.core.get(locationRoll);
		}

		throw new Exception(
				"Hit Location Not Found, Ship: " + ship.shipName + ", Side: " + hitSide + ", Roll: " + locationRoll);

	}

}
