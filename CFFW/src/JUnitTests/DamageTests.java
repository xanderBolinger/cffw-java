package JUnitTests;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import CorditeExpansion.ActionOrder;
import CorditeExpansionDamage.Damage;
import CorditeExpansionStatBlock.MedicalStatBlock.Status;
import Items.PersonalShield;
import Items.PersonalShield.ShieldType;
import Trooper.Trooper;
import UtilityClasses.DiceRoller;
import UtilityClasses.ExcelUtility;

public class DamageTests {
	
	private ActionOrder actionOrder; 
	
	@Before
	public void initTesting() {
		actionOrder = new ActionOrder();
		DiceRoller.initTesting();
	}
	
	@After
	public void finishTesting() {
		DiceRoller.finishTesting();
	}
	
	@Test
	public void testUnitTest() {
		assertEquals(1, 1);
	}
	
	
	@Test
	public void applyHit() throws Exception {
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		Damage.applyHit(100, 10, true, clone);
		
		assertEquals(200, clone.ceStatBlock.medicalStatBlock.getPdTotal());
		actionOrder.clear();
	}
	
	@Test
	public void incapTimeTest() throws Exception {
		
		// 2 0 6
		assertEquals("1p", ExcelUtility.getStringFromSheet(0, 0, "Formatted Excel Files\\incapacitationtime.xlsx",
				true, true));
		DiceRoller.randInt(0, 0);
		assertEquals(2, Damage.incapacitationImpulses(0));
		assertEquals(47 * 2, Damage.incapacitationImpulses(50));
		
		assertEquals(6 * 120 * 60 * 24, Damage.incapacitationImpulses(1200));
	}
	
	@Test
	public void KoTest() {
		
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		clone.KO = 50; 
		clone.physicalDamage = 100; 
		
		Damage.statusCheck(100, clone);
		
		assertEquals(true, clone.ceStatBlock.medicalStatBlock.conscious());
		
		Damage.statusCheck(250, clone);
		
		assertEquals(false, clone.ceStatBlock.medicalStatBlock.conscious());
		
		clone.ceStatBlock.medicalStatBlock.setConscious(0, clone.ceStatBlock);
		
		Damage.statusCheck(200, clone);
		Damage.statusCheck(200, clone);
		Damage.statusCheck(200, clone);
		
		Damage.statusCheck(250, clone);
		
		assertEquals(false, clone.ceStatBlock.medicalStatBlock.conscious());
	}
	
	@Test
	public void statusTest() {
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		clone.KO = 50; 
		clone.physicalDamage = 100; 
		
		Damage.statusCheck(250, clone);
		
		assertEquals(Status.STUNNED, clone.ceStatBlock.medicalStatBlock.status);
		
		Damage.statusCheck(150, clone);
		
		assertEquals(Status.STUNNED, clone.ceStatBlock.medicalStatBlock.status);
		
	}
	
	@Test
	public void shieldTest() {
		
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		
		clone.personalShield = new PersonalShield(ShieldType.DCR1RifleShield);
		
		assertEquals(0, Damage.shieldEpen(50, 100, true, clone));
		assertEquals(100,clone.personalShield.currentShieldStrength);
		assertEquals(100, Damage.shieldEpen(51, 100, true, clone));
		assertEquals(0, Damage.shieldEpen(51, 100, false, clone));
		assertEquals(0,clone.personalShield.currentShieldStrength);
		
	}
	
	@Test
	public void armorGlanceTest() {
		
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		
		try {
			assertEquals(26, clone.armor.getModifiedProtectionFactor(33, 20, false));
			assertEquals(22, clone.armor.getModifiedProtectionFactor(33, 20, false));
			assertEquals(37, clone.armor.getModifiedProtectionFactor(33, 20, false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void epenTest() {
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		
		assertEquals(0, clone.armor.getPF(34, false));
		assertEquals(20, clone.armor.getPF(34, true));
		
		try {
			assertEquals(20,Damage.armorEpen(34, 20, false, clone));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			assertEquals(-6,Damage.armorEpen(34, 20, true, clone));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
