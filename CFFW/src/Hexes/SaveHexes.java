package Hexes;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveHexes implements Serializable {

	public ArrayList<Hex> hexes; 
	
	public SaveHexes(ArrayList<Hex> hexes) {
		this.hexes = hexes; 
	}
	
	
}
