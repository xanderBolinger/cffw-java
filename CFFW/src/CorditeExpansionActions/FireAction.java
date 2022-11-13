package CorditeExpansionActions;

import java.util.ArrayList;

import CeHexGrid.FloatingTextManager;
import Conflict.GameWindow;
import CorditeExpansion.Cord;
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
	public ArrayList<Cord> suppressHexes;

	public StatBlock statBlock;
	int coac = 2;
	int spentCoac = 0;
	
	int sustainedBurst = 0;

	public FireAction(StatBlock statBlock, Trooper target) {
		this.statBlock = statBlock;
		this.target = target;
	}
	
	public boolean followUpAim = false;
	
	@Override
	public void spendCombatAction() {
		if (!ready()) {
			spentCoac++;
			return;
		}
		
		try {
			if(statBlock.rangedStatBlock.fullAuto)
				fullAutoBurst();
			else
				shot();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		int eal = calculateEAL();
		
		CalledShots calledShots = null;
		if(statBlock.rangedStatBlock.shotTarget == ShotTarget.HEAD) {
			calledShots = new CalledShots(calcualteALM(), getSizeAlm());
			calledShots.setCalledShotBounds(statBlock.rangedStatBlock.shotTarget);
			eal = calledShots.getEal();
		} else if(!target.ceStatBlock.inCover && 
				(statBlock.rangedStatBlock.shotTarget == ShotTarget.LEGS || statBlock.rangedStatBlock.shotTarget == ShotTarget.BODY)) {
			calledShots = new CalledShots(calcualteALM(), getSizeAlm());
			calledShots.setCalledShotBounds(statBlock.rangedStatBlock.shotTarget);
			eal = calledShots.getEal();
		}
		eal -= statBlock.rangedStatBlock.weapon.sab * sustainedBurst;
		
		double ma = statBlock.rangedStatBlock.weapon.getMA(range);
		int rof = statBlock.rangedStatBlock.weapon.fullAutoROF;
		
		FullAutoResults far = FullAuto.burst(eal, ma, rof, statBlock);
		
		int hitLocation = calledShots != null ? calledShots.getHitLocation() : DiceRoller.randInt(0, 99);
		
		for(int i = 0; i < far.hits; i++) {
			applyHit(range, hitLocation);
		}
		
		int suppression = DiceRoller.d6_exploding() + DiceRoller.d6_exploding();
		
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
		
		int eal = calculateEAL();
		
		CalledShots calledShots = null;
		if(statBlock.rangedStatBlock.shotTarget == ShotTarget.HEAD) {
			calledShots = new CalledShots(calcualteALM(), getSizeAlm());
			calledShots.setCalledShotBounds(statBlock.rangedStatBlock.shotTarget);
			eal = calledShots.getEal();
		} else if(!target.ceStatBlock.inCover && 
				(statBlock.rangedStatBlock.shotTarget == ShotTarget.LEGS || statBlock.rangedStatBlock.shotTarget == ShotTarget.BODY)) {
			calledShots = new CalledShots(calcualteALM(), getSizeAlm());
			calledShots.setCalledShotBounds(statBlock.rangedStatBlock.shotTarget);
			eal = calledShots.getEal();
		}
		
		int odds = PCUtility.getOddsOfHitting(true, eal);
		int penalty = shotPenalty(statBlock);
		int roll = DiceRoller.randInt(0, 99);
		
		int suppression = DiceRoller.d6_exploding();
		
		if(statBlock.rangedStatBlock.weapon.shotgun)
			suppression += DiceRoller.d6_exploding();
		
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
		
		int range = statBlock.getDistance(target.ceStatBlock);
		int hitLocation = calledShots != null ? calledShots.getHitLocation() : DiceRoller.randInt(0, 99);
		if(statBlock.rangedStatBlock.weapon.shotgun) {
			//System.out.println("Pass Shotgun");
			applyShotgunHits(range, Weapons.getShotgunTableInteger(statBlock.rangedStatBlock.weapon.salm, range), hitLocation);
		} else {
			applyHit(range, hitLocation);
		}
	
	
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
	
	public int calculateEAL() {
		
		int sizeALM;
		
		sizeALM = getSizeAlm();
		
		
		
		int alm = calcualteALM();
		
		StatBlock targetStatBlock = target.ceStatBlock;
		
		int distance = GameWindow.dist(statBlock.cord.xCord, statBlock.cord.yCord, targetStatBlock.cord.xCord,
				targetStatBlock.cord.yCord);
		
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
	
	public int calcualteALM() {
		
		int rangeALM; 
		int speedALM = 0; 
		int visibilityALM = 0;
		int aimALM = statBlock.rangedStatBlock.weapon.aimTime.get(statBlock.getAimTime());
		int stanceAlm = getStanceAlm(); 
		
		rangeALM = getDistanceAlm();
		
		try {
			speedALM = getSpeedAlm(statBlock, target.ceStatBlock) + getSpeedAlm(target.ceStatBlock, statBlock);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rangeALM + speedALM + visibilityALM + aimALM + stanceAlm; 
	
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
		StatBlock targetStatBlock = target.ceStatBlock;

		int distance = GameWindow.dist(statBlock.cord.xCord, statBlock.cord.yCord, targetStatBlock.cord.xCord,
				targetStatBlock.cord.yCord);
		return PCUtility.findRangeALM(distance);
	}

	public int getSizeAlm() {
		
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

	public int getSpeedAlm(StatBlock movingBlock, StatBlock targetStatBlock) throws Exception {
		
		if(!movingBlock.acting() || movingBlock.getAction().getActionType() != ActionType.MOVE)
			return 0; 
		
		
		int distance = GameWindow.dist(statBlock.cord.xCord, statBlock.cord.yCord, targetStatBlock.cord.xCord,
				targetStatBlock.cord.yCord);
		
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
			// TODO Auto-generated catch block
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
			for (Cord cord : statBlock.rangedStatBlock.aimHexes) {
				rslts += "(" + cord.xCord + "," + cord.yCord + ")";

				if (cord != statBlock.rangedStatBlock.aimHexes.get(statBlock.rangedStatBlock.aimHexes.size() - 1)) {
					rslts += ", ";
				}

			}
		}

		return rslts + " [" + statBlock.getAimTime() + "], EAL: "+calculateEAL();
	}

}
