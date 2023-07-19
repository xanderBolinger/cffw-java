package Melee;

import java.io.Serializable;
import java.util.ArrayList;

import Melee.Damage.MeleeDamage;
import Melee.Damage.MeleeHitLocation.MeleeDamageType;
import UtilityClasses.DiceRoller;

public class MeleeCombatResolver implements Serializable {

	// Should be robust enough to be a stand alone wargame revolving around mass melee combat
	
	// melee's last for shorts amount of time 
	// individuals gain large amounts of fatigue
	// In hand to hand combat there are low 2-3% casualty ratings,
	// Only after one side breaks formation does that casualty rate jump to 30-60%
	// Only a small number of individuals enter bouts with each other 
	// Formations move together and break apart before charges are reinitiated 
	// Battles were highly mobile 
	// there systems for replacing wounded or fatigued soldiers 


	// How is combat going to work?
	
	// Units in the same hex with each other can fight in melee combat
	// Units have a facing 
	// charges to the flank or rear are most demoralizing
	
	// Units have a resolve factor 
	// The resolve factor is tested when a unit spends an action in melee combat 
	
	// In order to charge, a unit must make a successful command check
	
	// Aggressive commanders make units better at charging and breaking the enemy 
	// Standard bearers increase unit cohesion and make changing formations easier
	public static void resolveBout(Bout bout) {
		MeleeDamageType firstDamageType = getMeleeDamageType(bout.combatantA);
		MeleeDamageType secondDamageType = getMeleeDamageType(bout.combatantB);
		
		int firstCombatFactor = MeleeCombatFactor.getMeleeCombatFactorAttacker(bout.combatantA, bout.combatantB, firstDamageType);
		int secondCombatFactor = MeleeCombatFactor.getMeleeCombatFactorAttacker(bout.combatantB, bout.combatantA, secondDamageType);
		
		double ratio = (double)firstCombatFactor / (double)secondCombatFactor;

		int aHits = 0;
		int bHits = 0;
		
		if(ratio <= 0.25) { // 3%, 50% for 2
			aHits += d100() <= 3 ? 1 : 0;
			bHits += d100() <= 50 ? 1 : 0;
			bHits += d100() <= 50 ? 1 : 0;
		} else if(ratio <= 0.5) { // 10%, 50% for 2
			aHits += d100() <= 10 ? 1 : 0;
			bHits += d100() <= 50 ? 1 : 0;
			bHits += d100() <= 50 ? 1 : 0;
		} else if(ratio <= 1.0) { // 33%, 66%
			aHits += d100() <= 33 ? 1 : 0;
			bHits += d100() <= 66 ? 1 : 0;
		} else if(ratio <= 1.5) { // 66%, 33%
			aHits += d100() <= 66 ? 1 : 0;
			bHits += d100() <= 33 ? 1 : 0;
		} else if(ratio <= 2) { // 50% for 2, 10%
			aHits += d100() <= 50 ? 1 : 0;
			aHits += d100() <= 50 ? 1 : 0;
			bHits += d100() <= 10 ? 1 : 0;
		} else { // 50% for 2, 3%
			aHits += d100() <= 50 ? 1 : 0;
			aHits += d100() <= 50 ? 1 : 0;
			bHits += d100() <= 3 ? 1 : 0;
		}
		
		applyHits(bout, true, aHits, firstDamageType);
		applyHits(bout, false, bHits, secondDamageType);
		
	}
	
	private static void applyHits(Bout bout, boolean firstCombatantAttacking, int hits, MeleeDamageType dmgType) {
		for(int i = 0; i < hits; i++) {
			try {
				MeleeDamage.applyMeleeHit(firstCombatantAttacking ? bout.combatantA : bout.combatantB, 
						firstCombatantAttacking ? bout.combatantB : bout.combatantA, DiceRoller.roll(1, 5), dmgType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static int d100() {
		return DiceRoller.roll(1, 100);
	}
	
	private static MeleeDamageType getMeleeDamageType(Combatant combatant) {
		if(combatant.meleeWeapon == null)
			return MeleeDamageType.BLUNT;
		var wep = combatant.meleeWeapon;
		ArrayList<MeleeDamageType> options = new ArrayList<MeleeDamageType>();
		
		if(wep.attackTargetNumberCut != 0) 
			options.add(MeleeDamageType.CUTTING);
		if(wep.attackTargetNumberBash != 0) 
			options.add(MeleeDamageType.BLUNT);
		if(wep.attackTargetNumberStab != 0) 
			options.add(MeleeDamageType.PEIRICNG);
		
		return options.get(DiceRoller.roll(0, options.size()-1));
		
	}
}
