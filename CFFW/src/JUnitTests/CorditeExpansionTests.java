package JUnitTests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import CorditeExpansion.ActionOrder;
import CorditeExpansion.AimAction;
import CorditeExpansion.CeAction;
import CorditeExpansion.CeStatBlock;
import CeHexGrid.Chit.Facing;
import CorditeExpansion.Cord;
import CorditeExpansion.CorditeExpansionGame;
import CorditeExpansion.MoveAction;
import CorditeExpansion.CeStatBlock.MoveSpeed;
import CorditeExpansion.CeStatBlock.Stance;
import Trooper.Trooper;
import UtilityClasses.ExcelUtility;

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
		clone.ceStatBlock.quickness = 6; 
		
		ArrayList<Cord> cords = new ArrayList<>();
		cords.add(new Cord(1,1));
		
		CeAction.addMoveAction(clone.ceStatBlock, cords, 0);

		clone.ceStatBlock.spendCombatAction();
		
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,1)));
		assertEquals(false, clone.ceStatBlock.getPosition().compare(new Cord(1,0)));
		assertEquals(false, clone.ceStatBlock.acting());
		
		cords = new ArrayList<>();
		cords.add(new Cord(1,2));
		cords.add(new Cord(1,3));
		CeAction.addMoveAction(clone.ceStatBlock, cords, 0);
		
		clone.ceStatBlock.spendCombatAction();
		
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,2)));
		
		MoveAction moveAction = (MoveAction) clone.ceStatBlock.getAction();
		moveAction.addTargetHex(new Cord(1,4));
		
		clone.ceStatBlock.spendCombatAction();
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,3)));
		
		cords = new ArrayList<>();
		cords.add(new Cord(1,5));
		cords.add(new Cord(1,6));
		cords.add(new Cord(1,7));
		moveAction = (MoveAction) clone.ceStatBlock.getAction();
		moveAction.addTargetHexes(cords);
		
		clone.ceStatBlock.spendCombatAction();
		System.out.println(clone.ceStatBlock.getPosition());
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,4)));
		
		clone.ceStatBlock.spendCombatAction();
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,5)));
		assertEquals(5, clone.ceStatBlock.totalMoved);
		clone.ceStatBlock.spendCombatAction();
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,6)));
		
		clone.ceStatBlock.spendCombatAction();
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,6)));
		
		actionOrder.clear();
	}
	
	@Test
	public void crawlTest() {
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		
		clone.ceStatBlock.moveSpeed = MoveSpeed.CRAWL;
		
		ArrayList<Cord> cords = new ArrayList<>();
		cords.add(new Cord(1,1));
		
		CeAction.addMoveAction(clone.ceStatBlock, cords, 0);
		
		clone.ceStatBlock.spendCombatAction();
		assertEquals(false, clone.ceStatBlock.getPosition().compare(new Cord(1,1)));
		
		clone.ceStatBlock.spendCombatAction();
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,1)));
		
		actionOrder.clear();
		
	}
	
	@Test
	public void coacTest() {
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		
		ArrayList<Cord> cords = new ArrayList<>();
		cords.add(new Cord(1,1));
		
		CeAction.addMoveAction(clone.ceStatBlock, cords, 2);
		
		assertEquals(false, clone.ceStatBlock.acting());
		assertEquals(1, clone.ceStatBlock.coacSize());
		
		clone.ceStatBlock.prepareCourseOfAction();
		clone.ceStatBlock.prepareCourseOfAction();
		
		assertEquals(true, clone.ceStatBlock.acting());
		assertEquals(0, clone.ceStatBlock.coacSize());
		
		clone.ceStatBlock.spendCombatAction();
		
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,1)));
		assertEquals(false, clone.ceStatBlock.acting());
		assertEquals(0, clone.ceStatBlock.coacSize());
		
		actionOrder.clear();
		
	}
	
	@Test
	public void facingValueTest() {
		

		Facing facing = Facing.A;
		
		assertEquals(Facing.AB, Facing.turnClockwise(facing));
		
		assertEquals(Facing.FA, Facing.turnCounterClockwise(facing));
		

	}
	
	@Test
	public void facingTest() {
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		
		CeAction.addTurnAction(clone.ceStatBlock, true);
		
		clone.ceStatBlock.spendCombatAction();
		
		assertEquals(Facing.AB, clone.ceStatBlock.getFacing());
	
		actionOrder.clear();
	}
	
	@Test
	public void updateTurnActionTest() {
		
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		
		CeAction.addTurnAction(clone.ceStatBlock, true);
		
		CeAction.updateTurnAction(clone.ceStatBlock, true);
		
		clone.ceStatBlock.spendCombatAction();
		
		assertEquals(Facing.B, clone.ceStatBlock.getFacing());
		
		CeAction.addTurnAction(clone.ceStatBlock, false);
		
		clone.ceStatBlock.spendCombatAction();
		
		assertEquals(Facing.AB, clone.ceStatBlock.getFacing());
	
		actionOrder.clear();
		
	}
	
	@Test
	public void setFacingAfterMove() {
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		
		clone.ceStatBlock.moveSpeed = MoveSpeed.CRAWL;
		
		ArrayList<Cord> cords = new ArrayList<>();
		
		Cord cord = new Cord(1,1);
		cord.facing = Facing.B;
		
		cords.add(cord);
		
		CeAction.addMoveAction(clone.ceStatBlock, cords, 0);
		
		clone.ceStatBlock.spendCombatAction();
		assertEquals(false, clone.ceStatBlock.getPosition().compare(new Cord(1,1)));
		
		clone.ceStatBlock.spendCombatAction();
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,1)));
		
		assertEquals(Facing.B, clone.ceStatBlock.getFacing());
		
		actionOrder.clear();
	}
	
	@Test 
	public void corditeGameTimingTest() {
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		CorditeExpansionGame.actionOrder = actionOrder;
		
		clone.ceStatBlock.combatActions = 5; 
		
		assertEquals(1, CorditeExpansionGame.impulse.getValue());
		assertEquals(2, clone.ceStatBlock.getActionTiming());
		CorditeExpansionGame.action();
		assertEquals(2, CorditeExpansionGame.impulse.getValue());
		
		//System.out.println("Test 2");
		assertEquals(1, clone.ceStatBlock.getActionTiming());
		CorditeExpansionGame.action();
		assertEquals(3, CorditeExpansionGame.impulse.getValue());
		
		assertEquals(1, clone.ceStatBlock.getActionTiming());
		CorditeExpansionGame.action();
		assertEquals(4, CorditeExpansionGame.impulse.getValue());
		
		assertEquals(1, clone.ceStatBlock.getActionTiming());
		CorditeExpansionGame.action();
		assertEquals(1, CorditeExpansionGame.impulse.getValue());
		
		assertEquals(2, CorditeExpansionGame.round);
		
		actionOrder.clear();
		CorditeExpansionGame.actionOrder.clear();
	}
	
	@Test
	public void corditeGameActionTest() {
		actionOrder.clear();
		CorditeExpansionGame.actionOrder.clear();
		
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		CorditeExpansionGame.actionOrder = actionOrder;
		
		ArrayList<Cord> cords = new ArrayList<>();
		cords.add(new Cord(1,1));
		CeAction.addMoveAction(clone.ceStatBlock, cords, 2);
		
		clone.ceStatBlock.combatActions = 5; 
		
		CorditeExpansionGame.action();
		
		assertEquals(true, clone.ceStatBlock.acting());
		assertEquals(false, clone.ceStatBlock.getPosition().compare(new Cord(1,1)));
		
		CorditeExpansionGame.action();
			
		assertEquals(true, clone.ceStatBlock.getPosition().compare(new Cord(1,1)));	
		assertEquals(3, clone.ceStatBlock.spentCombatActions);
		
		actionOrder.clear();
		CorditeExpansionGame.actionOrder.clear();
	}
	
	@Test 
	public void stanceEnumTest() {
		
		Stance stance = Stance.STANDING;
		
		assertEquals(3, stance.getValue());
		
		stance = Stance.valueOf(2);
		
		assertEquals(Stance.CROUCH, stance);
	}
	
	@Test 
	public void stanceTest() {
		
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);

		assertEquals(Stance.STANDING, clone.ceStatBlock.stance);
		
		CeAction.addChangeStanceAction(clone.ceStatBlock, Stance.PRONE);
		
		clone.ceStatBlock.spendCombatAction();
		
		assertEquals(Stance.CROUCH, clone.ceStatBlock.stance);
		
		//CeAction.addChangeStanceAction(clone.ceStatBlock, Stance.PRONE);
		
		clone.ceStatBlock.spendCombatAction();
		
		assertEquals(Stance.PRONE, clone.ceStatBlock.stance);

		CeAction.addChangeStanceAction(clone.ceStatBlock, Stance.STANDING);
		
		clone.ceStatBlock.spendCombatAction();
		
		assertEquals(Stance.CROUCH, clone.ceStatBlock.stance);
		
		clone.ceStatBlock.spendCombatAction();
		
		assertEquals(Stance.STANDING, clone.ceStatBlock.stance);
		
		actionOrder.clear();

		
	}
	
	@Test 
	public void adaptabilityFactorTest() {
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		
		int fighter = 59; 
		int fighterTens = (fighter/10)%10; 
		assertEquals(5, fighterTens);
		
		assertEquals(3, Math.round(fighterTens/2)+1);
		
		fighter = 75; 
		fighterTens = (fighter/10)%10; 
		assertEquals(4, Math.round(fighterTens/2)+1);
		
		actionOrder.clear();
	}
	
	@Test
	public void aimTest() {
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		
		
		CeStatBlock stat = clone.ceStatBlock;
		stat.combatActions = 4;
		
		Cord cord1 = new Cord(1,1);
		Cord cord2 = new Cord(1,2);
		Cord cord3 = new Cord(1,3);
		Cord cord4 = new Cord(1,4);
		
		CeAction.addAimAciton(clone.ceStatBlock,cord1);
		
		//AimAction aimAction = (AimAction) stat.getAimAction();
		AimAction aimAction = (AimAction) stat.getCoac().get(0);
				
		aimAction.addTargetHex(cord2);
		aimAction.addTargetHex(cord3);
		
		assertEquals(true, clone.ceStatBlock.aimHexes.contains(cord1));
		assertEquals(true, clone.ceStatBlock.aimHexes.contains(cord2));
		assertEquals(true, clone.ceStatBlock.aimHexes.contains(cord3));
		
		aimAction.addTargetHex(cord4);
		
		assertEquals(false, clone.ceStatBlock.aimHexes.contains(cord1));
		assertEquals(true, clone.ceStatBlock.aimHexes.contains(cord2));
		assertEquals(true, clone.ceStatBlock.aimHexes.contains(cord3));
		assertEquals(true, clone.ceStatBlock.aimHexes.contains(cord4));
		
		stat.prepareCourseOfAction();
		stat.prepareCourseOfAction();
		stat.spendCombatAction();
		stat.spendCombatAction();
		stat.spendCombatAction();
		
		assertEquals(3, stat.aimTime);
		
		actionOrder.clear();
	}
	
	
}
