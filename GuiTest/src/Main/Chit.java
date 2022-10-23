package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Chit {
	public Image chitImage;

	private static Chit selectedChit = null;
	private static int selectedX; 
	private static int selectedY;
	
	
	private int chitWidth = 20; 
	private int chitHeight = 12;
	private double oldZoom = 1.0; 
	public int xCord = 0; 
	public int yCord = 0;
	public int xPoint = 0; 
	public int yPoint = 0;
	
	public Chit() {
		try {
			chitImage =  ImageIO.read(new File("Unit Images/BLUFOR_INFANTRY.png"));
			chitImage =  chitImage.getScaledInstance(chitWidth, chitHeight,
					Image.SCALE_DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void rescale(double zoom) {
		try {
			chitImage =  ImageIO.read(new File("Unit Images/BLUFOR_INFANTRY.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		chitImage = chitImage.getScaledInstance((int)(20.0*zoom), (int)(12.0*zoom), Image.SCALE_SMOOTH);
	}
	
	public int getWidth() {
		return chitImage.getWidth(null);
	}

	public int getHeight() {
		return chitImage.getHeight(null);
	}

	public void drawChit(double zoom, Graphics2D g2, Polygon hex) {
		
		int hexCenterX = hex.getBounds().x + hex.getBounds().width / 2;
		int hexCenterY = hex.getBounds().y + hex.getBounds().height / 2;
		//System.out.println("Zoom: "+zoom);
		int width = (int) Math.round(chitWidth * zoom); 
		int height = (int) Math.round(chitHeight * zoom);
		
		int chitsInHex = 0;
		int chitIndexInHex = 0; 
		
		if(oldZoom != zoom) {
			oldZoom = zoom;
			rescale(zoom);
		}
		
		
		if(!Chit.isSelected(this)) {		
			xPoint = (hexCenterX - width / 2) - (3 * (chitsInHex - chitIndexInHex));
			yPoint = (hexCenterY - height / 2) + (3 * (chitsInHex - chitIndexInHex));
		}
		
		
		g2.drawImage(chitImage, xPoint, yPoint, null);
	}
	
	public static void drawShadow(double zoom, Graphics2D g2, Polygon hex) {
		
		Chit chit = selectedChit;
		
		int hexCenterX = hex.getBounds().x + hex.getBounds().width / 2;
		int hexCenterY = hex.getBounds().y + hex.getBounds().height / 2;
		//System.out.println("Zoom: "+zoom);
		int width = (int) Math.round(chit.chitHeight * zoom); 
		int height = (int) Math.round(chit.chitHeight * zoom);
		
		int chitsInHex = 0;
		int chitIndexInHex = 0; 
		
		if(chit.oldZoom != zoom) {
			chit.oldZoom = zoom;
			chit.rescale(zoom);
		}
		
	
		int xPoint = (hexCenterX - width / 2) - (3 * (chitsInHex - chitIndexInHex));
		int yPoint = (hexCenterY - height / 2) + (3 * (chitsInHex - chitIndexInHex));
		
		
		
		g2.drawImage(chit.chitImage, (int)(xPoint-(4.0*zoom)), yPoint, null);
	}

	public static void setSelectedChit(Chit chit, int x, int y) {
		selectedChit = chit; 
		System.out.println("Selected X diff: "+x);
		selectedX = x;
		selectedY = y; 
	}
	
	public static void moveSelectedChit(int x, int y) {
		selectedChit.xCord = x; 
		selectedChit.yCord = y; 
	}
	
	public static void translateChit(int x, int y) {
		selectedChit.xPoint += x;
		selectedChit.yPoint += y; 
	}
	
	public static void unselectChit() {
		selectedChit = null; 
	}
	
	public static boolean isAChitSelected() {
		if(selectedChit != null)
			return true; 
		else 
			return false; 
	}
	
	public static boolean isSelected(Chit chit) {
		if(chit == selectedChit)
			return true; 
		else 
			return false; 
	}
	
}
