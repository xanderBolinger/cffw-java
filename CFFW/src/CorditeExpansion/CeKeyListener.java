package CorditeExpansion;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import CeHexGrid.Chit.Facing;
import CorditeExpansionActions.CeAction;
import CorditeExpansionStatBlock.StatBlock;
import CorditeExpansionStatBlock.StatBlock.MoveSpeed;
import CorditeExpansionStatBlock.StatBlock.Stance;
import UtilityClasses.DiceRoller;
import UtilityClasses.Keyboard;

import CeHexGrid.CeHexGrid;

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

				stanceChange();

				cancelAction();

				reaction();

				stabalize();

				CorditeExpansionWindow.refreshCeLists();
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
	}

	public static boolean textDisplayed() {
		return FloatingTextManager.size() > 0 ? true : false;
	}

	public static boolean spacePressed() {
		return Keyboard.isKeyPressed(KeyEvent.VK_SPACE);
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
		if (CorditeExpansionGame.selectedTrooper == null || !Keyboard.isKeyPressed(KeyEvent.VK_X))
			return;

		if (CorditeExpansionWindow.actionList.getSelectedIndex() > -1) {
			CorditeExpansionGame.selectedTrooper.ceStatBlock
					.removeCoac(CorditeExpansionWindow.actionList.getSelectedIndex());
		} else if (CorditeExpansionWindow.detailsList.getSelectedIndex() == 0) {
			CorditeExpansionGame.selectedTrooper.ceStatBlock.cancelAction();
		}

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
		
		System.out.println("Stabalize init");
		CeAction.addStabalizeAction(CorditeExpansionGame.selectedTrooper.ceStatBlock);

	}

}
