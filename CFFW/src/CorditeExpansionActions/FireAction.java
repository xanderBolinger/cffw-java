package CorditeExpansionActions;

import java.util.ArrayList;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import CorditeExpansionDamage.Damage;
import CorditeExpansionStatBlock.StatBlock;
import CorditeExpansionStatBlock.StatBlock.MoveSpeed;
import CorditeExpansionStatBlock.StatBlock.Stance;
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

	public FireAction(StatBlock statBlock, Trooper target) {
		this.statBlock = statBlock;
		this.target = target;
	}
	
	@Override
	public void spendCombatAction() {
		if (!ready()) {
			spentCoac++;
			return;
		}
	}

	public void shot() throws Exception {
		if(statBlock.weapon.ceStats.criticalHit) {
			return;
		}
		
		int eal = calculateEAL();
		int odds = PCUtility.getOddsOfHitting(statBlock.fullAuto, eal);
		int roll = DiceRoller.randInt(0, 99);
		
		if(roll > odds) {
			return;
		}
		
		int range = statBlock.getDistance(target.ceStatBlock);
		
		Damage.applyHit(statBlock.weapon.getPen(range), statBlock.weapon.getDc(range), 
				target.ceStatBlock.inCover, target);
		
	}
	
	public int calculateEAL() {
		
		int sizeALM;
		
		sizeALM = getSizeAlm();
		
		int alm = calcualteALM();
		
		StatBlock targetStatBlock = target.ceStatBlock;
		
		int distance = GameWindow.dist(statBlock.cord.xCord, statBlock.cord.yCord, targetStatBlock.cord.xCord,
				targetStatBlock.cord.yCord);
		
		if(statBlock.weapon.getBA(distance) < alm) {
			return statBlock.weapon.getBA(distance) + sizeALM;
		} else {
			return alm + sizeALM;			
		}
		
		
	}
	
	public int calcualteALM() {
		
		int rangeALM; 
		int speedALM = 0; 
		int visibilityALM = 0;
		int aimALM = statBlock.weapon.aimTime.get(statBlock.aimTime);
		
		rangeALM = getDistanceAlm();
		
		try {
			speedALM = getSpeedAlm(statBlock, target.ceStatBlock) + getSpeedAlm(target.ceStatBlock, statBlock);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rangeALM + speedALM + visibilityALM + aimALM; 
	
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

		if (statBlock.aimTarget != null) {
			rslts += statBlock.aimTarget.name;
		} else {
			for (Cord cord : statBlock.aimHexes) {
				rslts += "(" + cord.xCord + "," + cord.yCord + ")";

				if (cord != statBlock.aimHexes.get(statBlock.aimHexes.size() - 1)) {
					rslts += ", ";
				}

			}
		}

		return rslts + " [" + statBlock.aimTime + "]";
	}

}
