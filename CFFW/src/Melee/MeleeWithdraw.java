package Melee;

import java.util.ArrayList;

import Trooper.Trooper;

public class MeleeWithdraw {

	
	public static void withdraw(ArrayList<Bout> bouts, ArrayList<Trooper> individualsRunning) {
		
		
		for(var bout : bouts) {
			
			boolean combatantARunning = individualsRunning.contains(bout.combatantA.trooper);
			boolean combatantBRunning = individualsRunning.contains(bout.combatantB.trooper);
			
			if(combatantARunning && combatantBRunning) {
				System.err.println("Both combatants running, this is strange.");
			} else if(combatantARunning) {
				MeleeCombatResolver.applyHits(bout, false, 1, MeleeCombatResolver.getMeleeDamageType(bout.combatantB));
			} else if(combatantBRunning) {
				MeleeCombatResolver.applyHits(bout, true, 1, MeleeCombatResolver.getMeleeDamageType(bout.combatantA));
			}
			
			MeleeManager.meleeManager.meleeCombatBouts.remove(bout);
			
		}
		
	}
	
}
