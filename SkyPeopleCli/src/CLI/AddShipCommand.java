package CLI;

import java.util.ArrayList;
import java.util.Arrays;

import Game.GameMaster;
import Mechanics.Formation;
import Ship.Ship;
import Ship.Ship.ShipType;

public class AddShipCommand implements Command {

	String shipName; 
	String formationName;
	ShipType shipType; 
	int number;
	
	public AddShipCommand(ArrayList<String> parameters) {
		
		if(parameters.size() < 3 || parameters.size() > 5
				) {
			System.out.println("Invalid number of parameters.");
			return;
		}
		
		if(parameters.size() >= 4 
				&& !Command.verifyParameters(parameters, new ArrayList<Integer>(
					    Arrays.asList(3))))  {
			System.out.println("Invalid parameter type.");
			return; 
		}
		
		
		shipType = Ship.getType(parameters.get(1));
		shipName = parameters.get(2);
		
		if(parameters.size() >= 4) 
			number = Integer.parseInt(parameters.get(3));
		else 
			number = -1;
		
		if(parameters.size() == 5) 
			formationName = parameters.get(4);
		else 
			formationName = null;
		
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
	
	
	public void addShip() {
		GameMaster.game.addShip(shipType, shipName);	
	}
	
	public void addShips(int number) {
		for(int i = 0; i < number; i++) {
			GameMaster.game.addShip(shipType, shipName+"_"+(i+1));	
		}
	}
	
	public void addShips(int number, String formationName) {
		
		ArrayList<Ship> ships = new ArrayList<>();
		for(int i = 0; i < number; i++) {
			Ship ship = new Ship(shipType);
			ship.shipName = shipName+"_"+(i+1);
			ships.add(ship);
			GameMaster.game.ships.add(ship);
		}
		
		GameMaster.game.formations.add(new Formation(formationName, ships));
		
	}
	
	@Override
	public void resolve() {
		System.out.println("pass");
		if(number == -1 && formationName == null) {	
			System.out.println("pass1");
			addShip();
		} else if(number != -1 && formationName != null) {
			System.out.println("pass2");
			addShips(number, formationName);
		} else if(number == -1 && formationName != null) {
			System.out.println("pass3");
			addShips(number);
		} 
		
		GameMaster.move();
	}

	@Override
	public CommandType getType() {
		return CommandType.ADD_SHIP;
	}

}
