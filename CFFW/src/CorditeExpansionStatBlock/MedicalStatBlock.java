package CorditeExpansionStatBlock;

import UtilityClasses.TrooperUtility;

public class MedicalStatBlock {
	
	public int pain = 0;
	public Status status = Status.NORMAL;
	public int spentCa = 0;
	public int statusDuration = 0;
	
	
	
	public enum Status {
		NORMAL,DAZED,DISORIENTED,STUNNED
	}
	
	public void setStunned(int pd, StatBlock statBlock) {
		
		status = Status.STUNNED;
		spentCa = 0;
		statusDuration = pd / 25;
		statBlock.combatActions = TrooperUtility.calculateCA(statBlock.trooper.maximumSpeed.get(), 
				statBlock.trooper.isf);
		statBlock.combatActions -= 2; 
		spentCheck(statBlock);
	}
	
	public void setDazed(int pd, StatBlock statBlock) {
		
		if(status == Status.STUNNED) 
			return;
		
		status = Status.DAZED;
		spentCa = 0;
		statusDuration = pd / 25;
		statBlock.combatActions = TrooperUtility.calculateCA(statBlock.trooper.maximumSpeed.get(), 
				statBlock.trooper.isf);
		statBlock.combatActions -= 2; 
		spentCheck(statBlock);
	}
	
	public void setDisoriented(int pd, StatBlock statBlock) {
		
		if(status == Status.STUNNED || status == Status.DAZED) 
			return;
		
		status = Status.DISORIENTED;
		spentCa = 0;
		statusDuration = pd / 25;
		statBlock.combatActions = TrooperUtility.calculateCA(statBlock.trooper.maximumSpeed.get(), 
				statBlock.trooper.isf);
		statBlock.combatActions -= 1; 
		spentCheck(statBlock);
	}
	
	public void recoverCheck(StatBlock statBlock) {
		
		if(spentCa >= statusDuration) {
			statusDuration = 0; 
			spentCa = 0;
			status = Status.NORMAL;
		}
		
		statBlock.combatActions = TrooperUtility.calculateCA(statBlock.trooper.maximumSpeed.get(), 
				statBlock.trooper.isf);
		
	}
	
	public void spentCheck(StatBlock statBlock) {
		if(statBlock.spentCombatActions > statBlock.combatActions)
			statBlock.spentCombatActions  = statBlock.combatActions;
	}
	
	public int getDifficultyDice() {
		
		int statusDice = 0; 
		
		switch(status) {
		
			case DAZED:
				statusDice = 2;
				break;
			case DISORIENTED: 
				statusDice = 1; 
				break; 
			case STUNNED:
				statusDice = 3; 
				break; 
			default: 
				statusDice = 0;
				break;
		
		}
		
		return pain + statusDice; 
		
		
	}
	
}
