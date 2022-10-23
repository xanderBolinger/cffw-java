package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Timer;

/*import Conflict.AttackHexWindow;
import Conflict.GameWindow;
import Conflict.OpenUnit;
import Conflict.SelectedUnitsWindow;
import HexGrid.HexGrid;
import HexGrid.MovementSpeedDialogBox;
import HexGrid.HexGrid.DeployedUnit;
import HexGrid.HexGrid.DrawnString;
import HexGrid.HexGrid.Panel.PanelPopUp;
import HexGrid.HexGrid.Panel.TimerListener;
import Hexes.Building;
import Hexes.Feature;
import Hexes.Hex;
import Hexes.HexWindow;
import Unit.Unit;
import UtilityClasses.HexGridUtility;
import UtilityClasses.Keyboard;
import UtilityClasses.HexGridUtility.ShownType;
import UtilityClasses.SwingUtility.FPSCounter;*/

public class HexGrid extends JPanel {
	private static final Color FILL_COLOR = Color.BLUE;
	private static final Color BORDER_COLOR = Colors.BRIGHT_RED;
	public static final Stroke STROKE = new BasicStroke(1.5f);
	private ArrayList<Polygon> shapeList = new ArrayList<>();
	private ArrayList<ArrayList<Polygon>> hexMap = new ArrayList<>();
	private ArrayList<DrawnString> drawnStrings = new ArrayList<>();
	public static ArrayList<Chit> chits = new ArrayList<>();
	
	class DrawnString {
		public String text;
		public Point position;

		// Insert constructor and getter methods here
		public DrawnString(String text, Point position) {
			this.text = text;
			this.position = position;
		}

	}
	//public ArrayList<DeployedUnit> selectedUnits = new ArrayList<>();
	//public DeployedUnit selectedUnit = null;
	public int selectedUnitIndex = 0;
	//public ArrayList<DeployedUnit> deployedUnits = new ArrayList<>();

	Point pressedCursorPoint;
	Point currentCursorPoint;
	Point draggedCursorPoint;
	boolean dragging = false;
	double s = 17;
	//double s = 30;
	int cordX = 0;
	int cordY = 0;
	int rows;
	int columns;
	// Point topCorner;
	Image originalImage;
	Image backgroundImage;

	int backgroundImageX = 0;
	int backgroundImageY = 0;
	int backgroundImageWidth;
	int backgroundImageHeight;
	double oldZoom = 1.0;
	double zoom = 1.0;
	
	
	public HexGrid(int hexRows, int hexCols) {
		
		this.rows = hexRows+1;
		this.columns = hexCols;
		
		
		int hexes = hexRows * hexCols; 
		
		if(hexes < (10*10))
			s = 30;
		else if(hexes < (20*20))
			s = 25;
		else 
			s = 17;
		
		//System.out.println("S: "+s);
		// set a preferred size for the custom panel.
		setPreferredSize(new Dimension(420, 420));
	
		makeHexes(rows, columns);
	
		// originalImage = ImageIO.read(new File("Map Images/CondorValley.png"));
		//originalImage = ImageIO.read(new File("Map Images/refuge.png"));
		//originalImage = ImageIO.read(new File("Map Images/"+GameWindow.gameWindow.game.mapImageFileName));
		/*backgroundImageWidth = (int) (s * (columns / 2 * 3));
		backgroundImageHeight = (int) (s * 1.7175 * rows);
		backgroundImageHeight -= backgroundImageHeight / rows / 2;
		backgroundImage = originalImage.getScaledInstance(backgroundImageWidth, backgroundImageHeight,
				Image.SCALE_SMOOTH);*/

		
		chits.add(new Chit());
		//addTemporaryChits();
		
		new Timer(20, new TimerListener()).start();
	
	}

	public void addTemporaryChits() {
		for(int i = 0; i < 32; i++) {
			Chit chit = new Chit();
			chit.xCord = i; 
			chit.yCord = i;
			chits.add(chit);
			chit = new Chit();
			chit.xCord = i; 
			chit.yCord = 1;
			chits.add(chit);
		}
		
	}
	

	
	public void mousePressed(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1) {
	        return;
		}
		dragging = false;
		currentCursorPoint = null;
		pressedCursorPoint = e.getPoint();
		draggedCursorPoint = pressedCursorPoint;
		int[] points = getHexFromPoint(e.getPoint());
		if(points == null)
			return;
		
		System.out.println("Mouse Pressed");
		if(Chit.isAChitSelected()) {
			int[] newCords = getHexFromPoint(e.getPoint());
			Chit.moveSelectedChit(newCords[0], newCords[1]);
		}
		
