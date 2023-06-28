package HexGridIcons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import UtilityClasses.ExcelUtility;

public class GrassHexagonExample extends JPanel {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int HEX_SIZE = 100;
    
    private static final String GRASS_IMAGE_PATH = ExcelUtility.path + "\\HexImages\\Grass1.png";
    private Image grassImage;

    public GrassHexagonExample() {
        try {
            // Load the grass image
            grassImage = ImageIO.read(new File(GRASS_IMAGE_PATH));
            grassImage = grassImage.getScaledInstance(HEX_SIZE*2, HEX_SIZE*2, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a hexagon-shaped polygon
        Polygon hex = createHexagonPolygon();
        
		
        // Draw the grass hexagon
        drawGrassHexagon(g2d, hex);
    }

    private Polygon createHexagonPolygon() {
        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;

        int[] xPoints = new int[6];
        int[] yPoints = new int[6];

        for (int i = 0; i < 6; i++) {
            double angleRad = Math.PI / 3.0 * i;
            int x = centerX + (int) (HEX_SIZE * Math.cos(angleRad));
            int y = centerY + (int) (HEX_SIZE * Math.sin(angleRad));

            xPoints[i] = x;
            yPoints[i] = y;
        }

        return new Polygon(xPoints, yPoints, 6);
    }

    private void drawGrassHexagon(Graphics2D g2d, Polygon hex) {
    	int hexCenterX = hex.getBounds().x + hex.getBounds().width / 2;
		int hexCenterY = hex.getBounds().y + hex.getBounds().height / 2;
        g2d.setColor(Color.RED);
        g2d.fill(hex);

        // Calculate the grass image position within the hexagon
        int grassImageX = hexCenterX - hex.getBounds().width / 2;
        int grassImageY = hexCenterY - hex.getBounds().height / 2 - 10;

        // Draw the grass image on top of the hexagon
        g2d.drawImage(grassImage, grassImageX, grassImageY, null);
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Grass Hexagon Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.getContentPane().add(new GrassHexagonExample());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}