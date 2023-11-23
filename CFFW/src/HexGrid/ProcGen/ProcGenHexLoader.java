package HexGrid.ProcGen;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

import Company.Company;
import Hexes.Feature;
import Hexes.Hex;
import Hexes.HexWindow;
import JsonSaveRunner.JsonSaveRunner;
import UtilityClasses.DiceRoller;

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
		
		var hex = new Hex(tile.x, tile.y, new ArrayList<Feature>(), 0,0,tile.elevation);
		var features = createHexFeatures(tile, hex);
		hex.features = features;
		hex.setConcealment();
		hex.setTotalPositions();
		return hex;
	}
	
	private static ArrayList<Feature> createHexFeatures(Tile tile, Hex hex) {
		
		ArrayList<Feature> features = new ArrayList<Feature>();
		
		if(tile.tileType.equals("Clear"))
			return features;
		else if(tile.tileType.contains("HeavyWoods")) {
			//System.out.println("create heavy woods");
			int pos = HexWindow.getCoverPostitions("Heavy Forest");
			features.add(new Feature("Heavy Forest", pos));
		} else if(tile.tileType.contains("MediumWoods")) {
			int pos = HexWindow.getCoverPostitions("Medium Forest");
			features.add(new Feature("Medium Forest", pos));
		} else if(tile.tileType.contains("LightWoods")) {
			int pos = HexWindow.getCoverPostitions("Light Forest");
			features.add(new Feature("Light Forest", pos));
		} else if(tile.tileType.equals("Building")) {
			int pos = HexWindow.getCoverPostitions("Light Urban Sprawl");
			features.add(new Feature("Light Urban Sprawl", pos));
			hex.addBuilding("Small Building", DiceRoller.roll(1, 2), DiceRoller.roll(2, 4), DiceRoller.roll(5, 8));
		}  else if(tile.tileType.equals("BigBuilding")) {
			int pos = HexWindow.getCoverPostitions("Dense Urban Sprawl");
			features.add(new Feature("Dense Urban Sprawl", pos));
			hex.addBuilding("Big Building", DiceRoller.roll(2, 4), DiceRoller.roll(4, 6), DiceRoller.roll(6, 8));
		} 
		
		if(tile.tileType.contains("HeavyBrush")) {
			features.add(new Feature("Heavy Brush", 0));
		} else if(tile.tileType.contains("Brush")) {
			features.add(new Feature("Brush", 0));
		}
		
		return features;
	}
	
	private static Map loadMap(String json) throws JsonMappingException, JsonProcessingException {
		// return new Gson().fromJson(json, Company.class);
		return new Gson().fromJson(json, Map.class);
	}
	
	public class Map implements Serializable {
		public List<Tile> tiles;
		
		
		public String getHexType(int x, int y) throws Exception {
			for(var tile : tiles) {
				if(tile.x == x && tile.y == y) 
					return tile.tileType;
			}
			throw new Exception("tile not found for: "+x+", "+y);
		}
		
	}
	
	public class Tile implements Serializable {
		public int x; 
		public int y; 
		public int elevation; 
		public String tileType;
		
	}
	
	
}
