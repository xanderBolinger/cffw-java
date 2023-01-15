package CLI;

import java.util.ArrayList;
import Game.GameMaster;
import Ship.Ship;
import Ship.Ship.ShipType;

public class AddShipCommand implements Command {

	String shipName; 
	ShipType shipType; 
	
	public AddShipCommand(ArrayList<String> parameters) {
		
		if(!Command.checkParameters(parameters, 3)) {
			System.out.println("Invalid number of parameters.");
			return;
		}
		
		shipType = Ship.getType(parameters.get(1));
		shipName = parameters.get(2);
		
		if(shipType == null) {
			System.out.println("Ship Type Not Found");
			return;
		}
		
		for(Ship ship : GameMaster.game.ships) {
			if(ship.shipName.equals(shipName)) {
				System.out.println("Ship name already taken");
				return;
			}
		}
				
		resolve();
	}
	
	@Override
	public void resolve() {
		GameMaster.game.addShip(shipType, shipName);
		GameMaster.move();
	}

	@Override
	public CommandType getType() {
		return CommandType.ADD_SHIP;
	}

}
