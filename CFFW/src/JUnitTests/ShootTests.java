package JUnitTests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Shoot.Shoot;
import Trooper.Trooper;
import Trooper.generateSquad;
import Unit.Unit;
import UtilityClasses.DiceRoller;

public class ShootTests {

	Unit targetUnit;
	Unit shooterUnit;
	Trooper shooter;
	Trooper target;
	Shoot shoot;

	@Before
	public void initTesting() throws InterruptedException {
		DiceRoller.initTesting();

		generateSquad squad = new generateSquad("Clone Trooper Phase 1", "Riflesquad");

		// Instantiates unit based off of side and type
		shooterUnit = new Unit("Shooter Unit", 0, 0, squad.getSquad(), 100, 0, 100, 0, 0, 20, 0, "No Contact");
		// Sets unit stat
		shooterUnit.X = 0;
		shooterUnit.Y = 0;
		shooterUnit.speed = "None";
		shooter = shooterUnit.individuals.get(0);

		squad = new generateSquad("CIS Battle Droid", "Droid Riflesquad");

		// Instantiates unit based off of side and type
		targetUnit = new Unit("Target Unit", 0, 0, squad.getSquad(), 100, 0, 100, 0, 0, 20, 0, "No Contact");
		// Sets unit stat
		targetUnit.X = 0;
		targetUnit.Y = 0;
		targetUnit.speed = "None";
		target = targetUnit.individuals.get(0);

		shoot = new Shoot(shooterUnit, targetUnit, shooter, target);

	}

	@After
	public void finishTesting() throws InterruptedException {
		Shoot.testVisibility = "Good Visibility";
		DiceRoller.finishTesting();
	}

	@Test
	public void testUnitTest() {
		assertEquals(1, 1);
	}

	@Test
	public void hits() {
		// head
		shoot.pcHexRange = 5;
		shoot.targetUnit.speed = "None";
		shoot.calculateModifiers();
		shoot.aimBonus = 0;
		shoot.setALM();
		shoot.setEAL();
		assertEquals(29, shoot.ealSum);
		shoot.setSingleTn();
		shoot.singleShotRoll();
		assertEquals(1, shoot.hits);

		shoot.hits = 0;
		
		// head
		shoot.pcHexRange = 5;
		shoot.targetUnit.speed = "None";
		shoot.calculateModifiers();
		shoot.aimBonus = 0;
		shoot.setALM();
		shoot.setCalledShotBounds(1);
		shoot.setEAL();
		shoot.setFullAutoTn();
		shoot.burstRoll();
		assertEquals(3, shoot.hits);
		
		shoot.hits = 0;
		
		shoot.pcHexRange = 100;
		shoot.ealSum = 30;
		shoot.setFullAutoTn();
		shoot.burstRoll();
		assertEquals(0, shoot.hits);
		shoot.burstRoll();
		assertEquals(1, shoot.hits);
	}

	@Test
	public void tn() {
		shoot.pcHexRange = 10;
		shoot.target.inCover = true;
		shoot.calculateModifiers();
		shoot.aimBonus = 0;
		shoot.setEAL();
		assertEquals(16, shoot.ealSum);
		shoot.setSingleTn();
		shoot.setFullAutoTn();
		assertEquals(39, shoot.singleTn);
		assertEquals(62, shoot.fullAutoTn);

		Shoot.testVisibility = "Dusk";
		shoot.targetUnit.speed = "Walk";
		shoot.pcHexRange = 20;
		shoot.shooter.nightVisionInUse = true;
		shoot.shooter.weaponIRLaserOn = true;
		shoot.target.inCover = false;
		shoot.calculateModifiers();
		shoot.aimBonus = 0;
		shoot.setEAL();
		assertEquals(14, shoot.ealSum);
		shoot.setSingleTn();
		shoot.setFullAutoTn();
		assertEquals(27, shoot.singleTn);
		assertEquals(52, shoot.fullAutoTn);

		shoot.ealSum = -23;
		shoot.setSingleTn();
		shoot.setFullAutoTn();
		assertEquals(0, shoot.singleTn);
		assertEquals(0, shoot.fullAutoTn);

		shoot.ealSum = 33;
		shoot.setSingleTn();
		shoot.setFullAutoTn();
		assertEquals(99, shoot.singleTn);
		assertEquals(99, shoot.fullAutoTn);
	}

	@Test
	public void aim() {
		// SL 13
		// System.out.println("Shooter SL: "+shoot.shooter.sl);

		shoot.calculateModifiers();
		assertEquals(-8, shoot.aimBonus);

		shoot.aimTime++;
		shoot.spentCombatActions++;
		shoot.calculateModifiers();

		assertEquals(1, shoot.aimTime);
		assertEquals(1, shoot.spentCombatActions);
		assertEquals(1, shoot.aimBonus);
		shoot.aimAction();
		shoot.calculateModifiers();

		assertEquals(2, shoot.aimTime);
		assertEquals(2, shoot.spentCombatActions);
		assertEquals(5, shoot.aimBonus);
	}

