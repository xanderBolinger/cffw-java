package HexGrid.Waypoint;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class DrawWaypoints {
	final static int SQUARE_SIZE = 20;
	
	public static void drawLine(Graphics2D g, int x1, int y1, int x2, int y2) {
        g.setColor(Color.YELLOW);
        g.drawLine(x1, y1, x2, y2);
    }
	
	public static void drawYellowSquare(Graphics2D g, int x, int y) {
        g.setColor(Color.YELLOW);
        
        y -= SQUARE_SIZE;
        
        g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);

        // Draw the letter "W" in the middle of the square
        g.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 20);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth("W");
        int textHeight = fm.getHeight();
        int centerX = x + (SQUARE_SIZE - textWidth) / 2;
        int centerY = y + (SQUARE_SIZE - textHeight) / 2 + fm.getAscent();
        g.drawString("W", centerX, centerY);
    }
	
}
