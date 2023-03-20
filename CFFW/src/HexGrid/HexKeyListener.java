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
import UtilityClasses.Keyboard;

public class HexKeyListener {
	private HexKeyListener() {
	}
	
	
	public static void addKeyListeners() {
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

			
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
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
