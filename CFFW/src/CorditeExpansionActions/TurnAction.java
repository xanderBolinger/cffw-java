package CorditeExpansionActions;

import CeHexGrid.Chit.Facing;
import CorditeExpansionActions.CeAction.ActionType;
import CorditeExpansionStatBlock.StatBlock;

public class TurnAction implements CeAction {

	StatBlock statBlock;
	Facing targetFacing;
	
	public TurnAction(StatBlock statBlock, Facing targetFacing) {
		this.statBlock = statBlock;
		this.targetFacing = targetFacing;
	}
	
	public Facing getTargetFacing() {
		return targetFacing;
	}
	
	public void setFacing(boolean clockwise) {
		
		if(clockwise) {
			targetFacing = Facing.turnClockwise(targetFacing);
		} else {
			targetFacing = Facing.turnCounterClockwise(targetFacing);
		}
		
	}
	
	@Override
	public void spendCombatAction() {
		statBlock.setFacing(targetFacing);
	}

	@Override
	public boolean completed() {
		return statBlock.getFacing() == targetFacing ? true : false;
	}

	@Override
	public boolean ready() {
		return true;
	}

	@Override
	public ActionType getActionType() {
		return ActionType.TURN;
	}

	@Override 
	public String toString() {
		return "Turn Action, Target Facing: "+targetFacing.toString();
	}

	@Override
	public void setPrepared() {
		
	}
	
}
