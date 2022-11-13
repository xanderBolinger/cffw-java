package CeHexGrid;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

import CorditeExpansion.Cord;

public class FloatingText {

	
	
	private final String FONTNAME = "Tw Cen MT";
	private final int FONTSIZE = 11;
	private final int STYLE = Font.BOLD;
	private final Color FOREGROUND = Colors.FOREGROUND;
	private final Color BACKGROUND = Colors.GRAY;
	private final int FLOATDISTANCE = 15;
	private final float FLOATDURATION = 2;
	public Cord cord;
	public String content;

	private Point drawPoint;
	private float floatTime;
	private boolean stationary;

	public FloatingText(Cord cord, String content, float floatTime) {
		this.cord = cord;
		this.content = content;
		this.floatTime = floatTime;

		stationary = false;
	}

	public FloatingText(Cord cord, String content) {
		this.cord = cord;
		this.content = content;

		stationary = true;
	}

	public void draw(Graphics2D g2/*, float time*/) {

		//floatTime += time;
		
		Polygon hex = CeHexGrid.hexMap.get(cord.xCord).get(cord.yCord);

		int hexCenterX = hex.getBounds().x + hex.getBounds().width / 2;

		FontMetrics fm = g2.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(content, g2);

		//int drawX = hexCenterX - (int) (rect.getWidth() / 2);
		
		int drawX = hexCenterX - (int) (hex.getBounds().width / 2);
		int drawY;
		
		drawY = hex.getBounds().y + hex.getBounds().height / 5;
		
		/*if(stationary)
			drawY = hex.getBounds().y + hex.getBounds().height / 5;
		else 
			drawY = hex.getBounds().y + hex.getBounds().height / 5 - (int) (FLOATDISTANCE * floatTime / FLOATDURATION);*/

		g2.setColor(BACKGROUND);
		//g2.fillRect(drawX, drawY - fm.getAscent(), (int) rect.getWidth(), (int) rect.getHeight());

		g2.setColor(FOREGROUND);
		g2.setFont(new Font(FONTNAME, STYLE, FONTSIZE)); 
		//g2.drawString(content, drawX, drawY);
		
		for (String line : content.split("\n"))
	        g2.drawString(line, drawX, drawY += g2.getFontMetrics().getHeight());
		
	}

	 
	
}
