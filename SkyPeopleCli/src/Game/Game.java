package Game;

import java.util.ArrayList;

import Ship.Ship;
import Ship.Ship.ShipType;

public class Game {
	public ArrayList<Ship> ships;
	public int round;
	
	
	public Game() {
		round = 1;
		ships = new ArrayList<>();
		//System.out.println("Add Game");
	}
	
	public Game(int round, ArrayList<Ship> oldShips) {
		this.round = round;
		ships = new ArrayList<>();
		
		for(Ship oldShip : oldShips) {
			ships.add(new Ship(oldShip));
		}
	}
	
	public void nextRound() {
		
		for(Ship ship : ships) {
			ship.generationStep();
		}
		
		round++;
		GameMaster.move();
	}
	
	public void addShip(ShipType shipType, String shipName) {
		Ship ship = new Ship(shipType);
		ship.shipName = shipName;
		ships.add(ship);
	}
	
	public Ship findShip(String shipName) {
		for(Ship ship : ships) {
			if(ship.shipName.equals(shipName)) 
				return ship;
		}
		
		return null; 
	}
	
	@Override
	public String toString() {
		String rslts = "Round: "+round+"\n";
		
		for(Ship ship : ships) {
			rslts += ship.shipName +": "+ship.description.model+"\n";
		} 
		
		return rslts; 
	}
	
	
}
