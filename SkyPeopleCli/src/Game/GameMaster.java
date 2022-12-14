package Game;

import java.util.ArrayList;

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
		game = new Game(previousGame.round, previousGame.ships);
		
	}
	
	public static void move() {
		//System.out.println("Move");
		games.add(new Game(game.round, game.ships));
	}
}
