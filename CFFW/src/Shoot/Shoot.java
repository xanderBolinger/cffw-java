package Shoot;

import java.util.ArrayList;
import java.util.Random;

import Conflict.GameWindow;
import Trooper.Trooper;
import Unit.Unit;

public class Shoot {

	public static ArrayList<Shoot> shootActions = new ArrayList<>();

	public int spentCombatActions;

	public Trooper shooter;
	public Trooper target;

	// Required methods
	// calculate ALM
	// calculate EAL
	// get TN single
	// get TN full auto
	// get full auto results
	// shot
	// full auto shot
	// called shot
	// on miss suppressive fire
	// shoot suppressive fire
	// aim, max aim, rush <= 20 hexes
	
	public int speedALM; 
	
	public void setSpeedALM(Unit targetUnit, Trooper targetTrooper, int pcHexRange) {
		int speedALM;
		String speed = targetUnit.speed;
		
		
		if (speed.equals("None")) {
			speedALM = 0; 
		} else if (speed.equals("Rush")) {
			if (pcHexRange <= 10) {
				speedALM = -10;
			} else if (pcHexRange <= 20) {
				speedALM = -8;
			} else if (pcHexRange <= 40) {
				speedALM = -6;
			} else {
				speedALM = -5;
			}
		} else {

			// Checks if individual is moving;
			int action = GameWindow.gameWindow.game.getCurrentAction();
			int trooperMovingInAction = 0;

			int count = 1;
			for (Trooper trooper : targetUnit.individuals) {

				if (count == 1) {
					if (trooper.compareTo(targetTrooper)) {
						trooperMovingInAction = 1;
						break;
					}

				} else if (count == 2) {
					if (trooper.compareTo(targetTrooper)) {
						trooperMovingInAction = 2;
						break;
					}
				} else {
					if (trooper.compareTo(targetTrooper)) {
						trooperMovingInAction = 3;
						break;
					}
					count = 0;
				}

				count++;

			}

			if (action == 1) {
				if (trooperMovingInAction == 1) {
					speedALM = findSpeedALM(pcHexRange);
				}
			} else if (action == 2) {
				if (trooperMovingInAction == 2) {
					speedALM = findSpeedALM(pcHexRange);
				}
			} else if (action == 3) {
				if (trooperMovingInAction == 3) {
					speedALM = findSpeedALM(pcHexRange);
				}
			} else {

				int roll = new Random().nextInt(3) + 1;
				if (trooperMovingInAction == roll) {
					speedALM = findSpeedALM(pcHexRange);
				}

			}

		}

		this.speedALM = speedALM;
	}
	
	// Takes unit speed
	// If walking, takes action and determines which units are moving
	// Returns speed ALM
	public int findSpeedALM(String speed, int rangeInHexes, int trooperNumber, Unit targetUnit, Trooper targetTrooper) {

		int speedALM = 0;

		if (speed.equals("None")) {
			return 0;
		} else if (speed.equals("Rush")) {
			if (rangeInHexes <= 10) {

				if (maxAim != 1)
					maxAim = 2;
				speedALM = -10;
			} else if (rangeInHexes <= 20) {
				if (maxAim != 1)
					maxAim = 2;
				speedALM = -8;
			} else if (rangeInHexes <= 40) {
				speedALM = -6;
			} else {
				speedALM = -5;
			}

		} else {

			// Checks if individual is moving;
			int action = GameWindow.gameWindow.game.getCurrentAction();
			int trooperMovingInAction = 0;

			int count = 1;
			for (Trooper trooper : targetUnit.individuals) {

				if (count == 1) {
					if (trooper.compareTo(targetTrooper)) {
						trooperMovingInAction = 1;
						break;
					}

				} else if (count == 2) {
					if (trooper.compareTo(targetTrooper)) {
						trooperMovingInAction = 2;
						break;
					}
				} else {
					if (trooper.compareTo(targetTrooper)) {
						trooperMovingInAction = 3;
						break;
					}
					count = 0;
				}

				count++;

			}

			if (action == 1) {
				if (trooperMovingInAction == 1) {
					speedALM = findSpeedALM(rangeInHexes);
				}
			} else if (action == 2) {
				if (trooperMovingInAction == 2) {
					speedALM = findSpeedALM(rangeInHexes);
				}
			} else if (action == 3) {
				if (trooperMovingInAction == 3) {
					speedALM = findSpeedALM(rangeInHexes);
				}
			} else {

				int roll = new Random().nextInt(3) + 1;
				if (trooperMovingInAction == roll) {
					speedALM = findSpeedALM(rangeInHexes);
				}

			}

		}

		return speedALM;

	}

	// Returns single HPI
	public int findSpeedALM(int rangeInHexes) {

		if (rangeInHexes <= 10) {
			return -8;
		} else if (rangeInHexes <= 20) {
			return -6;
		} else {
			return -5;
		}

	}
}
