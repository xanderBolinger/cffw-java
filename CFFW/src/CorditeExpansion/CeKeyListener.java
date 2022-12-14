package CorditeExpansion;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;

import CeHexGrid.Chit.Facing;
import CorditeExpansionActions.CeAction;
import CorditeExpansionStatBlock.StatBlock;
import CorditeExpansionStatBlock.StatBlock.MoveSpeed;
import CorditeExpansionStatBlock.StatBlock.Stance;
import UtilityClasses.DiceRoller;
import UtilityClasses.Keyboard;

import CeHexGrid.CeHexGrid;
import CeHexGrid.FloatingTextManager;
import CommandLineInterface.CommandLineInterface;

public class CeKeyListener {

	private CeKeyListener() {
	}

	public static void addKeyListeners(JFrame jframe) {
		jframe.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" +
				// e.getKeyChar());

				if (textDisplayed() && !spacePressed()) {
					CorditeExpansionWindow.refreshCeLists();
					return;
				} else if (textDisplayed() && spacePressed()) {
					FloatingTextManager.clearFloatingText();
					CorditeExpansionWindow.refreshCeLists();
					return;
				}

				if (spacePressed()) {
					CorditeExpansionGame.action();
				}

				numberPress();

				rotation();

				reload();
				
				stanceChange();

				prepare();
				
				cancelAction();

				reaction();

				stabalize();

				dodge();
				
				cli();
				
				CorditeExpansionWindow.refreshCeLists();
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
	}

