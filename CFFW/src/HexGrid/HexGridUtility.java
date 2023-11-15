package HexGrid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingWorker;

import Actions.Spot;
import Conflict.GameWindow;
import CorditeExpansion.Cord;
import HexGrid.HexDirectionUtility.HexDirection;
import HexGrid.HexGrid.DeployedUnit;
import Trooper.Trooper;
import Unit.Unit;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.awt.ShapeReader;
import org.locationtech.jts.simplify.TopologyPreservingSimplifier;

public class HexGridUtility {

	public enum ShownType {
		BLUFOR, OPFOR, BOTH
	}
	
	private static boolean movingUnits;
	
	public static void MoveUnits(DeployedUnit selectedUnit, ArrayList<DeployedUnit> selectedUnits, int i, int j) {
		if(movingUnits)
			return;
		
		SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

			@Override
			protected Void doInBackground() throws Exception {
				
				movingUnits = true;
				
				for (DeployedUnit dp : selectedUnits) {
					dp.unit.move(GameWindow.gameWindow, i, j, null);
				}

				if (selectedUnit != null)
					selectedUnit.unit.move(GameWindow.gameWindow, i, j, null);
				
				return null;
			}

			@Override
			protected void done() {
				GameWindow.gameWindow.hexGrid.refreshDeployedUnits();
				selectedUnits.clear();
				movingUnits = false;
				GameWindow.gameWindow.conflictLog.addQueuedText();
			}

		};

