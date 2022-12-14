package CorditeExpansionActions;

import java.util.ArrayList;

import CeHexGrid.FloatingTextManager;
import Conflict.GameWindow;
import CorditeExpansion.Cord;
import CorditeExpansion.CorditeExpansionGame;
import CorditeExpansion.FullAuto;
import CorditeExpansion.FullAuto.FullAutoResults;
import CorditeExpansionDamage.Damage;
import CorditeExpansionRangedCombat.CalledShots;
import CorditeExpansionRangedCombat.CalledShots.ShotTarget;
import CorditeExpansionStatBlock.StatBlock;
import CorditeExpansionStatBlock.StatBlock.MoveSpeed;
import CorditeExpansionStatBlock.StatBlock.Stance;
import Items.Weapons;
import Trooper.Trooper;
import UtilityClasses.DiceRoller;
import UtilityClasses.ExcelUtility;
import UtilityClasses.PCUtility;
import UtilityClasses.PcDamageUtility;

public class FireAction implements CeAction {

	public Trooper target;
	public ArrayList<Cord> suppressHexes = new ArrayList<Cord>();

	public StatBlock statBlock;
	int coac = 2;
	int spentCoac = 0;
	
	int sustainedBurst = 0;

	public FireAction(StatBlock statBlock, Trooper target) {
		this.statBlock = statBlock;
		this.target = target;
		if(statBlock.rangedStatBlock.aimTarget == null)
			statBlock.rangedStatBlock.aimTarget = target;
	}
	
	public FireAction(StatBlock statBlock, Cord cord) {
		this.statBlock = statBlock;
		suppressHexes.add(cord);
	}
	
	public boolean followUpAim = false;
	
