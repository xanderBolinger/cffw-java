package HexGrid.ProcGen;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

import Company.Company;
import CreateGame.JsonSaveRunner;
import Hexes.Feature;
import Hexes.Hex;

public class ProcGenHexLoader {

	public static Map getMap() throws JsonMappingException, JsonProcessingException, IOException {
		var map = loadMap(JsonSaveRunner.loadFile());
		return map;
	}
	
	public static ArrayList<Hex> getHexes(Map map) throws JsonMappingException, JsonProcessingException, IOException {
		var hexes = new ArrayList<Hex>();
		for(var tile : map.tiles)
			hexes.add(createHex(tile));
		
		return hexes;
	}
	
	private static Hex createHex(Tile tile) {
		return new Hex(tile.x, tile.y, new ArrayList<Feature>(), 0,0,tile.elevation);
	}
	
	
	private static Map loadMap(String json) throws JsonMappingException, JsonProcessingException {
		// return new Gson().fromJson(json, Company.class);
		return new Gson().fromJson(json, Map.class);
	}
	
	public class Map implements Serializable {
		public List<Tile> tiles;
		
		
		public String getHexType(int x, int y) {
			for(var tile : tiles) {
				if(tile.x == x && tile.y == y) 
					return tile.hexType;
			}
			return "";
		}
		
	}
	
	public class Tile implements Serializable {
		public int x; 
		public int y; 
		public int elevation; 
		public String hexType;
		
	}
	
	
}
