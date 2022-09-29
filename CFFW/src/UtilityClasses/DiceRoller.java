package UtilityClasses;

import java.util.Random;

public class DiceRoller {

	public static Random rand = new Random();
	
	public DiceRoller() {
		
	}
	
	public static int d6_exploding() {
		
		int value = randInt(1,6);
		
		if(value == 6)
			return d6_exploding(value);
		
		return value;
		
	}
	
	public static int d6_exploding(int value) {
		
		value += randInt(1,6);
		
		if(value == 6)
			return d6_exploding(value);
		
		return value;
		
	}
	
	public static int randInt(int min, int max) {	    

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
		
}
