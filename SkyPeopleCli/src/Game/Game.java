package Game;

import java.util.ArrayList;

import Mechanics.Formation;
import Ship.Ship;
import Ship.Ship.ShipType;
import Turns.Turn;

public class Game {
	public ArrayList<Ship> ships;
	public ArrayList<Ship> destroyed;
	public ArrayList<Formation> formations;

	public ArrayList<Turn> turns;
	
	public Game() {
		ships = new ArrayList<>();
		formations = new ArrayList<>();
		destroyed = new ArrayList<>();
		turns = new ArrayList<>();
		turns.add(new Turn());
	}
	
	public Game(Game original) {
	    this.ships = new ArrayList<>(original.ships.size());
	    for (Ship ship : original.ships) {
	        this.ships.add(new Ship(ship));
	    }
	    this.destroyed = new ArrayList<>(original.destroyed.size());
	    for (Ship ship : original.destroyed) {
	        this.destroyed.add(new Ship(ship));
	    }
	    this.formations = new ArrayList<>(original.formations.size());
	    for (Formation formation : original.formations) {
	        this.formations.add(new Formation(formation));
	    }
	    this.turns = new ArrayList<>(original.turns.size());
	    for (Turn turn : original.turns) {
	        this.turns.add(new Turn(turn));
	    }
	}
	
	
	public void nextRound() {
		
		for(Ship ship : ships) {
			ship.generationStep();
		}
		
		turns.add(new Turn());
		
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
	
	public Turn currentTurn() {
		return turns.get(turns.size() - 1);
	}
	
	public String turns() {
		return "";
	}
	
	@Override
	public String toString() {
		
		String rslts = "Turn: "+ (turns.size() + 1)
				+ ", Segment: " + turns.get(turns.size() - 1).currentSegmentNumber()
				+"\n";
		
		rslts += "Step: " + currentTurn().currentSegment().currentStep().toString() + "\n";
		
		rslts += "\nShips:\n";
		
		for(Ship ship : ships) {
			rslts += ship.shipName +": "+ship.description.model+"\n";
		} 
		
		rslts +="\nFormations: \n";
		
		for(Formation formation : formations) {
			rslts += formation.formationName;
			rslts += "\n---\n";
			for(Ship ship : formation.ships) {
				rslts += ship.shipName +": "+ship.description.model+"\n";
			}
			rslts += "---\n";
		}
		
		rslts +="\nDestroyed: \n";
		
		for(Ship ship : destroyed) {
			rslts += ship.shipName +": "+ship.description.model+"\n";
		}
		
		
		return rslts; 
	}
	
	
}
