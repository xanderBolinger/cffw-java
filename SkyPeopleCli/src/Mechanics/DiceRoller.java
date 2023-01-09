package Mechanics;

import java.util.Random;

public class DiceRoller {
		static Random rand = new Random();
		
		public static int randum_number(int min, int max) {
			return rand.nextInt(min, max) + 1;
		}
	
		public static int d10() {
			return randum_number(1, 10);
		}
		
		public static int twoD10Minus() {
			int roll1 = d10();
			int roll2 = d10();
			
			return roll1 > roll2 ? roll1 - roll2 : roll2 - roll1;
		}

		public static int twoD10() {
			return d10() + d10();
		}
		
}
