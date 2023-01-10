package CLI;

public class ExitCommand implements Command{

	public ExitCommand() {
		resolve();
	}
	
	@Override
	public void resolve() {
		CommandLineInterface.cli.exit();
	}

	@Override
	public CommandType getType() {
		return CommandType.EXIT;
	}

}
