package CorditeExpansion;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import CeHexGrid.Chit.Facing;
import CorditeExpansion.CeStatBlock.Stance;
import UtilityClasses.Keyboard;

public interface CeAction {
	
	public enum ActionType {
		MOVE,TURN,CHANGESTANCE
	}
	
	@Override
	String toString();
	
	void spendCombatAction();
	void setPrepared();
	boolean completed();
	boolean ready();
	ActionType getActionType();
		
	public static void addMoveAction(CeStatBlock statBlock, ArrayList<Cord> cords, int coac) {
		
		if(coac == 0) {
			statBlock.addAction(new MoveAction(statBlock, cords, coac)); 			
		} else {
			statBlock.addActionCoac(new MoveAction(statBlock, cords, coac)); 
		}
				
	}
	
	public static void addTurnAction(CeStatBlock statBlock, boolean clockwise) {

		
		Facing facing = statBlock.getFacing();
		
		if(clockwise) {
			facing = Facing.turnClockwise(facing);
		} else {
			facing = Facing.turnCounterClockwise(facing);
		}
		
		statBlock.addAction(new TurnAction(statBlock, facing));
		
	}
	
	public static void updateTurnAction(CeStatBlock statBlock, boolean clockwise) {
		
		CeAction action = statBlock.getTurnAction();
		
		if(action == null || (action.getActionType() != ActionType.MOVE && action.getActionType() != ActionType.TURN)) {
			return; 
		} 
		
		if(action.getActionType() == ActionType.MOVE) {
			MoveAction moveAction = (MoveAction) action;
			
			moveAction.lastCord().setFacing(clockwise);
		}
		
		if(action.getActionType() == ActionType.TURN) {
			TurnAction turnAction = (TurnAction) action; 
			
			turnAction.setFacing(clockwise);
			
		}
		
	}
	
	public static void addChangeStanceAction(CeStatBlock statBlock, Stance targetStance) {
		statBlock.addAction(new ChangeStanceAction(statBlock, targetStance));
	}
	
}
