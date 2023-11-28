package test;

import UtilityClasses.DiceRoller;

import java.awt.geom.Point2D;

import CorditeExpansion.*;
public class TestMain {

	public static void main(String[] args) {
		testDistanceCalc();
	}
	
	private static void testDistanceCalc() {
		int count = 1000;
		var cord = new Cord(DiceRoller.d10(), DiceRoller.d10());
		
		
		System.out.println("Method 1: ");
		int dist = 0;
		for(int i = 0; i < count; i++) {
			var x = DiceRoller.d10();
			var y = DiceRoller.d10();
			var tDist = Point2D.distance(x, y, cord.xCord, cord.yCord);
			dist += tDist;
			//System.out.println("Dist: "+tDist);
		}
		System.out.println("Avg Dist: "+dist/count);
		
		System.out.println("Method 2: ");
		dist = 0;
		for(int i = 0; i < count; i++) {
			var tDist = DiceRoller.d10();
			//System.out.println("Dist: "+tDist);
			dist += tDist;
		}
		System.out.println("Avg Dist: "+dist/count);
		
		System.out.println("Method 3: ");
		dist = 0;
		for(int i = 0; i < count; i++) {
			cord = new Cord(DiceRoller.d10(), DiceRoller.d10());
			var x = DiceRoller.d10();
			var y = DiceRoller.d10();
			var tDist = Point2D.distance(x, y, cord.xCord, cord.yCord);
			dist += tDist;
			//System.out.println("Dist: "+tDist);
		}
		System.out.println("Avg Dist: "+dist/count);
		
		
	}
	
}
