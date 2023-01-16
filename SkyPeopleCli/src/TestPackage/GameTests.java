package TestPackage;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import CLI.CommandLineInterface;
import Game.Game;
import Game.GameMaster;
import Ship.Ship;
import Ship.Ship.ShipType;

public class GameTests {

	Game game;
	
	@Before
	public void before() {
		game = new Game();
		new GameMaster(game);
		
		game.addShip(ShipType.VENATOR, "Resolute");
	}
	
	@Test
	public void nextRound() {
		
		game.ships.get(0).power -= 15;
		
		game.nextRound();
		
		assertEquals(true, game.ships.get(0).heat == 15);
	}
	
	@Test
	public void findShip() {
		
		Ship ship = game.findShip("Resolute");
		assertEquals(game.ships.get(0), ship);
		
	}
	
}
