package JUnitTests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import CorditeExpansion.ActionOrder;
import CorditeExpansion.CeAction;
import CorditeExpansion.Cord;
import CorditeExpansion.MoveAction;
import CorditeExpansion.MoveAction.MoveType;
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

		actionOrder.addTrooper(clone);

		assertEquals(clone.maximumSpeed.get(), actionOrder.get(clone).ceStatBlock.quickness, 0);
				
		actionOrder.removeTrooper(clone);

	}
	
	@Test 
	public void actionOrderSortTest() {
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		clone.ceStatBlock.quickness = 3; 
		
		Trooper cloneMarksman = new Trooper("Clone Marksman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(cloneMarksman);
		cloneMarksman.ceStatBlock.quickness = 2; 
		
		Trooper b1 = new Trooper("B1 Rifleman", "CIS Battle Droid");
		actionOrder.addTrooper(b1);
		b1.ceStatBlock.quickness = 1; 
		
		actionOrder.sort();
		
		assertEquals(clone, actionOrder.get(0));
		assertEquals(cloneMarksman, actionOrder.get(1));
		assertEquals(b1, actionOrder.get(2));
		
		actionOrder.clear();
		
	}
	
	@Test
	public void moveTest() {
		
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		
		ArrayList<Cord> cords = new ArrayList<>();
		cords.add(new Cord(1,1));
		
		CeAction.addMoveAction(MoveType.STEP, clone.ceStatBlock, cords);

		clone.ceStatBlock.spendCombatAction();
		
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,1)));
		assertEquals(false, clone.ceStatBlock.getPosition().compare(new Cord(1,0)));
		assertEquals(0, clone.ceStatBlock.actionsSize());
		
		cords = new ArrayList<>();
		cords.add(new Cord(1,2));
		cords.add(new Cord(1,3));
		CeAction.addMoveAction(MoveType.STEP, clone.ceStatBlock, cords);
		
		clone.ceStatBlock.spendCombatAction();
		
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,2)));
		
		MoveAction moveAction = (MoveAction) clone.ceStatBlock.getActions(0);
		moveAction.addTargetHex(new Cord(1,4));
		
		clone.ceStatBlock.spendCombatAction();
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,3)));
		
		cords = new ArrayList<>();
		cords.add(new Cord(1,5));
		cords.add(new Cord(1,6));
		moveAction = (MoveAction) clone.ceStatBlock.getActions(0);
		moveAction.addTargetHexes(cords);
		
		clone.ceStatBlock.spendCombatAction();
		System.out.println(clone.ceStatBlock.getPosition());
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,4)));
		
		clone.ceStatBlock.spendCombatAction();
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,5)));
		
		clone.ceStatBlock.spendCombatAction();
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,6)));
	}
	
}
