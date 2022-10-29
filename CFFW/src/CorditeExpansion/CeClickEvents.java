package CorditeExpansion;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
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
	
}
