package CorditeExpansionActions;

import CorditeExpansion.Cord;
import CorditeExpansion.CorditeExpansionGame;
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
	
		if(!overwatching() || (overwatching() && !doneOverwatching())) {
			//System.out.println("Aim");
			statBlock.aim();
		} else if(overwatching() && doneOverwatching() && getOverwatchTarget() == null) {
			for(CeAction action : statBlock.getCoac()) {
				if(!action.ready()) {
					action.spendCombatAction();
					break; 
				}
			}
		}
		
	}
	
	public void overwatchCheck() {
		try {
			performOverwatch(getOverwatchTarget());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Trooper getOverwatchTarget() {
		
		for(Trooper trooper : CorditeExpansionGame.actionOrder.getOrder()) {
			
			for(Cord cord : statBlock.rangedStatBlock.aimHexes) {
				if(cord.compare(trooper.ceStatBlock.cord)) {
					System.out.println("Perform overwatch true");
					return trooper;
				}
			}
		}
		
		System.out.println("Perform overwatch false");
		return null;
	}
	
	public void performOverwatch(Trooper target) throws Exception {
		if(target == null)
			return;
		
		FireAction fireAction = new FireAction(statBlock, target);
		
		if(statBlock.rangedStatBlock.fullAuto)
			fireAction.fullAutoBurst();
		else
			fireAction.shot();
		
	}
	
	public boolean doneOverwatching() {
		return statBlock.rangedStatBlock.aimTime >= 3;
	}
	
	public boolean overwatching() {
		return statBlock.rangedStatBlock.aimHexes.size() > 0; 
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
		if(overwatching())
			return false;
		
		
		System.out.println("Aim Time Size: "
				+ statBlock.rangedStatBlock.weapon.aimTime.size() + 
				", Aim Bonus: "+statBlock.rangedStatBlock.weapon.aimTime.get(statBlock.getAimTime()));
		
		
		
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
		
		if(overwatching()) {
			rslts = "Overwatching: ";
		}
		
		if(statBlock.rangedStatBlock.aimTarget != null && !overwatching()) {
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