	@Test
	public void calledShot() {
		ArrayList<Integer> calledShotBounds;
		// head
		shoot.pcHexRange = 10;
		shoot.target.inCover = true;
		shoot.calculateModifiers();
		shoot.aimBonus = 0;
		shoot.setALM();
		shoot.setCalledShotBounds(1);
		calledShotBounds = shoot.calledShotBounds;
		assertEquals(1, (int) calledShotBounds.get(0));
		assertEquals(48, (int) calledShotBounds.get(1));
		assertEquals(-1, (int) calledShotBounds.get(2));
		assertEquals(-1, (int) calledShotBounds.get(3));

		Shoot.testVisibility = "Dusk";
		shoot.targetUnit.speed = "Walk";
		shoot.pcHexRange = 20;
		shoot.shooter.nightVisionInUse = true;
		shoot.shooter.weaponIRLaserOn = true;
		shoot.target.inCover = false;
		shoot.calculateModifiers();
		shoot.aimBonus = 0;
		shoot.setALM();
		shoot.setCalledShotBounds(2);
		calledShotBounds = shoot.calledShotBounds;
		assertEquals(7, shoot.almSum);
		assertEquals(1, (int) calledShotBounds.get(0));
		assertEquals(100, (int) calledShotBounds.get(1));
		assertEquals(-1, (int) calledShotBounds.get(2));
		assertEquals(-1, (int) calledShotBounds.get(3));

		// head
		shoot.pcHexRange = 5;
		shoot.targetUnit.speed = "None";
		shoot.calculateModifiers();
		shoot.aimBonus = 0;
		shoot.setALM();
		shoot.setCalledShotBounds(1);
		assertEquals(24, shoot.almSum);
		calledShotBounds = shoot.calledShotBounds;
		assertEquals(1, (int) calledShotBounds.get(0));
		assertEquals(23, (int) calledShotBounds.get(1));
		assertEquals(-1, (int) calledShotBounds.get(2));
		assertEquals(-1, (int) calledShotBounds.get(3));
		assertEquals(0, shoot.sizeALM);
		shoot.setEAL();
		assertEquals(24, shoot.ealSum);

	}

	@Test
	public void almSum() {
		shoot.pcHexRange = 10;
		shoot.target.inCover = true;
		shoot.calculateModifiers();
		shoot.aimBonus = 0;
		shoot.setALM();
		assertEquals(16, shoot.almSum);

		Shoot.testVisibility = "Dusk";
		shoot.targetUnit.speed = "Walk";
		shoot.pcHexRange = 20;
		shoot.shooter.nightVisionInUse = true;
		shoot.shooter.weaponIRLaserOn = true;
		shoot.target.inCover = false;
		shoot.calculateModifiers();
		shoot.aimBonus = 0;
		shoot.setALM();
		assertEquals(7, shoot.almSum);
	}

	@Test
	public void ealSum() {
		shoot.pcHexRange = 10;
		shoot.target.inCover = true;
		shoot.calculateModifiers();
		shoot.aimBonus = 0;
		shoot.setEAL();
		assertEquals(16, shoot.ealSum);

		Shoot.testVisibility = "Dusk";
		shoot.targetUnit.speed = "Walk";
		shoot.pcHexRange = 20;
		shoot.shooter.nightVisionInUse = true;
		shoot.shooter.weaponIRLaserOn = true;
		shoot.target.inCover = false;
		shoot.calculateModifiers();
		shoot.aimBonus = 0;
		shoot.setEAL();
		assertEquals(14, shoot.ealSum);
	}

	@Test
	public void closeCombatDistance() {
		shoot.setCloseCombatDistance();
		assertEquals(7, shoot.pcHexRange);
		shoot.setCloseCombatDistance();
		assertEquals(7, shoot.pcHexRange);
	}

	@Test
	public void laserLightALM() {

		shoot.pcHexRange = 10;
		shoot.shooter.weaponLightOn = true;
		Shoot.testVisibility = "Night";
		shoot.setLaserLightALM();
		assertEquals(6, shoot.laserLightALM);
		shoot.shooter.weaponLightOn = false;
		shoot.target.weaponLightOn = true;
		shoot.setLaserLightALM();
		assertEquals(-8, shoot.laserLightALM);
		shoot.target.weaponLightOn = false;
		shoot.shooter.weaponLaserOn = true;
		shoot.setLaserLightALM();
		assertEquals(2, shoot.laserLightALM);
		shoot.shooter.weaponLaserOn = false;

		shoot.shooter.weaponIRLaserOn = true;
		shoot.shooter.nightVisionInUse = true;
		shoot.pcHexRange = 25;
		shoot.setLaserLightALM();
		assertEquals(2, shoot.laserLightALM);
	}

