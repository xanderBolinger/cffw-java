package Melee;

import java.io.Serializable;

import org.apache.commons.math3.util.Pair;

import Items.Weapons;
import Melee.Damage.MeleeHitLocation.MeleeDamageType;
import Trooper.Trooper;

public class MeleeCombatFactor implements Serializable {

	// Resolve combat
	// Reach
	// PC size
	// ATN
	// Skil
	// - DTN
	// - Shield encumberance penalty
	// - Armor encumberence penalty
	// - Injuries
	// - Fatigue
	// /= by number of bouts combatant is in
	public static int getMeleeCombatFactorAttacker(Combatant combatant, Combatant target, MeleeDamageType meleeDamageType) {
		int sizeDiff = (int)((combatant.trooper.PCSize - target.trooper.PCSize)/0.5);
		int aReach = combatant.meleeWeapon != null ? combatant.meleeWeapon.reach : 0;
		int bReach = target.meleeWeapon != null ? target.meleeWeapon.reach : 0;
		
		int atnMod = combatant.meleeWeapon != null ? 10 - combatant.meleeWeapon.getAtn(meleeDamageType) : 0;
		int tempDtn = (target.meleeShield != null ? 10 - target.meleeShield.dtn : 0);
		int dtnMod = tempDtn < 3 ? 3 : tempDtn;
		int reachMod = aReach - bReach;
		var encum = getEncumberanceFactor(combatant.trooper);
		var fatigue = getFatiguePenalty(combatant.trooper);
		
		int tempFactor = combatant.trooper.meleeCombatSkillLevel + atnMod + sizeDiff + reachMod - encum - fatigue - dtnMod;
		return tempFactor < 1 ? 1 : tempFactor;
	}
	
	private static int getEncumberanceFactor(Trooper trooper) {
		
		var ms = trooper.meleeShield;
		
		int armorFactor = 0; 
		
		if(trooper.armor != null && trooper.armor.meleeArmorStats != null) {
			for(var armor : trooper.armor.meleeArmorStats) {
				armorFactor += armor.encumberencePenalty;
			}
		}
		
		return armorFactor +
				( ms != null && ms.meleeShield != null ? ms.meleeShield.encumberancePenalty : 0 );
	}
	
	private static int getFatiguePenalty(Trooper trooper) {
		double fatiguePoints = trooper.fatigueSystem.fatiguePoints.get();
		
		if(fatiguePoints < 5) {
			return 0;
		}
		else if(fatiguePoints < 11) {
			return 1;
		} else if(fatiguePoints <= 15) {
			return 2;
		} else if(fatiguePoints <= 19 ) {
			return 3;
		} else if(fatiguePoints <= 23 ) {
			return 4;
		} else if(fatiguePoints <= 27 ) {
			return 5;
		} else if(fatiguePoints <= 31 ) {
			return 6;
		} else if(fatiguePoints <= 32 ) {
			return 7;
		} else if(fatiguePoints <= 33 ) {
			return 8;
		} else if(fatiguePoints <= 34 ) {
			return 9;
		} else if(fatiguePoints > 34) {
			return (int)(9 + fatiguePoints - 34);
		}
		
		return 0;
	}

	private static int numberOfBouts(Combatant combatant) {
		int bouts = 0; 
		
		for(var bout : MeleeManager.meleeCombatBouts) {
			if(bout.combatantA.trooper.compareTo(combatant.trooper))
				bouts++;
			else if(bout.combatantB.trooper.compareTo(combatant.trooper))
				bouts++;
		}
		
		if(bouts == 0)
			System.err.println("Number of bouts combatant is in is equal to 0: "+combatant.trooper.name);
		
		return bouts; 
	}
	
}
