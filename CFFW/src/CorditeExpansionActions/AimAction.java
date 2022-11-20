package CorditeExpansionActions;

import CorditeExpansion.Cord;
import CorditeExpansionActions.CeAction.ActionType;
import CorditeExpansionStatBlock.StatBlock;
import Trooper.Trooper;

public class AimAction implements CeAction {

	int coac = 2; 
	int spentCoac = 0;
	
	StatBlock statBlock;
	
	public AimAction(StatBlock statBlock) {
		this.statBlock = statBlock;
	}
	
	@Override
	public void spendCombatAction() {
		if(!ready()) {
			//System.out.println("Not ready");
			spentCoac++;
			return; 
		}
	
		statBlock.aim();
	}
	
	public void setTargetTrooper(Trooper target) {
		statBlock.setAimTarget(target);
	}
	
	public void addTargetHex(Cord cord) {
		
		statBlock.rangedStatBlock.aimHexes.add(cord);
		
		if(statBlock.rangedStatBlock.aimHexes.size() > 3)
			statBlock.rangedStatBlock.aimHexes.remove(0);
		
	}

	@Override
	public void setPrepared() {
		spentCoac = coac;
		
	}

	@Override
	public boolean completed() {
		return statBlock.getAimTime() >= statBlock.rangedStatBlock.weapon.aimTime.size() - 1;
	}

	@Override
	public boolean ready() {
		return spentCoac < coac ? false : true;
	}

	@Override
	public ActionType getActionType() {
		return ActionType.AIM;
	}

	@Override
	public String toString() {
		String rslts = "Aim: "; 
		
		if(statBlock.rangedStatBlock.aimTarget != null) {
			rslts += statBlock.rangedStatBlock.aimTarget.name;
		} else {
			for(Cord cord : statBlock.rangedStatBlock.aimHexes) {
				rslts += "("+cord.xCord+","+cord.yCord+")";
				
				if(cord != statBlock.rangedStatBlock.aimHexes.get(statBlock.rangedStatBlock.aimHexes.size()-1)) {
					rslts += ", ";
				}
				
			}
		}
		
		return rslts+" ["+statBlock.getAimTime()+"]"; 
	}
	
}
