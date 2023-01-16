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
	
	public Formation(Formation original) {
	    this.formationName = new String(original.formationName);
	    this.ships = new ArrayList<>(original.ships.size());
	    for (Ship ship : original.ships) {
	        this.ships.add(new Ship(ship));
	    }
	}

}
