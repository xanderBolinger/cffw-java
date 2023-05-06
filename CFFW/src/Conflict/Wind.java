package Conflict;

import java.io.Serializable;

import UtilityClasses.DiceRoller;

public class Wind implements Serializable {

	private int speedMph;
	public WindDirection windDirection;

	public enum WindDirection {
		A, B, C, D, E, F
	}

	public static WindDirection getOppositeDirection(WindDirection dir) {

		switch (dir) {

		case A:
			return WindDirection.D;
		case B:
			return WindDirection.E;
		case C:
			return WindDirection.F;
		case D:
			return WindDirection.A;
		case E:
			return WindDirection.B;
		case F:
			return WindDirection.C;
		default:
			return null;

		}

	}

	public int getWindSpeedHpp() {
		return speedMph / 2;
	}

	public int getWindSpeedMph() {
		return speedMph;
	}

	public Wind() {

		setStats();

	}

	public void setStats() {
		// 60% 0-16mph
		// 95% 17-39
		// 5% 40-60mph
		setSpeed();

		setDirection();

	}
	
	public void setSpeed() {
		int windRoll = DiceRoller.randInt(1, 100);

		if (windRoll <= 60) {
			speedMph = DiceRoller.randInt(0, 16);
		} else if (windRoll <= 95) {
			speedMph = DiceRoller.randInt(17, 39);
		} else {
			speedMph = DiceRoller.randInt(40, 60);
		}
	}
	
	public void setDirection() {
		windDirection = WindDirection.values()[DiceRoller.randInt(0, WindDirection.values().length - 1)];
	}
	
	public void advanceTime() {
		
		int windRoll = DiceRoller.randInt(1, 100);
		if(windRoll == 1)
			setSpeed();
		windRoll = DiceRoller.randInt(1, 100);
		
		if(windRoll <= 3) {
			setDirection();
		}
	}

	@Override
	public String toString() {
		return "Wind: " + windDirection +", "+speedMph+" MPH"; 
	}
	
}
