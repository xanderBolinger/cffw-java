package HexGrid;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import CeHexGrid.Chit;
import CeHexGrid.Chit.Facing;
import CeHexGrid.FloatingTextManager;
import Conflict.Game;
import Conflict.GameWindow;
import CorditeExpansion.CorditeExpansionGame;
import CorditeExpansion.CorditeExpansionWindow;
import HexGrid.HexGrid.Panel;
import HexGrid.Waypoint.WaypointManager;
import UtilityClasses.Keyboard;

public class HexKeyListener {
	private HexKeyListener() {
	}
	
	
	public static void addKeyListeners() {
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

			
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
            	
            	var hexGrid = GameWindow.gameWindow.hexGrid;
            	var panel = hexGrid.panel;
            	
            	synchronized (HexKeyListener.class) {
                    switch (e.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (e.getKeyCode() == KeyEvent.VK_Q && 
                        Keyboard.isKeyPressed(KeyEvent.VK_CONTROL) &&
	                        Chit.isAChitSelected()) {
	                            Chit.getSelectedChit().facing = 
	                            		Facing.turnCounterClockwise(Chit.getSelectedChit().facing);
                            System.out.println("Facing: "+Chit.getSelectedChit().facing);
                        } else if(e.getKeyCode() == KeyEvent.VK_E && 
                                Keyboard.isKeyPressed(KeyEvent.VK_CONTROL)
                                && Chit.isAChitSelected()) {
                        	 Chit.getSelectedChit().facing = 
                             		Facing.turnClockwise(Chit.getSelectedChit().facing);
                        	 System.out.println("Facing: "+Chit.getSelectedChit().facing);
                        } else if(e.getKeyCode() == KeyEvent.VK_DELETE && Chit.isAChitSelected()) {
                        	GameWindow.gameWindow.game.chits.remove(Chit.getSelectedChit());
                        	Chit.unselectChit();
                        } else if(e.getKeyCode() == KeyEvent.VK_A && panel.selectedUnit != null && 
                                Keyboard.isKeyPressed(KeyEvent.VK_SHIFT)) {
                        	 panel.selectedUnit.unit.waypointData
    						.clearWaypoints(panel.selectedUnit.unit);
                        } else if(e.getKeyCode() == KeyEvent.VK_A && panel.selectedUnit != null) {
                        	WaypointManager.addWaypoints = !WaypointManager.addWaypoints;
                        	System.out.println("Toggle add waypoints: "+WaypointManager.addWaypoints);
                        } 
                        
                        break;
                       
                    case KeyEvent.KEY_RELEASED:
                        
                        break;
                    }
                    return false;
                }
            }
        });
		
	}
}
