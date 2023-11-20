package Hexes;

import java.io.Serializable;
import java.util.ArrayList;

import HexGrid.ProcGen.ProcGenHexLoader.Map;

public class SaveHexes implements Serializable {

	public ArrayList<Hex> hexes; 
	public Map map;
	
	public SaveHexes(ArrayList<Hex> hexes, Map map) {
		this.hexes = hexes; 
		this.map = map;
	}
	
	
}