	@Override
	public void spendCombatAction() {
		if (!ready()) {
			spentCoac++;
			return;
		}
		
		if(suppressHexes.size() > 0) {
			for(Cord cord : suppressHexes) {
				try {
					suppress(cord);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return; 
		}
		
		try {
			if(statBlock.rangedStatBlock.fullAuto)
				fullAutoBurst();
			else
				shot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void suppress(Cord cord) throws Exception {
		FloatingTextManager.addFloatingText(cord, "Suppress Hex: "+cord.toString());
		
		if(statBlock.rangedStatBlock.fullAuto) 
			suppressFullAuto(cord);
		else
			suppressSingle(cord);
		
	}
	
	public void suppressSingle(Cord cord) throws Exception {
		if(statBlock.rangedStatBlock.weapon.ceStats.criticalHit) {
			FloatingTextManager.addFloatingText(statBlock.cord, "Weapon destroyed via critical hit.");
			return;
		}
		
		if(statBlock.rangedStatBlock.weapon.ceStats.magazine == null || !statBlock.rangedStatBlock.weapon.ceStats.ammoCheck()) {
			FloatingTextManager.addFloatingText(statBlock.cord, "Weapon out of ammo, DP: "+ 
		(statBlock.rangedStatBlock.weapon.ceStats.magazine != null ? statBlock.rangedStatBlock.weapon.ceStats.magazine.ammo.depletionPoints : "No Magazine"));
			return;
		}
		
		int eal = calculateEAL(cord);
		int range = statBlock.getDistance(cord);
		int odds = PCUtility.getOddsOfHitting(true, eal);
		int penalty = shotPenalty(statBlock);
		int roll = DiceRoller.randInt(0, 99);
		int hits = 0; 
		
		if(roll + penalty < odds) {
			hits = 1; 
		}
		statBlock.rangedStatBlock.weapon.ceStats.singleFire();
		applySuppressHits(hits, cord, false, range);
	}
	
	public void suppressFullAuto(Cord cord) throws Exception {
		if(statBlock.rangedStatBlock.weapon.ceStats.criticalHit) {
			FloatingTextManager.addFloatingText(statBlock.cord, "Weapon destroyed via critical hit.");
			return;
		}
		
		if(statBlock.rangedStatBlock.weapon.ceStats.magazine == null || !statBlock.rangedStatBlock.weapon.ceStats.ammoCheck()) {
			FloatingTextManager.addFloatingText(statBlock.cord, "Weapon out of ammo, DP: "+ 
		(statBlock.rangedStatBlock.weapon.ceStats.magazine != null ? statBlock.rangedStatBlock.weapon.ceStats.magazine.ammo.depletionPoints : "No Magazine"));
			return;
		}
		
		int range = statBlock.getDistance(cord);
		int eal = calculateEAL(cord);
		
		eal -= statBlock.rangedStatBlock.weapon.sab * sustainedBurst;
		
		double ma = statBlock.rangedStatBlock.weapon.getMA(range);
		int rof = statBlock.rangedStatBlock.weapon.fullAutoROF;
		
		FullAutoResults far = FullAuto.burst(eal, ma, rof, statBlock);
		
		applySuppressHits(far.hits, cord, true, range);
		
		statBlock.rangedStatBlock.weapon.ceStats.burst(statBlock.rangedStatBlock.weapon);
	}
	
	public void applySuppressHits(int hits, Cord cord, boolean fullauto, int range) throws Exception {
		
		if(hits < 1)
			return;
		
		for(int i = 0; i < hits; i++) {
			
			for(Trooper trooper : CorditeExpansionGame.actionOrder.getOrder()) {
				if(!trooper.ceStatBlock.cord.compare(cord))
					continue; 
				
				int suppression = 1;
				
				if(fullauto && hits < 9) 
					suppression += 1;
				else if(fullauto && hits >= 9) {
					suppression += 2;
				}
				
				trooper.ceStatBlock.rangedStatBlock.suppression.increaseSuppression(suppression);
				FloatingTextManager.addFloatingText(trooper.ceStatBlock.cord, "Suppression: "+suppression);
				
				if(DiceRoller.randInt(0, 99) <= 11) {
					Damage.applyHit(statBlock.rangedStatBlock.weapon.name, statBlock.rangedStatBlock.weapon.getPen(range), 
							statBlock.rangedStatBlock.weapon.getDc(range), 
							!target.ceStatBlock.inCover, target, DiceRoller.randInt(0, 99));
				}
				
			}
			
		}
		
	}

	public void fullAutoBurst() throws Exception {
		if(statBlock.rangedStatBlock.weapon.ceStats.criticalHit) {
			FloatingTextManager.addFloatingText(statBlock.cord, "Weapon destroyed via critical hit.");
			return;
		}
		
		if(statBlock.rangedStatBlock.weapon.ceStats.magazine == null || !statBlock.rangedStatBlock.weapon.ceStats.ammoCheck()) {
			FloatingTextManager.addFloatingText(statBlock.cord, "Weapon out of ammo, DP: "+ 
		(statBlock.rangedStatBlock.weapon.ceStats.magazine != null ? statBlock.rangedStatBlock.weapon.ceStats.magazine.ammo.depletionPoints : "No Magazine"));
			return;
		}
		
		if(statBlock.rangedStatBlock.aimTarget == null)
			statBlock.setAimTarget(target);
		
		int range = statBlock.getDistance(target.ceStatBlock);
		int eal = calculateEAL(target.ceStatBlock.cord);
		
		CalledShots calledShots = null;
		if(statBlock.rangedStatBlock.shotTarget == ShotTarget.HEAD) {
			calledShots = new CalledShots(calcualteALM(null), getSizeAlm());
			calledShots.setCalledShotBounds(statBlock.rangedStatBlock.shotTarget);
			eal = calledShots.getEal();
		} else if(!target.ceStatBlock.inCover && 
				(statBlock.rangedStatBlock.shotTarget == ShotTarget.LEGS || statBlock.rangedStatBlock.shotTarget == ShotTarget.BODY)) {
			calledShots = new CalledShots(calcualteALM(null), getSizeAlm());
			calledShots.setCalledShotBounds(statBlock.rangedStatBlock.shotTarget);
			eal = calledShots.getEal();
		}
		eal -= statBlock.rangedStatBlock.weapon.sab * sustainedBurst;
		
		double ma = statBlock.rangedStatBlock.weapon.getMA(range);
		int rof = statBlock.rangedStatBlock.weapon.fullAutoROF;
		
		FullAutoResults far = FullAuto.burst(eal, ma, rof, statBlock);
		
		for(int i = 0; i < far.hits; i++) {
			if(!blindFireCheck()) {
				continue;
			}
			
			int hitLocation = calledShots != null ? calledShots.getHitLocation() : DiceRoller.randInt(0, 99);
			applyHit(range, hitLocation);
		}
		
		int suppression = 2;
		
		target.ceStatBlock.rangedStatBlock.suppression.increaseSuppression(suppression);
		FloatingTextManager.addFloatingText(target.ceStatBlock.cord, "Suppression: "+suppression);
		
		FloatingTextManager.addFloatingText(statBlock.cord, far.rslts);
		sustainedBurst++;
		statBlock.rangedStatBlock.weapon.ceStats.burst(statBlock.rangedStatBlock.weapon);
	}
	
	public void shot() throws Exception {
		if(statBlock.rangedStatBlock.weapon.ceStats.criticalHit) {
			FloatingTextManager.addFloatingText(statBlock.cord, "Weapon destroyed via critical hit.");
			return;
		}
		
		if(statBlock.rangedStatBlock.weapon.ceStats.magazine == null || !statBlock.rangedStatBlock.weapon.ceStats.ammoCheck()) {
			FloatingTextManager.addFloatingText(statBlock.cord, "Weapon out of ammo, DP: "+ 
		(statBlock.rangedStatBlock.weapon.ceStats.magazine != null ? statBlock.rangedStatBlock.weapon.ceStats.magazine.ammo.depletionPoints : "No Magazine"));
			return;
		}
		
		
		System.out.println("Firing: "+statBlock.rangedStatBlock.weapon.name);
		
		if(statBlock.rangedStatBlock.aimTarget == null)
			statBlock.setAimTarget(target);
		
		int eal = calculateEAL(target.ceStatBlock.cord);
		
		CalledShots calledShots = null;
		if(statBlock.rangedStatBlock.shotTarget == ShotTarget.HEAD) {
			calledShots = new CalledShots(calcualteALM(null), getSizeAlm());
			calledShots.setCalledShotBounds(statBlock.rangedStatBlock.shotTarget);
			eal = calledShots.getEal();
		} else if(!target.ceStatBlock.inCover && 
				(statBlock.rangedStatBlock.shotTarget == ShotTarget.LEGS || statBlock.rangedStatBlock.shotTarget == ShotTarget.BODY)) {
			calledShots = new CalledShots(calcualteALM(null), getSizeAlm());
			calledShots.setCalledShotBounds(statBlock.rangedStatBlock.shotTarget);
			eal = calledShots.getEal();
		}
		
		int odds = PCUtility.getOddsOfHitting(true, eal);
		int penalty = shotPenalty(statBlock);
		int roll = DiceRoller.randInt(0, 99);
		
		int suppression = 1;
		
		if(statBlock.rangedStatBlock.weapon.shotgun)
			suppression++;
		
		target.ceStatBlock.rangedStatBlock.suppression.increaseSuppression(suppression);
		FloatingTextManager.addFloatingText(target.ceStatBlock.cord, "Suppression: "+suppression);
		
		FloatingTextManager.addFloatingText(statBlock.cord, "Shot, Roll: "+roll
				+", TN: "+odds+", Penalty: "+penalty);
		
		/*System.out.println("Shooter Cord: "+statBlock.cord.toString()+" Target Cord: "+target.ceStatBlock.cord.toString());
		System.out.println("Shooter Chit Cord: "+statBlock.chit.getCord().toString()+
				" Target Chit Cord: "+target.ceStatBlock.chit.getCord().toString());*/
		
		statBlock.rangedStatBlock.weapon.ceStats.singleFire();
		
		if(statBlock.rangedStatBlock.stabalized && !followUpAim) {
			statBlock.aim();
			followUpAim = true;
		}
		
		if(roll > odds) {
			return;
		} 
		
		if(!blindFireCheck()) {
			return;
		}	
		
		int range = statBlock.getDistance(target.ceStatBlock);
		int hitLocation = calledShots != null ? calledShots.getHitLocation() : DiceRoller.randInt(0, 99);
		if(statBlock.rangedStatBlock.weapon.shotgun) {
			//System.out.println("Pass Shotgun");
			applyShotgunHits(range, Weapons.getShotgunTableInteger(statBlock.rangedStatBlock.weapon.salm, range), hitLocation);
		} else {
			applyHit(range, hitLocation);
		}
	
	
	}
	
	public boolean blindFireCheck() {
		if(!statBlock.rangedStatBlock.blindFiring)
			return true;
		
		int blindRoll = DiceRoller.randInt(0, 99);
		int tn = 1; 
		
		if(target.ceStatBlock.stance == Stance.STANDING) {
			tn = 12; 
		} else if(target.ceStatBlock.stance == Stance.CROUCH) {
			tn = 3; 
		} 
		
		FloatingTextManager.addFloatingText(target.ceStatBlock.cord, "Bind Fire Check: "+blindRoll+", TN: "+tn);
		
		if(blindRoll <= tn)
			return true; 
		else 
			return false; 
		
	}
	
	public void applyShotgunHits(int range, int salm, int hitLocation) throws Exception {
		Weapons weapon = statBlock.rangedStatBlock.weapon;
		String bphc = Weapons.getShotgunTableString(weapon.bphc, range);
		//System.out.println("BPHC: "+bphc);
		int hits = 0;
		try {
	        int hitChance = Integer.parseInt(bphc);
	        int hitRoll = DiceRoller.randInt(0, 99);
	        if(hitRoll <= hitChance)
	        	hits++;
	        else {
	        	FloatingTextManager.addFloatingText(statBlock.cord, "Pellet Missed, Roll: "+hitChance+", TN: "+hitChance);
	        	return; 
	        }
	        
	    } catch (NumberFormatException nfe) {
	        hits = PcDamageUtility.getDamageValue(bphc);
	    }
		
		//System.out.println("Hits: "+hits);
		
		for(int i = 0; i < hits; i++) {
			Damage.applyHit(weapon.name, Weapons.getShotgunTableInteger(weapon.pen, range), Weapons.getShotgunTableInteger(weapon.dc, range), 
					!target.ceStatBlock.inCover, target, Weapons.getShotgunHitLocation(hitLocation, salm));
		}
		
	}
	
	public void applyHit(int range, int hitLocation) throws Exception {
		
		Damage.applyHit(statBlock.rangedStatBlock.weapon.name, statBlock.rangedStatBlock.weapon.getPen(range), 
				statBlock.rangedStatBlock.weapon.getDc(range), 
				!target.ceStatBlock.inCover, target, hitLocation);
	}
	
	public int calculateEAL(Cord cord) {
		
		int sizeALM;
		
		sizeALM = getSizeAlm();
		
		int alm = calcualteALM(cord);
		
		int distance = GameWindow.dist(statBlock.cord.xCord, statBlock.cord.yCord, cord.xCord,
				cord.yCord);
		
		if(statBlock.rangedStatBlock.weapon.shotgun) {
			int salm = Weapons.getShotgunTableInteger(statBlock.rangedStatBlock.weapon.salm, distance);
			sizeALM = sizeALM > salm ? sizeALM : salm; 
		}
		
		if(statBlock.rangedStatBlock.weapon.getBA(distance) < alm) {
			return statBlock.rangedStatBlock.weapon.getBA(distance) + sizeALM;
		} else {
			return alm + sizeALM;			
		}
		
		
	}
	
	public int calcualteALM(Cord cord) {
		
		int rangeALM; 
		int speedALM = 0; 
		int visibilityALM = 0;
		System.out.println("Shot SL: "+statBlock.trooper.sl);
		int aimALM = statBlock.trooper.sl +statBlock.rangedStatBlock.weapon.aimTime.get(statBlock.getAimTime());
		int stanceAlm = getStanceAlm(); 
		int dodgeAlm = 0;
		int defensiveAlm = 0;
		int stabalized = statBlock.rangedStatBlock.stabalized ? 0 : -6; 
		int lookingIntoLight = !statBlock.rangedStatBlock.lookingIntoLight ? 0 : -12; 
		int laserAlm = getLaserAlm();
		
		rangeALM = getDistanceAlm();
		
		try {
			
			if(target != null) {
				speedALM = getSpeedAlm(statBlock, target.ceStatBlock.cord);
				speedALM += getSpeedAlm(target.ceStatBlock, statBlock.cord);			
				
				if(target.ceStatBlock.getAction() != null && 
						target.ceStatBlock.getAction().getActionType() == ActionType.DODGE) {
					dodgeAlm = -6; 
				}
				
				defensiveAlm = PCUtility.defensiveALM(target.sl + (target.wit));
				
			} else {
				speedALM = getSpeedAlm(statBlock, cord);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Dodge ALM: "+dodgeAlm);
		
		System.out.println("Range ALM: "+rangeALM);
		System.out.println("Speed ALM: "+speedALM);
		System.out.println("Visibility ALM: "+visibilityALM);
		System.out.println("aimALM: "+aimALM);
		System.out.println("Stance ALM: "+stanceAlm);
		System.out.println("Laser ALM: "+laserAlm);
		System.out.println("Dodge ALM: "+dodgeAlm);
		System.out.println("Defensive ALM: "+defensiveAlm);
		System.out.println("Stabalized: "+stabalized);
		System.out.println("Looking Into Light: "+lookingIntoLight);
		
		return rangeALM + speedALM + visibilityALM + aimALM + stanceAlm + 
				laserAlm + dodgeAlm + defensiveAlm + stabalized + lookingIntoLight; 
	
	}
	
	public int getLaserAlm() {
		
		if(!statBlock.rangedStatBlock.weapon.laser || statBlock.getAimTime() > 3)
			return 0;
		
		
		Cord cord; 
		
		if(target == null) {
			cord = suppressHexes.get(0);
		} else {
			cord = target.ceStatBlock.cord;
		}

		int range = GameWindow.hexDif(statBlock.cord.xCord, statBlock.cord.yCord, cord.xCord,
				cord.yCord) * CorditeExpansionGame.distanceMultiplier;
		
		if(range < 5)
			return 6; 
		else if(range < 10)
			return 3; 
		 
		
		return 0;
	}
	
	public int getStanceAlm() {
		
		if(statBlock.stance == Stance.CROUCH) {
			return 3;
		} else if(statBlock.stance == Stance.PRONE) {
			return 6;
		} else {
			return 0;
		}
		
	}

	public int getDistanceAlm() {
		Cord cord; 
		
		if(target == null) {
			cord = suppressHexes.get(0);
		} else {
			cord = target.ceStatBlock.cord;
		}

		int distance = GameWindow.hexDif(statBlock.cord.xCord, statBlock.cord.yCord, cord.xCord,
				cord.yCord) * CorditeExpansionGame.distanceMultiplier;
		//System.out.println("Shot distance: "+distance);
		return PCUtility.findRangeALM(distance);
	}

	public int getSizeAlm() {
		if(target == null || statBlock.rangedStatBlock.blindFiring) {
			return 12;
		}
		
		if(target.ceStatBlock.inCover)
			return 0;
		
		String stance; 
		
		if(target.ceStatBlock.stance == Stance.STANDING) {
			stance = "Standing";
		} else if(target.ceStatBlock.stance == Stance.CROUCH) {
			stance = "Crouching";
		} else {
			stance = "Prone";
		}
		
		return PCUtility.findSizeALM(stance, target.PCSize);
	}

	public int getSpeedAlm(StatBlock movingBlock, Cord cord) throws Exception {
		
		if(!movingBlock.acting() || movingBlock.getAction().getActionType() != ActionType.MOVE)
			return 0; 
		
		
		int distance = GameWindow.dist(statBlock.cord.xCord, statBlock.cord.yCord, cord.xCord,
				cord.yCord);
		
		double actions = (double) movingBlock.getActionTiming();
		
		double modifier;
		
		if(movingBlock.moveSpeed == MoveSpeed.CRAWL) {
			modifier = 0.25;
		} else if(movingBlock.moveSpeed == MoveSpeed.STEP) {
			modifier = 1; 
		} else if(movingBlock.moveSpeed == MoveSpeed.RUSH) {
			modifier = 2; 
		} else {
			modifier = 4; 
		}
		
		int value = -1; 
		
		try {
			value = (int) ExcelUtility.getNumberFromSheet(distance, Math.round(actions * modifier), "speed.xlsx", true, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(value == -1)
			throw new Exception("Speed value not found: "+distance +", "+Math.round(actions * modifier));
		
		return value;
	}

	public static int shotPenalty(StatBlock statBlock) {
		int penalty = 0; 
		
		for(int i = 0; i < statBlock.medicalStatBlock.pain; i++) {
			penalty += DiceRoller.d6_exploding();
		}
		
		for(int i = 0; i < statBlock.rangedStatBlock.getSuppression(); i++) {
			penalty += DiceRoller.d6_exploding();
		}
		
		return penalty;
	}
	
	public void addSuppresionHex(Cord cord) {
		
		suppressHexes.add(cord);
		
		if(suppressHexes.size() > 3)
			suppressHexes.remove(0);
		
	}
	
	@Override
	public void setPrepared() {
		spentCoac = coac;

	}

	@Override
	public boolean completed() {
		return false;
	}

	@Override
	public boolean ready() {
		return spentCoac < coac ? false : true;
	}

	@Override
	public ActionType getActionType() {
		return ActionType.FIRE;
	}

	@Override
	public String toString() {
		String rslts = "Fire: ";

		if (statBlock.rangedStatBlock.aimTarget != null) {
			rslts += statBlock.rangedStatBlock.aimTarget.name;
		} else {
			rslts = "Suppresion: ";
			for (Cord cord : suppressHexes) {
				rslts += "(" + cord.xCord + "," + cord.yCord + ")";

				if (cord != suppressHexes.get(suppressHexes.size() - 1)) {
					rslts += ", ";
				}

			}
		}

		return rslts + " [" + statBlock.getAimTime() + "], EAL: "+calculateEAL(target != null ? target.ceStatBlock.cord : suppressHexes.get(0));
	}

}
