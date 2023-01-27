package UtilityClasses;

import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import Conflict.GameWindow;
import Shoot.Shoot;
import Trooper.Trooper;
import Unit.Unit;

public class ShootUtility {
	
	public static Shoot setTarget(Unit shooterUnit, Unit targetUnit, Shoot shoot, Trooper shooter, Trooper target,
			String wepName, int ammoIndex) {
		System.out.println("Set target tropoer");

		if (shoot == null) {
			return new Shoot(shooterUnit, targetUnit, shooter, target, wepName, ammoIndex);
		} else {
			Shoot newShoot = new Shoot(shooterUnit, targetUnit, shooter, target, wepName, ammoIndex);
			System.out.println("Shoot starting aim time: "+shoot.startingAimTime);
			System.out.println("Shots: "+shoot.shots+", Aim Time: "+shoot.aimTime);
			newShoot.previouslySpentCa = shoot.aimTime - shoot.startingAimTime + shoot.previouslySpentCa + shoot.shots;
			newShoot.spentCombatActions = newShoot.previouslySpentCa;
			System.out.println("New spent combat actions: "+newShoot.spentCombatActions);
			return newShoot;
		}

	}

	public static Shoot setTargetUnit(Unit shooterUnit, Unit targetUnit, Shoot shoot, Trooper shooter, String wepName,
			int ammoIndex) {
		System.out.println("Set target unit");

		if (shoot == null) {
			System.out.println("new shoot target unit");
			return new Shoot(shooterUnit, targetUnit, shooter, null, wepName, ammoIndex);
		} else {
			Shoot newShoot = new Shoot(shooterUnit, targetUnit, shooter, null, wepName, ammoIndex);
			newShoot.previouslySpentCa = shoot.aimTime - shoot.startingAimTime + shoot.previouslySpentCa + shoot.shots;
			newShoot.spentCombatActions = newShoot.previouslySpentCa;
			return newShoot;
		}

	}

	public static void shootGuiUpdate(JLabel lblPossibleShots, JLabel lblAimTime, JLabel lblTN, JLabel lblTfSpentCa,
			JLabel lblAmmo, JLabel lblCombatActions, JCheckBox chckbxFullAuto, ArrayList<Shoot> shoot) {
		System.out.println("Shoot Gui Update");

		int meanAimTime = getMeanAimTime(shoot);
		int meanCombatActions = getMeanCombatActions(shoot);
		int meanSpentCombatActions = getMeanSpentCombatActions(shoot);
		int meanFullAutoTn = getMeanFullAutoTn(shoot);
		int meanSingleTn = getMeanSingleTn(shoot);
		int meanSuppressiveTn = getMeanSuppresiveTn(shoot);
		int meanAmmo = getMeanAmmo(shoot);

		lblPossibleShots.setText((shoot.get(0).target == null ? "Suppressive Fire Shots: " : "Possible Shots: ")
				+ (shoot.get(0).target == null ? shoot.get(0).wep.suppressiveROF
						: (meanCombatActions - meanSpentCombatActions)));
		lblAimTime.setText("Aim Time: " + meanAimTime);

		String rslt = "TN: "
				+ (shoot.get(0).target != null ? (chckbxFullAuto.isSelected() ? meanFullAutoTn : meanSingleTn)
						: meanSuppressiveTn);

		rslt += chckbxFullAuto.isSelected() ? ", Full Auto: " + shoot.get(0).fullAutoResults() : "";

		lblTN.setText(rslt);
		lblTfSpentCa.setText("Spent CA: " + meanSpentCombatActions);
		lblAmmo.setText("Ammo: " + meanAmmo);
		lblCombatActions.setText("CA: " + meanCombatActions);

		GameWindow.gameWindow.conflictLog.addQueuedText();
		GameWindow.gameWindow.refreshInitiativeOrder();
	}

	public static int getMeanAimTime(ArrayList<Shoot> shots) {

		int aimTime = 0;

		for (Shoot shoot : shots) {
			aimTime += shoot.aimTime;
		}

		return aimTime / shots.size();

	}

	public static int getMeanCombatActions(ArrayList<Shoot> shots) {

		int combatActions = 0;

		for (Shoot shoot : shots) {
			combatActions += shoot.shooter.combatActions;
		}

		return combatActions / shots.size();

	}

	public static int getMeanSpentCombatActions(ArrayList<Shoot> shots) {

		int spentCombatActions = 0;

		for (Shoot shoot : shots) {
			spentCombatActions += shoot.spentCombatActions;
		}

		return spentCombatActions / shots.size();

	}

	public static int getMeanFullAutoTn(ArrayList<Shoot> shots) {

		int fullAutoTn = 0;

		for (Shoot shoot : shots) {
			fullAutoTn += shoot.fullAutoTn;
		}

		return fullAutoTn / shots.size();

	}

	public static int getMeanSingleTn(ArrayList<Shoot> shots) {

		int singleTn = 0;

		for (Shoot shoot : shots) {
			singleTn += shoot.singleTn;
		}

		return singleTn / shots.size();

	}

	public static int getMeanSuppresiveTn(ArrayList<Shoot> shots) {

		int suppressiveTn = 0;

		for (Shoot shoot : shots) {
			suppressiveTn += shoot.suppressiveTn;
		}

		return suppressiveTn / shots.size();

	}

	public static int getMeanAmmo(ArrayList<Shoot> shots) {

		int ammo = 0;

		for (Shoot shoot : shots) {
			ammo += shoot.shooter.ammo;
		}

		return ammo / shots.size();

	}

	public static void aimUpdate(Shoot shoot, int aimIndex) {
		System.out.println("Aim Update");

		if (aimIndex == 0)
			shoot.autoAim();
		else
			shoot.setAimTime(aimIndex);

	}

	public static void bonusUpdate(Shoot shoot) {
		System.out.println("Bonus Auto Update");

		shoot.setBonuses(0, 0, 0);
	}

	public static void shoot(Shoot shoot, boolean fullAuto, boolean homing) {

		if (fullAuto)
			shoot.burst();
		else
			shoot.shot(homing);

		GameWindow.gameWindow.conflictLog.addNewLine(shoot.shotResults);
	}

}
