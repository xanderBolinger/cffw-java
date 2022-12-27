package CorditeExpansionActions;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import CeHexGrid.Chit.Facing;
import CorditeExpansion.Cord;
import CorditeExpansionStatBlock.StatBlock;
import CorditeExpansionStatBlock.StatBlock.Stance;
import Items.Weapons;
import Trooper.Trooper;
import UtilityClasses.Keyboard;

public interface CeAction {
	
	public enum ActionType {
		MOVE,TURN,CHANGESTANCE,STABALIZE,AIM,FIRE,THROW,RELOAD,DODGE,CUSTOM
	}
	
	@Override
	String toString();
	
	void spendCombatAction();
	void setPrepared();
	boolean completed();
	boolean ready();
	ActionType getActionType();
	
	public static void addCustomAction(Trooper trooper, CustomAction customAction) {
		trooper.ceStatBlock.addActionCoac(customAction);
	}
	
	public static void addShootAction(StatBlock statBlock, Trooper target) {
		statBlock.addActionCoac(new FireAction(statBlock, target));
	}
	
	public static void addShootAction(StatBlock statBlock, Cord cord) {
		statBlock.addActionCoac(new FireAction(statBlock, cord));
	}
	
	public static void addReloadAction(Trooper trooper) {
		trooper.ceStatBlock.addActionCoac(new ReloadAction(trooper));
	}
	
	public static void addThrowAction(Trooper trooper, Weapons throwAble, Cord cord) {
		trooper.ceStatBlock.addActionCoac(new ThrowAction(trooper.ceStatBlock, throwAble, cord));
	}
	
	public static void addMoveAction(StatBlock statBlock, ArrayList<Cord> cords, int coac) {
		
		if(coac == 0) {
			statBlock.addAction(new MoveAction(statBlock, cords, coac)); 			
		} else {
			statBlock.addActionCoac(new MoveAction(statBlock, cords, coac)); 
		}
				
	}
	
	public static void addTurnAction(StatBlock statBlock, boolean clockwise) {

		
		Facing facing = statBlock.getFacing();
		
		if(clockwise) {
			facing = Facing.turnClockwise(facing);
		} else {
			facing = Facing.turnCounterClockwise(facing);
		}
		
		statBlock.addAction(new TurnAction(statBlock, facing));
		
	}
	
	public static void updateTurnAction(StatBlock statBlock, boolean clockwise) {
		
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
	
	public static void addChangeStanceAction(StatBlock statBlock, Stance targetStance) {
		statBlock.addAction(new ChangeStanceAction(statBlock, targetStance));
	}
	
	public static void addStabalizeAction(StatBlock statBlock) {
		System.out.println("add action");
		statBlock.addActionCoac(new StabalizeAction(statBlock));
	}
	
	public static void addAimAciton(StatBlock statBlock, Cord cord) {
		
		statBlock.clearAim();
		
		AimAction action = new AimAction(statBlock);
		action.addTargetHex(cord);
		statBlock.addActionCoac(action);
	}
	
	public static void addAimAciton(StatBlock statBlock, Trooper target) {
		
		statBlock.clearAim();
		
		AimAction action = new AimAction(statBlock);
		action.setTargetTrooper(target);
		statBlock.addActionCoac(action);
	}
	
}
