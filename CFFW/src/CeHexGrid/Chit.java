package CeHexGrid;

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
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Chit {
	public Image chitImage;

	private static Chit selectedChit = null;
	
	private double chitWidth = 15; 
	private double chitHeight = 15;
	private double oldZoom = 1.0; 
	public int xCord = 0; 
	public int yCord = 0;
	public int xPoint = 0; 
	public int yPoint = 0;
	
	public Facing facing; 
	public enum Facing {
		A,AB,B,BC,C,CD,D,DE,E,EF,F,FA
	}
	
	public Chit() {
		try {
			chitImage =  ImageIO.read(new File("CeImages/temp.png"));
			chitImage =  chitImage.getScaledInstance((int) chitWidth, (int) chitHeight,
					Image.SCALE_DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void rescale(double zoom) {
		try {
			chitImage =  ImageIO.read(new File("CeImages/temp.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		chitImage = chitImage.getScaledInstance((int)(chitWidth*zoom), (int)(chitHeight*zoom), Image.SCALE_SMOOTH);
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
		
		
		g2.drawImage(rotate(getRotationDegrees(), chitImage), xPoint, yPoint, null);
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
		
		g2.drawImage(rotate(chit.getRotationDegrees(), chit.chitImage), xPoint, yPoint, null);
	}

	public int getRotationDegrees() {
		if(facing == null) {
			return 0;
		}
		
		switch(facing) {
			case A:
				System.out.println("Facing 1");
				return 0;
			case AB:
				System.out.println("Facing 2");
				return 30;
			case B:
				System.out.println("Facing 3");
				return 60;
			case BC:
				System.out.println("Facing 4");
				return 90;
			default:
				return 0;
		}
		
		
	}
	
	public static BufferedImage rotate(double angle, Image image) {
		final double rads = Math.toRadians(90);
		final double sin = Math.abs(Math.sin(rads));
		final double cos = Math.abs(Math.cos(rads));
		final int w = (int) Math.floor(image.getWidth(null) * cos + image.getHeight(null) * sin);
		final int h = (int) Math.floor(image.getHeight(null) * cos + image.getWidth(null) * sin);
		final BufferedImage rotatedImage = toBufferedImage(image);
		final AffineTransform at = new AffineTransform();
		at.translate(w / 2, h / 2);
		at.rotate(rads,0, 0);
		at.translate(-image.getWidth(null) / 2, -image.getHeight(null) / 2);
		final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		return rotateOp.filter(toBufferedImage(image),rotatedImage);
	}

	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
	public static void setSelectedChit(Chit chit, int x, int y) {
		selectedChit = chit; 
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
