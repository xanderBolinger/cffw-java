package CeHexGrid;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Timer;

import CeHexGrid.Chit.Facing;
import Conflict.GameWindow;
import CorditeExpansion.ActionOrder;
import CorditeExpansion.CeAction;
import CorditeExpansion.CeClickEvents;
import CorditeExpansion.Cord;
import CorditeExpansion.CorditeExpansionGame;
import CorditeExpansion.CorditeExpansionWindow;
import CorditeExpansion.FloatingText;
import CorditeExpansion.FloatingTextManager;
import CorditeExpansion.MoveAction;
import CorditeExpansion.TurnAction;
import CorditeExpansion.CeAction.ActionType;
import CorditeExpansionStatBlock.StatBlock;
import Trooper.Trooper;
import UtilityClasses.Keyboard;

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

public class CeHexGrid extends JPanel {
	private static final Color FILL_COLOR = Color.BLUE;
	private static final Color BORDER_COLOR = Colors.BRIGHT_RED;
	public static final Stroke STROKE = new BasicStroke(1.5f);
	private ArrayList<Polygon> shapeList = new ArrayList<>();
	public static ArrayList<ArrayList<Polygon>> hexMap = new ArrayList<>();
	
	//public ArrayList<DeployedUnit> selectedUnits = new ArrayList<>();
	//public DeployedUnit selectedUnit = null;
	public int selectedUnitIndex = 0;
	//public ArrayList<DeployedUnit> deployedUnits = new ArrayList<>();

	Point pressedCursorPoint;
	Point currentCursorPoint;
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
	
	public CeHexGrid(int hexRows, int hexCols) {
		
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

		
		//chits.add(new Chit());
		//addTemporaryChits();
		
		new Timer(20, new TimerListener()).start();
	
	}


	
	public void mousePressed(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1) {
	        return;
		}
		dragging = false;
		currentCursorPoint = null;
		pressedCursorPoint = e.getPoint();

		int[] points = getHexFromPoint(e.getPoint());
		

		if(points == null) {
			Chit.unselectChit();
			return;			
		}
		
		//System.out.println("Mouse Pressed");
		if(Chit.isAChitSelected()) {
			Chit.moveSelectedChit(points[0], points[1]);
		}
		
		Chit.unselectChit();
		
		
		
