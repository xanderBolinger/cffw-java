package JUnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CorditeExpansion.ActionOrder;
import Trooper.Trooper;
import UtilityClasses.DiceRoller;
import UtilityClasses.ExcelUtility;
import UtilityClasses.PCUtility;
import UtilityClasses.PcDamageUtility;

public class PcDamageUtilityTests {

	Sheet worksheet;
	Workbook workbook;
	
	ActionOrder actionOrder;
	
	@Before
	public void initTesting() {
		FileInputStream excelFile;
		try {
			excelFile = new FileInputStream(new File(ExcelUtility.path + "\\hittable.xlsx"));
			workbook = new XSSFWorkbook(excelFile);
			worksheet = workbook.getSheetAt(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		actionOrder = new ActionOrder();
	}
	
	@After
	public void finishTesting() {
		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void coverageTest() {
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		
		assertEquals(true, PCUtility.armorCoverage(clone));
		clone.armor = null;
		assertEquals(false, PCUtility.armorCoverage(clone));
	}
	
	@Test
	public void setRecoveryTest() {
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		clone.ceStatBlock.medicalStatBlock.increasePd(1);
		
		PcDamageUtility.setRecovery(0, 0, 5, false, clone.ceStatBlock.medicalStatBlock);
		assertEquals(94, clone.ceStatBlock.medicalStatBlock.recoveryChance);
		assertEquals(568800, clone.ceStatBlock.medicalStatBlock.criticalTime);
		
		PcDamageUtility.setRecovery(0, 0, 100000, false, clone.ceStatBlock.medicalStatBlock);
		assertEquals(-1, clone.ceStatBlock.medicalStatBlock.recoveryChance);
		assertEquals(2, clone.ceStatBlock.medicalStatBlock.criticalTime);
		
		clone.ceStatBlock.medicalStatBlock.deathCheck();
		clone.ceStatBlock.medicalStatBlock.deathCheck();
		
		assertEquals(false, clone.ceStatBlock.medicalStatBlock.alive);
		
		actionOrder.clear();
	}
	
	@Test 
	public void bloodLossPdTest() {
		
		
		Trooper b1 = new Trooper("B1 Rifleman", "CIS Battle Droid");
		assertEquals(null, PcDamageUtility.getBloodLossPD(0, 0, null, b1));
		
		Trooper clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		clone.mechanicalZones.add("Shoulder");
		assertEquals(null, PcDamageUtility.getBloodLossPD(0, 0, "Shoulder", clone));
		//int blpd = PcDamageUtility.getBloodLossPD(0, 0, null, null);
		
		clone.mechanicalZones.clear();
		
		assertEquals(400, PcDamageUtility.getBloodLossPD(0, 2, "Neck Flesh", clone).blpd);
		assertEquals(400, PcDamageUtility.getBloodLossPD(0, 2, "Neck Spine", clone).blpd);
		assertEquals(400, PcDamageUtility.getBloodLossPD(0, 2, "Base of Neck", clone).blpd);
		
		assertEquals(300, PcDamageUtility.getBloodLossPD(0, 2, "Arm Flesh", clone).blpd);
		assertEquals(300, PcDamageUtility.getBloodLossPD(0, 2, "Arm Bone", clone).blpd);
		assertEquals(200, PcDamageUtility.getBloodLossPD(0, 2, "Shoulder", clone).blpd);
	}
	
	@Test 
	public void damageStringTest() {
		
		String damageString = "10TD";
		
		int multiplier = PcDamageUtility.getMultiplier(damageString);
		int value = PcDamageUtility.getDamageValue(damageString);
		boolean disabled = PcDamageUtility.getDisabled(damageString);
		
		assertEquals(10000,multiplier);
		assertEquals(10, value);
		assertEquals(true, disabled);
		assertEquals(100000, value*multiplier);
		
	}
	
	@Test
	public void hitTableTest() throws IOException {
		assertEquals("5", PcDamageUtility.getDamageString(1, 1, true, 0, worksheet));
		assertEquals("95D", PcDamageUtility.getDamageString(10, 10, true, 99, worksheet));
		assertEquals("8K", PcDamageUtility.getDamageString(10, 10, false, 0, worksheet));
		assertEquals("0", PcDamageUtility.getDamageString(1, 1, false, 99, worksheet));
	}
	
	@Test
	public void hitLocationRowTest() {
		
		assertEquals(4, PcDamageUtility.getHitLocationRow(false, 0, worksheet));
		assertEquals(12, PcDamageUtility.getHitLocationRow(false, 55, worksheet));
		assertEquals(19, PcDamageUtility.getHitLocationRow(false, 99, worksheet));
		
		assertEquals(4, PcDamageUtility.getHitLocationRow(true, 0, worksheet));
		assertEquals(35, PcDamageUtility.getHitLocationRow(true, 55, worksheet));
		assertEquals(42, PcDamageUtility.getHitLocationRow(true, 99, worksheet));
	}
	
	@Test
	public void hitLocationNameTest() {
		
		assertEquals("Head Glance", PcDamageUtility.getHitLocationName(false, 0, worksheet));
		assertEquals("Arm Glance", PcDamageUtility.getHitLocationName(false, 55, worksheet));
		assertEquals("Weapon Critical", PcDamageUtility.getHitLocationName(false, 99, worksheet));
		
		assertEquals("Head Glance", PcDamageUtility.getHitLocationName(true, 0, worksheet));
		assertEquals("Pelvis", PcDamageUtility.getHitLocationName(true, 55, worksheet));
		assertEquals("Ankle - Foot", PcDamageUtility.getHitLocationName(true, 99, worksheet));
	}
	
	@Test
	public void dcColumnTest () {
		
		assertEquals(3, PcDamageUtility.getDcColumn(1, worksheet));
		assertEquals(10, PcDamageUtility.getDcColumn(2, worksheet));
		assertEquals(17, PcDamageUtility.getDcColumn(3, worksheet));
	}

	@Test
	public void epenColumnTest() {
		
		int dcColumn = PcDamageUtility.getDcColumn(1, worksheet);
		assertEquals(4, PcDamageUtility.getEpenColumn(dcColumn, 1, worksheet));
		assertEquals(9, PcDamageUtility.getEpenColumn(dcColumn, 10, worksheet));
		dcColumn = PcDamageUtility.getDcColumn(2, worksheet);
		assertEquals(dcColumn+1, PcDamageUtility.getEpenColumn(dcColumn, 1, worksheet));
		assertEquals(dcColumn+3, PcDamageUtility.getEpenColumn(dcColumn, 2, worksheet));
	}
	
}
