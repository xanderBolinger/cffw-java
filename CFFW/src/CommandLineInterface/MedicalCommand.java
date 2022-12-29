package CommandLineInterface;

import CorditeExpansion.CorditeExpansionGame;
import CorditeExpansionStatBlock.MedicalStatBlock;
import CorditeExpansionStatBlock.MedicalStatBlock.Status;

public class MedicalCommand implements Command {

	public MedicalCommand() {
		if(!Command.selectedTrooper()) {
			return;
		}
		
		resolve();
	}
	
	@Override
	public void resolve() {
		
		CommandLineInterface cli = CommandLineInterface.cli;
		MedicalStatBlock stat = CorditeExpansionGame.selectedTrooper.ceStatBlock.medicalStatBlock;
		
		cli.print("Medical Stat Block");
		cli.print("Alive: "+stat.alive+", Conscious: "+stat.conscious());
		cli.print("Status: "+stat.status);
		
		if(stat.status != Status.NORMAL) {
			cli.print("Status: "+stat.statusDuration);
		}
		
		cli.print("Stabalized: "+stat.stabalized);
		cli.print("PD Total: "+stat.getPdTotal());
		cli.print("BLPD: "+stat.getBloodLossPd());
		cli.print("Pain: "+stat.pain);
		
		if(stat.getPdTotal() > 0) {
			cli.print("Time Spent Injure: "+stat.timeSpentInjured);
			cli.print("Critical Time: "+stat.criticalTime);
			cli.print("Recovery Chance: "+stat.recoveryChance);
			cli.print("Reciving First Aid: false");
		}
		
		cli.print("Injuries: ");
		int i = 0;
		for(String injury : stat.getInjuries()) {
			cli.print(i + ": "+ injury);
			i++; 
		}
		
		i = 0;
		cli.print("Blood Loss Locations: ");
		for(String injury : stat.getblLocations()) {
			cli.print(i + ": "+ injury);
			i++; 
		}
		
	}

	@Override
	public CommandType getType() {
		return CommandType.MEDICAL;
	}
	
}
