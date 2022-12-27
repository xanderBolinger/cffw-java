package CommandLineInterface;


public class HelpCommand implements Command {

	@Override
	public void resolve() {
		
		CommandLineInterface.cli.print("Base CLI Commands: ");
		CommandLineInterface.cli.print("help - lists possible commands");
		CommandLineInterface.cli.print("exit - closes out of the cli");
		CommandLineInterface.cli.print("custom - [actionNumber, ationCost{int}, coacCost{int}] adds custom action to selected trooper.");
	}

	@Override
	public CommandType getType() {
		return CommandType.HELP;
	}

}
