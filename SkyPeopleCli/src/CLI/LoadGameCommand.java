package CLI;

import java.io.IOException;

import CLI.Command.CommandType;
import Game.GameMaster;
import SaveRunner.JsonSaveRunner;

public class LoadGameCommand implements Command {

	
	public LoadGameCommand() {
		
		resolve();
	}
	
	@Override
	public void resolve() {
		try {
			GameMaster.game = JsonSaveRunner.loadGame(JsonSaveRunner.loadFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		GameMaster.move();
	}

	@Override
	public CommandType getType() {
		return CommandType.SHOW_SHIP;
	}

}
