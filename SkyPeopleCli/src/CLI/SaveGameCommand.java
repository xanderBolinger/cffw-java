package CLI;

import java.io.IOException;

import Game.GameMaster;
import SaveRunner.JsonSaveRunner;

public class SaveGameCommand implements Command {

	
	public SaveGameCommand() {
		
		resolve();
	}
	
	@Override
	public void resolve() {
		try {
			JsonSaveRunner.saveFile("SkyPeople", JsonSaveRunner.saveGame(GameMaster.game));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public CommandType getType() {
		return CommandType.SHOW_SHIP;
	}

}
