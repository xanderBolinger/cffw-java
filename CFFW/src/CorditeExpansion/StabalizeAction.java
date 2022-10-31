package CorditeExpansion;

import UtilityClasses.DiceRoller;

public class StabalizeAction implements CeAction {

	CeStatBlock statBlock;
	int ergo; 
	int firearms;
	
	int coac = 2; 
	int spentCoac = 0; 
	
	public StabalizeAction(CeStatBlock statBlock) {
		this.statBlock = statBlock; 
		this.ergo = statBlock.weapon.ceStats.baseErgonomics; 
		this.firearms = statBlock.weaponPercent;
	}
	
	@Override
	public void spendCombatAction() {
		
		if(!ready()) {
			spentCoac++;
			return; 
		}
		
		
		// stabalize check
		int roll = DiceRoller.randInt(1, 100);
		int tn = (ergo + firearms) / 2;
		String results = ", Roll: "+roll+", TN: "+tn;
		
		if(roll < tn) {
			statBlock.stabalized = true;
			results = "Stabalized"+results;
		}
		else
			results = "Failed to Stabalize"+results;
			
		FloatingTextManager.addFloatingText(statBlock.cord, results);
		
	}

	@Override
	public void setPrepared() {
		spentCoac = coac;
	}

	@Override
	public boolean completed() {
		return statBlock.stabalized;
	}

	@Override
	public boolean ready() {
		return spentCoac < coac ? false : true;
	}

	@Override
	public ActionType getActionType() {
		return ActionType.STABALIZE;
	}
	
	@Override
	public String toString() {
		return "Stabalize";
	}

}
