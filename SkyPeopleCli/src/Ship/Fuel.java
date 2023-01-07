package Ship;

import java.util.ArrayList;

public class Fuel {

	public int rows;
	public int spaces;
	public int fusion;
	public int spentFuel;
	
	public Fuel(int rows, int spaces, int fusion) {
		this.rows = rows; 
		this.spaces = spaces; 
		this.fusion = fusion;
		spentFuel = 0;
	}
	
	public void destroyFuel() {
		int lostSpent = 0; 
		int columns = spaces / rows;
		
		spentFuel -= lostSpent;
		spaces -= rows;
	}
	
}
