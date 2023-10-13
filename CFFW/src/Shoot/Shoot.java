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

import Actions.Spot;
import Conflict.GameWindow;
import Conflict.InjuryLog;
import CorditeExpansion.Cord;
import CorditeExpansion.FullAuto;
import CorditeExpansion.FullAuto.FullAutoResults;
import HexGrid.CalculateLOS;
import Injuries.Explosion;
import Injuries.ResolveHits;
import Items.Item;
import Items.PCAmmo;
import Items.Weapons;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.DiceRoller;
import UtilityClasses.ExcelUtility;
import UtilityClasses.PCUtility;

public class Shoot {

	public static ArrayList<Shoot> shootActions = new ArrayList<>();

	public int spentCombatActions;
	public int startingAimTime;
	public int shots;
	public int previouslySpentCa;
	public int fullAutoShots;

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
	// Stance ALM
	// Launcher, homing
	// Launcher ammo
	// bonuses, defensive ALM 

	public int pcHexRange;
	public int rangeALM;
	public int speedALM;
	public int sizeALM;
	public int visibilityALM;
	public int laserLightALM;
	public int stanceALM;

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
	public PCAmmo pcAmmo;
	public ArrayList<Integer> calledShotBounds = new ArrayList<Integer>();
	public String calledShotLocation;

	String autofireResults;
	public String shotResults;
	
	
	public int percentBonus; 
	public int ealBonus; 
	public int hiddenEalBonus;
	public int ealConcurrentBonus; 
	
	public boolean outOfAmmo = false;

	public Shoot(Unit shooterUnit, Unit targetUnit, Trooper shooter, Trooper target, String wepName, int ammoIndex) {
		this.shooter = shooter;
		this.target = target;
		this.shooterUnit = shooterUnit;
		this.targetUnit = targetUnit;

		spentCombatActions = 0;

		if (target != null && shooter.storedAimTime.containsKey(target)) {
			aimTime = shooter.storedAimTime.get(target);
			System.out.println("stored aim time: "+aimTime);
		}

		wep = new Weapons().findWeapon(wepName);
		maxAim = wep.aimTime.size() - 1;

		pcAmmo = ammoIndex < 0 || ammoIndex >= wep.pcAmmoTypes.size() ? null : wep.pcAmmoTypes.get(ammoIndex);

		setDistance();
		setStartingAim();
		recalc();
	}

	public void setBonuses(int percent, int eal, int ealConcurrent) {
		this.percentBonus = percent; 
		this.ealBonus = eal; 
		this.ealConcurrentBonus = ealConcurrent;
		setALM();
		setEAL();
		setSingleTn();
		setFullAutoTn();
	}

	public void shot(boolean homing) {
		if(pcAmmo != null && pcAmmo.shots != -1 && !shooter.inventory.launcherAmmoCheck(wep, pcAmmo, 1)) {
			shotResults = "Not enough ammunition.";
			outOfAmmo = true;
			return;
		} else if ((pcAmmo == null || pcAmmo.shots == -1) && !ammoCheckSingle()) {
			System.out.println("shot return");
			shotResults = "Not enough ammunition.";
			outOfAmmo = true;
			return;
		}

		//System.out.println("shot");
		singleShotRoll(homing);
		//System.out.println("shot 2");
		resolveHits();
		//System.out.println("shot 3");
		resolveSuppressiveHits();
		//System.out.println("shot 4");
		spentCombatActions++;
		shots++;
		setShotResults(false);
		recalc();
		hiddenEalBonus += 2;
		hiddenEalBonus += ealConcurrentBonus;
		setALM();
		setEAL();
		setSingleTn();
		setSuppressiveTn();
		setFullAutoTn();
		
		updateInjuryLog();
		
		if(wep == null || wep.tracers) 
			shooter.firedTracers = true;
		shooter.firedWeapons = true;
	}
	
