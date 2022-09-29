import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class Save implements Serializable {
	private ArrayList<Character> characters = new ArrayList<Character>();
	
	public Save(ArrayList<Character> characters) {
		this.characters = characters;
	}
	
	// Loops through each company 
	// Writes an individual file for each company 
	public void fileOut() throws FileNotFoundException, IOException, ClassNotFoundException {
		int fileNum = 1;
		for(int i = 0; i < characters.size(); i++) {
			Character company = characters.get(i);
			String fileName = "Character_" + fileNum;
			
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(company);
			out.close();
			fileNum++;
		}
		
	}
	
	
	
	
}
