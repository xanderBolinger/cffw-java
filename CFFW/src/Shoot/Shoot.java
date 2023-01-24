package Shoot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Conflict.GameWindow;
import CorditeExpansion.FullAuto;
import CorditeExpansion.FullAuto.FullAutoResults;
import Injuries.ResolveHits;
import Items.Weapons;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;
import UtilityClasses.ExcelUtility;
import UtilityClasses.PCUtility;

public class Shoot {

	public static ArrayList<Shoot> shootActions = new ArrayList<>();

	public int spentCombatActions;

	public Trooper shooter;
	public Unit shooterUnit;

	public Trooper target;
	public Unit targetUnit;

	// Required methods
	// calculate ALM
	// calculate EAL
	// get TN single
	// get TN full auto
	// get full auto results
	// shot
	// full auto shot
	// called shot
	// on miss suppressive fire
	// shoot suppressive fire
	// aim, max aim 2, rush <= 20 hexes
	// Percent penalty, alm bonus 
	// Recurring alm bonus 

	// Ammo check
	// Resolve Hits

	public int pcHexRange;
	public int rangeALM;
	public int speedALM;
	public int sizeALM;
	public int visibilityALM;
	public int laserLightALM;

	public int aimBonus;
	public int aimALM;
	public int aimTime;
	public int maxAim;

	public int almSum;
	public int ealSum;

	public int singleTn;
	public int fullAutoTn;
	public int suppressiveTn;

	public int shotRoll;
	public int burstRoll;
	public int hits;
	public int suppressiveHits;

	public Weapons wep;

	public ArrayList<Integer> calledShotBounds = new ArrayList<Integer>();

	public Shoot(Unit shooterUnit, Unit targetUnit, Trooper shooter, Trooper target) {
		this.shooter = shooter;
		this.target = target;
		this.shooterUnit = shooterUnit;
		this.targetUnit = targetUnit;

		spentCombatActions = 0;

		if (shooter.storedAimTime.containsKey(target)) {
			aimTime = shooter.storedAimTime.get(target);
		}

		wep = new Weapons().findWeapon(shooter.wep);
		maxAim = wep.aimTime.size() - 1;

		setDistance();
	}

	public void setBonuses(int percent, int eal, int ealConcurrent) {
		
	}
	
	public void shot() {
		if (!ammoCheckSingle()) {
			return;
		}

		singleShotRoll();
		resolveHits();
		resolveSuppressiveHits();
		ealSum += 2;
		spentCombatActions++;
		setSingleTn();
		setSuppressiveTn();
		setFullAutoTn();
	}

	public void burst() {
		if (!ammoCheckFull()) {
			return;
		}

		burstRoll();
		resolveHits();
		resolveSuppressiveHits();
		ealSum -= wep.sab;
		spentCombatActions++;
		setSingleTn();
		setSuppressiveTn();
		setFullAutoTn();
	}

	public void suppressiveFire(int shots) {
		if (!ammoCheckSuppressive(shots))
			return;

		for (int i = 0; i < shots; i++) {
			suppressiveShotRoll(DiceRoller.randInt(0, 99));
		}

		resolveHits();
		resolveSuppressiveHits();
	}

	public void updateTarget(Unit targetUnit, Trooper target) {
		this.targetUnit = targetUnit;
		this.target = target;

		setDistance();
		setStartingAim();
		calculateModifiers();
	}

	public void setDistance() {
		pcHexRange = GameWindow.hexDif(targetUnit, shooterUnit) * GameWindow.hexSize;

		if (pcHexRange == 0)
			setCloseCombatDistance();

	}

	public void updateWeapon(String wep) {
		this.wep = new Weapons().findWeapon(wep);
		calculateModifiers();
	}

	public boolean ammoCheckSingle() {
		return shooter.inventory.fireShots(1, wep);
	}

	public boolean ammoCheckFull() {
		return shooter.inventory.fireShots(wep.fullAutoROF, wep);
	}

