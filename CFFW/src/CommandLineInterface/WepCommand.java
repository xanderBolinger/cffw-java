package CommandLineInterface;

import java.util.ArrayList;
import java.util.Arrays;

import CorditeExpansion.CorditeExpansionGame;
import Items.Weapons;

public class WepCommand implements Command {

	String wepName;
	String newOld;
	int instance;
	
	public WepCommand(ArrayList<String> params) {
		if(!Command.selectedTrooper()
				|| !Command.checkParameters(params, 4)
				|| !Command.verifyParameters(params, new ArrayList<Integer>(
					    Arrays.asList(3)))) {
			return;
		}
		
		wepName = params.get(1).replaceAll("_", " ");
		String newOld = params.get(2).toLowerCase();
		
		if(!newOld.equals("new") && !newOld.equals("old")) {
			System.out.println("Missing second param, new / old.");
			return;
		}
		
		this.newOld = newOld;
		instance = Integer.parseInt(params.get(3));
		
		resolve();
	}
	
	@Override
	public void resolve() {
		CorditeExpansionGame.selectedTrooper.wep = wepName; 
		var stat = CorditeExpansionGame.selectedTrooper.ceStatBlock.rangedStatBlock;
		
		stat.weapon = null;
		
		int i = 0;
		for(var wep : stat.weapons) {
			if(wep.name.equals(wepName) && i == instance) {
				stat.weapon = wep;
				return;
			}
			i++;
		}
		
		if(newOld.equals("old")) {
			System.out.println("Weapon with name: "+wepName+", and index: "+instance+" not found.");
			return;
		}
			
		stat.weapon = new Weapons().findWeapon(wepName);
		
		if(newOld.equals("new")) {
			stat.weapons.add(stat.weapon);
			stat.reload(CorditeExpansionGame.selectedTrooper);
		}
		
		if(!CorditeExpansionGame.selectedTrooper.ceStatBlock.rangedStatBlock.weapon.name.equals(wepName))
			CommandLineInterface.cli.print("Weapon not found - setting to DC15A");
	}

	@Override
	public CommandType getType() {
		return CommandType.WEP;
	}

}
