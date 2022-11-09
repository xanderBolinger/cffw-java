package JUnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CorditeExpansion.ActionOrder;
import CorditeExpansionRangedCombat.Suppression.SuppressionStatus;
import CorditeExpansionStatBlock.RangedStatBlock;
import Trooper.Trooper;
import UtilityClasses.DiceRoller;

public class CeSuppressionTests {
	ActionOrder actionOrder; 
	RangedStatBlock rangedStats;
	Trooper clone;
	
	@Before
	public void initTesting() {
		actionOrder = new ActionOrder();
		clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		rangedStats = clone.ceStatBlock.rangedStatBlock;
		DiceRoller.initTesting();
	}
	
	@After
	public void finishTesting() {
		DiceRoller.finishTesting();
		actionOrder.clear();
	}
	
	@Test
	public void testUnitTest() {
		assertEquals(1, 1);
	}
	
	@Test
	public void testSuppressionData() {
		rangedStats.suppression.setSuppression(1);
		assertEquals(1,rangedStats.getSuppression());
		rangedStats.suppression.decreaseSuppression(123);
		assertEquals(0,rangedStats.getSuppression());
		rangedStats.suppression.increaseSuppression(2);
		assertEquals(2,rangedStats.getSuppression());
	}
	
	@Test
	public void rangedStatBlockTest() {
		clone.skills.getSkill("Fighter").value = 50;
		clone.skills.getSkill("Composure").value = 50;
		actionOrder.clear();
		actionOrder.addTrooper(clone);
		assertEquals(50, clone.ceStatBlock.rangedStatBlock.coolnessUnderFire);
	}
	
	@Test
	public void suppresionStatusTest() {
		assertEquals(SuppressionStatus.NONE,rangedStats.suppression.getStatus());
		rangedStats.suppression.setSuppression(1);
		assertEquals(SuppressionStatus.SPEND,rangedStats.suppression.getStatus());
		rangedStats.suppression.cycleSuppressionStatus();
		assertEquals(SuppressionStatus.HESITATE,rangedStats.suppression.getStatus());
		rangedStats.suppression.cycleSuppressionStatus();
		assertEquals(SuppressionStatus.COOLNESS_TEST,rangedStats.suppression.getStatus());
		rangedStats.suppression.cycleSuppressionStatus();
		assertEquals(SuppressionStatus.SPEND,rangedStats.suppression.getStatus());
		rangedStats.suppression.decreaseSuppression(1);
		assertEquals(SuppressionStatus.NONE,rangedStats.suppression.getStatus());
		rangedStats.suppression.increaseSuppression(1);
		assertEquals(SuppressionStatus.SPEND,rangedStats.suppression.getStatus());
	}
	
}
