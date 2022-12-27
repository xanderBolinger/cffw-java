package CommandLineInterface;


public class HelpCommand implements Command {

	@Override
	public void resolve() {
		
		CommandLineInterface.cli.print("Base CLI Commands: ");
		CommandLineInterface.cli.print("help - lists possible commands");
		CommandLineInterface.cli.print("exit - closes out of the cli");
		
	}

	@Override
	public CommandType getType() {
		return CommandType.HELP;
	}

}
