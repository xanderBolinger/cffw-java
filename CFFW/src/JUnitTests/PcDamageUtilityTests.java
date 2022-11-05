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

import UtilityClasses.DiceRoller;
import UtilityClasses.ExcelUtility;
import UtilityClasses.PcDamageUtility;

public class PcDamageUtilityTests {

	
	Sheet worksheet;
	Workbook workbook;
	
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
		assertEquals("5", PcDamageUtility.getDamageString(1, 1, true, 0));
		assertEquals("95D", PcDamageUtility.getDamageString(10, 10, true, 99));
		assertEquals("8K", PcDamageUtility.getDamageString(10, 10, false, 0));
		assertEquals("0", PcDamageUtility.getDamageString(1, 1, false, 99));
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
