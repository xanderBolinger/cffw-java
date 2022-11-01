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
	
	
	/*
	d10, 0,9
	2
	0
	6
	9
	5
	7
	4
	7
	5
	3
	
	d99, 0,99
	39
	26
	72
	65
	37
	49
	20
	85
	54
	16
	
	d100, 1,100
	23
	38
	37
	51
	79
	54
	9
	93
	22
	45
	*/
	
	public static void initTesting() {
		
		rand = new Random(123);
		
		/*for(int i = 0; i < 10; i++) {
			System.out.println(DiceRoller.randInt(0, 9));
		}
		
		for(int i = 0; i < 10; i++) {
			System.out.println(DiceRoller.randInt(0, 99));
		}
		
		for(int i = 0; i < 10; i++) {
			System.out.println(DiceRoller.randInt(1, 100));
		}*/
		
	}
	
	public static void finishTesting() {
		rand = new Random();
		
		/*System.out.println("\n\n\n");
		for(int i = 0; i < 10; i++) {
			System.out.println(DiceRoller.randInt(0, 9));
		}
		
		for(int i = 0; i < 10; i++) {
			System.out.println(DiceRoller.randInt(0, 99));
		}
		
		for(int i = 0; i < 10; i++) {
			System.out.println(DiceRoller.randInt(1, 100));
		}*/
	}
	
		
}
