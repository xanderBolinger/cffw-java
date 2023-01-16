package Game;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import SaveRunner.JsonSaveRunner;

public class GameMaster {
	public static Game game;
	public static ArrayList<Game> games;
	
	public GameMaster(Game g) {
		//System.out.println("Set game");
		game = g; 
		games = new ArrayList<>();
		move();
	}
	
	public static void undo() {
		if(games.size() == 1)
			return;
		// Remove last move
		games.remove(games.size() - 1);
		// Get previous move
		Game previousGame = games.get(games.size() - 1);
		game = new Game(previousGame);
		
	}
	
	public static void move() {
		//System.out.println("Move");
		games.add(new Game(game));
		autosave();
	}
	
	public static void autosave() {
		String json = JsonSaveRunner.saveGame(game);
		try {
			//System.out.println("Autosave game: "+JsonSaveRunner.path+"\\autosave.json");
			FileWriter file = new FileWriter(JsonSaveRunner.path+"\\autosave.json");
			file.write(json);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
