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
			spentCoac++;
			return; 
		}
	
		statBlock.aim();
	}
	
	public void setTargetTrooper(Trooper target) {
		statBlock.setAimTarget(target);
	}
	
	public void addTargetHex(Cord cord) {
		
		statBlock.aimHexes.add(cord);
		
		if(statBlock.aimHexes.size() > 3)
			statBlock.aimHexes.remove(0);
		
	}

	@Override
	public void setPrepared() {
		spentCoac = coac;
		
	}

	@Override
	public boolean completed() {
		return statBlock.getAimTime() >= statBlock.weapon.aimTime.size() - 1;
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
		
		if(statBlock.aimTarget != null) {
			rslts += statBlock.aimTarget.name;
		} else {
			for(Cord cord : statBlock.aimHexes) {
				rslts += "("+cord.xCord+","+cord.yCord+")";
				
				if(cord != statBlock.aimHexes.get(statBlock.aimHexes.size()-1)) {
					rslts += ", ";
				}
				
			}
		}
		
		return rslts+" ["+statBlock.getAimTime()+"]"; 
	}
	
}
