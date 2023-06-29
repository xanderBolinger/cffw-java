package Vehicle.Damage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import Conflict.GameWindow;
import Hexes.Hex;
import Injuries.Injuries;
import Trooper.Trooper;
import UtilityClasses.DiceRoller;
import Vehicle.Vehicle;

public class VehicleCollision {

	public enum CollisionHitLocation {
		
		Legs,Arms,Body,Head
		
	}
	
	public static void hiddenObstaclesCheck(Hex hex, Vehicle vehicle) {
		
		int obstacles = hex.coverPositions / 3;
		
		int hitChance = hiddenObstacleCollisionChance(obstacles);
		
		int roll = DiceRoller.roll(0, 99);
		
		if(roll > hitChance) {
			return;
		}
		
		int impactDamage = getRapidSlowdownImpactDamage(vehicle.movementData.speed);
		
		for(var trooper : vehicle.getTroopers()) {
			int injuries = getNumberOfInjuries();
			for(int i = 0; i < injuries; i++) {
				var collisionHitLocation = CollisionHitLocation.values()
						[DiceRoller.roll(0, CollisionHitLocation.values().length)];
				int hitLocationRoll = getHitLocationZoneOpen(collisionHitLocation);
				int damageCol = getDamageCol(trooper, impactDamage, hitLocationRoll);
				int pd = getInjuryPhysicalDamage(damageCol, collisionHitLocation);
				
				pd = applyShieldProtection(trooper, pd, hitLocationRoll);
				
				if(pd <= 0)
					continue;
				
				var injury = new Injuries(pd, "Rapid Deceleration: "+collisionHitLocation.toString(), 
						(collisionHitLocation == CollisionHitLocation.Arms || collisionHitLocation == CollisionHitLocation.Legs)
						&& damageCol >= 5
						, null);
				trooper.injured(GameWindow.gameWindow.conflictLog, injury, GameWindow.gameWindow.game, GameWindow.gameWindow);
				trooper.calculateInjury(null, null);
			}
		}

	}
	
	public static int hiddenObstacleCollisionChance(int obstacles) {
		if(obstacles == 0) {
			return 0;
		} else if(obstacles == 1) {
			return 13;
		} else if(obstacles == 2) {
			return 25;
		} else if(obstacles == 3) {
			return 35;
		} else if(obstacles == 4) {
			return 44;
		} else if(obstacles == 6) {
			return 58;
		} else if(obstacles == 8) {
			return 68;
		} else if(obstacles == 10) {
			return 76;
		} else if(obstacles == 14) {
			return 86;
		} else if(obstacles == 20) {
			return 94;
		} else if(obstacles == 32) {
			return 98;
		} else {
			return 98;
		}
	}
	
	public static int getRapidSlowdownImpactDamage(int speed) {
		if(speed < 4)
			return DiceRoller.roll(1, 3); 
		else if(speed < 6)
			return DiceRoller.roll(4, 5);  
		else if(speed <= 7)
			return DiceRoller.roll(6, 7);
		else if(speed <= 8)
			return DiceRoller.roll(8, 10);
		else if(speed <= 9)
			return DiceRoller.roll(11, 15);
		else if(speed <= 10)
			return DiceRoller.roll(16, 25);
		else if(speed <= 12)
			return DiceRoller.roll(26, 35);
		else if(speed <= 14)
			return DiceRoller.roll(36, 50);
		else if(speed <= 16)
			return DiceRoller.roll(51, 75);
		else 
			return DiceRoller.roll(76, 99);
		
		
	}
	
	
	public static int getNumberOfInjuries() {
		return DiceRoller.roll(1, 3);
	}
	
	
	public static int getInjuryPhysicalDamage(int col, CollisionHitLocation location) {
		
		var headDamage = new ArrayList<Integer>(Arrays.asList(
				1, 2, 4, 34, 200, 400, 700, 1000, 2000, 4000, 6000));
		var bodyDamage = new ArrayList<Integer>(Arrays.asList(
				1, 2, 3, 6, 11, 18, 27, 37, 61, 100, 200));
		var limbDamage = new ArrayList<Integer>(Arrays.asList(
				1, 1, 2, 4, 8, 15, 19, 23, 43, 72, 100));
		
		switch(location) {
			case Head:
				return headDamage.get(col);
			case Body:
				return bodyDamage.get(col);
			default:
				return limbDamage.get(col);
		}
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
	
	public static int getDamageCol(Trooper trooper, int impactDamage, int hitLocationRoll) {
		if(trooper.armor == null)
			return impactDamage;
		
		int pf = 0; 
		
		pf = trooper.armor.getBPF(hitLocationRoll, true);
		
		var noArmor = new ArrayList<Integer>(Arrays.asList(
				1,2,3,4,5,6,7,8,10,12,14));
		
		if(pf <= 0)
			return getColHelper(impactDamage, noArmor);

		var flexible = new ArrayList<Integer>(Arrays.asList(
				4,7,8,11,15,17,20,24,28,32,39));
		var rigid = new ArrayList<Integer>(Arrays.asList(
				11,22,24,31,40,44,53,62,73,84,99));
		var heavyRigid = new ArrayList<Integer>(Arrays.asList(
				44,46,53,56,60,67,73,78,84,90,99));
		
		if(!trooper.armor.isHardBodyArmor(hitLocationRoll, true)) {
			return getColHelper(impactDamage, flexible);
		} else if(pf >= 30){
			return getColHelper(impactDamage, heavyRigid);
		} else {
			return getColHelper(impactDamage, rigid);
		}
	}
	
	private static int getColHelper(int id, ArrayList<Integer> list) {
		return IntStream.range(0, list.size())
		        .filter(x -> id <= list.get(x))
		        .findFirst().getAsInt();
	}
	
	public static int getHitLocationZoneOpen(CollisionHitLocation location) {
		
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
