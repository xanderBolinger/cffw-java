package CorditeExpansion;

import java.util.ArrayList;

import CorditeExpansion.MoveAction.MoveType;

public interface CeAction {
	
	public boolean completed = false; 
	public boolean ready = false; 
	
	void spendCombatAction();
	boolean completed();
	boolean ready();
		
	public static void addMoveAction(MoveType type, CeStatBlock statBlock, ArrayList<Cord> cords, int coac) {
		
		if(coac == 0) {
			statBlock.addAction(new MoveAction(type, statBlock, cords, coac)); 			
		} else {
			statBlock.addActionCoac(new MoveAction(type, statBlock, cords, coac)); 
		}
		
		
		
	}
	
}
