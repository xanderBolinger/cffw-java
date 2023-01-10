package CLI;

import CLI.Command.CommandType;
import Game.GameMaster;

public class UndoCommand implements Command {
	
	public UndoCommand() {
		resolve();
	}
	
	@Override
	public void resolve() {
		GameMaster.undo();
	}

	@Override
	public CommandType getType() {
		return CommandType.GAME;
	}

}
