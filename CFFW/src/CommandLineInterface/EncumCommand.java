package CommandLineInterface;

import java.util.ArrayList;

import CorditeExpansion.CorditeExpansionGame;
import Trooper.Trooper;

public class EncumCommand implements Command {

	boolean auto = false; 
	int value;
	Trooper trooper = CorditeExpansionGame.selectedTrooper;
	
	public EncumCommand(ArrayList<String> params) {
		if(!Command.selectedTrooper()
				|| !Command.checkParameters(params, 2)
				|| (!params.get(1).equals("auto") && !params.get(1).matches("[0-9]+"))) {
			return; 
		}
		
		if(params.get(1).equals("auto")) {
			auto = true; 
		} else {
			value =  Integer.parseInt(params.get(1));
		}
		
		resolve();
	}
	
	@Override
	public void resolve() {
		
		int before = trooper.encumberance;
		
		if(auto) {
			trooper.inventory.setEncumberance();
		} else {
			trooper.encumberance = value; 
		}
		
		trooper.setCombatStats(trooper);
		
		CommandLineInterface.cli.print("Encumberance, before: "+before+", after: "+trooper.encumberance);
	}

	@Override
	public CommandType getType() {
		return CommandType.ENCUM;
	}

}
