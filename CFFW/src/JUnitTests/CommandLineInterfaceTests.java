package JUnitTests;

import static org.junit.Assert.assertEquals;

import java.awt.AWTException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import CommandLineInterface.CommandLineInterface;

public class CommandLineInterfaceTests {

	
	@Test
	public void testCli() {
		
		try {
			System.setIn(new ByteArrayInputStream("exit".getBytes()));
			
			CommandLineInterface cli = new CommandLineInterface();
			
			assertEquals("exit", cli.input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testCommandNotFound() {
		
		try {
			System.setIn(new ByteArrayInputStream("test\nexit".getBytes()));
			CommandLineInterface cli = new CommandLineInterface();
			assertEquals(true, cli.readLines.contains("test"));
			assertEquals(true, cli.printedLines.contains("Command not found"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testHelp() {
		
		try {
			System.setIn(new ByteArrayInputStream("help\nexit".getBytes()));
			CommandLineInterface cli = new CommandLineInterface();
			assertEquals(true, cli.readLines.contains("help"));
			
			assertEquals(true, cli.printedLines.contains("help - lists possible commands"));
			assertEquals(true, cli.printedLines.contains("exit - closes out of the cli"));
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
