package Melee;

import java.util.ArrayList;

import Conflict.GameWindow;
import HexGrid.HexDirectionUtility;
import HexGrid.HexDirectionUtility.HexDirection;
import Melee.Window.MeleeCombatWindow;
import Trooper.Trooper;
import UtilityClasses.DiceRoller;

public class MeleeCombatCalculator {

	
	public static void charge(MeleeCombatUnit chargingUnit, MeleeCombatUnit targetUnit) {
		
		if(chargingUnit.charged && GameWindow.gameWindow != null) {
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(chargingUnit.unit.side + ":: "+chargingUnit.unit.callsign+" already charged this turn.");
			return;
		}
		
		chargingUnit.charged = true;
		
		
		
		// TODO: Set charge velocities, addcharges here, remove charges else where, keep track of charges in melee combat unit class
		int speed = DiceRoller.roll(1, 4);
		//boolean flanking = HexDirectionUtility.flanking(chargingUnit.facing, targetUnit.facing);
		//boolean rear = HexDirectionUtility.rear(chargingUnit.facing, targetUnit.facing);
		var charge = new ChargeData((int)((double)speed * 0.5),
				false,false,false);
		chargingUnit.activeCharges.add(charge);
		var newCharge = new ChargeData(charge);
		newCharge.incoming = true;
		targetUnit.activeCharges.add(newCharge);

		chargingUnit.resolve.baseMeleeResolve = chargingUnit.unit.commandValue * 10;
		chargingUnit.resolve.calculateSuppressionModifier(chargingUnit.unit);
		chargingUnit.resolve.calculateFatigueModifier(chargingUnit.unit);
		chargingUnit.resolve.calculateNumbersAdvantageModifier(chargingUnit.unit.individuals,targetUnit.unit.individuals);
		chargingUnit.resolve.calcaulteChargeModifier(chargingUnit.activeCharges);
		targetUnit.resolve.calcaulteChargeModifier(targetUnit.activeCharges);

		// if charging unit charges, 
		// calls enter combat
		var chargeResult = chargingUnit.AttemptCharge();
		
		if(chargeResult) {
			enterCombat(chargingUnit, targetUnit);
		}
		
		if(GameWindow.gameWindow != null)
			GameWindow.gameWindow.conflictLog.addNewLineToQueue(chargingUnit.unit.side + ":: "+chargingUnit.unit.callsign+" charges "+
					targetUnit.unit.side + ":: "+targetUnit.unit.callsign+", "+(chargeResult ? "CHARGE SUCCESS" : "CHARGE FAIL")+", "
					+"flanking: "+false+", rear: "+false);
		
	}
	
	public static void enterCombat(MeleeCombatUnit unit1, MeleeCombatUnit unit2) {
		if(!MeleeManager.meleeManager.unitsInCombat.contains(unit1)) 
			MeleeManager.meleeManager.unitsInCombat.add(unit1);
		if(!MeleeManager.meleeManager.unitsInCombat.contains(unit2)) 
			MeleeManager.meleeManager.unitsInCombat.add(unit2);
		
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
		
		MeleeManager.meleeManager.meleeCombatBouts.add(new Bout(combatantA, combatantB));
		
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
		for(var b : MeleeManager.meleeManager.meleeCombatBouts) {
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
		for(var b : MeleeManager.meleeManager.meleeCombatBouts) {
			b.combatantA.trooper.fatigueSystem.AddStrenuousActivityTime(60);
			b.combatantB.trooper.fatigueSystem.AddStrenuousActivityTime(60);
		}

		// refresh units 
		for(var u : MeleeManager.meleeManager.meleeCombatUnits) {
			u.charged = false;
		}
		
		if(MeleeCombatWindow.meleeCombatWindow != null) {
			var w = MeleeCombatWindow.meleeCombatWindow;
			w.setBouts();
			w.setChargeTargets();
			w.setMeleeCombatIndividuals();
		}
		
		
	}

	public static void calculateCombatResults() {
		removeBoutsCheck();
		for(var b : MeleeManager.meleeManager.meleeCombatBouts)
			MeleeCombatResolver.resolveBout(b);
		removeBoutsCheck();
	}
	
	private static void removeBoutsCheck() {
		var bouts = new ArrayList<Bout>(MeleeManager.meleeManager.meleeCombatBouts);
		for(var b : bouts)
			removeBout(b);
	}
	
	private static void removeBout(Bout bout) {
		if((!bout.combatantA.trooper.conscious || !bout.combatantA.trooper.alive || 
				!bout.combatantB.trooper.conscious || !bout.combatantB.trooper.alive)
				&& MeleeManager.meleeManager != null) {
			
			MeleeManager.meleeManager.meleeCombatBouts.remove(bout);
			
		}
	}
	
}
