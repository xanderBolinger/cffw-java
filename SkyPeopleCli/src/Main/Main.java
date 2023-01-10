package Main;

import java.awt.AWTException;
import java.io.IOException;

import CLI.CommandLineInterface;
import Game.Game;
import Game.GameMaster;

public class Main {

	public static void main(String[] args) throws IOException, AWTException {
		
		new GameMaster(new Game());
		new CommandLineInterface();
	}
	
}
