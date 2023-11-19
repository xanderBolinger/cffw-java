package HexGrid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.List;

import UtilityClasses.DiceRoller;

import java.util.ArrayList;

public class HexGridExplosiveImpacts implements Serializable {

	public List<double[]> offsets;
	
	public HexGridExplosiveImpacts() {
		offsets = new ArrayList<double[]>();
	}
	
	public void addImpact() {
		if(offsets.size() >= 8)
			return;
		
		offsets.add(new double[] {DiceRoller.roll(-100, 100), DiceRoller.roll(-100, 100)});
	}
	
	public void drawImpacts(Graphics2D g2, Polygon hex, double zoom) {
		
		for(int i = 0; i < offsets.size(); i++)
			drawSingleImpact(g2, hex, zoom, offsets.get(i));
		
	}
	
	public void drawSingleImpact(Graphics2D g2, Polygon hex, double zoom, double[] offsets) {
		double hexCenterX = hex.getBounds().x + hex.getBounds().width / 2;
		double hexCenterY = hex.getBounds().y + hex.getBounds().height / 2;
		
		double rectangleSize = (3.0 * zoom);

	    int rectangleX = (int)(hexCenterX - (rectangleSize* (offsets[0]/25.0)) / 2.0 );
	    int rectangleY = (int)(hexCenterY - (rectangleSize* (offsets[1]/25.0)) / 2.0 );

	    g2.setColor(Color.BLACK);
	    g2.fillRect(rectangleX, rectangleY, (int)rectangleSize, (int)rectangleSize);
	}
	
}
