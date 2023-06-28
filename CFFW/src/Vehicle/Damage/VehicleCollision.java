package Vehicle.Damage;

import Trooper.Trooper;
import UtilityClasses.DiceRoller;

public class VehicleCollision {

	public enum CollisionHitLocation {
		
		Legs,Arms,Body,Head
		
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
	
	public static void applyArmorProtection(Trooper trooper) {
		
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
