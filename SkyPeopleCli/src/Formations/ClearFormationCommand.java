package Formations;

import java.util.ArrayList;

import CLI.Command;
import CLI.Command.CommandType;
import Game.GameMaster;
import Mechanics.Formation;
import Ship.Ship;
import Ship.Ship.ShipType;

public class ClearFormationCommand implements Command {

	String formationName; 
	
	public ClearFormationCommand(ArrayList<String> parameters) {
		
		if(!Command.checkParameters(parameters, 2)) {
			System.out.println("Invalid number of parameters.");
			return;
		}
		
		formationName = parameters.get(1);
		
		for(Formation formation : GameMaster.game.formations) {
			if(formation.formationName.equals(formationName)) {
				resolve();
				return;
			}
		}
		
		System.out.println("Formation Name Not Found.");
		
	}
	
	@Override
	public void resolve() {
		Formation formation = null; 
		
		for(Formation form : GameMaster.game.formations) {
			if(form.formationName.equals(formationName)) {
				formation = form;
				break;
			}
 		}
		
		GameMaster.game.formations.remove(formation);
		
		GameMaster.move();
	}

	@Override
	public CommandType getType() {
		return CommandType.ADD_SHIP;
	}

}