	@Test
	public void rangeALM() {
		shoot.pcHexRange = 10;
		shoot.setRangeALM();
		assertEquals(16, shoot.rangeALM);
	}

	@Test
	public void sizeALM() {
		shoot.setSizeALM();
		assertEquals(7, shoot.sizeALM);

		shoot.target.stance = "Prone";
		shoot.setSizeALM();
		assertEquals(2, shoot.sizeALM);

		shoot.target.stance = "Crouching";
		shoot.setSizeALM();
		assertEquals(5, shoot.sizeALM);

		shoot.target.inCover = true;
		shoot.setSizeALM();
		assertEquals(0, shoot.sizeALM);

	}

	@Test
	public void visibilityALM() {
		assertEquals(false, shoot.shooter.nightVisionInUse);
		shoot.setVisibilityALM();
		assertEquals(0, shoot.visibilityALM);
		shoot.shooter.nightVisionInUse = true;
		shoot.setVisibilityALM();
		assertEquals(0, shoot.visibilityALM);

		Shoot.testVisibility = "Dusk";
		shoot.shooter.nightVisionInUse = true;
		shoot.shooter.nightVisionEffectiveness = 1;
		shoot.setVisibilityALM();
		assertEquals(0, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 2;
		shoot.setVisibilityALM();
		assertEquals(0, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 3;
		shoot.setVisibilityALM();
		assertEquals(0, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 4;
		shoot.setVisibilityALM();
		assertEquals(0, shoot.visibilityALM);
		shoot.shooter.nightVisionInUse = false;
		shoot.setVisibilityALM();
		assertEquals(-2, shoot.visibilityALM);

		Shoot.testVisibility = "Night - Full Moon";
		shoot.shooter.nightVisionInUse = true;
		shoot.shooter.nightVisionEffectiveness = 1;
		shoot.setVisibilityALM();
		assertEquals(0, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 2;
		shoot.setVisibilityALM();
		assertEquals(0, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 3;
		shoot.setVisibilityALM();
		assertEquals(0, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 4;
		shoot.setVisibilityALM();
		assertEquals(0, shoot.visibilityALM);
		shoot.shooter.nightVisionInUse = false;
		shoot.setVisibilityALM();
		assertEquals(-4, shoot.visibilityALM);

		Shoot.testVisibility = "Night - Half Moon";
		shoot.shooter.nightVisionInUse = true;
		shoot.shooter.nightVisionEffectiveness = 1;
		shoot.setVisibilityALM();
		assertEquals(-2, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 2;
		shoot.setVisibilityALM();
		assertEquals(0, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 3;
		shoot.setVisibilityALM();
		assertEquals(0, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 4;
		shoot.setVisibilityALM();
		assertEquals(0, shoot.visibilityALM);
		shoot.shooter.nightVisionInUse = false;
		shoot.setVisibilityALM();
		assertEquals(-6, shoot.visibilityALM);

		Shoot.testVisibility = "Night - No Moon";
		shoot.shooter.nightVisionInUse = true;
		shoot.shooter.nightVisionEffectiveness = 1;
		shoot.setVisibilityALM();
		assertEquals(-8, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 2;
		shoot.setVisibilityALM();
		assertEquals(-6, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 3;
		shoot.setVisibilityALM();
		assertEquals(-3, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 4;
		shoot.setVisibilityALM();
		assertEquals(-2, shoot.visibilityALM);
		shoot.shooter.nightVisionInUse = false;
		shoot.setVisibilityALM();
		assertEquals(-12, shoot.visibilityALM);

		Shoot.testVisibility = "Smoke/Fog/Haze/Overcast";
		shoot.shooter.nightVisionInUse = true;
		shoot.shooter.nightVisionEffectiveness = 1;
		shoot.setVisibilityALM();
		assertEquals(-6, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 2;
		shoot.setVisibilityALM();
		assertEquals(-6, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 3;
		shoot.setVisibilityALM();
		assertEquals(-6, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 4;
		shoot.setVisibilityALM();
		assertEquals(-6, shoot.visibilityALM);
		shoot.shooter.nightVisionInUse = false;
		shoot.setVisibilityALM();
		assertEquals(-6, shoot.visibilityALM);

		Shoot.testVisibility = "Dusk - Smoke/Fog/Haze/Overcast";
		shoot.shooter.nightVisionInUse = true;
		shoot.shooter.nightVisionEffectiveness = 1;
		shoot.setVisibilityALM();
		assertEquals(-8, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 2;
		shoot.setVisibilityALM();
		assertEquals(-8, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 3;
		shoot.setVisibilityALM();
		assertEquals(-8, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 4;
		shoot.setVisibilityALM();
		assertEquals(-8, shoot.visibilityALM);
		shoot.shooter.nightVisionInUse = false;
		shoot.setVisibilityALM();
		assertEquals(-8, shoot.visibilityALM);

		Shoot.testVisibility = "Night - Smoke/Fog/Haze/Overcast";
		shoot.shooter.nightVisionInUse = true;
		shoot.shooter.nightVisionEffectiveness = 1;
		shoot.setVisibilityALM();
		assertEquals(-14, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 2;
		shoot.setVisibilityALM();
		assertEquals(-12, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 3;
		shoot.setVisibilityALM();
		assertEquals(-10, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 4;
		shoot.setVisibilityALM();
		assertEquals(-8, shoot.visibilityALM);
		shoot.shooter.nightVisionInUse = false;
		shoot.setVisibilityALM();
		assertEquals(-14, shoot.visibilityALM);

		Shoot.testVisibility = "No Visibility - Heavy Fog - White Out";
		shoot.shooter.nightVisionInUse = true;
		shoot.shooter.nightVisionEffectiveness = 1;
		shoot.setVisibilityALM();
		assertEquals(-14, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 2;
		shoot.setVisibilityALM();
		assertEquals(-14, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 3;
		shoot.setVisibilityALM();
		assertEquals(-14, shoot.visibilityALM);
		shoot.shooter.nightVisionEffectiveness = 4;
		shoot.setVisibilityALM();
		assertEquals(-14, shoot.visibilityALM);
		shoot.shooter.nightVisionInUse = false;
		shoot.setVisibilityALM();
		assertEquals(-14, shoot.visibilityALM);
	}

	@Test
	public void speedALM() {

		shoot.noSpeed();

		assertEquals(0, shoot.speedALM);

		for (int i = 0; i <= 10; i++) {
			shoot.pcHexRange = i;
			shoot.rushSpeed();
			assertEquals(-10, shoot.speedALM);
		}

		for (int i = 11; i <= 20; i++) {
			shoot.pcHexRange = i;
			shoot.rushSpeed();
			assertEquals(-8, shoot.speedALM);
		}

		for (int i = 21; i <= 40; i++) {
			shoot.pcHexRange = i;
			shoot.rushSpeed();
			assertEquals(-6, shoot.speedALM);
		}

		shoot.pcHexRange = 41;
		shoot.rushSpeed();
		assertEquals(-5, shoot.speedALM);

		for (int i = 0; i <= 10; i++) {
			shoot.pcHexRange = i;
			shoot.walkSpeed();
			assertEquals(-8, shoot.speedALM);
		}

		for (int i = 11; i <= 20; i++) {
			shoot.pcHexRange = i;
			shoot.walkSpeed();
			assertEquals(-6, shoot.speedALM);
		}

		for (int i = 21; i <= 40; i++) {
			shoot.pcHexRange = i;
			shoot.walkSpeed();
			assertEquals(-5, shoot.speedALM);
		}

		shoot.pcHexRange = 41;
		shoot.walkSpeed();
		assertEquals(-5, shoot.speedALM);
		shoot.target = targetUnit.individuals.get(1);
		shoot.walkSpeed();
		assertEquals(0, shoot.speedALM);

		shoot.testAction = 2;
		shoot.walkSpeed();
		assertEquals(-5, shoot.speedALM);
		shoot.target = targetUnit.individuals.get(2);
		shoot.walkSpeed();
		assertEquals(0, shoot.speedALM);

		shoot.testAction = 3;
		shoot.walkSpeed();
		assertEquals(-5, shoot.speedALM);
		shoot.target = targetUnit.individuals.get(3);
		shoot.walkSpeed();
		assertEquals(0, shoot.speedALM);

		shoot.testAction = 1;
		shoot.walkSpeed();
		assertEquals(-5, shoot.speedALM);

		shoot.testAction = 4;
		shoot.walkSpeed();
		assertEquals(0, shoot.speedALM);
		shoot.target = targetUnit.individuals.get(1);
		shoot.walkSpeed();
		assertEquals(-5, shoot.speedALM);

		shoot.testAction = 1;
		shoot.target = targetUnit.individuals.get(0);

		shoot.targetUnit.speed = "None";
		shoot.setSpeedALM();
		assertEquals(0, shoot.speedALM);

		shoot.targetUnit.speed = "Rush";
		shoot.setSpeedALM();
		assertEquals(-5, shoot.speedALM);

		shoot.targetUnit.speed = "Walk";
		shoot.pcHexRange = 10;
		shoot.setSpeedALM();
		assertEquals(-8, shoot.speedALM);

	}
}
