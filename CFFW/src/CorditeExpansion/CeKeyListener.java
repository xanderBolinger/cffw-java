package CorditeExpansion;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import UtilityClasses.Keyboard;

public class CeKeyListener {
	
	private CeKeyListener() {}
	
	public static void addKeyListeners(JFrame jframe) {
		jframe.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
			
				if(spacePressed()) {
					CorditeExpansionGame.action();
				}
			
				CorditeExpansionWindow.refreshCeLists();
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
	}

	
	public static boolean spacePressed() {
		return Keyboard.isKeyPressed(KeyEvent.VK_SPACE);
	}
	

}
