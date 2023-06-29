package Vehicle.Damage;

import Hexes.Hex;
import Trooper.Trooper;
import UtilityClasses.DiceRoller;

public class VehicleCollision {

	public enum CollisionHitLocation {
		
		Legs,Arms,Body,Head
		
	}
	
	public static void HiddenObstaclesCheck(Hex hex) {
		
		
	}
	
	public static int HiddenObstacleCollisionChance() {
		
	}
	
	public static int getRapidSlowdownMagnitude(int speed) {
		if(speed < 4)
			return 1; 
		else if(speed < 6)
			return 2; 
		else if(speed <= 7)
			return 3;
		else if(speed <= 8)
			return 4;
		return 5;
	}
	
	
	public static int getNumberOfInjuries() {
		return DiceRoller.roll(1, 3);
	}
	
	
	// TODO: Replace with melee blunt hit table when that table is finished (which it mostly is) and is integrated into this java applet for melee
	public static int getInjuryPhysicalDamage(int magnitude) {
		if(magnitude == 1)
			return DiceRoller.roll(1, 20);
		else if(magnitude == 2)
			return 70;
		else if(magnitude == 3)
			return 150;
		else if(magnitude == 4)
			return 300; 
		else 
			return 500;
	}
	
	public static int applyShieldProtection(Trooper trooper, int physicalDamage, int hitLocationOpen) {
		
		var shield = trooper.personalShield;
		
		if(shield == null || !shield.isZoneProtected(hitLocationOpen, true))
			return physicalDamage;
		
		int returnDamage = physicalDamage - shield.currentShieldStrength * 10;
		
		if(returnDamage <= 0) {
			shield.currentShieldStrength -= physicalDamage / 10;
			return 0;
		}
		else {
			shield.currentShieldStrength = 0;
			return returnDamage;
		}
		
	}
	
	// TODO: make return PF to armor value for blunt table 
	public static int applyArmorProtection(Trooper trooper, int damageMagnitude, int hitLocationRoll) {
		if(trooper.armor == null)
			return damageMagnitude;
		
		int pf = 0; 
		
		pf = trooper.armor.getBPF(hitLocationRoll, true);
		
		return damageMagnitude - pf / 10;
	}
	
	
	public static int hitLocationZoneOpen(CollisionHitLocation location) {
		
		switch(location) {
		
		case Legs:
			return DiceRoller.roll(57, 99);
		case Body:
			return DiceRoller.roll(18, 56);
		case Arms:
			return DiceRoller.roll(8, 16);
		default: // head
			return DiceRoller.roll(0, 7);
		}
		
	}
	
	
}
