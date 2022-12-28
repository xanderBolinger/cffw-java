package CorditeExpansionActions;

import CeHexGrid.Chit;
import CeHexGrid.FloatingTextManager;
import Conflict.GameWindow;
import CorditeExpansion.Cord;
import CorditeExpansion.CorditeExpansionGame;
import CorditeExpansion.ThrowAble;
import CorditeExpansionStatBlock.StatBlock;
import Items.Weapons;
import UtilityClasses.DiceRoller;
import UtilityClasses.PCUtility;

public class ThrowAction implements CeAction {

	int coac = 2; 
	int spentCoac = 0; 
	int spentArmTime = 0; 
	Weapons throwable; 
	StatBlock statBlock; 
	Cord targetCord;
	
	public ThrowAction(StatBlock statBlock, Weapons throwable, Cord targetCord) {
		this.throwable = throwable;
		this.statBlock = statBlock;
		this.targetCord = targetCord; 
	}
	
	@Override
	public void spendCombatAction() {
		if(!ready()) {
			spentCoac++; 
			return;
		}
		
		spentArmTime++; 
		
		if(completed()) {
			toss();
		}
		
	}

	public void toss() {
		Chit chit = new Chit("CeImages/grenade.png");
		chit.xCord = targetCord.xCord;
		chit.yCord = targetCord.yCord;
		chit.setDimensions(chit.getWidth() / 2, chit.getHeight() / 2, CeHexGrid.CeHexGrid.zoom);
		CorditeExpansionGame.throwAbles.add(new ThrowAble(chit, throwable));
		
		// hex size 12 ALM 
		int eal = 12 + statBlock.trooper.sl  
				+ throwable.aimTime.get(statBlock.getAimTime())
				+ getDistanceAlm();
		
		int odds = PCUtility.getOddsOfHitting(true, eal);
		
		int roll = DiceRoller.randInt(0, 99);
		
		if(roll <= odds) {
			FloatingTextManager.addFloatingText(targetCord, "Throw Hit");
		} else {
			int missEal = PCUtility.getEAL(true, roll);
			int diff = Math.abs(eal - missEal);
			int distance = getScatterDistance(diff);
			FloatingTextManager.addFloatingText(targetCord, "Throw Miss, scatter dist: "+
					distance+", direction: "+DiceRoller.randInt(1, 12));
		}
		
	}
	public int getDistanceAlm() {
		int distance = GameWindow.hexDif(statBlock.cord.xCord, statBlock.cord.yCord, targetCord.xCord,
				targetCord.yCord) * CorditeExpansionGame.distanceMultiplier;
		return PCUtility.findRangeALM(distance);
	}
	
	public int getScatterDistance(int diff) {
		
		if(diff <= 7) {
			return 1;
		} else if(diff <= 11) {
			return 2;
		} else if(diff <= 13) {
			return 3;
		} else if(diff <= 15) {
			return 4;
		} else if(diff <= 17) {
			return 5;
		} else if(diff <= 19) {
			return 6;
		} else if(diff <= 21) {
			return 8;
		} else if(diff <= 22) {
			return 10;
		} else if(diff <= 23) {
			return 12;
		} else if(diff <= 24) {
			return 14;
		} else if(diff <= 25) {
			return 16;
		} else if(diff <= 26) {
			return 19;
		} else if(diff <= 27) {
			return 21;
		} else {
			return 25;
		}
		
	}
	
	@Override
	public void setPrepared() {
		spentCoac = coac; 
	}

	@Override
	public boolean completed() {
		return spentArmTime >= throwable.armTime;
	}

	@Override
	public boolean ready() {
		return spentCoac >= coac;
	}

	@Override
	public ActionType getActionType() {
		return ActionType.THROW;
	}

	@Override
	public String toString() {
		return "Throw<"+targetCord.xCord+","+targetCord.yCord+">: "+throwable.name;
	}
	
}
