package HexGrid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import Conflict.GameWindow;
import Hexes.Building;
import Hexes.Hex;
import Hexes.SaveHexes;
import Hexes.Building.Floor;
import Hexes.Building.Room;

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
				for(Building building : hex.buildings)
					for(Floor floor : building.floors)
						for(Room room : floor.rooms)
							room.occupants.clear();
				
				hex.concealment = 0; 
				hex.coverPositions = 0; 
				
				hex.setConcealment();
				hex.setTotalPositions();
				
			}
			
			if(saveHexes.map != null) {
				GameWindow.gameWindow.game.procGenMap = saveHexes.map;
				GameWindow.gameWindow.game.backgroundMap = false;
			}
				
			
			GameWindow.gameWindow.game.vehicleManager.generate();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		in.close();
	}
	
}
