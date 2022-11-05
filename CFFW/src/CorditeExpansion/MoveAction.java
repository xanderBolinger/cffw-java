package CorditeExpansion;

import java.util.ArrayList;

import CeHexGrid.Chit.Facing;
import CorditeExpansion.CeAction.ActionType;
import CorditeExpansionStatBlock.StatBlock;
import CorditeExpansionStatBlock.StatBlock.MoveSpeed;

public class MoveAction implements CeAction {

	public ActionType actionType; 
	boolean completed = false; 
	int coac; 
	int spentCoac = 0; 
	
	StatBlock statBlock;
	public ArrayList<Cord> cords = new ArrayList<>();
	double movementFraction = 0.0; 
	
	public MoveAction(StatBlock statBlock, ArrayList<Cord> cords, int coac) {
		
		actionType = ActionType.MOVE;
		
		this.statBlock = statBlock;
		this.cords = cords;
		this.coac = coac;
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
		if(cords.size() < 1)
			return; 
		
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
			//System.out.println("Not prepared");
			spentCoac++;
			return; 
		}
		
		if(statBlock.totalMoved >= statBlock.quickness) {
			return; 
		}
		
		if(statBlock.moveSpeed == MoveSpeed.CRAWL) {
			//System.out.println("Crawl");
			movementFraction += 0.5; 
			if(movementFraction >= 1) {
				moveTrooper();
			}
		} else if(statBlock.moveSpeed == MoveSpeed.STEP) {
			//System.out.println("Move");
			moveTrooper();
		} else if(statBlock.moveSpeed == MoveSpeed.RUSH) {
			//System.out.println("Rush");
			moveTrooper();
			moveTrooper();
		} else if(statBlock.moveSpeed == MoveSpeed.SPRINT) {
			//System.out.println("Sprint");
			moveTrooper();
			moveTrooper();
			moveTrooper();
			moveTrooper();
		}
				
		if(cords.size() < 1) {
			//System.out.println("Set completed");
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
		String rslt = "Move Action: <";
		
		for(Cord cord : cords) {
			rslt += "("+cord.xCord+","+cord.yCord+")";
			
			if(cord != cords.get(cords.size()-1)) {
				rslt += ", ";
			}
			
		}
		
		return rslt + ">";
	}

	@Override
	public void setPrepared() {
		spentCoac = coac;
	}
	
}
