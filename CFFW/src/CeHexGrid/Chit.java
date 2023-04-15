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
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import CeHexGrid.Chit.Facing;
import CorditeExpansion.Cord;
import UtilityClasses.ExcelUtility;

public class Chit implements Serializable {
	public transient Image chitImage;
	public String imagePath; 
	
	private static Chit selectedChit = null;
	
	private double chitWidth = 15; 
	private double chitHeight = 15;
	private double oldZoom = 1.0; 
	public int number;
	public int shiftX = 0; 
	public int shiftY = 0; 
	public boolean shifted = false;
	public int xCord = 0; 
	public int yCord = 0;
	public int xPoint = 0; 
	public int yPoint = 0;
	
	public Facing facing = Facing.A;
	
	public enum Facing implements Serializable {
		A(1),AB(2),B(3),BC(4),C(5),CD(6),D(7),DE(8),E(9),EF(10),F(11),FA(12);
		
		public int value; 
		
		private static Map map = new HashMap<>();

	    private Facing(int value) {
	        this.value = value;
	    }

	    static {
	        for (Facing facingType : Facing.values()) {
	            map.put(facingType.value, facingType);
	        }
	    }

	    public static Facing valueOf(int facingType) {
	        return (Facing) map.get(facingType);
	    }

	    public int getValue() {
	        return value;
	    }
	    
	    public static Facing turnClockwise(Facing facing) {
			int facingValue = facing.getValue() + 1;
			
			if(facingValue > 12)
				facingValue = 1; 
			
			return Facing.valueOf(facingValue);
			
			
		}
		
		public static Facing turnCounterClockwise(Facing facing) {
			int facingValue = facing.getValue() - 1;
			
			if(facingValue <= 0)
				facingValue = 12; 
			
			return Facing.valueOf(facingValue);
		}
		
	}
	
	public Chit(String path, int width, int height) {
		this.imagePath = path;
		chitWidth = width;
		chitHeight = height;
		try {
			chitImage =  ImageIO.read(new File(path));
			chitImage =  chitImage.getScaledInstance((int) chitWidth, (int) chitHeight,
					Image.SCALE_DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Chit() {
		imagePath = "CeImages/temp.png";
		try {
			chitImage =  ImageIO.read(new File(imagePath));
			chitImage =  chitImage.getScaledInstance((int) chitWidth, (int) chitHeight,
					Image.SCALE_DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Chit(String imagePath) {
		this.imagePath = imagePath;
		try {
			chitImage =  ImageIO.read(new File(imagePath));
			chitImage =  chitImage.getScaledInstance((int) chitWidth, (int) chitHeight,
					Image.SCALE_DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setDimensions(int width, int height, double zoom) {
		chitWidth = width; 
		chitHeight = height;
		rescale(zoom);
	}
	
	public void rescale(double zoom) {
		try {
			chitImage =  ImageIO.read(new File(imagePath));
		} catch (IOException e) {
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

	public void loadImage() {
		if(chitImage == null) {
			try {
				//throw new Exception("Image Path: "+imagePath);
				
				if(imagePath.contains("blufor")) {
					this.imagePath = ExcelUtility.path+"\\Icons\\unknown_blufor_icon.png";
				} else if(imagePath.contains("opfor")) {
					this.imagePath = ExcelUtility.path+"\\Icons\\unknown_opfor_icon.png";
				} else {
					this.imagePath = ExcelUtility.path+"\\Icons\\unknown_icon.png";
				}
				
				File file = new File(imagePath);
				
				chitImage =  ImageIO.read(file);
				chitImage =  chitImage.getScaledInstance((int) chitWidth, (int) chitHeight,
						Image.SCALE_DEFAULT);
			} catch (IOException e) {
				System.out.println("ImagePath: "+imagePath);
				e.printStackTrace();
			} /*catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}
	
	public void drawChit(double zoom, Graphics2D g2, Polygon hex) {
		loadImage();
		
		if(chitImage == null)
			return;
		
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
		
		int tempXPoint = xPoint;
		int tempYPoint = yPoint;
		
		if(facing != Facing.A && facing != facing.BC && facing != Facing.D
				&& facing != Facing.EF) {
			tempXPoint -= getWidth() / 8;
			tempYPoint -= getHeight() / 8;
		}
		
		g2.drawImage(rotate(getRotationDegrees(), chitImage), tempXPoint, tempYPoint, null);
	}
	
	public void drawChit(double zoom, Graphics2D g2, Polygon hex, int shiftX, int shiftY) {
		 loadImage();
		
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
		
		xPoint += shiftX;
		yPoint += shiftY;
		
		
		g2.drawImage(rotate(getRotationDegrees(), chitImage), xPoint, yPoint, null);
	}
	
	public Cord getCord() {
		return new Cord(xCord, yCord);
	}
	
	public static Chit getSelectedChit() {
		return selectedChit;
	}
	
	public static void drawShadow(double zoom, Graphics2D g2, Polygon hex) {
		
		Chit chit = selectedChit;
		chit.loadImage();
		
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
				return 0;
			case AB:
				return 30;
			case B:
				return 60;
			case BC:
				return 90;
			case C:
				return 120;
			case CD:
				return 150;
			case D:
				return 180; 
			case DE:
				return 210;
			case E:
				return 240;
			case EF:
				return 270;
			case F:
				return 300; 
			case FA:
				return 330;
			default:
				return 0;
		}
		
		
	}
	
	public static BufferedImage rotate(double angle, Image image) {
		final double rads = Math.toRadians(angle);
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
		return rotateOp.filter(toBufferedImage(image),null);
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
