package HexGrid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import Conflict.GameWindow;
import Hexes.Hex;
import Hexes.SaveHexes;

public class LoadMap {

	
	public LoadMap() {
		
	}
	
	public static void loadMap(String mapName) throws FileNotFoundException, IOException {
		if(!new File("Map Saves/"+mapName).exists()) {
			System.out.println("File Does Not Exist");
			return; 
		}
		
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("Map Saves/"+mapName));
		try {
			SaveHexes saveHexes = (SaveHexes) in.readObject();
			GameWindow.gameWindow.hexes = saveHexes.hexes;
			
			for(Hex hex : GameWindow.gameWindow.hexes) {
				
				hex.concealment = 0; 
				hex.coverPositions = 0; 
				
				hex.setConcealment();
				hex.setTotalPositions();
				
			}
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		in.close();
	}
	
}
