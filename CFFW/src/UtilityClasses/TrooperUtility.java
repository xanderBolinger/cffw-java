package UtilityClasses;

import Conflict.GameWindow;
import FatigueSystem.FatigueSystem.FatiguePoints;
import FatigueSystem.FatigueSystem.PhysicalRecoveryTime;
import Trooper.IndividualStats;
import Trooper.Trooper;
import Unit.Unit;

public class TrooperUtility {

	public TrooperUtility() {

	}

	public static int getKO(Trooper trooper) {
		return (trooper.skills.getSkill("Fighter").value / 6) * (trooper.hlt / 2);
	}

	public static void recalcTrooper(Trooper trooper) {

		String name = trooper.name;

		// Creates skills
		// Stores attributes in an array for the skill attr parameter
		int attr[] = { trooper.str, trooper.wit, trooper.soc, trooper.wil, trooper.per, trooper.hlt, trooper.agi };
		trooper.skills(trooper.designation, attr);

		setTrooperStatsSafe(trooper);

		trooper.name = name;
	}

	public static void setTrooperStatsSafe(Trooper trooper) {

		String name = trooper.name;

		trooper.setPCStats();
		// Create and set individual stats
		IndividualStats individual = new IndividualStats(trooper.combatActions, trooper.sal, trooper.pistol,
				trooper.rifle, trooper.launcher, trooper.heavy, trooper.subgun, false);
		trooper.name = individual.name;
		trooper.P1 = individual.P1;
		trooper.P2 = individual.P2;
		trooper.pistolRWS = individual.pistolRWS;
		trooper.rifleRWS = individual.rifleRWS;
		trooper.launcherRWS = individual.launcherRWS;
		trooper.heavyRWS = individual.heavyRWS;
		trooper.subgunRWS = individual.subgunRWS;

		trooper.name = name;

	}

	public static double getAVMod(int analeticValue) {
		if (analeticValue <= 40)
			return 2;
		if (analeticValue >= 180)
			return 0.25;

		// return ((double)analeticValue - 20.0) / 80.0;
		return 0.0000245936 * (analeticValue * analeticValue) + (-0.0175524) * analeticValue + 2.63639;
	}

	public static int getFatiguePointPenaltyChange(int fatiguePoints) {

		int fpCount = fatiguePoints;
		int penalty;

		if (fpCount < 11) {
			penalty = 0;
		} else if (fpCount < 15) {
			penalty = 1;
		} else if (fpCount < 19) {
			penalty = 2;
		} else if (fpCount < 23) {
			penalty = 3;
		} else if (fpCount < 27) {
			penalty = 4;
		} else if (fpCount < 31) {
			penalty = 5;
		} else if (fpCount < 32) {
			penalty = 6;
		} else if (fpCount < 33) {
			penalty = 7;
		} else if (fpCount < 34) {
			penalty = 8;
		} else {

			penalty = fpCount - 34 + 9;

		}

		return penalty;
	}

	public void AddRecoveryTimeLight(float time, FatiguePoints fatiguePoints, PhysicalRecoveryTime physicalRecoveryTime,
			int analeticValue) {
		if (fatiguePoints.get() == 0) {
			physicalRecoveryTime.set(0);
			return;
		}

		physicalRecoveryTime.set(physicalRecoveryTime.get() + time);
		float count = physicalRecoveryTime.get();
		// System.out.println("Seconds: "+count);
		// System.out.println("FP: "+fatiguePoints.get());

		for (int i = 0; i < count; i++) {
			double recovery = ((double) analeticValue / 10.0) / 12.0 / 3;
			System.out.println("Recovery(" + i + "): " + recovery);
			// System.out.println("Pre Recovery FP: "+fatiguePoints.get());

			// System.out.println("Diff: "+(fatiguePoints.get() - recovery));

			if (fatiguePoints.get() - recovery < 0) {
				fatiguePoints.set(0);
			} else {
				fatiguePoints.set(fatiguePoints.get() - recovery);
			}
			// System.out.println("Post Recovery FP: "+fatiguePoints.get());

			// if (physicalRecoveryTime.get() - 1 < 0) { physicalRecoveryTime.set(0); } else
			// { physicalRecoveryTime.set(physicalRecoveryTime.get() - 1); }
		}
		physicalRecoveryTime.set(0);

	}

	// Takes incorrect trooper
	// Returns correct trooper
	public static Trooper getRealTrooperReference(Trooper incorrectTrooper) {

		for (Unit unit : GameWindow.gameWindow.initiativeOrder) {
			for (Trooper trooper : unit.individuals) {
				if (trooper.identifier.equals(incorrectTrooper.identifier))
					return trooper;
			}
		}

		return null;
	}

}