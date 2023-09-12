package HexGrid.ProcGen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import Company.Company;
import Conflict.GameWindow;
import CreateGame.JsonSaveRunner;
import UtilityClasses.ExcelUtility;

public class ProcHexManager {
	private static Image grassImage;
    private static Image grassImageOriginal;
    private static final String GRASS_IMAGE_PATH = ExcelUtility.path + "\\HexImages\\Grass1.png";
    
    public static void GetHexImages() {
    	try {
            grassImage = ImageIO.read(new File(GRASS_IMAGE_PATH));
            grassImageOriginal = ImageIO.read(new File(GRASS_IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public static void LoadMap() throws IOException {		
		var map = ProcGenHexLoader.getMap();
		var hexes = ProcGenHexLoader.getHexes(map);
		
		GameWindow.gameWindow.game.backgroundMap = false;
		GameWindow.gameWindow.game.procGenMap = map;
		GameWindow.gameWindow.hexes = hexes;
		GameWindow.gameWindow.game.vehicleManager.generate();
	}
	
	public static void GetScaledInstances(int width, int height) {
		grassImage = grassImageOriginal.getScaledInstance(width, height, 
				Image.SCALE_SMOOTH);
	}
	
	public static void PaintHex(Graphics2D g2d, Polygon hex, int x, int y) {
		if(GameWindow.gameWindow.game.backgroundMap)
			return;
		
		int hexCenterX = hex.getBounds().x + hex.getBounds().width / 2;
		int hexCenterY = hex.getBounds().y + hex.getBounds().height / 2;

        int grassImageX = hexCenterX - hex.getBounds().width / 2;
        int grassImageY = hexCenterY - hex.getBounds().height / 2;

        g2d.drawImage(getHexImage(x,y), grassImageX, grassImageY, null);
	}
	
	private static Image getHexImage(int x, int y) {
		var hex = GameWindow.gameWindow.game.procGenMap.getHexType(x, y);
		return grassImage;
	}
	
}
