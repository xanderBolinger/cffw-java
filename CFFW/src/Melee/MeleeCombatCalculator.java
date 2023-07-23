package Melee;

import HexGrid.HexDirectionUtility;
import HexGrid.HexDirectionUtility.HexDirection;
import UtilityClasses.DiceRoller;

public class MeleeCombatCalculator {

	
	public static void charge(MeleeCombatUnit chargingUnit, MeleeCombatUnit targetUnit) {
		
		// if charging unit charges, 
		// calls enter combat
		var chargeResult = chargingUnit.AttemptCharge();
		
		// TODO: Set charge velocities, addcharges here, remove charges else where, keep track of charges in melee combat unit class
		int speed = DiceRoller.roll(1, 4);
		boolean flanking = HexDirectionUtility.flanking(chargingUnit.facing, targetUnit.facing);
		boolean rear = HexDirectionUtility.rear(chargingUnit.facing, targetUnit.facing);
		var charge = new ChargeData((int)((double)speed * 0.5),
				flanking,rear,false);
		chargingUnit.activeCharges.add(charge);
		var newCharge = new ChargeData(charge);
		newCharge.incoming = true;
		targetUnit.activeCharges.add(newCharge);
		
		chargingUnit.resolve.calcaulteChargeModifier(chargingUnit.activeCharges);
		targetUnit.resolve.calcaulteChargeModifier(targetUnit.activeCharges);

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