	public void burst() {
		if(pcAmmo != null && !shooter.inventory.launcherAmmoCheck(wep, pcAmmo, wep.fullAutoROF)) {
			shotResults = "Not enough ammunition.";
			return;
		} else if (pcAmmo == null && !ammoCheckFull()) {
			shotResults = "Not enough ammunition.";
			return;
		}

		burstRoll();
		resolveHits();
		resolveSuppressiveHits();
		spentCombatActions++;
		shots++;
		fullAutoShots++;
		setShotResults(true);
		recalc();
		hiddenEalBonus -= wep.sab * fullAutoShots;
		hiddenEalBonus += ealConcurrentBonus;
		setALM();
		setEAL();
		setSingleTn();
		setSuppressiveTn();
		setFullAutoTn();
		
		updateInjuryLog();
		
		if(wep == null || wep.tracers)
			shooter.firedTracers = true;
		shooter.firedWeapons = true;
	}

	public void updateInjuryLog() {
		if(target == null)
			return; 
		System.out.println("Shoot Add trooper log");
		InjuryLog.InjuryLog.addTrooper(target);
		
	}
	
	public void suppressiveFire(int shots) {
		
		System.out.println("Shoot suppressive");
		
		if(pcAmmo != null && !shooter.inventory.launcherAmmoCheck(wep, pcAmmo, shots)) {
			shotResults = "Not enough ammunition.";
			return;
		} else if (pcAmmo == null && !ammoCheckSuppressive(shots)) {
			shotResults = "Not enough ammunition.";
			return;
		}

		for (int i = 0; i < shots; i++) {
			suppressiveShotRoll(DiceRoller.roll(0, 99));
		}

		shotResults = "Suppresive fire from " + shooterUnit.callsign + " to " 
				+ targetUnit.callsign + ": " + suppressiveHits + " hits.";
		
		spentCombatActions = shooter.combatActions;
		this.shots = shooter.combatActions;
		resolveHits();
		resolveSuppressiveHits();
		
		if(wep.tracers)
			shooter.firedTracers = true;
		shooter.firedWeapons = true;
	}
	
	public void suppressiveFireFree(int shots) {
		
		System.out.println("Shoot suppressive free");
		
		if(pcAmmo != null && !shooter.inventory.launcherAmmoCheck(wep, pcAmmo, shots)) {
			shotResults = "Not enough ammunition.";
			return;
		} else if (pcAmmo == null && !ammoCheckSuppressive(shots)) {
			shotResults = "Not enough ammunition.";
			return;
		}

		for (int i = 0; i < shots; i++) {
			suppressiveShotRoll(DiceRoller.roll(0, 99));
		}

		shotResults += "Suppresive fire from " + shooterUnit.callsign + " to " 
				+ targetUnit.callsign + ": " + suppressiveHits + " hits.";
		
		//spentCombatActions = shooter.combatActions;
		//this.shots = shooter.combatActions;
		resolveHits();
		resolveSuppressiveHits();
		
		if(wep == null || wep.tracers)
			shooter.firedTracers = true;
		shooter.firedWeapons = true;
	}
	
	public void setShotResults(boolean fullAuto) {
		
		if(target != null) {
			shotResults = (fullAuto ? "Full Auto Burst from " : "Single Shot from ") + shooterUnit.callsign + ": "
					+ shooter.number + " " + shooter.name + " to " + targetUnit.callsign + ": " + target.number + " "
					+ target.name + ", Using: "+wep.name + (pcAmmo != null ? ": " +pcAmmo.name +"round.": "");
		}

		if (fullAuto) {
			shotResults += " " + "Elevation roll: " + shotRoll + ", Elevation TN: " + fullAutoTn + ", Second Roll: "
					+ burstRoll + ", AutoFire Results: " + autofireResults;
		} else {
			shotResults += " " + "Shot roll: " + shotRoll + ", Shot TN: " + singleTn + ", Supp TN: " + suppressiveTn;
		}

		shotResults += ", EAL Sum: " + ealSum + ", ALM Sum: " + almSum + ", Range ALM: " + rangeALM + ", Speed ALM: "
				+ speedALM + ", Size ALM: " + sizeALM + ", Visibility ALM: " + visibilityALM + ", Laser Light ALM: "
				+ laserLightALM + ", Stance ALM: " + stanceALM + ", Aim ALM: " + aimALM + ", Aim Time: " + aimTime
				+ ", Max Aim: " + maxAim;

		if (calledShotBounds.size() > 0) {
			shotResults += ", Called Shot: " + calledShotLocation + ", Bounds: " + calledShotBounds.get(0) + ", "
					+ calledShotBounds.get(1) + ", " + calledShotBounds.get(2) + ", " + calledShotBounds.get(3);
		}

	}

