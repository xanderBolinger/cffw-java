package CorditeExpansion;

import java.util.ArrayList;

import CeHexGrid.Chit.Facing;
import CorditeExpansion.MoveAction.MoveType;

public interface CeAction {
	
	public enum ActionType {
		MOVE,TURN
	}
	
	@Override
	String toString();
	
	void spendCombatAction();
	boolean completed();
	boolean ready();
	ActionType getActionType();
		
	public static void addMoveAction(MoveType type, CeStatBlock statBlock, ArrayList<Cord> cords, int coac) {
		
		if(coac == 0) {
			statBlock.addAction(new MoveAction(type, statBlock, cords, coac)); 			
		} else {
			statBlock.addActionCoac(new MoveAction(type, statBlock, cords, coac)); 
		}
				
	}
	
	public static void addTurnAction(MoveType type, CeStatBlock statBlock, Facing facing) {

		statBlock.addAction(new MoveAction(type, statBlock, facing));
		
	}
	
}
