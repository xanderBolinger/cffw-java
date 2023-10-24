package HexGrid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import Actions.Spot;
import Conflict.GameWindow;
import CorditeExpansion.Cord;
import HexGrid.HexDirectionUtility.HexDirection;
import Trooper.Trooper;
import Unit.Unit;

public class HexGridUtility {

	public enum ShownType {
		BLUFOR, OPFOR, BOTH
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

	

    

}
