package TestPackage;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Game.Game;
import Game.GameMaster;
import Mechanics.Formation;
import Ship.Ship.ShipType;
import Turns.Segment;
import Turns.Turn;

public class TurnTests {
	Game game;

	@Before
	public void before() {
		game = new Game();
		new GameMaster(game);

		game.addShip(ShipType.VENATOR, "venator");
		game.addShip(ShipType.VENATOR, "venator1");
	}

	@Test
	public void turnTest() {
		
		Turn turn = new Turn();
		
		assertEquals(true, turn.nextSegment());
		assertEquals(true, turn.nextSegment());
		assertEquals(true, turn.nextSegment());
		assertEquals(true, turn.nextSegment());
		assertEquals(true, turn.nextSegment());
		assertEquals(true, turn.nextSegment());
		assertEquals(true, turn.nextSegment());
		assertEquals(false, turn.nextSegment());
	}
	
	@Test
	public void segmentTest() {
		
		Turn turn = new Turn();
		
		assertEquals("Orders Step", turn.currentSegment().currentStep().stepName);
		turn.currentSegment().nextStep();
		assertEquals("Process Fire Orders", turn.currentSegment().currentStep().stepName);
	}
	
	@Test 
	public void stepTest() {
		
		Turn turn = new Turn();
		assertEquals(2, turn.currentSegment().currentStep().numberOfResponses());
		
		GameMaster.game.formations.add(new Formation("form1", GameMaster.game.ships));
		
		assertEquals(1, turn.currentSegment().currentStep().numberOfResponses());
		
		GameMaster.game.formations.clear();
		
		assertEquals(2, turn.currentSegment().currentStep().numberOfResponses());
		
		turn.currentSegment().currentStep().addResponse(GameMaster.game.ships.get(0), "test");
		turn.currentSegment().currentStep().addResponse(GameMaster.game.ships.get(1), "test");
		
		assertEquals(true, turn.currentSegment().currentStep().completed());
	}
	
	@Test
	public void fullTurnTest() {
		Turn turn = GameMaster.game.currentTurn();
	}
	
}