		worker.execute();
		
	}
	
	public static int distance(Cord start, Cord end) {
		return GameWindow.hexDif(start.xCord, start.yCord, end.xCord, end.yCord);
	}

	public static void drawUnitOutline(Graphics2D g2, Polygon hex, boolean opfor, int bluforUnitWidth, int bluforUnitHeight, 
			int opforUnitWidth, int opforUnitHeight, boolean yellow) {
		int hexCenterX = hex.getBounds().x + hex.getBounds().width / 2;
		int hexCenterY = hex.getBounds().y + hex.getBounds().height / 2;

		if (opfor) {
			// System.out.println("pass draw 1");
			Polygon diamond = new Polygon();

			// Top point
			diamond.addPoint(hexCenterX, (int) (hexCenterY - opforUnitHeight / 2 - 3));
			// Left point
			diamond.addPoint((int) (hexCenterX - opforUnitWidth / 2 - 3), hexCenterY);
			// Bottom point
			diamond.addPoint(hexCenterX, (int) (hexCenterY + opforUnitHeight / 2 + 3));
			// Right point
			diamond.addPoint((int) (hexCenterX + opforUnitWidth / 2 + 3), hexCenterY);

			g2.setColor(yellow ? Color.YELLOW : Color.MAGENTA);
			g2.setStroke(new BasicStroke(2f));
			g2.draw(diamond);
			// System.out.println("pass draw 2");
		} else {

			g2.setColor(yellow ? Color.YELLOW : Color.MAGENTA);
			g2.setStroke(new BasicStroke(3f));

			Polygon outline = new Polygon();

			// Top Left point
			outline.addPoint(hexCenterX - bluforUnitWidth / 2,
					hexCenterY + bluforUnitHeight / 2);

			// Bottom left point
			outline.addPoint(hexCenterX - bluforUnitWidth / 2,
					hexCenterY - bluforUnitHeight / 2);

			// Bottom Right point
			outline.addPoint(hexCenterX + bluforUnitWidth / 2,
					hexCenterY - bluforUnitHeight / 2);

			// Top Right Point
			outline.addPoint(hexCenterX + bluforUnitWidth / 2,
					hexCenterY + bluforUnitHeight / 2);

			g2.draw(outline);
			g2.setStroke(new BasicStroke(2f));
		}
	}
	
	public static boolean canShow(ShownType shownType, Unit targetUnit) {

		if (shownTypeEqualsSide(shownType, targetUnit.side))
			return true;

		// If the unit is within LOS of a shown unit
		if (canShowLos(shownType, targetUnit))
			return true;

		return false;
	}

	public static boolean shownTypeEqualsSide(ShownType shownType, String side) {

		if (shownType == ShownType.BOTH) {
			return true;
		} else if (side.equals("BLUFOR") && shownType == ShownType.BLUFOR) {
			return true;
		} else if (side.equals("OPFOR") && shownType == ShownType.OPFOR) {
			return true;
		}

		return false;
	}

	public static ShownType getShownTypeFromSide(String side) {
		if (side.equals("BLUFOR"))
			return ShownType.BLUFOR;
		else
			return ShownType.OPFOR;
	}

	public static boolean spottedSomeTrooper(Unit spotterUnit, Unit targetUnit) {

		for (Trooper trooper : spotterUnit.individuals) {

			for (Spot spot : trooper.spotted) {

				for (Trooper spottedTrooper : spot.spottedIndividuals) {

					if (targetUnit.individuals.contains(spottedTrooper))
						return true;

				}

			}

		}

		return false;
	}

	public static boolean canShowLos(ShownType shownType, Unit targetUnit) {

		for (Unit unit : GameWindow.gameWindow.initiativeOrder) {

			if (unit.side.equals("BLUFOR") && shownType != ShownType.BLUFOR) {
				continue;
			} else if (unit.side.equals("OPFOR") && shownType != ShownType.OPFOR) {
				continue;
			}

			if (unit.lineOfSight.contains(targetUnit) && spottedSomeTrooper(unit, targetUnit))
				return true;

		}

		return false;
	}

	public static void paintHexes(Graphics2D g2, ArrayList<ArrayList<Polygon>> hexMap, int screenWidth, 
			int screenHeight) {
		
		ExecutorService es = Executors.newFixedThreadPool(16);
		
		for(var row : hexMap) {
			es.submit(() -> {
				for(var hex : row) {
					if(!HexGrid.OnScreen(hex, screenWidth, screenHeight))
						continue;
					var xpoint = hex.xpoints[0];
					var ypoint = hex.ypoints[0];
					if(xpoint > screenWidth && ypoint > screenHeight)
						break;
					g2.draw(hex);
				}
			});
		}
		
		es.shutdown();
		try {
		  es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
		 e.printStackTrace();
		}
	}
	
	
	/*static Polygon getHexMapShape(ArrayList<ArrayList<Polygon>> hexes) {
		
		Coordinate[] cords = mergePolygons(hexes);
		var polygon = new Polygon();
		for(var cord : cords) {
			polygon.addPoint((int)cord.getX(), (int)cord.getY());
		}
		return polygon;
	}
	
	
	static Coordinate[] mergePolygons(ArrayList<ArrayList<Polygon>> polygons) {
        Path2D mergedShape = new Path2D.Double();

        for(var row : polygons) {
        	for (var polygon : row) {
            	var firstXPoint = polygon.xpoints[0];
            	var firstYPoint = polygon.ypoints[0];
            	
                mergedShape.moveTo(firstXPoint, firstYPoint);

                for (int i = 1; i < 6; i++) {
                    var xPoint = polygon.xpoints[i];
                    var yPoint = polygon.ypoints[i];
                    mergedShape.lineTo(xPoint, yPoint);
                }

                mergedShape.closePath();
            }
        }

        return simplifyPath(mergedShape) ;
    }
	private static final GeometryFactory geometryFactory = new GeometryFactory();
	private static ShapeReader reader = new ShapeReader(geometryFactory);
	static Coordinate[] simplifyPath(Path2D path) {
		Geometry geometry  = reader.read(path.getPathIterator(null)); 

        TopologyPreservingSimplifier simplifier = new TopologyPreservingSimplifier(geometry);
        Geometry simplifiedGeometry = simplifier.getResultGeometry();

        return simplifiedGeometry.getCoordinates();
    }

	public static Polygon convertPathToPolygon(Path2D path) {
        //Path2D.Iterator iterator = new Path2D.Double(path, null).getPathIterator(null);
        PathIterator iterator = path.getPathIterator(null);
        double[] coords = new double[6];

        Polygon polygon = new Polygon();
        
        while (!iterator.isDone()) {
            int type = iterator.currentSegment(coords);
            
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    polygon.addPoint((int) coords[0], (int) coords[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    polygon.addPoint((int) coords[0], (int) coords[1]);
                    break;
                // Handle other segment types if needed

            }

            iterator.next();
        }

        return polygon;
    }*/
    

}