	public boolean ammoCheckSuppressive(int suppShots) {
		return shooter.inventory.fireShots(suppShots, wep);
	}

	public void suppressiveShotRoll(int roll) {
		if (roll <= suppressiveTn) {
			suppressiveHits++;
			if (DiceRoller.randInt(0, 99) <= 0)
				hits++;
		}
	}

	public void singleShotRoll() {
		shotRoll = DiceRoller.randInt(0, 99);

		if (shotRoll <= singleTn) {
			hits++;
			suppressiveHits++;
		} else {
			suppressiveShotRoll(burstRoll);
		}
	}

	public void burstRoll() {
		shotRoll = DiceRoller.randInt(0, 99);

		for (int i = 0; i < wep.fullAutoROF; i++) {
			suppressiveShotRoll(shotRoll);
		}

		if (shotRoll <= fullAutoTn) {
			double ma = wep.getMA(pcHexRange);
			// System.out.println("MA: "+ma);
			int rof = wep.fullAutoROF;
			// System.out.println("ROF: "+rof);
			String autofireTable;
			try {
				autofireTable = ExcelUtility.getStringFromSheet(rof, ma, "\\PC Hit Calc Xlsxs\\automaticfire.xlsx",
						true, true);
				if (FullAuto.hits(autofireTable)) {
					hits = FullAuto.getNumericResults(autofireTable);
				} else {
					int tn = FullAuto.getNumericResults(autofireTable);
					burstRoll = DiceRoller.randInt(0, 99);
					if (burstRoll <= tn) {
						hits++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String fullAutoResults() {
		double ma = wep.getMA(pcHexRange);
		int rof = wep.fullAutoROF;
		String autofireTable = "";
		try {
			autofireTable = ExcelUtility.getStringFromSheet(rof, ma, "\\PC Hit Calc Xlsxs\\automaticfire.xlsx",
					true, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return autofireTable;
	}
	
	public void resolveHits() {

		while (hits > 0) {

			ResolveHits resolveHits = new ResolveHits(target, hits, wep,
					GameWindow.gameWindow != null ? GameWindow.gameWindow.conflictLog : null, targetUnit, shooterUnit,
					GameWindow.gameWindow);

			if (calledShotBounds.size() > 0) {
				resolveHits.calledShot = true;
				resolveHits.calledShotBounds = calledShotBounds;
			}

			if (GameWindow.gameWindow != null)
				resolveHits.performCalculations(GameWindow.gameWindow.game, GameWindow.gameWindow.conflictLog);

			hits--;
		}

	}

	public void resolveSuppressiveHits() {
		if (targetUnit.suppression + suppressiveHits < 100) {
			targetUnit.suppression += suppressiveHits / 2;
		} else {
			targetUnit.suppression = 100;
		}

		if (targetUnit.organization - suppressiveHits > 0) {
			targetUnit.organization -= suppressiveHits / 2;
		} else {
			targetUnit.organization = 0;
		}

		suppressiveHits = 0;
	}

	public void setSingleTn() {
		singleTn = PCUtility.getOddsOfHitting(true, ealSum);
	}

	public void setSuppressiveTn() {
		suppressiveTn = PCUtility.getOddsOfHitting(true, almSum + 18);
	}

	public void setFullAutoTn() {
		fullAutoTn = PCUtility.getOddsOfHitting(false, ealSum);
	}

	public boolean canAim() {
		if (aimTime >= maxAim)
			return false;
		else
			return true;
	}

	public void aimAction() {
		if (!canAim())
			return;

		if(target != null && shooter.storedAimTime.get(target) != null) {
			aimTime = shooter.storedAimTime.get(target);
		}
		
		aimTime++;
		spentCombatActions++;

		shooter.storedAimTime.clear();
		shooter.storedAimTime.put(target, aimTime);
	}

	public void setStartingAim() {
		if(target != null && shooter.storedAimTime.get(target) != null) {
			aimTime = shooter.storedAimTime.get(target);
		} else {
			aimTime = 0;
		}
	}
	
	public void setAimTime(int newTime) {
		setStartingAim();
		System.out.println("Aim Time: "+aimTime+", New Time: "+newTime);
		spentCombatActions +=  newTime - aimTime;
		
		if(spentCombatActions < 0)
			spentCombatActions = 0;
		
		aimTime = newTime;
		
	}
	
	public void setAimBonus() {
		aimBonus = wep.aimTime.get(target != null && shooter.storedAimTime.get(target) != null ? shooter.storedAimTime.get(target) : aimTime)
				+ shooter.sl;
	}

	public void calculateModifiers() {
		setRangeALM();
		setVisibilityALM();
		setSpeedALM();
		setSizeALM();
		setLaserLightALM();
		setAimBonus();
	}

	public void setALM() {
		almSum = rangeALM + visibilityALM + speedALM + laserLightALM + aimBonus;
	}

	public void setEAL() {
		ealSum = rangeALM + visibilityALM + sizeALM + speedALM + laserLightALM + aimBonus;
	}

	public void noSpeed() {
		speedALM = 0;
	}

	public void rushSpeed() {
		if (pcHexRange <= 10) {
			speedALM = -10;
		} else if (pcHexRange <= 20) {
			speedALM = -8;
		} else if (pcHexRange <= 40) {
			speedALM = -6;
		} else {
			speedALM = -5;
		}
	}

	public void walkSpeed() {
		int penalty;

		if (pcHexRange <= 10) {
			penalty = -8;
		} else if (pcHexRange <= 20) {
			penalty = -6;
		} else {
			penalty = -5;
		}

		if (walkMoving())
			speedALM = penalty;
		else
			speedALM = 0;
	}

	public boolean walkMoving() {
		// Checks if individual is moving
		int action = GameWindow.gameWindow != null ? GameWindow.gameWindow.game.getCurrentAction() : testAction;
		int count = 1;

		for (Trooper trooper : targetUnit.individuals) {
			int roll = DiceRoller.randInt(1, 3);
			if (trooper.compareTo(target)) {
				// System.out.println("Roll: "+roll+", Count: "+count);
				if ((action == 1 && count == 1) || (action == 2 && count == 2) || (action == 3 && count == 3)
						|| (count == roll && action > 3)) {
					return true;
				}
			}

			if (count == 3)
				count = 1;
			else
				count++;
		}

		return false;
	}

	public void setRangeALM() {
		rangeALM = PCUtility.findRangeALM(pcHexRange);
	}

	public void setSpeedALM() {
		String speed = targetUnit.speed;

		if (speed.equals("None")) {
			noSpeed();
		} else if (speed.equals("Rush")) {
			rushSpeed();
		} else {
			walkSpeed();
		}

	}

	public void setVisibilityALM() {
		visibilityALM = PCUtility.findVisibiltyALM(targetUnit, shooter, pcHexRange);
	}

	public void setSizeALM() {
		if (target == null || target.inCover) {
			sizeALM = 0;
			return;
		}

		sizeALM = PCUtility.findSizeALM(target.stance, target.PCSize);
	}

	public void setLaserLightALM() {

		int alm = 0;

		String visibility = GameWindow.gameWindow != null ? GameWindow.gameWindow.visibility : Shoot.testVisibility;

		if (visibility.contains("Night") && shooter.weaponLightOn && pcHexRange <= 10) {
			alm += 6;
		}

		if (visibility.contains("Night") && target != null && target.weaponLightOn && pcHexRange <= 10) {
			alm += -8;
		}

		if (shooter.weaponLaserOn && pcHexRange <= 10) {
			alm += 2;
		} else if (shooter.weaponIRLaserOn && pcHexRange <= 25 && shooter.nightVisionInUse) {
			alm += 2;
		}

		laserLightALM = alm;

	}

	public void setCloseCombatDistance() {
		// System.out.println(" Close Combat Calculations:");
		if (shooter.pcRanges.containsKey(target)) {
			pcHexRange = shooter.pcRanges.get(target);
		} else if (target.pcRanges.containsKey(shooter)) {
			pcHexRange = target.pcRanges.get(shooter);
		} else {
			int range = DiceRoller.randInt(1, 10);
			shooter.pcRanges.put(target, range);
			pcHexRange = range;
		}

	}

	public void setCalledShotBounds(int calledShotTarget) {

		// System.out.println("Inside set called shot bounds");

		int sheetIndex = calledShotTarget - 1;

		if (sheetIndex <= -1)
			return;

		calledShotBounds.clear();

		try {

			FileInputStream excelFile = new FileInputStream(new File(ExcelUtility.path + "\\hitlocationzones.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			org.apache.poi.ss.usermodel.Sheet worksheet = workbook.getSheetAt(sheetIndex);

			// System.out.println("Work book 1");

			int ss = 0;

			for (int i = 1; i < 25; i++) {

				Row row = worksheet.getRow(i);

				Cell myCell = row.getCell(1);

				if (myCell.getCellType() == CellType.NUMERIC) {

					// System.out.println("Cell: "+myCell.getNumericCellValue());

					if (almSum <= myCell.getNumericCellValue()) {
						// System.out.println("ALMSum: "+ALMSum);
						// System.out.println("calledShotBounds: "+calledShotBounds.size());

						Cell low1 = row.getCell(4);
						Cell high1 = row.getCell(5);
						Cell low2 = row.getCell(6);
						Cell high2 = row.getCell(7);

						// System.out.println("Get Cells");

						int lowBound1;
						int lowBound2;
						int highBound1;
						int highBound2;

						if (low1.getCellType() != CellType.NUMERIC) {
							// System.out.println("Pass 1");
							lowBound1 = -1;
						} else {
							// System.out.println("Pass 2");
							lowBound1 = (int) low1.getNumericCellValue();
						}

						if (low2.getCellType() != CellType.NUMERIC) {
							// System.out.println("Pass 3");
							lowBound2 = -1;
						} else {
							// System.out.println("Pass 4");
							lowBound2 = (int) low2.getNumericCellValue();
						}

						if (high1.getCellType() != CellType.NUMERIC) {
							// System.out.println("Pass 5");
							highBound1 = -1;
						} else {
							// System.out.println("Pass 6");
							highBound1 = (int) high1.getNumericCellValue();
						}

						if (high2.getCellType() != CellType.NUMERIC) {
							// System.out.println("Pass 7");
							highBound2 = -1;
						} else {
							// System.out.println("Pass 8");
							highBound2 = (int) high2.getNumericCellValue();
						}

						/*
						 * System.out.println("bounds: "+lowBound1+" "+lowBound2+" "+ highBound1 + " "+
						 * highBound2);
						 */

						calledShotBounds.add(lowBound1);
						calledShotBounds.add(highBound1);
						calledShotBounds.add(lowBound2);
						calledShotBounds.add(highBound2);

						ss = (int) row.getCell(2).getNumericCellValue();
						// System.out.println("SS: "+ss);
						// System.out.println("calledShotBounds: "+calledShotBounds.size());
						break;
					}
				}

				// System.out.println("i: "+i);

			}

			// System.out.println("Work book 2");
			// System.out.println("SS: " + ss);
			if (ss < sizeALM)
				sizeALM = ss;

			if (calledShotBounds.get(2) < 1)
				calledShotBounds.set(2, -1);

			if (calledShotBounds.get(3) < 1)
				calledShotBounds.set(3, -1);

			workbook.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Below variables and methods are used for testing purposes
	public int testAction = 1;
	public static String testVisibility = "Good Visibility";
}
