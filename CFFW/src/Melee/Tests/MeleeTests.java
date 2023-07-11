package Melee.Tests;

import org.junit.Test;

import Melee.Bout;
import Melee.Combatant;
import Melee.MeleeCombatResolver;
import Trooper.generateSquad;

public class MeleeTests {

	
	@Test
	public void boutTest() {
		generateSquad squad = new generateSquad("Clone Trooper Phase 1", "Riflesquad");

		var troopers = squad.getSquad();

		var attacker = troopers.get(0);
		attacker.meleeWep = "Chain Sword";
		var target = troopers.get(1);
		target.meleeWep = "Vibroknife";

		var attackerCombatant = new Combatant(attacker);
		var defenderCombatant = new Combatant(target);
		
		var bout = new Bout(attackerCombatant, defenderCombatant);
		
		MeleeCombatResolver.resolveBout(bout);
		
	}
	
}
