package JUnitTests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import UtilityClasses.ExcelUtility;

public class ExcelUtililtyTests {
	Sheet worksheet;
	Workbook workbook;
	
	@Before
    public void init() throws IOException {
		FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "\\"+"MaximumSpeed.xlsx"));
		workbook = new XSSFWorkbook(excelFile);
		worksheet = workbook.getSheetAt(0);

    }
	
	@After
    public void close() throws IOException {
		workbook.close();
    }
	
	@Test
	public void targetColumnTest() throws Exception {
		assertEquals(1, ExcelUtility.getTargetColumn(ExcelUtility.countColumns(worksheet), 1, worksheet, true));
		assertEquals(8, ExcelUtility.getTargetColumn(ExcelUtility.countColumns(worksheet), 4.5, worksheet, true));
	}
	
	@Test
	public void targetRowTest() throws Exception {
		assertEquals(1, ExcelUtility.getTargetRow(ExcelUtility.countRows(worksheet), 21, worksheet, false));
		assertEquals(21, ExcelUtility.getTargetRow(ExcelUtility.countRows(worksheet), 1, worksheet, false));
	}
	
	@Test
	public void findMaxSpeed() throws Exception {
		assertEquals(2.0, ExcelUtility.getNumberFromSheet(1, 21, "MaximumSpeed.xlsx", true, false), 0);
		assertEquals(3.0, ExcelUtility.getNumberFromSheet(4.5, 1, "MaximumSpeed.xlsx", true, false), 0);
		assertEquals(1.0, ExcelUtility.getNumberFromSheet(1, 1, "MaximumSpeed.xlsx", true, false), 0);
		assertEquals(13.0, ExcelUtility.getNumberFromSheet(4.5, 21, "MaximumSpeed.xlsx", true, false), 0);
		assertEquals(6.0, ExcelUtility.getNumberFromSheet(2.5, 13, "MaximumSpeed.xlsx", true, false), 0);
	} 
	
	@Test
	public void findBaseSpeed() throws Exception {
		assertEquals(4.5, ExcelUtility.getNumberFromSheet(12, 21, "BaseSpeed.xlsx", true, false), 0);
		assertEquals(2.5, ExcelUtility.getNumberFromSheet(149, 21, "BaseSpeed.xlsx", true, false), 0);
		assertEquals(1.5, ExcelUtility.getNumberFromSheet(1, 1, "BaseSpeed.xlsx", true, false), 0);
		assertEquals(1.0, ExcelUtility.getNumberFromSheet(150, 1, "BaseSpeed.xlsx", true, false), 0);
	}
	
	@Test
	public void countRowsTest() throws Exception {
		assertEquals(22, ExcelUtility.countRows(worksheet));
	} 
	
	@Test 
	public void countColumnsTest() throws Exception {
		assertEquals(9, ExcelUtility.countColumns(worksheet));
	}
	
	
	@Test
	public void testCeSpreadSheet() {
		int actions = -1;
		int impulse = 1; 
		int combatActions = 5; 
		
		
		//System.out.println("Spreadsheet Test");
		try {
			actions = (int) ExcelUtility.getNumberFromSheet(impulse, combatActions, "caperimpulse.xlsx", true, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(2, actions);
		
		impulse = 2; 
		
		//System.out.println("Spreadsheet Test 2");
		try {
			actions = (int) ExcelUtility.getNumberFromSheet(impulse, combatActions, "caperimpulse.xlsx", true, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(1, actions);
	}
	
}
