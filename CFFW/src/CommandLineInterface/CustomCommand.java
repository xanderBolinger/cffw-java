package CommandLineInterface;

import java.util.ArrayList;
import java.util.Arrays;

import CorditeExpansion.CorditeExpansionGame;
import CorditeExpansionActions.CeAction;
import CorditeExpansionActions.CeAction.ActionType;
import CorditeExpansionActions.CustomAction;

public class CustomCommand implements Command {

	String actionName; 
	int actionCost; 
	int coacCost; 
	
	public CustomCommand(ArrayList<String> parameters) {
		
		if(!Command.selectedTrooper() || 
				!Command.checkParameters(parameters, 4) ||
				!Command.verifyParameters(parameters, new ArrayList<Integer>(
					    Arrays.asList(2, 3)))) {
			return;
		}
		
		actionName = parameters.get(1);
		actionCost = Integer.parseInt(parameters.get(2));
		coacCost = Integer.parseInt(parameters.get(3));
		
		resolve();
	}
	
	@Override
	public void resolve() {
		CeAction.addCustomAction(CorditeExpansionGame.selectedTrooper, 
				new CustomAction(CorditeExpansionGame.selectedTrooper.ceStatBlock, 
						actionName, actionCost, coacCost));
	}

	@Override
	public CommandType getType() {
		return CommandType.CUSTOM;
	}

}
