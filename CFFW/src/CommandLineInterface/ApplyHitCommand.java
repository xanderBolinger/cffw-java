package CommandLineInterface;

import java.util.ArrayList;
import java.util.Arrays;

import CorditeExpansion.CorditeExpansionGame;
import CorditeExpansionDamage.Damage;

public class ApplyHitCommand implements Command{

	
	int pen; 
	int dc; 
	String weaponName;
	
	public ApplyHitCommand(ArrayList<String> parameters) {
		
		if(!Command.selectedTrooper() || 
				!Command.checkParameters(parameters, 4) ||
				!Command.verifyParameters(parameters, new ArrayList<Integer>(
					    Arrays.asList(2, 3)))) {
			return;
		}
		
		weaponName = parameters.get(1);
		pen = Integer.parseInt(parameters.get(2));
		dc = Integer.parseInt(parameters.get(3));
		
		resolve();
	}
	
	@Override
	public void resolve() {
		try {
			Damage.applyHit(weaponName, pen, dc, 
					!CorditeExpansionGame.selectedTrooper.ceStatBlock.inCover, 
					CorditeExpansionGame.selectedTrooper);
			CommandLineInterface.cli.print("Applied hit");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public CommandType getType() {
		return null;
	}

}
