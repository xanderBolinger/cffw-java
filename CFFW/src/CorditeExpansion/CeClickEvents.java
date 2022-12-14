package CorditeExpansion;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import CorditeExpansionActions.AimAction;
import CorditeExpansionActions.CeAction;
import CorditeExpansionActions.FireAction;
import CorditeExpansionActions.MoveAction;
import CorditeExpansionStatBlock.StatBlock;
import Trooper.Trooper;
import UtilityClasses.Keyboard;

public class CeClickEvents {

	
	public static void addMoveAction(int x, int y) {
		
		
		MoveAction moveAction = CorditeExpansionGame.selectedTrooper.ceStatBlock.getLastMoveAction();
		
		if(Keyboard.isKeyPressed(KeyEvent.VK_CONTROL) || moveAction == null) {
			addNewMoveAction(x, y);
			return;
		}
		
		
		moveAction.addTargetHex(new Cord(x, y));
		
		
		CorditeExpansionWindow.refreshCeLists();
	}
	
	public static void addNewMoveAction(int x, int y) {
		

		ArrayList<Cord> cords = new ArrayList<>();
		cords.add(new Cord(x, y));
		CeAction.addMoveAction(CorditeExpansionGame.selectedTrooper.ceStatBlock, cords, 2);
		
		CorditeExpansionWindow.refreshCeLists();
	}
	
	public static void addAimHex(Cord cord) {
		
		StatBlock stat = CorditeExpansionGame.selectedTrooper.ceStatBlock;
		AimAction action = stat.getAimAction();
		
		if(action == null) {
			CeAction.addAimAciton(stat, cord);
			return;
		}
		
		System.out.println("coac size: "+stat.coacSize());
		
		action.addTargetHex(cord);
	}
	
	public static void addSuppressionHex(Cord cord) {
		
		StatBlock stat = CorditeExpansionGame.selectedTrooper.ceStatBlock;
		FireAction action = stat.getFireAction();
		
		if(action == null) {
			CeAction.addShootAction(stat, cord);
			return;
		}
		
		System.out.println("coac size: "+stat.coacSize());
		
		action.addSuppresionHex(cord);
	}
	
	public static void setAimTarget(Trooper target) {
		
		StatBlock stat = CorditeExpansionGame.selectedTrooper.ceStatBlock;
		AimAction action = stat.getAimAction();
		
		if(action == null) {
			CeAction.addAimAciton(stat, target);
			return;
		}
		
		//System.out.println("coac size: "+stat.coacSize());
		
		action.setTargetTrooper(target);
	}
	
	public static void setShootTarget(Trooper target) {
		
		StatBlock stat = CorditeExpansionGame.selectedTrooper.ceStatBlock;
		
		CeAction.addShootAction(stat, target);
		
		
	}
	
	public static void setGrenadeTarget(Cord cord) {
		
		CeAction.addThrowAction(CorditeExpansionGame.selectedTrooper, CorditeExpansionGame.selectedTrooper.ceStatBlock.rangedStatBlock.weapon, cord);
		
	}
	
	
}
