package CommandLineInterface;

import java.util.ArrayList;

import CorditeExpansion.CorditeExpansionGame;

public interface Command {

	public enum CommandType {
		HELP,EXIT,CUSTOM,MEDICAL,INJURY,WEP,ENCUM,STUN,DISTANCE
	}

	void resolve();
	CommandType getType();
	
	public static boolean selectedTrooper() {
		if(CorditeExpansionGame.selectedTrooper == null) {
			CommandLineInterface.cli.print("Select a trooper");
			return false; 
		} else {
			return true; 
		}
	}
	
	public static boolean checkParameters(ArrayList<String> params, int num) {
		if(num != params.size()) {
			CommandLineInterface.cli.print("Invalid number of parameters, type 'help' for usage");
			return false; 
		} else {
			return true; 
		}
	}
	
	public static boolean verifyParameters(ArrayList<String> params, ArrayList<Integer> numberIndexes) {
		
		for(int i = 0; i < params.size(); i++) {
			
			if(numberIndexes.contains(i)
					&& !params.get(i).matches("[0-9]+")) {
				return false; 
			}
			
		}
		
		return true; 
	}
	
	
}
