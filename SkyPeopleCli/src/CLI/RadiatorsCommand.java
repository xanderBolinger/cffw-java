package CLI;

import java.util.ArrayList;
import java.util.Arrays;

import CLI.Command.CommandType;
import Game.GameMaster;
import Mechanics.Formation;
import Ship.Ship;

public class RadiatorsCommand implements Command {

	String shipName;
	String response;
	Ship ship; 
	Formation formation;
	
	public RadiatorsCommand(ArrayList<String> parameters) {
		
		if(!Command.checkParameters(parameters, 2)) {
			System.out.println("Invalid Parameter Types");
			return;
		}
		
		shipName = parameters.get(1);
		ship = GameMaster.game.findShip(shipName);
		formation = GameMaster.game.findFormation(shipName);
		
		if(ship == null && formation == null) {
			System.out.println("Ship/Formation Not Found");
			return; 
		} 
		
		resolve();
	}
	
	@Override
	public void resolve() {
		
		if(formation != null) {
			for(Ship ship : formation.ships) {
				ship.extendedRadiators = !ship.extendedRadiators;
			}
		}
		else {
			ship.extendedRadiators = !ship.extendedRadiators;
		}
		
		GameMaster.move();
		
	}

	@Override
	public CommandType getType() {
		return CommandType.APPLY_DAMAGE;
	}

}
