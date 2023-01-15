package CLI;

import java.util.ArrayList;
import java.util.Arrays;

import Game.GameMaster;
import Ship.Ship;
import Ship.Component.ComponentType;

public class SetRegenerationCommand implements Command {

	String shipName; 
	Ship ship;
	int regeneration;
	public SetRegenerationCommand(ArrayList<String> parameters) {
		
		if(!Command.checkParameters(parameters, 3) 
				|| !Command.verifyParameters(parameters, new ArrayList<Integer>(
					    Arrays.asList(3)))) {
			System.out.println("Invalid parameters.");
			return;
		}
		
		shipName = parameters.get(1);
		regeneration = Integer.parseInt(parameters.get(2));
				
		
		for(Ship ship : GameMaster.game.ships) {
			if(ship.shipName.equals(shipName)) {
				this.ship = ship;
				break;
			}
		}
				
		if(ship == null) {
			System.out.println("Ship not found.");
			return;
		}
		
		if(regeneration < 0 || regeneration > ship.getNumberComponent(ComponentType.REACTORS)) {
			System.out.println("Invalid regeneration value.");
			return;
		}
		
		resolve();
	}
	
	@Override
	public void resolve() {
		ship.reactorSet = regeneration;
	}

	@Override
	public CommandType getType() {
		return CommandType.ADD_SHIP;
	}

}
