package CorditeExpansionActions;

import CorditeExpansion.CorditeExpansionGame;
import Trooper.Trooper;

public class DodgeAction implements CeAction {

	boolean completed = false;
	public Trooper trooper; 
	
	public DodgeAction(Trooper trooper) {
		this.trooper = trooper;
	}
	
	
	@Override
	public void spendCombatAction() {
		completed = true; 
		
		for(Trooper otherTrooper : CorditeExpansionGame.actionOrder.getOrder()) {
			
			if(otherTrooper.ceStatBlock.getAction() != null
					&& otherTrooper.ceStatBlock.getAction().getActionType() == ActionType.FIRE
					&& otherTrooper.ceStatBlock.getFireAction().target.compareTo(trooper)) {
				otherTrooper.ceStatBlock.cancelAction();
			}
			
		}
		
	}

	@Override
	public void setPrepared() {
		
	}

	@Override
	public boolean completed() {
		return completed;
	}

	@Override
	public boolean ready() {
		return true;
	}

	@Override
	public ActionType getActionType() {
		return ActionType.DODGE;
	}

	@Override
	public String toString() {
		return "Dodge";
	}
	
	
}
