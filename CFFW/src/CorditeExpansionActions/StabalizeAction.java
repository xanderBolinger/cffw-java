package CorditeExpansionActions;

import CeHexGrid.FloatingTextManager;
import CorditeExpansionActions.CeAction.ActionType;
import CorditeExpansionStatBlock.StatBlock;
import UtilityClasses.DiceRoller;

public class StabalizeAction implements CeAction {

	StatBlock statBlock;
	int ergo; 
	int firearms;
	
	int coac = 2; 
	int spentCoac = 0; 
	int attempts = 0; 
	
	public StabalizeAction(StatBlock statBlock) {
		this.statBlock = statBlock; 
		this.ergo = statBlock.rangedStatBlock.weapon.ceStats.baseErgonomics; 
		this.firearms = statBlock.rangedStatBlock.weaponPercent;
	}
	
	@Override
	public void spendCombatAction() {
		
		if(!ready()) {
			spentCoac++;
			return; 
		}
		
		// stabalize check
		int roll = DiceRoller.roll(1, 100);
		
		int bonus = 0; 
		
		for(int i = 0; i < attempts; i++) {
			bonus += DiceRoller.d6_exploding();
		}
		
		int tn = (ergo + firearms) / 2;
		String results = ", Roll: "+roll+", TN: "+tn+", Bonus: "+bonus;
		
		if(roll - bonus < tn) {
			statBlock.rangedStatBlock.stabalized = true;
			results = "Stabalized"+results;
		}
		else
			results = "Failed to Stabalize"+results;
			
		FloatingTextManager.addFloatingText(statBlock.cord, results);
		
		attempts++; 
	}

	@Override
	public void setPrepared() {
		spentCoac = coac;
	}

	@Override
	public boolean completed() {
		return statBlock.rangedStatBlock.stabalized;
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
