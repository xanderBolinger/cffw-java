package Melee.Tests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import Melee.Combatant;
import Melee.MeleeCombatFactor;
import Melee.Damage.MeleeHitLocation.MeleeDamageType;
import Trooper.generateSquad;

public class MeleeCombatFactorTests {

	@Test
	public void combatFactorTest() {
		generateSquad squad = new generateSquad("Clone Trooper Phase 1", "Riflesquad");

		var troopers = squad.getSquad();

		var attacker = troopers.get(0);
		attacker.meleeWep = "Chain Sword";
		var target = troopers.get(1);
		target.meleeWep = "Vibroknife";

		var attackerCombatant = new Combatant(attacker);
		var defenderCombatant = new Combatant(target);
		
		// reach 1 
		// atn 4 
		// skill 0 
		// size diff 0 
		// encum 0 
		// fatigue 0 
		// dtn 3
		// 5 - 3 = 2 
		var firstFactor = MeleeCombatFactor.getMeleeCombatFactorAttacker(
				attackerCombatant, 
				defenderCombatant, MeleeDamageType.CUTTING);
		assertEquals(2, firstFactor);
		
		// reach -1 
		// atn 4
		// skill 0 
		// size diff 0 
		// encum 0 
		// fatigue 0
		// dtn 4 
		// 4 - 1 - 4 = -1, returns 1 
		var secondFactor = MeleeCombatFactor.getMeleeCombatFactorAttacker(
				defenderCombatant, 
				attackerCombatant, MeleeDamageType.PEIRICNG);
		assertEquals(1, secondFactor);
	}

}
