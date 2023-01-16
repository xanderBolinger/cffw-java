package CLI;

import CLI.Command.CommandType;
import Game.GameMaster;

public class NextCommand implements Command {
	
	public NextCommand() {
		resolve();
	}
	
	@Override
	public void resolve() {
		
		if(!GameMaster.game.currentTurn().currentSegment().nextStep()) {
			if(!GameMaster.game.currentTurn().nextSegment()) {
				GameMaster.game.nextRound();
			}
		}
		
	}

	@Override
	public CommandType getType() {
		return CommandType.GAME;
	}

}
