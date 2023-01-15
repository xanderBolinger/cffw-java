package Formations;

import java.util.ArrayList;

import CLI.Command;
import CLI.Command.CommandType;
import Game.GameMaster;
import Mechanics.Formation;
import Ship.Ship;
import Ship.Ship.ShipType;

public class CreateFormationCommand implements Command {

	String formationName; 
	ArrayList<Ship> ships;
	
	public CreateFormationCommand(ArrayList<String> parameters) {
		
		if(parameters.size() < 2) {
			System.out.println("Invalid number of parameters.");
			return;
		}
		
		formationName = parameters.get(1);
		
		for(Formation formation : GameMaster.game.formations) {
			if(formation.formationName.equals(formationName)) {
				System.out.println("Formation Name Already Taken.");
				return;
			}
		}
		
		if(parameters.size() == 2) {
			resolve();
			return;
		}
		
		ships = new ArrayList<Ship>();
		
		outerloop:
		for(int i = 2; i < parameters.size(); i++) {
			
			String shipName = parameters.get(i);
			for(Ship ship : GameMaster.game.ships) {
				if(ship.shipName.equals(shipName)) {
					if(ships.contains(ship)) {
						System.out.println("Repeated ship names.");
						return;
					}
					
					ships.add(ship);
					continue outerloop;
				} 
			}
			
			System.out.println("Invalid Ship Name");
			return;
			
		}
		
		
				
		resolve();
	}
	
	@Override
	public void resolve() {
		GameMaster.game.formations.add(new Formation(formationName, ships));
		GameMaster.move();
	}

	@Override
	public CommandType getType() {
		return CommandType.ADD_SHIP;
	}

}
