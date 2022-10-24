package UtilityClasses;

import java.util.ArrayList;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

import JUnitTests.CorditeExpansionTests;
import JUnitTests.ExcelUtililtyTests;
import JUnitTests.InventoryTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({ InventoryTest.class, ExcelUtililtyTests.class, CorditeExpansionTests.class })

public class TestUtility {

	public static void runTests() throws Exception {
		ArrayList<Result> results = new ArrayList<>();
		
		results.add(JUnitCore.runClasses(CorditeExpansionTests.class));
		results.add(JUnitCore.runClasses(ExcelUtililtyTests.class));
		results.add(JUnitCore.runClasses(InventoryTest.class));
		
		for(Result result : results) {
			for (Failure failure : result.getFailures()) {
				throw new Exception(failure.toString());
				//System.out.println(failure.toString());
			}
		}

		
	}
	
}
