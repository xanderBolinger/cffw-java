package JUnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import CorditeExpansion.ActionOrder;
import Trooper.Trooper;

public class CorditeExpansionTests {
	
	private ActionOrder actionOrder; 
	
	@Before
    public void init() {
		actionOrder = new ActionOrder();
    }
	
	@Test
	public void testUnitTest() {
		assertEquals(1, 1);
	}

	@Test 
	public void actionOrderInitTest() {
		
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		Trooper cloneMarksman = new Trooper("Clone Marksman", "Clone Trooper Phase 1");
		Trooper b1 = new Trooper("B1 Rifleman", "CIS Battle Droid");
		
		actionOrder.addTrooper(clone);
		actionOrder.addTrooper(cloneMarksman);
		actionOrder.addTrooper(b1);
		
		assertEquals(3, actionOrder.initOrderSize());
		
		actionOrder.removeTrooper(clone);
		actionOrder.removeTrooper(cloneMarksman);
		actionOrder.removeTrooper(b1);
		
		assertEquals(0, actionOrder.initOrderSize());
		
	}
	

	@Test 
	public void ceStatBlock() {
		
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		
		
		
		
	}
	
	@Test 
	public void actionOrderSortTest() {
		
	}
	
}
