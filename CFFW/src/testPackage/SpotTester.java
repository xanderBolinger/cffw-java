package testPackage;

import java.util.ArrayList;

import Actions.Spot;
import Trooper.Trooper;
import Unit.Unit;

public class SpotTester {

	
	public static void main(String args[]) {
		
		
		ArrayList<Trooper> troopers = new ArrayList<Trooper>();
		Trooper trooper1 = new Trooper("Rifleman", "UNSC");
		Trooper trooper2 = new Trooper("Rifleman", "UNSC");
		troopers.add(trooper1);
		troopers.add(trooper2);
		
		
		Unit spotterUnit = new Unit("spotterUnit", 1, 0, troopers, 0, 0, 0, 0, 0, 0, 0, "No Contact");
		Unit targetUnit = new Unit("spotterUnit", 0, 0, troopers, 0, 0, 0, 0, 0, 0, 0, "No Contact");
		
		targetUnit.concealment = "Level 2";
		trooper2.firedTracers = true; 
		trooper1.firedTracers = true; 
		trooper1.spottingDifficulty = 100;
		trooper2.unspottable = true; 
		
		//Spot spotAction = new Spot(spotterUnit, targetUnit, trooper1, "60 Degrees", "Good Visibility");
		 
		
		
		 
	}
	
}
