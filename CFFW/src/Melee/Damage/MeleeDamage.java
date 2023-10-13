package Melee.Damage;

import java.io.Serializable;

import org.apache.commons.math3.util.Pair;

import Conflict.GameWindow;
import Conflict.InjuryLog;
import Injuries.Injuries;
import Items.Weapons;
import Melee.Combatant;
import Melee.Damage.MeleeHitLocation.MeleeDamageType;
import UtilityClasses.DiceRoller;

public class MeleeDamage implements Serializable {

	public static void applyMeleeHit(Combatant attacker, Combatant target, int attackMarginOfSuccess,
			MeleeDamageType damageType) throws Exception {

		Weapons meleeWeaponStats = new Weapons().findWeapon(attacker.trooper.meleeWep);
		
		int str = attacker.trooper.str / 3;
		int to = target.trooper.hlt / 3;
		
		int weaponMod = -2;
		int weapopnApMod = -2;
		int weaponMultiplier = 2;

		if(meleeWeaponStats != null && meleeWeaponStats.meleeWeapon != null) {
			var meleeWeapon = meleeWeaponStats.meleeWeapon;
			switch (meleeWeapon.weaponSize) {
			case GARGANTUAN:
				weaponMultiplier = 10;
				break;
			case HEAVY:
				weaponMultiplier = 4;
				break;
			case HYPER_LETHAL:
				weaponMultiplier = 6;
				break;
			case LIGHT:
				weaponMultiplier = 2;
				break;
			case MEDIUM:
				weaponMultiplier = 3;
				break;
			default:
				throw new Exception("Weapon size not accounted for: " + meleeWeapon.weaponSize);

			}

			switch (damageType) {
			case BLUNT:
				weaponMod = meleeWeapon.bashMod;
				weapopnApMod = meleeWeapon.bashApMod;
				break;
			case CUTTING:
				weaponMod = meleeWeapon.cutMod;
				weapopnApMod = meleeWeapon.cutApMod;
				break;
			case PEIRICNG:
				weaponMod = meleeWeapon.stabMod;
				weapopnApMod = meleeWeapon.stabApMod;
				break;
			default:
				throw new Exception("Damage type not found for dmg type: " + damageType);

			}
		}

		int zone = DiceRoller.roll(1, 7);
		int subZoneRoll = DiceRoller.roll(1, 6);

		var tempRslts = MeleeHitLocation.GetHitLocationResults(damageType, 1, 1, zone, subZoneRoll, 0);
		var anatomicalHitLocation = tempRslts.getSecond().hitLocationName;
		var armorResults = getAv(damageType, target, anatomicalHitLocation);

		int damagePoints = attackMarginOfSuccess * weaponMultiplier + weaponMod + str - to - (armorResults.getFirst()
				- ((weapopnApMod - armorResults.getSecond()) < 0 ? 0 : (weapopnApMod - armorResults.getSecond())));

		int dmgLevel = getDamageLevel(target, damagePoints);
		
		var hitRslts = MeleeHitLocation.GetHitLocationResults(damageType, damagePoints, dmgLevel, zone, subZoneRoll,
				((weapopnApMod - armorResults.getSecond()) < 0 ? 0 : (weapopnApMod - armorResults.getSecond())));

		Injuries injury = new Injuries(hitRslts.getFirst().bloodLossPD+hitRslts.getSecond().physicalDamage, 
				hitRslts.getSecond().hitLocationName,hitRslts.getSecond().disabled, meleeWeaponStats);
		
		injury.shockPd = hitRslts.getFirst().shockPD;
		
		target.shock += hitRslts.getFirst().shockPD/20;
		
		if(GameWindow.gameWindow != null && GameWindow.gameWindow.conflictLog != null) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(target.trooper.number+", "+target.trooper.name+" is wounded:");
			target.trooper.injured(GameWindow.gameWindow.conflictLog, injury, GameWindow.gameWindow.game, GameWindow.gameWindow);
			target.trooper.calculateInjury(GameWindow.gameWindow, GameWindow.gameWindow.conflictLog);
		} else {
			System.err.println("Gamewindow is null for adding melee injury");
		}
		
		if(InjuryLog.InjuryLog != null)
			InjuryLog.InjuryLog.addTrooper(target.trooper);
	}

	public static int getDamageLevel(Combatant target, int damagePoints) {

		if (target.trooper.PCSize < 1) {
			System.err.println("Target PC size less than 1: " + target.trooper.PCSize);
			if(damagePoints <= 1) {
				return 1;
			} else if(damagePoints <= 2) {
				return 2;
			} else if(damagePoints <= 3) {
				return 3; 
			} else if(damagePoints <= 4) {
				return 4; 
			} else {
				return 5; 
			}
		} else if (target.trooper.PCSize < 2.0) {
			if(damagePoints <= 2) {
				return 1;
			} else if(damagePoints <= 4) {
				return 2;
			} else if(damagePoints <= 6) {
				return 3; 
			} else if(damagePoints <= 8) {
				return 4; 
			} else {
				return 5; 
			}
		} else if (target.trooper.PCSize < 2.5) {
			if(damagePoints <= 3) {
				return 1;
			} else if(damagePoints <= 6) {
				return 2;
			} else if(damagePoints <= 9) {
				return 3; 
			} else if(damagePoints <= 12) {
				return 4; 
			} else {
				return 5; 
			}
		} else if (target.trooper.PCSize < 4) {
			if(damagePoints <= 4) {
				return 1;
			} else if(damagePoints <= 8) {
				return 2;
			} else if(damagePoints <= 12) {
				return 3; 
			} else if(damagePoints <= 16) {
				return 4; 
			} else {
				return 5; 
			}
		} else if (target.trooper.PCSize < 6) {
			if(damagePoints <= 5) {
				return 1;
			} else if(damagePoints <= 10) {
				return 2;
			} else if(damagePoints <= 15) {
				return 3; 
			} else if(damagePoints <= 20) {
				return 4; 
			} else {
				return 5; 
			}
		} else {
			if(damagePoints <= 6) {
				return 1;
			} else if(damagePoints <= 12) {
				return 2;
			} else if(damagePoints <= 18) {
				return 3; 
			} else if(damagePoints <= 24) {
				return 4; 
			} else {
				return 5; 
			}
		}

	}

	public static  Pair<Integer, Integer> getAv(MeleeDamageType damageType, Combatant target, String hitZone) {

		int armorValue = 0;
		int damageTypeMod = 0;

		if (target.trooper.armor != null && target.trooper.armor.meleeArmorStats != null) {
			for (var armor : target.trooper.armor.meleeArmorStats) {
				if (armor.protectedBodyParts.contains(hitZone)) {
					armorValue += armor.armorValue;
					switch (damageType) {
					case BLUNT:
						damageTypeMod += armor.bluntMod;
						break;
					case CUTTING:
						damageTypeMod += armor.cutMod;
						break;
					case PEIRICNG:
						damageTypeMod += armor.stabMod;
						break;
					default:
						break;

					}
				}
			}
		}

		return new Pair<Integer, Integer>(armorValue, damageTypeMod);
	}

}
