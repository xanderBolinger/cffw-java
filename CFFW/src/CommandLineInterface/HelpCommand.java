package CommandLineInterface;


public class HelpCommand implements Command {

	@Override
	public void resolve() {
		
		CommandLineInterface.cli.print("Base CLI Commands: ");
		CommandLineInterface.cli.print("help - lists possible commands");
		CommandLineInterface.cli.print("exit - closes out of the cli");
		CommandLineInterface.cli.print("custom - [actionNumber, ationCost{int}, coacCost{int}] adds custom action to selected trooper.");
		CommandLineInterface.cli.print("med - lists selected trooper medical stat block.");
		CommandLineInterface.cli.print("hit - [weaponName{no spaces}, pen{int}, dc{int}]");
		CommandLineInterface.cli.print("injury - [injuryName{no spaces}, locationName{underscores as spaces}, pd{int}, disabling{yes or no}]");
		CommandLineInterface.cli.print("stun - [sp{int} flashBang{yes or no}] applies stun damage and performs KO test");
		CommandLineInterface.cli.print("encum - auto or number, sets trooper encumberance");
		CommandLineInterface.cli.print("wep - [weaponName{underscores are spaces}] sets weapon name");
		CommandLineInterface.cli.print("dist - [newMultiplier{int}] sets new distance multiplier mult * hex dist = distance hexes");
	}

	@Override
	public CommandType getType() {
		return CommandType.HELP;
	}

}
