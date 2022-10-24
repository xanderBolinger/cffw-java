package JUnitTests;

import static org.junit.Assert.assertEquals;

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
		assertEquals(1, ExcelUtility.getTargetColumn(ExcelUtility.countColumns(worksheet), 1, worksheet));
	}
	
	/*@Test
	public void findMaxSpeed() throws Exception {
		assertEquals(3.0, ExcelUtility.getResultsTwoWayFixedValues(21, 1, "MaximumSpeed.xlsx"));
	} */
	
	@Test
	public void countRowsTest() throws Exception {
		assertEquals(22, ExcelUtility.countRows(worksheet));
	} 
	
	@Test 
	public void countColumnsTest() throws Exception {
		assertEquals(9, ExcelUtility.countColumns(worksheet));
	}
	
}