		// Check for click on chit
		
		
		//System.out.println("Pressed Cursor Hex, X: "+points[0]+", Y:"+points[1]);
	}

	public void mouseDragged(MouseEvent e) {
		dragging = true;
		currentCursorPoint = e.getPoint();
		//System.out.println("dragging");
		

			
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
		//System.out.println("Mouse Released");
		
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
		
		int[] points = getHexFromPoint(e.getPoint());
		
		if(points == null) {
			return;			
		}
		
		if(CorditeExpansionGame.selectedTrooper != null && Keyboard.isKeyPressed(KeyEvent.VK_CONTROL) && CorditeExpansionGame.selectedTrooper.ceStatBlock.aiming) {
			checkAimClick(e.getPoint());
			return;
		} else if(CorditeExpansionGame.selectedTrooper != null && Keyboard.isKeyPressed(KeyEvent.VK_CONTROL) && !CorditeExpansionGame.selectedTrooper.ceStatBlock.aiming) {
			checkFireClick(e.getPoint());
			return;
		}
		
		if(CorditeExpansionGame.selectedTrooper != null && legalMoveClick(points[0], points[1])) {
			CeClickEvents.addMoveAction(points[0], points[1]);
		}
		
		selectTrooper(e.getPoint());
		
	}
	
	public void checkFireClick(Point point) {
		
	}
	
	public void checkAimClick(Point point) {
		
		//System.out.println("Chits size: "+getChits().size());
		for(int i = 0; i < getChits().size(); i++) {
			
			Chit chit = getChits().get(i);
			Rectangle imageBounds = new Rectangle(chit.xPoint, chit.yPoint, chit.getWidth(), chit.getHeight());
			
			if (imageBounds.contains(point)) {
				//System.out.println("pass set target");
				CeClickEvents.setAimTarget(CorditeExpansionGame.actionOrder.get(i));
				return;
			}  
			
		}
		
		if(getHexFromPoint(point) != null) {
			//System.out.println("set hex");
			int[] cords = getHexFromPoint(point);
			CeClickEvents.addAimHex(new Cord(cords[0], cords[1]));
		} 
	}
	
	
	public boolean legalMoveClick(int x, int y) {
		
		int xCord = CorditeExpansionGame.selectedTrooper.ceStatBlock.chit.xCord;
		int yCord = CorditeExpansionGame.selectedTrooper.ceStatBlock.chit.yCord;
		
		if(CorditeExpansionGame.selectedTrooper.ceStatBlock.getLastMoveAction() != null) {
			xCord = CorditeExpansionGame.selectedTrooper.ceStatBlock.getLastMoveAction().lastCord().xCord;
			yCord = CorditeExpansionGame.selectedTrooper.ceStatBlock.getLastMoveAction().lastCord().yCord;
		}
		
		if(GameWindow.hexDif(x, y, xCord, yCord) > 1 || GameWindow.hexDif(x, y, xCord, yCord) == 0)
			return false; 
		else 
			return true; 
		
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
	
	public void selectTrooper(Point point) {
				
		for(int i = 0; i < getChits().size(); i++) {
			
			Chit chit = getChits().get(i);
			Rectangle imageBounds = new Rectangle(chit.xPoint, chit.yPoint, chit.getWidth(), chit.getHeight());
			if (imageBounds.contains(point) &&
					CorditeExpansionGame.selectedTrooper == CorditeExpansionGame.actionOrder.get(i)) {
				CorditeExpansionGame.selectedTrooper = null;
				CorditeExpansionWindow.trooperList.clearSelection();
				CorditeExpansionWindow.refreshCeLists();
				return; 
			} else if(imageBounds.contains(point)) {
				CorditeExpansionGame.selectedTrooper = CorditeExpansionGame.actionOrder.get(i);
				CorditeExpansionWindow.trooperList.setSelectedIndex(i);
				CorditeExpansionWindow.refreshCeLists();
				return;
			}
			
		}

	}
	
	public void checkChitClick(Point point) {
		for(Chit chit : getChits()) {
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

				/*drawnStrings.add(new DrawnString(i + ":" + j,
						new Point((int) (cordX + j * ((3 * s) / 2)), (int) (cordY + (j % 2) * a + 2 * i * a))));*/
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
		
		for(int i = 0; i < getChits().size(); i++) {
			
			Chit chit = getChits().get(i);
			Polygon hex = hexMap.get(chit.xCord).get(chit.yCord);
			
			if(CorditeExpansionGame.selectedTrooper != null && 
					CorditeExpansionGame.actionOrder.get(i).compareTo(CorditeExpansionGame.selectedTrooper)) {
				shadeHex(g2, hex, Colors.ORANGE);
			}
			
			chit.drawChit(zoom, g2, hex);
			
			CeAction action = CorditeExpansionGame.actionOrder.getOrder().get(i).ceStatBlock.getTurnAction();
			
			if(action != null && action.getActionType() == ActionType.TURN) {
				
				TurnAction turnAction = (TurnAction) action; 
				
				drawChevron(g2, hex, turnAction.getTargetFacing());
				
			}
			
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
	
	public void translateSelectedChit() {
		if (pressedCursorPoint != null && currentCursorPoint != null && dragging && Chit.isAChitSelected()) {
			Chit.translateChit(currentCursorPoint.x - pressedCursorPoint.x,
					currentCursorPoint.y - pressedCursorPoint.y);
		}
	}

	public void drawChitShadow(Graphics2D g2) {
		if(currentCursorPoint == null || !Chit.isAChitSelected())
			return;
		
		setOpacity(0.5f, g2);
		
		int[] points = getHexFromPoint(currentCursorPoint);
		if(points == null)
			return;
		
		Chit.drawShadow(zoom, g2, hexMap.get(points[0]).get(points[1]));
		
		setOpacity(1f, g2);

	}
	
	public void setOpacity(float alpha, Graphics2D g2) {
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		g2.setComposite(ac);
	}
	
	public ArrayList<Chit> getChits() {
		
		ArrayList<Chit> chits = new ArrayList<>();
		
		
		for(Trooper trooper : CorditeExpansionGame.actionOrder.getOrder()) {
			chits.add(trooper.ceStatBlock.chit);
		}
		
		
		return chits; 
		
	}
	
	
	public void drawChevron(Graphics2D g2, Polygon hex, Facing facing) {
		String path = "CeImages/chev.png";

		setOpacity(0.65f, g2);
		
		Chit chevron = new Chit(path, 15, 15);
		chevron.facing = facing;
		int xShift = 0; 
		int yShift = 0;
		
	
		
		if(facing == Facing.A) {
			yShift -= hex.getBounds().height/4;
		} else if(facing == Facing.AB) {
			xShift += hex.getBounds().width/10;
			yShift -= hex.getBounds().height/3;
		} else if(facing == Facing.B) {
			xShift += hex.getBounds().width/8;
			yShift -= hex.getBounds().height/4;
		} else if(facing == Facing.BC) {
			xShift += hex.getBounds().width/7;
		} else if(facing == Facing.C) {
			xShift += hex.getBounds().width/9;
			yShift += hex.getBounds().height/10;
		} else if(facing == Facing.CD) {
			xShift += hex.getBounds().width/9;
			yShift += hex.getBounds().height/10;
		} 
		
		else if(facing == Facing.D) {
			yShift += hex.getBounds().height/4;
		} else if(facing == Facing.DE) {
			xShift -= hex.getBounds().width/6;
			yShift += hex.getBounds().height/8;
		} else if(facing == Facing.E) {
			xShift -= hex.getBounds().width/4;
			yShift += hex.getBounds().height/9;
		} else if(facing == Facing.EF) {
			xShift -= hex.getBounds().width/5;
		} else if(facing == Facing.F) {
			xShift -= hex.getBounds().width/3;
			yShift -= hex.getBounds().height/4;
		} else if(facing == Facing.FA) {
			xShift -= hex.getBounds().width/5;
			yShift -= hex.getBounds().height/5;
		}
		
		chevron.drawChit(zoom, g2, hex, xShift, yShift);
		
		setOpacity(1f, g2);
		
	}
	
	public void shadeHex(Graphics2D g2, Polygon hex, Color color) {
		setOpacity(0.33f, g2);
		g2.setPaint(color);
		Area area = new Area(hex);
		g2.fill(area);
		setOpacity(1f, g2);
	}
	
	public void shadeHexes(Graphics2D g2) {
		
		if(CorditeExpansionGame.selectedTrooper != null) {
			
			StatBlock stat = CorditeExpansionGame.selectedTrooper.ceStatBlock;
			
			if(stat.acting() && stat.getAction().getActionType() == ActionType.MOVE) {
				shadeMoveAction(g2, (MoveAction) stat.getAction());
			}
			
			for(CeAction ceAction : stat.getCoac()) {
				if(ceAction.getActionType() != ActionType.MOVE)
					continue; 
				
				shadeMoveAction(g2, (MoveAction) ceAction);
				
			}
			
		}
		
	}
	
	public void shadeMoveAction(Graphics2D g2, MoveAction moveAction) {
		
		for(Cord cord : moveAction.cords) {
			
			Polygon hex = hexMap.get(cord.xCord).get(cord.yCord);
			
			shadeHex(g2, hex, Colors.GREEN);
			
			if(cord.facing != null) {
				drawChevron(g2, hex, cord.facing);
			}
			
		}
		
	}
	
	
	public boolean zoomChanged() {
		return zoom != oldZoom ? true : false;
	}
	
	public void drawFloatingText(Graphics2D g2) {
		for(FloatingText text : FloatingTextManager.getFloatingText()) {
			text.draw(g2);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//System.out.println("Paint!");
		
		//System.out.println("shapeList: "+shapeList.size()+", Deployed Units: "+deployedUnits.size());
		
		Graphics2D g2 = (Graphics2D) g;
		setRendering(g2);
		
		/*shadeHex(g2, hexMap.get(0).get(1));
		shadeHex(g2, hexMap.get(0).get(2));
		shadeHex(g2, hexMap.get(0).get(3));
		shadeHex(g2, hexMap.get(0).get(4));*/
		
		/*shadeHex(g2, hexMap.get(1).get(0));
		shadeHex(g2, hexMap.get(2).get(0));
		shadeHex(g2, hexMap.get(3).get(0));
		shadeHex(g2, hexMap.get(4).get(0));
		shadeHex(g2, hexMap.get(5).get(0));
		
		shadeHex(g2, hexMap.get(6).get(0));
		shadeHex(g2, hexMap.get(7).get(0));
		shadeHex(g2, hexMap.get(8).get(0));
		shadeHex(g2, hexMap.get(9).get(0));
		shadeHex(g2, hexMap.get(10).get(0));
		shadeHex(g2, hexMap.get(11).get(0));
		shadeHex(g2, hexMap.get(12).get(0));*/
		
		/*drawChevron(g2, Facing.A, hexMap.get(0).get(1));
		drawChevron(g2, Facing.D, hexMap.get(0).get(2));
		drawChevron(g2, Facing.AB, hexMap.get(0).get(3));
		drawChevron(g2, Facing.B, hexMap.get(0).get(4));*/
		
		/*drawChevron(g2, hexMap.get(1).get(0), Facing.A);
		drawChevron(g2, hexMap.get(2).get(0), Facing.AB);
		drawChevron(g2, hexMap.get(3).get(0), Facing.B);
		drawChevron(g2, hexMap.get(4).get(0), Facing.BC);
		drawChevron(g2, hexMap.get(5).get(0), Facing.C);
		
		drawChevron(g2, hexMap.get(6).get(0), Facing.CD);
		drawChevron(g2, hexMap.get(7).get(0), Facing.D);
		drawChevron(g2, hexMap.get(8).get(0), Facing.DE);
		drawChevron(g2, hexMap.get(9).get(0), Facing.E);
		drawChevron(g2, hexMap.get(10).get(0), Facing.EF);
		drawChevron(g2, hexMap.get(11).get(0), Facing.F);
		drawChevron(g2, hexMap.get(12).get(0), Facing.FA);*/
		
		
		
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
		
		shadeHexes(g2);
		
		drawHexMap(g2);

		translateSelectedChit();

		drawChitShadow(g2);

		drawChits(g2);		
		
		drawFloatingText(g2);
		
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
