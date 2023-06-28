package Trooper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import UtilityClasses.DiceRoller;
public class TLHStats implements Serializable {
   // Stats 
   public int str;
   public int wit; 
   public int soc;
   public int wil;
   public int per;
   public int hlt;
   public int agi;

   public TLHStats(int bonusStr, int bonusInt, int bonusSoc, int bonusWill, int bonusPer, int bonusHtl, int bonusAgi) {
      this.str = roll3D4(bonusStr);
      this.wit = roll3D4(bonusInt);
      this.soc = roll3D4(bonusSoc);
      this.wil = roll3D4(bonusWill);
      this.per = roll3D4(bonusPer);
      this.hlt = roll3D4(bonusHtl);
      this.agi = roll3D4(bonusAgi);
   }
   
   // Method rolls three d4, drop one adds prascribed bonus 
   // Gets the sum and adds the bonus 
   // Returns the attribute
   public static int roll3D4(int bonus){
      // Rolls dice three times and comes up with the attribute sum. Includes the attr bonus. 
		int sum = 0;
	
		// Rolls 4 dice
		ArrayList<Integer> rolls = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			int roll = DiceRoller.roll(1, 4);
			rolls.add(roll);
		}
	
		// Finds the lowest value
		/*
		 * int lowest = rolls.get(0); int lowestIndex = 0; for(int i = 0; i <
		 * rolls.size(); i++) { if(rolls.get(i) < lowest) { lowest = rolls.get(i);
		 * lowestIndex = i; } }
		 * 
		 * // Removes lowest value rolls.remove(lowestIndex);
		 */
	
		// Creates sum out of the remaining three
		for (int i = 0; i < rolls.size(); i++) {
			sum += rolls.get(i);
		}
	
		return sum + 4;
   }

}