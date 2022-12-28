package CommandLineInterface;

import java.util.ArrayList;

import CorditeExpansion.CorditeExpansionGame;
import Items.Weapons;

public class WepCommand implements Command {

	String wepName;
	
	public WepCommand(ArrayList<String> params) {
		if(!Command.selectedTrooper()
				|| !Command.checkParameters(params, 2)) {
			return;
		}
		
		wepName = params.get(1).replaceAll("_", " ");
		
		resolve();
	}
	
	@Override
	public void resolve() {
		CorditeExpansionGame.selectedTrooper.wep = wepName; 
		CorditeExpansionGame.selectedTrooper.ceStatBlock.rangedStatBlock.weapon = new Weapons().findWeapon(wepName);
		
		if(!CorditeExpansionGame.selectedTrooper.ceStatBlock.rangedStatBlock.weapon.name.equals(wepName))
			CommandLineInterface.cli.print("Weapon not found - setting to DC15A");
	}

	@Override
	public CommandType getType() {
		return CommandType.WEP;
	}

}
