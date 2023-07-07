package CommandLineInterface;

import java.util.ArrayList;
import java.util.Arrays;

import CorditeExpansion.CorditeExpansionGame;
import Items.Weapons;

public class WepsCommand implements Command {

	
	public WepsCommand(ArrayList<String> params) {
		if(!Command.selectedTrooper()) {
			return;
		}
		
		resolve();
	}
	
	@Override
	public void resolve() {
		
		int i = 0;
		for(var wep : CorditeExpansionGame.selectedTrooper.ceStatBlock.rangedStatBlock.weapons) {
			System.out.println(i+": "+wep.name);
			i++;
		}
	
	}

	@Override
	public CommandType getType() {
		return CommandType.WEP;
	}

}
