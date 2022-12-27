package CommandLineInterface;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
		
		
		
		while(open) {
			String input = reader.nextLine();
			read(input);
		}
		
		System.out.println("---CLI Closed---");
	}

	public void read(String input) throws AWTException {
		
		this.input = input;
		
		readLines.add(input);
		
		if(input.equals("help")) {
			new HelpCommand().resolve();
		} else if(input.equals("exit")) {
			new ExitCommand().resolve();
			return;
		} else {
			print("Command not found");
		}
		
		System.out.print("-> ");
		
		
	}
	
	public void print(String input) {
		printedLines.add(input);
		System.out.println(input);
	}
	
	public void exit() {
		open = false; 
	}
	
}
