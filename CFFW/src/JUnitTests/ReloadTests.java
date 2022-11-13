package JUnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import CorditeExpansion.ActionOrder;
import Items.Item;
import Trooper.Trooper;

public class ReloadTests {

	private ActionOrder actionOrder; 
	
	@Test 
	public void reloadTest() {
		actionOrder = new ActionOrder();
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		
		actionOrder.addTrooper(clone);
		
		Item magazine1 = clone.ceStatBlock.rangedStatBlock.weapon.ceStats.magazine;
		
		magazine1.ammo.depletionPoints += 2;
		
		assertEquals(magazine1, clone.ceStatBlock.rangedStatBlock.weapon.ceStats.magazine);
		
		clone.ceStatBlock.rangedStatBlock.reload(clone);
		
		Item magazine2 = clone.ceStatBlock.rangedStatBlock.weapon.ceStats.magazine;
		
		assertEquals(true, magazine1 == magazine1);
		
		assertEquals(false, magazine1 == magazine2);
		
		clone.ceStatBlock.rangedStatBlock.weapon.ceStats.magazine.ammo.depletionPoints += 3;
		
		clone.ceStatBlock.rangedStatBlock.reload(clone);
		
		clone.ceStatBlock.rangedStatBlock.weapon.ceStats.magazine.ammo.depletionPoints += 3;
		
		clone.ceStatBlock.rangedStatBlock.reload(clone);
		
		assertEquals(magazine1, clone.ceStatBlock.rangedStatBlock.weapon.ceStats.magazine);
		assertEquals(2, clone.ceStatBlock.rangedStatBlock.weapon.ceStats.magazine.ammo.depletionPoints);
	
	}
	
}
