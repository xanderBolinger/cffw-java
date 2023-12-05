package HexGrid.ProcGen;

import java.awt.Color;
import CorditeExpansion.Cord;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import Company.Company;
import Conflict.GameWindow;
import JsonSaveRunner.JsonSaveRunner;
import UtilityClasses.ExcelUtility;
import HexGrid.ProcGen.ProcGenHexLoader.Map;
import HexGrid.Roads.*;
public class ProcHexManager {
	
	private static ArrayList<HexImage> hexImages;
    

    // Can switch to loop that will loop over directories and get specific tile sets for certain maps
    public static void GetHexImages(String imageFolder) {
    	hexImages = new ArrayList<HexImage>();
    	AddHexImage("BigBuilding", imageFolder);
    	AddHexImage("Building", imageFolder);
    	AddHexImage("Brush", imageFolder);
    	AddHexImage("HeavyBrush", imageFolder);
    	AddHexImage("Clear", imageFolder);
    	AddHexImage("HeavyWoods", imageFolder);
    	AddHexImage("MediumWoods", imageFolder);
    	AddHexImage("LightWoods", imageFolder);
    	AddHexImage("Rough", imageFolder);
    }
    
    private static void AddHexImage(String imageName, String imageFolder) {
    	var img = new HexImage(imageName, imageFolder);
    	hexImages.add(img);
    }
    
	public static void LoadMap() throws IOException {		
		var map = ProcGenHexLoader.getMap();
		var hexes = ProcGenHexLoader.getHexes(map);
		
		GameWindow.gameWindow.game.backgroundMap = false;
		GameWindow.gameWindow.game.procGenMap = map;
		GameWindow.gameWindow.hexes = hexes;
		GameWindow.gameWindow.game.vehicleManager.generate();
		
		var pathwaySegment = new RoadSegment();
		var highwaySegment = new RoadSegment();
		loadRoadSegment(map, pathwaySegment, false);
		loadRoadSegment(map, highwaySegment, true);
		
		GameWindow.gameWindow.game.roadManager.segments.add(pathwaySegment);
		GameWindow.gameWindow.game.roadManager.segments.add(highwaySegment);
		
		GameWindow.gameWindow.hexGrid.panel.firstTimeMakingHexes = true;
	}
	
	private static void loadRoadSegment(Map map, RoadSegment segment, boolean highway) {
		
		for(var road : !highway ? map.pathways : map.highways) {
			segment.addRoad(new Road(new Cord(road.x, road.y), highway, false));
		}
		
	}
	
	public static void GetScaledInstances(int width, int height) {
		for(var img : hexImages) {
			img.getScaledInstance(width, height);
		}
	}
	
	public static void PaintHex(Graphics2D g2d, Polygon hex, int x, int y, JComponent comp) {
		if(GameWindow.gameWindow.game.backgroundMap)
			return;
		
		int hexCenterX = hex.getBounds().x + hex.getBounds().width / 2;
		int hexCenterY = hex.getBounds().y + hex.getBounds().height / 2;

        int grassImageX = hexCenterX - hex.getBounds().width / 2;
        int grassImageY = hexCenterY - hex.getBounds().height / 2;

        BufferedImage img = null;
		try {
			img = getHexImage(x,y);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        g2d.drawImage(img, grassImageX, grassImageY, comp);
	}
	
	private static BufferedImage getHexImage(int x, int y) throws Exception {
		var hex = GameWindow.gameWindow.game.procGenMap.getHexType(x, y);
		
		for(var hexImage : hexImages)
			if(hexImage.imageName.equals(hex))
				return hexImage.image;
			
		
		throw new Exception("Hex not found for hex type: "+hex);
	}
	
	
	
}
