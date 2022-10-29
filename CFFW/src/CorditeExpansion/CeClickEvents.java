package CorditeExpansion;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import CorditeExpansion.MoveAction.MoveType;
import UtilityClasses.Keyboard;

public class CeClickEvents {

	
	public static void addMoveAction(int x, int y) {
		
		System.out.println("Add move action");
		
		MoveAction moveAction = CorditeExpansionGame.selectedTrooper.ceStatBlock.getLastMoveAction();
		
		if(Keyboard.isKeyPressed(KeyEvent.VK_CONTROL) || moveAction == null) {
			addNewMoveAction(x, y);
			return;
		}
		
		
		System.out.println("move action add target hex");
		moveAction.addTargetHex(new Cord(x, y));
		
		
		CorditeExpansionWindow.refreshCeLists();
	}
	
	public static void addNewMoveAction(int x, int y) {
		
		System.out.println("add new target hex");
		
		ArrayList<Cord> cords = new ArrayList<>();
		cords.add(new Cord(x, y));
		CeAction.addMoveAction(MoveType.STEP, CorditeExpansionGame.selectedTrooper.ceStatBlock, cords, 2);
		
		CorditeExpansionWindow.refreshCeLists();
	}
	
	
}
