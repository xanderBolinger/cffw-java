package CorditeExpansion;

import Trooper.Trooper;

public class AimAction implements CeAction {

	int coac = 2; 
	int spentCoac = 0;
	
	CeStatBlock statBlock;
	
	public AimAction(CeStatBlock statBlock) {
		this.statBlock = statBlock;
	}
	
	@Override
	public void spendCombatAction() {
		if(!ready()) {
			spentCoac++;
			return; 
		}
	
		
		statBlock.aimTime++; 
		
	}
	
	public void setTargetTrooper(Trooper target) {
		statBlock.aimTarget = target;
		statBlock.aimTime = 0;
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
		return statBlock.aimTime >= statBlock.weapon.aimTime.size() - 1;
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
		
		return rslts+" ["+statBlock.aimTime+"]"; 
	}
	
}
