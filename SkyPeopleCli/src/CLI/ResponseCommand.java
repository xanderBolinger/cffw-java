package CLI;

import java.util.ArrayList;
import java.util.Arrays;

import CLI.Command.CommandType;
import Game.GameMaster;
import Mechanics.Formation;
import Ship.Ship;
import Ship.Component.ComponentType;
import Ship.Ship.ShipType;

public class ResponseCommand implements Command {

	String shipName;
	String response;
	Ship ship; 
	Formation formation;
	
	public ResponseCommand(ArrayList<String> parameters) {
		
		if(parameters.size() < 3) {
			System.out.println("Invalid Parameter Types");
			return;
		}
		
		shipName = parameters.get(1);
		response = "";
		
		for(int i = 2; i < parameters.size(); i++) {
			response += parameters.get(i) + " ";
		}
		
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
		
		if(formation != null)
			GameMaster.game.currentTurn().currentSegment().currentStep().addResponse(formation, response);
		else
			GameMaster.game.currentTurn().currentSegment().currentStep().addResponse(ship, response);
		
		if(GameMaster.game.currentTurn().currentSegment().currentStep().completed()) {
			if(!GameMaster.game.currentTurn().currentSegment().nextStep()) {
				if(!GameMaster.game.currentTurn().nextSegment()) {
					GameMaster.game.nextRound();
				}
			}
		}
		
		GameMaster.move();
		
	}

	@Override
	public CommandType getType() {
		return CommandType.APPLY_DAMAGE;
	}

}
