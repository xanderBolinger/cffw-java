package CommandLineInterface;

public class ExitCommand implements Command{

	@Override
	public void resolve() {
		CommandLineInterface.cli.exit();
	}

	@Override
	public CommandType getType() {
		return CommandType.EXIT;
	}

}
