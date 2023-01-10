package CLI;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomCommand implements Command {

	String param1; 
	int param2; 
	int param3; 
	
	public CustomCommand(ArrayList<String> parameters) {
		
		if(!Command.checkParameters(parameters, 4) ||
				!Command.verifyParameters(parameters, new ArrayList<Integer>(
					    Arrays.asList(2, 3)))) {
			return;
		}
		
		param1 = parameters.get(1);
		param2 = Integer.parseInt(parameters.get(2));
		param3 = Integer.parseInt(parameters.get(3));
		
		resolve();
	}
	
	@Override
	public void resolve() {
		
	}

	@Override
	public CommandType getType() {
		return CommandType.CUSTOM;
	}

}
