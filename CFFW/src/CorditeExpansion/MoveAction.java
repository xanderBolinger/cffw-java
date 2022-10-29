package CorditeExpansion;

import java.util.ArrayList;

import CeHexGrid.Chit.Facing;
import CorditeExpansion.CeAction.ActionType;

public class MoveAction implements CeAction {

	MoveType type;
	public ActionType actionType; 
	boolean completed = false; 
	int coac; 
	int spentCoac = 0; 
	
	CeStatBlock statBlock;
	public ArrayList<Cord> cords = new ArrayList<>();
	double movementFraction = 0.0; 
	
	Facing turnFacing;
	
	public enum MoveType {
		CRAWL,STEP,TURN
	}
	
	
	public MoveAction(MoveType type, CeStatBlock statBlock, ArrayList<Cord> cords, int coac) {
		
		actionType = ActionType.MOVE;
		
		this.type = type; 
		this.statBlock = statBlock;
		this.cords = cords;
		this.coac = coac;
	}
	
	public MoveAction(MoveType type, CeStatBlock statBlock, Facing facing) {
		
		actionType = ActionType.TURN;
		
		this.type = type; 
		this.statBlock = statBlock;
		this.turnFacing = facing;
	}
	
	
	public void addTargetHex(Cord cord) {
		cords.add(cord);
	}
	
	public void addTargetHexes(ArrayList<Cord> cords) {
		
		for(Cord cord : cords)
			this.cords.add(cord);

	}
	
	public void removeTargetHex(int index) {
		cords.remove(index);
	}
	
	public void moveTrooper() {
		
		//System.out.println("Move Trooper");
		
		Cord cord = cords.remove(0);
		statBlock.moveTrooper(cord);
		movementFraction = 0.0;
		statBlock.totalMoved++;
	}
	
	public Cord lastCord() {
		return cords.get(cords.size()-1);
	}
	
	
	@Override
	public ActionType getActionType() {
		return actionType;
	}

	@Override
	public void spendCombatAction() {

		if(spentCoac < coac) {
			spentCoac++;
			return; 
		}
		
		if(statBlock.totalMoved >= statBlock.quickness) {
			return; 
		}
		
		switch(type) {
		
			case STEP:
				System.out.println("Move");
				moveTrooper();
			case CRAWL:
				movementFraction += 0.25; 
				if(movementFraction >= 1) {
					moveTrooper();
				}
			case TURN:
				statBlock.setFacing(turnFacing);

		}
		
		if(cords.size() < 1) {
			System.out.println("Set completed");
			completed = true;
		}
		
	}
	
	@Override
	public boolean completed() {
		return completed;
	}
	
	@Override
	public boolean ready() {
		return spentCoac >= coac ? true : false;
	}
	
	@Override
	public String toString() {
		return "Move Action";
	}
	
}
