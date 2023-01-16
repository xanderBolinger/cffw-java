package CLI;

import CLI.Command.CommandType;
import Game.GameMaster;

public class NextRoundCommand implements Command {
	
	public NextRoundCommand() {
		resolve();
	}
	
	@Override
	public void resolve() {
		GameMaster.game.nextRound();
		GameMaster.move();
	}

	@Override
	public CommandType getType() {
		return CommandType.GAME;
	}

}
