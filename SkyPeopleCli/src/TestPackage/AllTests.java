package TestPackage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ComponentTests.class, HardPointTests.class, HitTableTests.class, WeaponTest.class, DamageTests.class })
public class AllTests {

}