	public static void cli() {
		if (!Keyboard.isKeyPressed(KeyEvent.VK_SLASH))
			return;

		try {
			new CommandLineInterface();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean textDisplayed() {
		return FloatingTextManager.size() > 0 ? true : false;
	}

	public static boolean spacePressed() {
		return Keyboard.isKeyPressed(KeyEvent.VK_SPACE);
	}

	public static void dodge() {
		if (CorditeExpansionGame.selectedTrooper == null || !Keyboard.isKeyPressed(KeyEvent.VK_D))
			return;
		
		CorditeExpansionGame.selectedTrooper.ceStatBlock.dodge();
	}
	
	public static void reload() {
		if (CorditeExpansionGame.selectedTrooper == null || !Keyboard.isKeyPressed(KeyEvent.VK_R)
				|| Keyboard.isKeyPressed(KeyEvent.VK_CONTROL))
			return;
		
		if(!CorditeExpansionGame.selectedTrooper.ceStatBlock.rangedStatBlock.hasAmmo(CorditeExpansionGame.selectedTrooper)) {
			FloatingTextManager.addFloatingText(CorditeExpansionGame.selectedTrooper.ceStatBlock.cord, "Out of ammo!");
		}
		
		CeAction.addReloadAction(CorditeExpansionGame.selectedTrooper);
		
		
	}
	
	public static void numberPress() {

		if (CorditeExpansionGame.selectedTrooper == null)
			return;

		StatBlock stat = CorditeExpansionGame.selectedTrooper.ceStatBlock;

		if (Keyboard.isKeyPressed(KeyEvent.VK_1)) {
			stat.moveSpeed = MoveSpeed.STEP;
		} else if (Keyboard.isKeyPressed(KeyEvent.VK_2)) {
			stat.moveSpeed = MoveSpeed.RUSH;
		} else if (Keyboard.isKeyPressed(KeyEvent.VK_3)) {
			stat.moveSpeed = MoveSpeed.SPRINT;
		}

	}

	public static void rotation() {
		if (CorditeExpansionGame.selectedTrooper == null
				|| (!Keyboard.isKeyPressed(KeyEvent.VK_E) && !Keyboard.isKeyPressed(KeyEvent.VK_Q)))
			return;

		if (Keyboard.isKeyPressed(KeyEvent.VK_CONTROL)
				&& (Keyboard.isKeyPressed(KeyEvent.VK_E) || Keyboard.isKeyPressed(KeyEvent.VK_Q)))
			CeAction.addTurnAction(CorditeExpansionGame.selectedTrooper.ceStatBlock,
					Keyboard.isKeyPressed(KeyEvent.VK_E));
		else
			CeAction.updateTurnAction(CorditeExpansionGame.selectedTrooper.ceStatBlock,
					Keyboard.isKeyPressed(KeyEvent.VK_E));

	}

	public static void stanceChange() {
		if (CorditeExpansionGame.selectedTrooper == null
				|| (!Keyboard.isKeyPressed(KeyEvent.VK_Z) && !Keyboard.isKeyPressed(KeyEvent.VK_C)))
			return;

		StatBlock stat = CorditeExpansionGame.selectedTrooper.ceStatBlock;

		if (stat.stance == Stance.STANDING) {
			if (Keyboard.isKeyPressed(KeyEvent.VK_Z)) {
				CeAction.addChangeStanceAction(stat, Stance.PRONE);
			} else {
				CeAction.addChangeStanceAction(stat, Stance.CROUCH);
			}
		} else if (stat.stance == Stance.CROUCH) {
			if (Keyboard.isKeyPressed(KeyEvent.VK_Z)) {
				CeAction.addChangeStanceAction(stat, Stance.PRONE);
			} else {
				CeAction.addChangeStanceAction(stat, Stance.STANDING);
			}
		} else {
			if (Keyboard.isKeyPressed(KeyEvent.VK_Z)) {
				CeAction.addChangeStanceAction(stat, Stance.STANDING);
			} else {
				CeAction.addChangeStanceAction(stat, Stance.CROUCH);
			}
		}

	}

	public static void cancelAction() {
		//System.out.println("Cancel Action");
		if (CorditeExpansionGame.selectedTrooper == null || !Keyboard.isKeyPressed(KeyEvent.VK_X)) {
			//System.out.println("Cancel Action Return");
			return;
		}

		if (CorditeExpansionWindow.actionList.getSelectedIndex() > -1) {
			//System.out.println("Cancel Action 1");
			CorditeExpansionGame.selectedTrooper.ceStatBlock
					.removeCoac(CorditeExpansionWindow.actionList.getSelectedIndex());
		} else if (CorditeExpansionWindow.detailsList.getSelectedIndex() == 0) {
			//System.out.println("Cancel Action 2");
			CorditeExpansionGame.selectedTrooper.ceStatBlock.cancelAction();
		}

	}

	public static void prepare() {
		if (CorditeExpansionGame.selectedTrooper == null
				|| !(Keyboard.isKeyPressed(KeyEvent.VK_P))
				|| CorditeExpansionWindow.actionList.getSelectedIndex() 
				>= CorditeExpansionGame.selectedTrooper.ceStatBlock.coacSize()
				|| CorditeExpansionWindow.actionList.getSelectedIndex()  < 0)
			return;
	
		
		CorditeExpansionGame.selectedTrooper.ceStatBlock
		.setPrepared(CorditeExpansionWindow.actionList.getSelectedIndex());
	}
	
	public static void reaction() {
		if (CorditeExpansionGame.selectedTrooper == null
				|| !(Keyboard.isKeyPressed(KeyEvent.VK_R) && Keyboard.isKeyPressed(KeyEvent.VK_CONTROL))
				|| CorditeExpansionGame.selectedTrooper.ceStatBlock.hesitating)
			return;

		int roll = DiceRoller.randInt(1, 6);
		StatBlock stat = CorditeExpansionGame.selectedTrooper.ceStatBlock;

		if (roll <= stat.adaptabilityFactor) {

			FloatingTextManager.addFloatingText(CorditeExpansionGame.selectedTrooper.ceStatBlock.chit.getCord(),
					"Reacting, AF: " + stat.adaptabilityFactor + ", Roll: " + roll);

			if (CorditeExpansionGame.selectedTrooper.ceStatBlock.getCoac().size() > 0)
				stat.react();
		} else {

			FloatingTextManager.addFloatingText(CorditeExpansionGame.selectedTrooper.ceStatBlock.chit.getCord(),
					"Hesitating, AF: " + stat.adaptabilityFactor + ", Roll: " + roll);

			stat.hesitate();
		}

	}

	public static void stabalize() {
		if (CorditeExpansionGame.selectedTrooper == null
				|| !(Keyboard.isKeyPressed(KeyEvent.VK_S))
				|| CorditeExpansionGame.selectedTrooper.ceStatBlock.hesitating)
			return;
		
		//System.out.println("Stabalize init");
		CeAction.addStabalizeAction(CorditeExpansionGame.selectedTrooper.ceStatBlock);

	}

}