		Chit.unselectChit();
		// Check for click on chit
		
		
		//System.out.println("Pressed Cursor Hex, X: "+points[0]+", Y:"+points[1]);
	}

	public void mouseDragged(MouseEvent e) {
		dragging = true;
		currentCursorPoint = e.getPoint();
		//System.out.println("dragging");
		
		if(Chit.isAChitSelected() && draggedCursorPoint != null) {
			Chit.translateChit(currentCursorPoint.x - draggedCursorPoint.x, 
					currentCursorPoint.y - draggedCursorPoint.y);
		}

		draggedCursorPoint = currentCursorPoint;
			
		// System.out.println("Current Cursor Point, X: "+currentCursorPoint.x+", Y:
		// "+currentCursorPoint.y);
		//repaint();
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1) {
		        return;
		}
		dragging = false;
		int[] points = getHexFromPoint(e.getPoint());
		if(points == null)
			return;
		checkChitClick(e.getPoint());
		System.out.println("Mouse Released");
		
		//System.out.println("Released Cursor Hex, X: "+points[0]+", Y:"+points[1]);
	}

	public void mouseWheelMoved(double zoom) {
		this.zoom = zoom;
		//repaint();
	}

	// Checks for hovering tooltip and displays message to user
	public void mouseMoved(MouseEvent e) {
		
		//System.out.println("Mouse Moved: ("+e.getPoint().x+", "+e.getPoint().y+")");

	

	}


	// Gets clicked hex
	// If hex contains units, selects first unit
	public void mouseLeftClick(MouseEvent e) {

		
		
			
			
			
		

	}

	public void mouseRightClick(MouseEvent e) {
		

	}

	public Polygon scaleHex(Polygon hex, AffineTransform at) {

		// Polygon mesh
		// AffineTransform at

		int[] x = hex.xpoints;
		int[] y = hex.ypoints;
		int[] rx = new int[x.length];
		int[] ry = new int[y.length];

		for (int i = 0; i < hex.npoints; i++) {
			Point p = new Point(x[i], y[i]);
			at.transform(p, p);
			rx[i] = p.x;
			ry[i] = p.y;
		}

		return new Polygon(rx, ry, hex.npoints);

	}
	
	public void checkChitClick(Point point) {
		for(Chit chit : HexGrid.chits) {
			Rectangle imageBounds = new Rectangle(chit.xPoint, chit.yPoint, chit.getWidth(), chit.getHeight());
			if (imageBounds.contains(point)){
			    System.out.println("Clicked Chit, chit.xPoint: "+chit.xPoint+", clicked x point: "+point.x);
				
				Chit.setSelectedChit(chit, point.x-chit.xPoint, point.y-chit.yPoint);
				return; 
			}
		}

	}
	
	public Polygon newHex(int X, int Y, double s) {

		double a = Math.sqrt(3) * (s / 2);

		Polygon p = new Polygon();

		p.addPoint((int) (X + (s / 2)), (int) (Y - a));
		
		
		
		p.addPoint((int) (X + s), (int) (Y));
		p.addPoint((int) (X + (s / 2)), (int) (Y + a));
		p.addPoint((int) (X - (s / 2)), (int) (Y + a));
		p.addPoint((int) (X - s), (int) (Y));
		p.addPoint((int) (X - (s / 2)), (int) (Y - a));

		return p;

		/*
		 * point 1: (X + (s/2), Y - a) point 2: (X + s, Y) point 3: (X + (s/2), Y + a)
		 * point 4: (X - (s/2), Y + a) point 5: (X - s, Y) point 6: (X - (s/2), Y - a)
		 */

	}
	
	// Scale
	// Deletes all hexes
	// Remakes all hexes based on scaled s value
	// translates all shapes based on the distance between 0,0 and the top left
	// corner of the first shape
	public void makeHexes(int rows, int columns) {

		//System.out.println("Make Hexes");
		
		if (shapeList.size() > 0)
			shapeList.clear();

		hexMap.clear();

		double a = Math.sqrt(3) * (s / 2);

		for (int i = 1; i <= rows; i++) {

			ArrayList<Polygon> row = new ArrayList<>();

			for (int j = 1; j <= columns; j++) {

				Polygon hex = newHex((int) (cordX + j * ((3 * s) / 2)), (int) (cordY + (j % 2) * a + 2 * i * a), s);

				row.add(hex);

				shapeList.add(hex);

				drawnStrings.add(new DrawnString(i + ":" + j,
						new Point((int) (cordX + j * ((3 * s) / 2)), (int) (cordY + (j % 2) * a + 2 * i * a))));
			}

			hexMap.add(row);

		}

	}
	
	public int[] getHexFromPoint(int x, int y) {
		
		//System.out.println("Get hex from point");
		
		for (int i = 0; i < hexMap.size(); i++) {

			for (int j = 0; j < hexMap.get(0).size(); j++) {

				Polygon hex = hexMap.get(i).get(j);

				if (hex.contains(new Point(x, y))) {
					int[] arr={i, j};
					return arr; 
				}
			}
		}
		
		return null; 
	}
	
	public int[] getHexFromPoint(Point point) {
		
		//System.out.println("Get hex from point");
		
		for (int i = 0; i < hexMap.size(); i++) {

			for (int j = 0; j < hexMap.get(0).size(); j++) {

				Polygon hex = hexMap.get(i).get(j);

				if (hex.contains(point)) {
					int[] arr={i, j};
					return arr; 
				}
			}
		}
		
		return null; 
	}
	
	

	public void setRendering(Graphics2D g2) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		g2.setStroke(STROKE);
		g2.setClip(0,0,screenSize.width,screenSize.height);
		/*g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		RenderingHints rh4 = new RenderingHints(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);*/
		//g2.setRenderingHints(rh4);
		
		System.setProperty("sun.java2d.opengl", "true");
		//System.setProperty("sun.java2d.uiScale", "1.0");
		RenderingHints rh3 = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g2.setRenderingHints(rh3);
		RenderingHints rh2 = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g2.setRenderingHints(rh2);
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		g2.setRenderingHints(rh);
	}
	
	public void drawChits(Graphics2D g2) {
		
		for(Chit chit : chits) {
			chit.drawChit(zoom, g2, hexMap.get(chit.xCord).get(chit.yCord));
		}
		
	}
	
	public void drawHexMap(Graphics2D g2) {
		for (Polygon shape : shapeList) {
	
			g2.setColor(BORDER_COLOR);
			g2.setStroke(STROKE);
	
			if (pressedCursorPoint != null && currentCursorPoint != null && dragging && !Chit.isAChitSelected()) {
				//System.out.println("dragging move hexes.");
				shape.translate(currentCursorPoint.x - pressedCursorPoint.x,
						currentCursorPoint.y - pressedCursorPoint.y);
				g2.draw(shape);
				
			} else if (zoom != oldZoom) {
	
				int baseX = shapeList.get(0).getBounds().x;
				int baseY = shapeList.get(0).getBounds().y;
	
				makeHexes(rows, columns);
	
				for (Polygon newShape : shapeList) {
	
					newShape.translate(baseX - cordX, baseY - cordY);
					g2.draw(newShape);
	
				}
	
				break;
	
			} else {
				g2.draw(shape);
			}
			
	
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//System.out.println("Paint!");
		
		//System.out.println("shapeList: "+shapeList.size()+", Deployed Units: "+deployedUnits.size());
		
		Graphics2D g2 = (Graphics2D) g;
		setRendering(g2);

		
		
		if (zoom != oldZoom) {
			s = Math.round(s / oldZoom * zoom);
			// System.out.println("S: "+s+", Zoom: "+zoom);
			//backgroundImageWidth = (int) Math.round(s * (columns / 2 * 3));
			//backgroundImageHeight = (int) Math.round(s * 1.7175 * rows);
			//backgroundImageHeight -= backgroundImageHeight / rows / 2;
		}

		if (pressedCursorPoint != null && currentCursorPoint != null && dragging && !Keyboard.isKeyPressed(KeyEvent.VK_CONTROL)) {

			//backgroundImageX = shapeList.get(0).getBounds().x + 11;
			//backgroundImageY = shapeList.get(0).getBounds().y;
			//g2.drawImage(backgroundImage, backgroundImageX, backgroundImageY, null);

		} else if (zoom != oldZoom) {

			/*backgroundImage = originalImage.getScaledInstance(backgroundImageWidth, backgroundImageHeight, 1);
			backgroundImageX = shapeList.get(0).getBounds().x + 11;
			backgroundImageY = shapeList.get(0).getBounds().y;*/

			//g2.drawImage(backgroundImage, backgroundImageX, backgroundImageY, null);

		} else {
			//makeHexes(rows, columns);
			
			//backgroundImageX = shapeList.get(0).getBounds().x + 11;
			//backgroundImageY = shapeList.get(0).getBounds().y;
			//g2.drawImage(backgroundImage, backgroundImageX, backgroundImageY, null);
			
			
		}
		
		drawHexMap(g2);

		drawChits(g2);

		
		
		pressedCursorPoint = currentCursorPoint;
		oldZoom = zoom;
		
	}
	
	private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { 
        	
        	
        	repaint();
        	
        	//fpscnt.frame();
        	//System.out.println("FPS: "+fpscnt.get());	
        }
    }


}
