package CommandLineInterface;

import java.util.ArrayList;
import java.util.Arrays;

import CorditeExpansion.CorditeExpansionGame;
import CorditeExpansionDamage.Damage;
import Trooper.Trooper;

public class StunCommand implements Command {

	int sp; 
	boolean flashBang;
	Trooper target = CorditeExpansionGame.selectedTrooper;
	
	public StunCommand(ArrayList<String> params) {
		if(!Command.selectedTrooper() 
				|| !Command.checkParameters(params, 3)
				|| !Command.verifyParameters(params, new ArrayList<Integer>(
					    Arrays.asList(1)))) {
			return;
		}
		
		sp = Integer.parseInt(params.get(1));
		flashBang = params.get(2).equals("yes");
		
		resolve();
	}
	
	@Override
	public void resolve() {
		
		boolean alreadyUnconscious = !target.ceStatBlock.medicalStatBlock.conscious();
		
		Damage.statusCheck(sp + target.ceStatBlock.medicalStatBlock.getPdTotal(), target);
		
		if(!target.ceStatBlock.medicalStatBlock.conscious() && !alreadyUnconscious
				&& flashBang) {
			try {
				target.ceStatBlock.medicalStatBlock.incapTime = 
						Damage.incapacitationImpulsesFlashbang();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		
	}

	@Override
	public CommandType getType() {
		return CommandType.STUN;
	}
	
}
