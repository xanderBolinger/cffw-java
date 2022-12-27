package CommandLineInterface;

import java.util.ArrayList;
import java.util.Arrays;

import CorditeExpansion.CorditeExpansionGame;
import CorditeExpansionDamage.Damage;
import CorditeExpansionDamage.Injury;
import CorditeExpansionStatBlock.MedicalStatBlock;
import Trooper.Trooper;

public class InjuryCommand implements Command {

	Trooper trooper = CorditeExpansionGame.selectedTrooper;
	MedicalStatBlock stat = trooper.ceStatBlock.medicalStatBlock;
	String injuryName; 
	String locationName; 
	int pd; 
	String disabled;
	
	public InjuryCommand(ArrayList<String> params) {
		if(!Command.selectedTrooper() 
				|| !Command.checkParameters(params, 5) 
				|| !Command.verifyParameters(params, new ArrayList<Integer>(
					    Arrays.asList(3)))) {
			return; 
		}
		
		injuryName = params.get(1);
		locationName = params.get(2).replaceAll("_", " ");
		pd = Integer.parseInt(params.get(3));
		disabled = params.get(4);
		
		resolve();
		
	}
	
	@Override
	public void resolve() {
		
		// Apply injury 
		stat.addInjury(new Injury(injuryName, locationName, pd,
				disabled.equals("yes")));
		
		
		System.out.println("Hit PD: "+pd);
		
		// Status check 
		if(pd > 0) {
			stat.increasePd(pd);
			Damage.statusCheck(stat.getPdTotal(), trooper);
		}
		
		// Disabled 
		if(disabled.equals("yes")) {
			Damage.addDisabledLimb(trooper, locationName, pd);
		}
	}

	@Override
	public CommandType getType() {
		return CommandType.INJURY;
	}

}
