package CorditeExpansion;

import CorditeExpansionStatBlock.StatBlock;
import CorditeExpansionStatBlock.StatBlock.Stance;

public class ChangeStanceAction implements CeAction {

	Stance targetStance; 
	StatBlock statBlock; 
	
	public ChangeStanceAction(StatBlock statBlock, Stance targetStance) {
		this.statBlock = statBlock;
		this.targetStance = targetStance;
	}
	
	@Override
	public void spendCombatAction() {

		if(targetStance.getValue() > statBlock.stance.getValue()) {
			statBlock.stance = Stance.valueOf(statBlock.stance.getValue() + 1);
		} else if (targetStance.getValue() < statBlock.stance.getValue()) {
			statBlock.stance = Stance.valueOf(statBlock.stance.getValue() - 1);
		} 
		
	}

	@Override
	public boolean completed() {
		return targetStance == statBlock.stance ? true : false;
	}

	@Override
	public boolean ready() {
		return true;
	}

	@Override
	public ActionType getActionType() {
		return ActionType.CHANGESTANCE;
	}

	@Override
	public String toString() {
		return "Change Stance to: "+targetStance.toString();
	}

	@Override
	public void setPrepared() {
		
	}
	
}
