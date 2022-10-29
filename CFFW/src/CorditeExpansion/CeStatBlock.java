package CorditeExpansion;

import java.util.ArrayList;

import CeHexGrid.Chit;
import CeHexGrid.Chit.Facing;
import CorditeExpansion.CeAction.ActionType;
import CorditeExpansion.CorditeExpansionGame.Impulse;
import Trooper.Trooper;
import Trooper.Trooper.MaximumSpeed;
import UtilityClasses.ExcelUtility;

public class CeStatBlock {

	public double quickness; 
	public int adaptabilityFactor; 
	public int combatActions; 
	public int spentCombatActions = 0; 
	
	public int totalMoved = 0; 

	private Cord cord = new Cord(0, 0);
	private CeAction activeAction;
	private ArrayList<CeAction> coac = new ArrayList<>();
	
	public Chit chit; 
	
	public CeStatBlock(Trooper trooper) {
		quickness = trooper.maximumSpeed.get();
		combatActions = trooper.combatActions;
		chit = new Chit();
	}
	
	public void moveTrooper(Cord cord) {
		this.cord = cord; 
		chit.xCord = cord.xCord;
		chit.yCord = cord.yCord;
	}
	
	public void spendCombatAction() {		
		activeAction.spendCombatAction();
		
		if(activeAction.completed()) {
			System.out.println("Action Completed");
			activeAction = null;
		}
		
		spendCaPoint();
	}
	
	public void prepareCourseOfAction() {
		coac.get(0).spendCombatAction();
		if(coac.get(0).ready()) {
			addAction(coac.remove(0));
		}

		spendCaPoint();
	}
	
	public void doNothing() {
		spendCaPoint();
	}
	
	public void spendCaPoint() {
		if(spentCombatActions >= combatActions) 
			spentCombatActions = 0; 
		
		spentCombatActions++;
	}
	
	public int getActionTiming() {
		
		int actions = 0; 
		int impulse = CorditeExpansionGame.impulse.getValue();
		
		//System.out.println("impulse: "+impulse);
		//System.out.println("combatActions: "+combatActions);
		try {
			actions = (int) ExcelUtility.getResultsTwoWayFixedValues(impulse, combatActions, "caperimpulse.xlsx", true, true);
			//System.out.println("Actions: "+ExcelUtility.getResultsTwoWayFixedValues(impulse, combatActions, "caperimpulse.xlsx", false, true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return actions; 
		
	}
	
	public void addAction(CeAction action) {
		activeAction = action;
	}
	
	public void addActionCoac(CeAction action) {
		coac.add(action);
	}
	
	public void cancelAction() {
		activeAction = null; 
	}
	
	public void removeCoac(int index) {
		coac.remove(index);
	}
	
	public Cord getPosition() {
		return cord; 
	}
	
	public CeAction getAction() {
		return activeAction;
	}
	
	
	public Facing getFacing() {
		return chit.facing;
	}
	
	public void setFacing(Facing facing) {
		chit.facing = facing;
	}
	
	
	public ArrayList<CeAction> getCoac() {
		return coac;
	}
	
	public MoveAction getLastMoveAction() {
		
		MoveAction moveAction = null; 
		
		for(CeAction action : coac) {
			if(action.getActionType() != ActionType.MOVE)
				continue; 
			moveAction = (MoveAction) action; 
		}
		
		return moveAction;
		
	}
	
	public ArrayList<String> getCeStatList() {
		
		ArrayList<String> results = new ArrayList<>();
		
		String activeAction = "Currently Executing Action: None."; 
		
		if(acting()) {
			activeAction = "Currently Executing Action: "+ activeAction.toString();
		}
		
		results.add(activeAction);
		
		String spentCombatActions = "Spent Combat Actions: "+this.spentCombatActions;
		String combatActions = "Combat Actions: "+this.combatActions;
		
		results.add(spentCombatActions);
		results.add(combatActions);
		
		return results; 
	}
	
	
	public boolean acting() {
		return activeAction != null ? true : false; 
	}
	
	public boolean preparing() {
		return coacSize() > 0 ? true : false; 
	}
	
	// the below methods are for testing 
	
	public int coacSize() {
		return coac.size();
	}
	
}
