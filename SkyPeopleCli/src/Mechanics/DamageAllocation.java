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

		appliedDamage = 0;

		int skinArmor = ship.hitTable.getSkinArmor(hitSide);

		HitLocation initialLocation = getHitLocation(DiceRoller.d10(), ship, hitSide);

		if (initialLocation.locationType != LocationType.HARDPOINT
				|| initialLocation.componentType != ComponentType.POINTDEFENSE
				|| initialLocation.componentType != ComponentType.BRIDGE) {
			damage = applyInitialHit(damage, DiceRoller.d10(), skinArmor);
			hitSide = depthCheck(ship, hitSide);
			if (hitSide == HitSide.NONE)
				return;
		}

		if (damage > 0) {
			damage = applyHit(damage, DiceRoller.twoD10Minus(), ship, initialLocation);
			hitSide = depthCheck(ship, hitSide);
			if (hitSide == HitSide.NONE)
				return;
		}

		while (damage > 0) {
			damage = applyHit(damage, DiceRoller.twoD10Minus(), ship, getHitLocation(DiceRoller.d10(), ship, hitSide));
			hitSide = depthCheck(ship, hitSide);
			if (hitSide == HitSide.NONE)
				return;
		}

	}

	public static HitSide depthCheck(Ship ship, HitSide currentHitSide) {

		int depth;

		switch (initialHitSide) {

		case NOSE:
			break;
		case AFT:
			break;
		case PORT:
			break;
		case STARBOARD:
			break;
		case TOP:
			break;
		case BOTTOM:
			break;
		}

		return currentHitSide;
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
		appliedDamage += soak - hitLocation.armor;

		if (damage - soak - hitLocation.armor > 0) {
			destroyLocation(ship, hitLocation);
		}
		return damage - soak - hitLocation.armor;
	}

	public static void destroyLocation(Ship ship, HitLocation hitLocation) {
		switch (hitLocation.locationType) {

		case COMPONENT:
			break;
		case ELECTRONICS:
			ship.electronics.destroyElectronics();
			break;
		case FUEL:
			ship.fuel.destroyFuel();
			break;
		case HARDPOINT:
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
