package Melee;

import java.util.ArrayList;

import HexGrid.HexDirectionUtility;
import HexGrid.HexDirectionUtility.HexDirection;
import Trooper.Trooper;
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
		if(!MeleeManager.unitsInCombat.contains(unit1)) 
			MeleeManager.unitsInCombat.add(unit1);
		if(!MeleeManager.unitsInCombat.contains(unit2)) 
			MeleeManager.unitsInCombat.add(unit2);
		
		createBouts(unit1, unit2);
	}
	
	public static void createBouts(MeleeCombatUnit unit1, MeleeCombatUnit unit2) {

		var firstTroopersUnitOne = getFirstTroopers(unit1);
		var secondTroopersUnitOne = getSecondTroopers(unit1);
		var firstTroopersUnitTwo  = getFirstTroopers(unit2);
		var secondTroopersUnitTwo = getSecondTroopers(unit2);
		
		
		for(int i = 0; i < unit1.formationWidth; i++) {
			if(i < firstTroopersUnitOne.size()) {
				createBout(firstTroopersUnitOne.get(i), i, unit2,
						firstTroopersUnitTwo, secondTroopersUnitTwo);
			} else if(i - firstTroopersUnitOne.size() 
					< secondTroopersUnitOne.size()) {
				createBout(secondTroopersUnitOne.get(i - firstTroopersUnitOne.size()), 
						i - firstTroopersUnitOne.size(), unit2,
						firstTroopersUnitTwo, secondTroopersUnitTwo);
			}
		}
		
	}
	
	private static void createBout(Trooper trooper, int index, MeleeCombatUnit targetUnit, 
			ArrayList<Trooper> firstTargets, ArrayList<Trooper> secondTargets) {
		
		Combatant combatantA = new Combatant(trooper);
		Combatant combatantB;
		if(index < firstTargets.size()) {
			combatantB = new Combatant(firstTargets.get(index));
		} else if(index - firstTargets.size() < secondTargets.size()) {
			combatantB = new Combatant(secondTargets.get(index-firstTargets.size()));
		} else {
			combatantB = new Combatant(targetUnit.meleeCombatIndividuals.get(DiceRoller.roll(0,
					targetUnit.meleeCombatIndividuals.size() - 1)));
		}
		
		MeleeManager.meleeCombatBouts.add(new Bout(combatantA, combatantB));
		
	}
	
	private static ArrayList<Trooper> getSecondTroopers(MeleeCombatUnit unit) {
		var secondTroopersUnitOne = new ArrayList<Trooper>();
		for(Trooper trooper : unit.meleeCombatIndividuals) {
			int trooperBouts = trooperBouts(trooper);
			if(trooperBouts == 1)
				secondTroopersUnitOne.add(trooper);
		}
		return secondTroopersUnitOne;
	}
	
	private static ArrayList<Trooper> getFirstTroopers(MeleeCombatUnit unit) {
		var firstTroopersUnitOne = new ArrayList<Trooper>();
		for(Trooper trooper : unit.meleeCombatIndividuals) {
			int trooperBouts = trooperBouts(trooper);
			if(trooperBouts == 0)
				firstTroopersUnitOne.add(trooper);
		}
		return firstTroopersUnitOne;
	}
	
	
	public static int trooperBouts(Trooper trooper) {
		int count = 0;
		for(var b : MeleeManager.meleeCombatBouts) {
			if(trooper.compareTo(b.combatantA.trooper) 
					|| trooper.compareTo(b.combatantB.trooper))
				count++;
		}
		return count;
	} 
	
	public static void resolveMeleeCombatRound() {
		
		// Resolve bouts 
		calculateCombatResults();
		
		// Add fatigue 
		for(var b : MeleeManager.meleeCombatBouts) {
			b.combatantA.trooper.fatigueSystem.AddStrenuousActivityTime(60);
			b.combatantB.trooper.fatigueSystem.AddStrenuousActivityTime(60);
		}
		
	}

	public static void calculateCombatResults() {
		for(var b : MeleeManager.meleeCombatBouts)
			MeleeCombatResolver.resolveBout(b);
	}
	
}
