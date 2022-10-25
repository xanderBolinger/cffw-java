package CorditeExpansion;

import java.util.ArrayList;

import CorditeExpansion.MoveAction.MoveType;

public interface CeAction {
	
	public boolean completed = false; 
	
	void spendCombatAction();
	boolean completed();
		
	public static void addMoveAction(MoveType type, CeStatBlock statBlock, ArrayList<Cord> cords) {
		
		statBlock.addAction(new MoveAction(type, statBlock, cords)); 
		
	}
	
}
