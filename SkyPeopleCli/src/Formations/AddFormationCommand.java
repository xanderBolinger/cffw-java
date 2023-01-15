package Formations;

import java.util.ArrayList;

import CLI.Command;
import CLI.Command.CommandType;
import Game.GameMaster;
import Mechanics.Formation;
import Ship.Ship;
import Ship.Ship.ShipType;

public class AddFormationCommand implements Command {

	String formationName; 
	Ship ship;
	
	public AddFormationCommand(ArrayList<String> parameters) {
		
		if(!Command.checkParameters(parameters, 3)) {
			System.out.println("Invalid number of parameters.");
			return;
		}
		
		for(Ship ship : GameMaster.game.ships) {
			if(ship.shipName.equals(parameters.get(2))) {
				this.ship = ship;
				break;
			}
		}
		
		if(ship == null) {
			System.out.println("Ship not found");
			return;
		}
		
		
		formationName = parameters.get(1);
		
		for(Formation formation : GameMaster.game.formations) {
			if(formation.formationName.equals(formationName)) {
				if(formation.ships.contains(ship)) {
					System.out.println("Ship already in formation.");
					return;
				}
				resolve();
				return;
			}
		}
		
		System.out.println("Formation Name Not Found.");
		
	}
	
	@Override
	public void resolve() {
		
		for(Formation form : GameMaster.game.formations) {
			if(form.formationName.equals(formationName)) {
				form.ships.add(ship);
				break;
			}
 		}
		
		GameMaster.move();
	}

	@Override
	public CommandType getType() {
		return CommandType.ADD_SHIP;
	}

}
