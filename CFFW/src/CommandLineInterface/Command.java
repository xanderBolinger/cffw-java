package CommandLineInterface;

public interface Command {

	public enum CommandType {
		HELP,EXIT
	}

	void resolve();
	CommandType getType();
	
}
