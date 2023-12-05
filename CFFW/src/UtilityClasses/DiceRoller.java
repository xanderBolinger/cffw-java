package UtilityClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import JUnitTests.JUnitTestUtil;

public class DiceRoller {

	public static Random rand = new Random();
	
	private static List<Integer> testValues = new ArrayList<Integer>();
	
	public DiceRoller() {
		
	}
	
	public static void addTestValue(int val) {
		testValues.add(val);
	}
	
	public static void addTestValues(int[] vals) {
		for(var i : vals)
			testValues.add(i);
	}
	
	public static void clearTestValues() {
		testValues.clear();
	}
	
	public static int d10() {
		return roll(1,10);
	}
	
	public static int d6_exploding() {
		
		int value = roll(1,6);
		
		if(value == 6)
			return d6_exploding(value);
		
		return value;
		
	}
	
	private static int d6_exploding(int value) {
		
		value += roll(1,6);
		
		if(value == 6)
			return d6_exploding(value);
		
		return value;
		
	}
	
	public static int roll(int min, int max) {	    
		if(min == 0 && max == 0 || (max < min)) {
			System.err.println("Min and max out of bounds: "+min+", "+max);
			return 0;
		}

		try {
			if(!JUnitTestUtil.isJUnitTest() && testValues.size() > 0)
				throw new Exception("Test values has content and you are outside of a j unit test");
		} catch(Exception e) {
			e.printStackTrace();
		}

		if(testValues.size() > 0)
			return testValues.remove(0);
		
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static boolean plusMinus() {
		return DiceRoller.roll(1, 2) == 1 ? true : false;
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
	82
	50
	76
	89
	95
	57
	34
	37
	85
	53
	82
	50
	
	*/
	public static void initTesting() {
		
		rand = new Random(123);
		
		/*for(int i = 0; i < 10; i++) {
			System.out.println(DiceRoller.randInt(0, 9));
		}*/
		
		/*for(int i = 0; i < 10; i++) {
			System.out.println(DiceRoller.randInt(0, 99));
		}*/
		
		/*for(int i = 0; i < 10; i++) {
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
