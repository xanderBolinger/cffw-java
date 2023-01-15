package Mechanics;

import Ship.Ship;

public class HeatIndex {

	
	public static double heatIndex(Ship ship) {
		
		return (ship.getHeat() - ship.heat)  * -1;
				
		
	}

}
