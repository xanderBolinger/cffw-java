package CorditeExpansionActions;

import Trooper.Trooper;

public class ReloadAction implements CeAction {

	boolean completed = false;
	int spentCoac = 0; 
	int coac = 2; 
	int spentReloadTime = 0;
	
	Trooper trooper; 
	
	public ReloadAction(Trooper trooper) {
		this.trooper = trooper;
	}
	
	
	@Override
	public void spendCombatAction() {
		if(!ready()) {
			spentCoac++; 
			return;
		}
		
		if(spentReloadTime >= trooper.ceStatBlock.rangedStatBlock.weapon.ceStats.reloadTime) {
			trooper.ceStatBlock.rangedStatBlock.reload(trooper);
			completed = true;
			return;
		}
		
		spentReloadTime++; 
		
	}

	@Override
	public void setPrepared() {
		spentCoac = coac; 
	}

	@Override
	public boolean completed() {
		return completed;
	}

	@Override
	public boolean ready() {
		return spentCoac >= coac;
	}

	@Override
	public ActionType getActionType() {
		return null;
	}
	
	@Override
	public String toString() {
		return "Reload["+(ready() ? spentReloadTime : spentCoac)+"/"+
				(ready() ? trooper.ceStatBlock.rangedStatBlock.weapon.ceStats.reloadTime : coac)+"]";
	}

}
