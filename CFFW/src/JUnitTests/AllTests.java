package JUnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CeSuppressionTests.class, CommandLineInterfaceTests.class, CorditeExpansionTests.class,
		DamageTests.class, ExcelUtililtyTests.class, GrenadeTests.class, InventoryTest.class, OpticTests.class,
		PcDamageUtilityTests.class, ReloadTests.class, MedicalTests.class })
public class AllTests {

}
