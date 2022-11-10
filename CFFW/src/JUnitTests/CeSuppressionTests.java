package JUnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CorditeExpansion.ActionOrder;
import CorditeExpansionRangedCombat.Suppression;
import CorditeExpansionRangedCombat.Suppression.SuppressionStatus;
import CorditeExpansionStatBlock.RangedStatBlock;
import CorditeExpansionStatBlock.SkillStatBlock;
import CorditeExpansionStatBlock.StatBlock;
import Trooper.Trooper;
import UtilityClasses.DiceRoller;

public class CeSuppressionTests {
	ActionOrder actionOrder; 
	StatBlock statBlock;
	RangedStatBlock rangedStats;
	SkillStatBlock skillStats;
	Suppression suppression;
	Trooper clone;
	
	@Before
	public void initTesting() throws InterruptedException {
		actionOrder = new ActionOrder();
		clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		clone.skills.getSkill("Fighter").value = 88;
		clone.skills.getSkill("Composure").value = 88;
		actionOrder.addTrooper(clone);
		statBlock = clone.ceStatBlock;
		rangedStats = clone.ceStatBlock.rangedStatBlock;
		//System.out.println("Init: "+rangedStats.suppression.statBlock);
		skillStats = clone.ceStatBlock.skilStatBlock;
		//System.out.println("Init 2: "+rangedStats.suppression.statBlock);
		suppression = rangedStats.suppression;
		//System.out.println(clone.ceStatBlock);
		DiceRoller.initTesting();
		//System.out.println("Before");
		//Thread.sleep(400);
	}
	
	@After
	public void finishTesting() throws InterruptedException {
		DiceRoller.finishTesting();
		//System.out.println("After");
		//Thread.sleep(400);
	}
	
	@Test
	public void testUnitTest() {
		assertEquals(1, 1);
	}
	
	@Test
	public void testSuppressionData() {
		//System.out.println("Before: "+suppression.statBlock);
		
		rangedStats.suppression.setSuppression(1);
		assertEquals(1,rangedStats.getSuppression());
		rangedStats.suppression.decreaseSuppression(123);
		assertEquals(0,rangedStats.getSuppression());
		rangedStats.suppression.increaseSuppression(2);
		assertEquals(2,rangedStats.getSuppression());
		//System.out.println("After: "+suppression.statBlock);
	}
	
	@Test
	public void rangedStatBlockTest() {
		//System.out.println("Before: "+suppression.statBlock);
		assertEquals(88, clone.ceStatBlock.rangedStatBlock.coolnessUnderFire);
		//System.out.println("After: "+suppression.statBlock);
	}
	
	@Test
	public void suppresionStatusTest() {
		//System.out.println("Before: "+suppression.statBlock);
		assertEquals(SuppressionStatus.NONE,rangedStats.suppression.getStatus());
		rangedStats.suppression.setSuppression(1);
		assertEquals(SuppressionStatus.SPEND,rangedStats.suppression.getStatus());
		rangedStats.suppression.cycleSuppressionStatus();
		assertEquals(SuppressionStatus.HESITATE,rangedStats.suppression.getStatus());
		rangedStats.suppression.cycleSuppressionStatus();
		assertEquals(SuppressionStatus.COOLNESS_TEST,rangedStats.suppression.getStatus());
		rangedStats.suppression.cycleSuppressionStatus();
		assertEquals(SuppressionStatus.SPEND,rangedStats.suppression.getStatus());
		assertEquals("Spend Suppression (remove 2 sup for mof)", rangedStats.suppression.getDisplayStatus());
		rangedStats.suppression.decreaseSuppression(1);
		assertEquals(SuppressionStatus.NONE,rangedStats.suppression.getStatus());
		rangedStats.suppression.increaseSuppression(1);
		assertEquals(SuppressionStatus.SPEND,rangedStats.suppression.getStatus());
		//System.out.println("After: "+suppression.statBlock);
	}
	
	@Test
	public void testSuppression() {
		//System.out.println("Before: "+suppression.statBlock);
		//System.out.println(clone.ceStatBlock);
		assertEquals(0, skillStats.failure);
		assertEquals(0, rangedStats.getSuppression());
		
		suppression.increaseSuppression(1);
			
		suppression.resolve();
		
		assertEquals(0, suppression.getSuppression());
		suppression.increaseSuppression(10);
		
		rangedStats.suppression.cycleSuppressionStatus();
		assertEquals(SuppressionStatus.HESITATE,rangedStats.suppression.getStatus());
		suppression.resolve();
		assertEquals(2, suppression.getSuppression());
		suppression.increaseSuppression(10);
		
		// 82 89 37
		rangedStats.suppression.cycleSuppressionStatus();
		assertEquals(SuppressionStatus.COOLNESS_TEST,rangedStats.suppression.getStatus());
		suppression.resolve();
		assertEquals(12, suppression.getSuppression());
		assertEquals(true, statBlock.hesitating);
		
		suppression.resolve();
		suppression.resolve();
		
		//System.out.println("After: "+suppression.statBlock);
	}
	
}
