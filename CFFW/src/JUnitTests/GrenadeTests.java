package JUnitTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CorditeExpansion.ActionOrder;
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
    }
	
	
	@Test
	public void explosionTest() {
		
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		clone.armor = new Armor();
		actionOrder.addTrooper(clone);
		Damage.explode(new Weapons().findWeapon("RGD-5"), 0, clone);
		
		/*for(String injury : clone.ceStatBlock.medicalStatBlock.getInjuries()) {
			System.out.println(injury);
		}*/
		
	}
	
	
}
