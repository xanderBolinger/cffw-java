package CLI;

import java.util.ArrayList;
import java.util.Arrays;

import CLI.Command.CommandType;
import Game.GameMaster;

public class ShowShipCommand implements Command {

	String shipName; 
	
	public ShowShipCommand(ArrayList<String> parameters) {
		
		if(!Command.checkParameters(parameters, 2)) {
			return;
		}
		
		shipName = parameters.get(1);
		
		resolve();
	}
	
	@Override
	public void resolve() {
		System.out.println(GameMaster.game.findShip(shipName).toString());
	}

	@Override
	public CommandType getType() {
		return CommandType.SHOW_SHIP;
	}

}
