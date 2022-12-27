package JUnitTests;

import static org.junit.Assert.assertEquals;

import java.awt.AWTException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CommandLineInterface.Command;
import CommandLineInterface.CommandLineInterface;
import CorditeExpansion.ActionOrder;
import CorditeExpansion.CorditeExpansionGame;
import Trooper.Trooper;

public class CommandLineInterfaceTests {

	ActionOrder actionOrder;
	Trooper clone; 
	
	CommandLineInterface cli;
	
	public void setCli(String message) {
		System.setIn(new ByteArrayInputStream(message.getBytes()));
		
		try {
			cli = new CommandLineInterface();
		} catch (IOException | AWTException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void before() {
		actionOrder = new ActionOrder();
		CorditeExpansionGame.actionOrder = actionOrder; 
		clone = new Trooper("Clone Rifleman", "Clone Trooper Phase 1");
		actionOrder.addTrooper(clone);
		CorditeExpansionGame.selectedTrooper = clone; 
	}
	
	@After
	public void after() {
		CorditeExpansionGame.selectedTrooper = null;
		CorditeExpansionGame.actionOrder = null;
		actionOrder.clear();
	}
	
	@Test
	public void testCli() {
		
		setCli("exit");
		assertEquals("exit", cli.input);
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
			assertEquals(true, cli.printedLines.contains("custom - [actionNumber, "
					+ "ationCost{int}, coacCost{int}] adds custom action to selected trooper."));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void customAction() {
		
		assertEquals(true, Command.selectedTrooper());
		assertEquals(true, Command.checkParameters(CommandLineInterface.parameters(
				"custom customAction 2 2"), 4));
		assertEquals(true, Command.verifyParameters(CommandLineInterface.parameters(
				"custom customAction 2 2"), new ArrayList<Integer>(
			    Arrays.asList(2, 3))));
		
		setCli("custom customAction 2 2");
		
		CorditeExpansionGame.action();
		CorditeExpansionGame.action();
		CorditeExpansionGame.action();
		CorditeExpansionGame.action();
		
		assertEquals(true, cli.printedLines.contains("customAction: Finished"));
		
	}
	
}
