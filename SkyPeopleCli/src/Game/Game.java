package Game;

import java.util.ArrayList;

import Mechanics.Formation;
import Ship.Ship;
import Ship.Ship.ShipType;

public class Game {
	public ArrayList<Ship> ships;
	public ArrayList<Ship> destroyed;
	public ArrayList<Formation> formations;
	public int round;
	public int turn;
	
	public Game() {
		round = 1;
		turn = 1;
		ships = new ArrayList<>();
		formations = new ArrayList<>();
		destroyed = new ArrayList<>();
		//System.out.println("Add Game");
	}
	
	public Game(int round, ArrayList<Ship> oldShips) {
		this.round = round;
		ships = new ArrayList<>();
		formations = new ArrayList<>();
		destroyed = new ArrayList<>();
		for(Ship oldShip : oldShips) {
			ships.add(new Ship(oldShip));
		}
	}
	
	public void nextRound() {
		
		for(Ship ship : ships) {
			ship.generationStep();
		}
		
		if(round % 8 == 0)
			turn++;
		
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
	
	public Formation findFormation(String formationName) {
		
		for(Formation formation : formations) {
			if(formationName.equals(formation.formationName)) {
				return formation;
			}
		}
		
		return null;
	} 
	
	@Override
	public String toString() {
		
		String roundNumber = "";
		
		if(round < 9) {
			roundNumber = Integer.toString(round);
		} else {
			int temp = round; 
			while(temp >= 9) {
				
				temp -=8;
			}
			roundNumber = Integer.toString(temp);
		}
		
		String rslts = "Turn: "+ turn
				+ ", Sequence: " + roundNumber
				+"\n";
		
		for(Ship ship : ships) {
			rslts += ship.shipName +": "+ship.description.model+"\n";
		} 
		
		rslts +="Formations: \n";
		
		for(Formation formation : formations) {
			rslts += formation.formationName;
			rslts += "\n---\n";
			for(Ship ship : formation.ships) {
				rslts += ship.shipName +": "+ship.description.model+"\n";
			}
			rslts += "---\n";
		}
		
		rslts +="Destroyed: \n";
		
		for(Ship ship : destroyed) {
			rslts += ship.shipName +": "+ship.description.model+"\n";
		}
		
		
		return rslts; 
	}
	
	
}
