package CommandLineInterface;

import java.util.ArrayList;
import java.util.Arrays;

import CorditeExpansion.CorditeExpansionGame;
import CorditeExpansionDamage.Damage;
import Trooper.Trooper;

public class DistanceCommand implements Command {

	int multiplier; 
	
	public DistanceCommand(ArrayList<String> params) {
		if(!Command.checkParameters(params, 2)
				|| !Command.verifyParameters(params, new ArrayList<Integer>(
					    Arrays.asList(1)))) {
			return;
		}
		
		multiplier = Integer.parseInt(params.get(1));
		
		resolve();
	}
	
	@Override
	public void resolve() {
		
		CorditeExpansionGame.distanceMultiplier = multiplier;
			
		
	}

	@Override
	public CommandType getType() {
		return CommandType.DISTANCE;
	}
	
}
