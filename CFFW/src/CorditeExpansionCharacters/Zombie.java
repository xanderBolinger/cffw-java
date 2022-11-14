package CorditeExpansionCharacters;

import Trooper.Trooper;

public class Zombie extends Trooper {

	public boolean headShotsOnly = true; 
	
	public Zombie() {
		zombie = true;
	}
	
	public void hitLocationCheck(String hitLocation, int pen) {
		
		if((hitLocation.equals("Forehead") || hitLocation.equals("Eye - Nose") ||
				hitLocation.equals("Mouth")) && pen > 0) {
			alive = false; 
			conscious = false;
		}
		
	}
	
}
