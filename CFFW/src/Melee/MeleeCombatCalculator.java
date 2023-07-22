package Melee;

public class MeleeCombatCalculator {

	
	public static void charge(MeleeCombatUnit chargingUnit, MeleeCombatUnit targetUnit) {
		
		// if charging unit charges, 
		// calls enter combat
		
		var chargeResult = chargingUnit.AttemptCharge();
		
		// TODO: Set charge velocities, addcharges here, remove charges else where, keep track of charges in melee combat unit class
		chargingUnit.resolve.calcaulteChargeModifier(null);
		
		if(chargeResult) {
			enterCombat(chargingUnit, targetUnit);
		}
		
	}
	
	public static void enterCombat(MeleeCombatUnit unit1, MeleeCombatUnit unit2) {
		
	}
	
	public static void createBouts() {
		
	}
	
	public static void resolveMeleeCombatRound() {
		
	}

	public static void calculateCombatResults() {
		
	}
	
}
