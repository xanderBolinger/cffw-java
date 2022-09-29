package SaveCompanies;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import Company.Company;
import Conflict.Game;
import Conflict.GameWindow;

public class SaveRunner implements Serializable {
	private GameSave gameSave; 
	
	public SaveRunner(String gameName, ArrayList<Company> companies, Game game, Hexes hexes, InitOrder initOrder, int activeUnit) {
		this.gameSave = new GameSave(gameName, companies, game, hexes, initOrder, activeUnit);
	}
	
	// Loops through each company 
	// Writes an individual file for each company 
	public void fileOut() throws FileNotFoundException, IOException, ClassNotFoundException {
		String fileName = "Game Saves/"+gameSave.gameName; 
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		out.writeObject(gameSave);
		out.close();
		
		/*int fileNum = 1;
		for(int i = 0; i < companies.size(); i++) {
			Company company = companies.get(i);
			String fileName = "Company_" + fileNum;
			
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(company);
			out.close();
			fileNum++;
		}
		
		if(game != null) {
			
			String fileName2 = "Game";
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName2));
			out.writeObject(game);
			out.close();
			
		}
		
		if(hexes != null) {
					
					String fileName3 = "Hexes";
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName3));
					out.writeObject(hexes);
					out.close();
					
		}
		
		if(initOrder != null) {
			
			String fileName4 = "InitOrder";
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName4));
			out.writeObject(initOrder);
			out.close();
			
		}
		
		String fileName5 = "ActiveUnit";
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName5));
		out.writeObject(activeUnit);
		out.close();*/

	}
	
	
	
	
}
