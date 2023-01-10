package CLI;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLineInterface {

	public static CommandLineInterface cli;

	public Scanner reader = new Scanner(System.in);

	public String input;

	public ArrayList<String> readLines = new ArrayList<>();
	public ArrayList<String> printedLines = new ArrayList<>();

	public boolean open = true;

	public CommandLineInterface() throws IOException, AWTException {

		cli = this;

		System.out.println("---CLI Initialized---");
		System.out.println("");
		System.out.print("-> ");

		while (open) {
			try {
				String input = reader.nextLine();
				read(input);
			} catch(Exception e) {
				e.printStackTrace();
				//exit();
			}
			
			
		}

		System.out.println("\n---CLI Closed---");
	}

	public void read(String input) throws AWTException {

		this.input = input;

		readLines.add(input);

		ArrayList<String> parameters = parameters(input);
		
		if(parameters.size() < 1) {
			print("Command not found");		
		} 
		
		String command = parameters.get(0);
		
		if (command.equals("help")) {
			new HelpCommand().resolve();
		} else if (command.equals("custom")) {
			new CustomCommand(parameters);
		} else if (command.equals("exit")) {
			new ExitCommand();
		} else if (command.equals("laser")) {
			new BeamAttackCommand(parameters);
		} else if (command.equals("applylaser")) {
			new ApplyLaserCommand(parameters);
		} else if (command.equals("applydamage")) {
			new ApplyDamageCommand(parameters);
		} else if (command.equals("addship")) {
			new AddShipCommand(parameters);
		} else if (command.equals("showship")) {
			new ShowShipCommand(parameters);
		} else if (command.equals("shields")) {
			new ShieldRechargeCommand(parameters);
		} else if (command.equals("game")) {
			new GameCommand();
		} else if (command.equals("nextround")) {
			new NextRoundCommand();
		} else if (command.equals("undo")) {
			new UndoCommand();
		} else if (command.equals("save")) {
			new SaveGameCommand();
		} else if (command.equals("load")) {
			new LoadGameCommand();
		} else {
			print("Command not found");
		}

		System.out.print("-> ");

	}

	public static ArrayList<String> parameters(String input) {
		ArrayList<String> list = new ArrayList<>();

		Pattern pattern = Pattern.compile("[\\w^-]+");
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			list.add(matcher.group());
		}
		
		return list;
	}

	public void print(String input) {
		printedLines.add(input);
		System.out.println(input);
	}

	public void exit() {
		open = false;
	}

}
