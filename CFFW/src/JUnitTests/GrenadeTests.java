package JUnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CorditeExpansion.ActionOrder;
import CorditeExpansion.Cord;
import CorditeExpansion.CorditeExpansionGame;
import CorditeExpansionActions.CeAction;
import CorditeExpansionActions.CeAction.ActionType;
import CorditeExpansionDamage.Damage;
import Items.Armor;
import Items.Weapons;
import Trooper.Trooper;
import UtilityClasses.DiceRoller;

public class GrenadeTests {

	ActionOrder actionOrder; 
	
	@Before
    public void init() {
		actionOrder = new ActionOrder();
		DiceRoller.initTesting();
    }
	
	@After
    public void finish() {
		DiceRoller.finishTesting();
		actionOrder.clear();
    }
	
	
	@Test
	public void explosionTest() {
		
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		clone.armor = new Armor();
		actionOrder.addTrooper(clone);
		Damage.explode(new Weapons().findWeapon("RGD-5"), 0, clone);
		
		CorditeExpansionGame.actionOrder = actionOrder;
		
		/*for(String injury : clone.ceStatBlock.medicalStatBlock.getInjuries()) {
			System.out.println(injury);
		}*/
		
		CeAction.addThrowAction(clone, new Weapons().findWeapon("RGD-5"), new Cord(1,1));
		
		// Coac 
		CorditeExpansionGame.action();
		CorditeExpansionGame.action();
		
		assertEquals(true, clone.ceStatBlock.acting());
		
		// Arm 
		CorditeExpansionGame.action();
		CorditeExpansionGame.action();
		// Toss 
		CorditeExpansionGame.action();
		
		// Detonate
		CorditeExpansionGame.action();
		CorditeExpansionGame.action();
		
		CorditeExpansionGame.actionOrder.clear();
	}
	
	
}
