package CLI;

import Game.GameMaster;
import Turns.Turn;

public class TurnsCommand implements Command {
	
	public TurnsCommand() {
		resolve();
	}
	
	@Override
	public void resolve() {
		
		System.out.println("Turn History: ");
		
		for(Turn turns : GameMaster.game.turns) {
			System.out.println("Turn "+(GameMaster.game.turns.indexOf(turns)+1));
			turns.toString();
		}
		
	}

	@Override
	public CommandType getType() {
		return CommandType.GAME;
	}

}
