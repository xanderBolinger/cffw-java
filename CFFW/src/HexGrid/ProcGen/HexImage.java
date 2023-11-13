package HexGrid.ProcGen;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import UtilityClasses.ExcelUtility;
import UtilityClasses.ImageUtility;

public class HexImage {
	public String imageName;
	public BufferedImage image;
	
	private String imagePath = ExcelUtility.path + "\\HexImages\\";
	private Image imageOriginal;
	
	public HexImage(String fileName, String imageFolder) {
		imageName = fileName;
		imagePath += imageFolder +"\\";
		imagePath += fileName+".png";
		try {
			image = ImageIO.read(new File(imagePath));
            imageOriginal = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	public void getScaledInstance(int width, int height) {
		var img = imageOriginal.getScaledInstance(width, height, 
				Image.SCALE_SMOOTH);
		image = ImageUtility.toBufferedImage(img);
	}
	
	
}
