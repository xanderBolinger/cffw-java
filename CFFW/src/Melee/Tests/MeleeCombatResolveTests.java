package Melee.Tests;

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Melee.MeleeCombatUnit;
import Melee.MeleeResolve;
import Trooper.Trooper;
import Unit.Unit;

public class MeleeCombatResolveTests {

	@Test
	public void chargeModifierTest() {
		var resolve = new MeleeResolve();
		var list = new ArrayList<Double>();
		list.add(3.0);
		list.add(2.5);
		list.add(2.0);
		list.add(1.5);
		list.add(1.0);
		list.add(0.5);
		list.add(0.0);
		resolve.calcaulteChargeModifier(list);
		assertEquals(66, resolve.chargeModifier);
	}
	
	@Test
	public void meleeCombatSuccessModifier() {
		var resolve = new MeleeResolve();
		resolve.calculateMeleeSuccessModifier(60);
		assertEquals(40, resolve.meleeSuccessModifier);
		resolve.calculateMeleeSuccessModifier(30);
		assertEquals(30, resolve.meleeSuccessModifier);
		resolve.calculateMeleeSuccessModifier(10);
		assertEquals(15, resolve.meleeSuccessModifier);
		resolve.calculateMeleeSuccessModifier(5);
		assertEquals(10, resolve.meleeSuccessModifier);
		resolve.calculateMeleeSuccessModifier(2.5);
		assertEquals(8, resolve.meleeSuccessModifier);
		resolve.calculateMeleeSuccessModifier(1.5);
		assertEquals(6, resolve.meleeSuccessModifier);
		resolve.calculateMeleeSuccessModifier(0.5);
		assertEquals(3, resolve.meleeSuccessModifier);
		resolve.calculateMeleeSuccessModifier(0.1);
		assertEquals(0, resolve.meleeSuccessModifier);
	}
	
	@Test
	public void formationManager() {
		var resolve = new MeleeResolve();
		MeleeCombatUnit mcu = new MeleeCombatUnit();
		resolve.meleeUnit = mcu;
		mcu.inFormation = true;
		mcu.formationRanks = 1;
		resolve.calculateFormationModifier();
		assertEquals(5, resolve.formationModifier);
	}
	
	@Test
	public void numbersAdvantage() {
		var resolve = new MeleeResolve();
		
		var bluforTroopers = new ArrayList<Trooper>();
		var opforTroopers = new ArrayList<Trooper>();
		
		setTroopers(bluforTroopers, 10);
		setTroopers(opforTroopers, 10);
		resolve.calculateNumbersAdvantageModifier(bluforTroopers, opforTroopers);
		assertEquals(0, resolve.numbersAdvantageModifier);
		
		setTroopers(bluforTroopers, 15);
		setTroopers(opforTroopers, 10);
		resolve.calculateNumbersAdvantageModifier(bluforTroopers, opforTroopers);
		assertEquals(10, resolve.numbersAdvantageModifier);
		
		setTroopers(bluforTroopers, 20);
		setTroopers(opforTroopers, 10);
		resolve.calculateNumbersAdvantageModifier(bluforTroopers, opforTroopers);
		assertEquals(20, resolve.numbersAdvantageModifier);
		
		setTroopers(bluforTroopers, 30);
		setTroopers(opforTroopers, 10);
		resolve.calculateNumbersAdvantageModifier(bluforTroopers, opforTroopers);
		assertEquals(30, resolve.numbersAdvantageModifier);
		
		setTroopers(bluforTroopers, 10);
		setTroopers(opforTroopers, 15);
		resolve.calculateNumbersAdvantageModifier(bluforTroopers, opforTroopers);
		assertEquals(-10, resolve.numbersAdvantageModifier);
		
		setTroopers(bluforTroopers, 10);
		setTroopers(opforTroopers, 20);
		resolve.calculateNumbersAdvantageModifier(bluforTroopers, opforTroopers);
		assertEquals(-20, resolve.numbersAdvantageModifier);
		
		setTroopers(bluforTroopers, 10);
		setTroopers(opforTroopers, 30);
		resolve.calculateNumbersAdvantageModifier(bluforTroopers, opforTroopers);
		assertEquals(-30, resolve.numbersAdvantageModifier);
	}
	
	private void setTroopers(ArrayList<Trooper> troopers, int amount) {
		troopers.clear();
		for(int i = 0; i < amount; i++)  {
			var trooper = new Trooper();
			trooper.meleeCombatSkillLevel = 1;
			troopers.add(trooper);
		}
	}
	
}
