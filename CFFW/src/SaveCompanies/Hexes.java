package SaveCompanies;

import java.io.Serializable;
import java.util.ArrayList;

import Hexes.Hex;

public class Hexes implements Serializable {

	public ArrayList<Hex> hexes;
	public int hexCols; 
	public int hexRows; 
	
	public Hexes(ArrayList<Hex> hexes, int hexCols, int hexRows) {
		this.hexes = hexes;
		this.hexCols = hexCols;
		this.hexRows = hexRows;
	}
	
}
