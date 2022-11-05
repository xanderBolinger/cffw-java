package JUnitTests;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import CorditeExpansion.ActionOrder;
import CorditeExpansionStatBlock.MedicalStatBlock.Status;
import Items.PersonalShield;
import Items.PersonalShield.ShieldType;
import Trooper.Trooper;
import UtilityClasses.DiceRoller;
import Damage.Damage;

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
	public void KoTest() {
		
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		clone.KO = 50; 
		clone.physicalDamage = 100; 
		
		Damage.statusCheck(100, clone);
		
		assertEquals(true, clone.conscious);
		
		Damage.statusCheck(250, clone);
		
		
		assertEquals(false, clone.conscious);
		
		clone.conscious = true;
		
		Damage.statusCheck(200, clone);
		Damage.statusCheck(200, clone);
		Damage.statusCheck(200, clone);
		
		Damage.statusCheck(250, clone);
		
		assertEquals(false, clone.conscious);
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