	public void setDistance() {
		pcHexRange = GameWindow.hexDif(targetUnit, shooterUnit) * GameWindow.hexSize;

		if (pcHexRange == 0)
			setCloseCombatDistance();

	}

	public void updateWeapon(String wep) {
		this.wep = new Weapons().findWeapon(wep);
	}

	public boolean ammoCheckSingle() {
		
		System.out.println("Ammo Check Single");
		
		if(wep.type.equals("Static")) {
			if(wep.ammoLoaded <= 0) {
				outOfAmmo = true;
				return false; 
			}
			wep.ammoLoaded--; 
			return true;
		}
		
		boolean results = shooter.inventory.fireShots(1, wep);
		
		if(!results)
			outOfAmmo = true;
		
		return results; 
	}

	public boolean ammoCheckFull() {
		if(wep.type.equals("Static")) {
			if(wep.ammoLoaded <= 0) {
				outOfAmmo = true;
				return false; 
			}
			wep.ammoLoaded-=wep.fullAutoROF; 
			return true;
		}
		
		boolean results = shooter.inventory.fireShots(wep.fullAutoROF, wep);
		
		if(!results)
			outOfAmmo = true;
		
		return results;
	}

	public boolean ammoCheckSuppressive(int suppShots) {
		if(wep.type.equals("Static")) {
			if(wep.ammoLoaded <= 0) {
				outOfAmmo = true;
				return false; 
			}
			wep.ammoLoaded-=suppShots; 
			return true;
		}
		
		boolean results =  shooter.inventory.fireShots(suppShots, wep);
		
		if(!results)
			outOfAmmo = true;
		
		return results;
	}

	public void suppressiveShotRoll(int roll) {
		if (roll <= suppressiveTn) {
			System.out.println("Plus Suppressive Hits 2");
			suppressiveHits++;
			if (DiceRoller.roll(0, 99) <= 0)
				hits++;
		}
	}

	public void singleShotRoll(boolean homing) {
		shotRoll = DiceRoller.roll(0, 99);

		if (shotRoll <= (!homing ? singleTn : wep.homingHitChance)) {
			hits++;
			suppressiveHits++;
			System.out.println("Plus Suppressive Hits");
		} else {
			suppressiveShotRoll(shotRoll);
		}
	}

