package CLI;

import java.util.ArrayList;
import java.util.Arrays;

import CLI.Command.CommandType;
import Game.GameMaster;

public class GameCommand implements Command {
	
	public GameCommand() {
		resolve();
	}
	
	@Override
	public void resolve() {
		System.out.println(GameMaster.game.toString());
	}

	@Override
	public CommandType getType() {
		return CommandType.GAME;
	}

}
