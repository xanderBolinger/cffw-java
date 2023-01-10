package CLI;

import java.util.ArrayList;

public interface Command {

	public enum CommandType {
		HELP,EXIT,CUSTOM,BEAM_ATTACK,ADD_SHIP,SHOW_SHIP,GAME,APPLY_DAMAGE
	}

	void resolve();
	CommandType getType();
	
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
