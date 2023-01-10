package CLI;

import java.util.ArrayList;
import java.util.Arrays;

import CLI.Command.CommandType;
import Game.GameMaster;
import Mechanics.DamageAllocation;
import Mechanics.DamageAllocation.HitSide;
import Ship.Component.ComponentType;
import Ship.Ship;

public class ShieldRechargeCommand implements Command {

	String shipName;
	int rechargeRate;
	Ship ship; 
	
	public ShieldRechargeCommand(ArrayList<String> parameters) {
		
		if(!Command.checkParameters(parameters, 3) ||
				!Command.verifyParameters(parameters, new ArrayList<Integer>(
					    Arrays.asList(2)))) {
			System.out.println("Invalid Parameter Types");
			return;
		}
		
		shipName = parameters.get(1);
		rechargeRate = Integer.parseInt(parameters.get(2));
		
		ship = GameMaster.game.findShip(shipName);
		
		if(ship == null) {
			System.out.println("Ship Not Found");
			return; 
		} else if(rechargeRate > ship.getNumberComponent(ComponentType.REACTORS)) {
			System.out.println("Recharge rate out of index.");
			return;
		}
		
		
		resolve();
	}
	
	@Override
	public void resolve() {
		
		ship.shieldGenerationValue = rechargeRate;
		
		GameMaster.move();
		
	}

	@Override
	public CommandType getType() {
		return CommandType.APPLY_DAMAGE;
	}

}