	public void burstRoll() {
		shotRoll = DiceRoller.roll(0, 99);

		for (int i = 0; i < wep.fullAutoROF; i++) {
			suppressiveShotRoll(shotRoll);
		}

		if (shotRoll <= fullAutoTn) {
			double ma = wep.getMA(pcHexRange);
			// System.out.println("MA: "+ma);
			int rof = wep.fullAutoROF;
			// System.out.println("ROF: "+rof);

			try {
				autofireResults = ExcelUtility.getStringFromSheet(rof, ma, "\\PC Hit Calc Xlsxs\\automaticfire.xlsx",
						true, true);
				if (FullAuto.hits(autofireResults)) {
					hits = FullAuto.getNumericResults(autofireResults);
				} else {
					int tn = FullAuto.getNumericResults(autofireResults);
					burstRoll = DiceRoller.roll(0, 99);
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
			autofireTable = ExcelUtility.getStringFromSheet(rof, ma, "\\PC Hit Calc Xlsxs\\automaticfire.xlsx", true,
					true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return autofireTable;
	}

	public void explosionCheck() {
		if (pcAmmo != null) {
			System.out.println("explode");
			Explosion explosion = new Explosion(pcAmmo);
			explosion.excludeTroopers.add(target);
			
			for(int i = 0; i < hits; i++) {
				explosion.explosiveImpact(target, pcAmmo, wep);
				explosion.explodeTrooper(target, 0);
			}
			
			if(suppressiveHits > 0)
				explosion.explodeHex(targetUnit.X, targetUnit.Y, shooterUnit.side);
		} else {
			System.out.println("null ammo");
		}
	}
	
	public void resolveHits() {
		if(pcAmmo != null)
			return;
		
		while (hits > 0) {
			Trooper target = this.target;
			
			if(this.target == null) {
				target = targetUnit.individuals.get(DiceRoller.roll(0, targetUnit.individuals.size()-1));
			}

			if(target.HD) {
				hits--;
				continue;
			}
			
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
			InjuryLog.InjuryLog.addTrooper(target);
		}

	}

	public void resolveSuppressiveHits() {
		if(suppressiveHits > 0)
			explosionCheck();
		
		if(suppressiveHits > 1 && !canSeeAtLeastOneEnemy())
			suppressiveHits /= 2;
		
		if(GameWindow.gameWindow != null && GameWindow.gameWindow.game != null)
			applyFortificationModifiers();
		
		
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

		if(suppressiveHits % 2 == 0)
			suppressiveHits = 0; 
		else 
			suppressiveHits = 1;
	}
	
	private void applyFortificationModifiers() {
		var level = GameWindow.gameWindow.game.fortifications.getTrenchesLevel(new Cord(targetUnit.X, targetUnit.Y));
		if(level == 2) {
			suppressiveHits /= 2;
		} else if(level >= 3) {
			suppressiveHits /= 3;
		}
	}
	
	public boolean canSeeAtLeastOneEnemy() {
		
		for(Trooper trooper : shooterUnit.individuals) {
			for(Spot spot : trooper.spotted) {
				for(Trooper spottedTrooper : spot.spottedIndividuals) {
					if(targetUnit.individuals.contains(spottedTrooper))
						return true;
				}
			}
		}
		
		return false;
	}

	public void setSingleTn() {
		singleTn = PCUtility.getOddsOfHitting(true, ealSum) + percentBonus - (int)((double) shooterUnit.suppression * PCUtility.suppressionPenalty(shooter));
	}

	public void setSuppressiveTn() {
		suppressiveTn = PCUtility.getOddsOfHitting(true, almSum + 18) + percentBonus - (int)((double) shooterUnit.suppression * PCUtility.suppressionPenalty(shooter));
	}

	public void setFullAutoTn() {
		fullAutoTn = PCUtility.getOddsOfHitting(false, ealSum) + percentBonus - (int)((double) shooterUnit.suppression * PCUtility.suppressionPenalty(shooter));
	}

	public void autoAim() {
		System.out.println("auto aim");
		aimTime = startingAimTime; 
		spentCombatActions = 0 + shots + previouslySpentCa;
		shooter.storedAimTime.clear();
		while (ealSum <= 17 && spentCombatActions + 1 < shooter.combatActions && canAim()) {
			System.out.println("aim action");
			setAimTime(aimTime + 1);
		}

	}

	public boolean canAim() {
		if (aimTime >= maxAim)
			return false;
		else
			return true;
	}

	public void setStartingAim() {
		if (target != null && shooter.storedAimTime.get(target) != null) {
			aimTime = shooter.storedAimTime.get(target);
			startingAimTime = shooter.storedAimTime.get(target);
		} else {
			aimTime = 0;
		}
	}

	public void setAimTime(int newTime) {
		// System.out.println("Aim Time: "+aimTime+", New Time: "+newTime);
		if(newTime >= wep.aimTime.size())
			newTime = wep.aimTime.size() - 1;
		
		
		if (aimTime < startingAimTime && newTime > startingAimTime)
			spentCombatActions += newTime - startingAimTime;
		else if (newTime > startingAimTime)
			spentCombatActions += newTime - aimTime;
		else if (newTime <= startingAimTime)
			spentCombatActions = 0 + shots + previouslySpentCa;

		if (newTime != aimTime) {
			System.out.println("set aim time");
			aimTime = newTime;
			setAimBonus();
			setALM();
			setEAL();
			setSingleTn();
			setSuppressiveTn();
			setFullAutoTn();
			shooter.storedAimTime.clear();
			if(target != null ) {
				shooter.storedAimTime.put(target, aimTime);
			}
		}
		setShotResults(false);
		//System.out.println(shotResults);
	}

	public void setAimBonus() {
		if(aimTime >= wep.aimTime.size() )
			aimTime = wep.aimTime.size() - 1;
		
		aimALM = wep.aimTime.get(aimTime) + shooter.sl;
		System.out.println("AimALM: "+aimALM+", aim time: "+aimTime);
	}

	public void setStanceALM() {

		if (wep.staticWeapon && wep.assembled) {
			stanceALM = 10;
		} else if (shooter.inCover || (shooter.stance == "Prone" && !wep.staticWeapon)) {
			stanceALM = 7;
		} else if (shooter.stance == "Crouched") {
			stanceALM = 3;
		}

	}

	public static int count;
	public void calculateModifiers() {
		count++;
		System.out.println("Calculate Modifiers: "+count);
		setRangeALM();
		setVisibilityALM();
		setSpeedALM();
		setSizeALM();
		setLaserLightALM();
		setAimBonus();
		setStanceALM();
	}
	
	public void recalc() {
		calculateModifiers();
		setALM();
		setEAL();
		setSingleTn();
		setSuppressiveTn();
		setFullAutoTn();
	}

	public void setALM() {
		almSum = rangeALM + visibilityALM + speedALM + laserLightALM + aimALM + 
				stanceALM + ealBonus + (target != null ? target.DALM : 0);
	}

	public void setEAL() {
		ealSum = rangeALM + visibilityALM + sizeALM + speedALM + laserLightALM + 
				aimALM + stanceALM + ealBonus + (target != null ? target.DALM : 0) + hiddenEalBonus;
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
		if(target == null)
			return false; 
		
		// Checks if individual is moving
		int action = GameWindow.gameWindow != null ? GameWindow.gameWindow.game.getCurrentAction() : testAction;
		int count = 1;

		for (Trooper trooper : targetUnit.individuals) {
			int roll = DiceRoller.roll(1, 3);
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
		
		visibilityALM += CalculateLOS.getConcealmentAlm(shooterUnit, targetUnit);
		
	}

	public void setSizeALM() {
		if (target == null) {
			sizeALM = 0;
			return;
		}

		sizeALM = PCUtility.findSizeALM(target.stance, target.PCSize, target.inCover);
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
		if(target == null)
			return;
		
		// System.out.println(" Close Combat Calculations:");
		if (shooter.pcRanges.containsKey(target)) {
			pcHexRange = shooter.pcRanges.get(target);
		} else if (target.pcRanges.containsKey(shooter)) {
			pcHexRange = target.pcRanges.get(shooter);
		} else {
			int range = DiceRoller.roll(1, 10);
			shooter.pcRanges.put(target, range);
			pcHexRange = range;
		}

	}

	public void setCalledShotBounds(int calledShotTarget) {

		// System.out.println("Inside set called shot bounds");

		if (calledShotTarget == 1) {
			calledShotLocation = "Head";
		} else if (calledShotTarget == 2) {
			calledShotLocation = "Body";
		} else if (calledShotTarget == 3) {
			calledShotLocation = "Legs";
		} else {

		}

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
