package JUnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import UtilityClasses.PCUtility;

public class MedicalTests {
	
	@Test
	public void recoveryTimeTest() {
		
		assertEquals("1d", PCUtility.convertTime(60 * 24));
		assertEquals("1.08d", PCUtility.convertTime(60 * 26));
		
	}
	
}
