package Mechanics;

import java.util.ArrayList;

import Ship.Ship;

public class Formation {

	public String formationName;
	public ArrayList<Ship> ships; 
	
	public Formation(String formationName, ArrayList<Ship> ships) {
		this.formationName = formationName;
		this.ships = ships;
	}
	
}
