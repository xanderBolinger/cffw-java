package JUnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import HexGrid.Tests.HexDirectionUtilityTests;
import Melee.Tests.MeleeHitLocationTests;
import Vehicle.UnitTests.VehicleDataTests;
import Vehicle.UnitTests.VehicleMovementTests;

@RunWith(Suite.class)
@SuiteClasses({ CeSuppressionTests.class, /*CommandLineInterfaceTests.class,*/ CorditeExpansionTests.class,
		DamageTests.class, ExcelUtililtyTests.class, GrenadeTests.class, InventoryTest.class, OpticTests.class,
		PcDamageUtilityTests.class, ReloadTests.class, MedicalTests.class,  ShootTests.class, VehicleDataTests.class, VehicleMovementTests.class, HexDirectionUtilityTests.class,
		MeleeHitLocationTests.class})
public class AllTests {

}
