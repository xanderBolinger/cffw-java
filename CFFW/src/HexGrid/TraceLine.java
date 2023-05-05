package HexGrid;

import java.awt.Polygon;
import java.util.ArrayList;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import HexGrid.HexGrid.Panel;

public class TraceLine {

	public static ArrayList<Cord> GetHexes(Cord cord1, Cord cord2, Panel hexGrid) {

		Cord hex1 = hexGrid.getCenterFromCoordinates(cord1.xCord, cord1.yCord);

		Cord hex2 = hexGrid.getCenterFromCoordinates(cord2.xCord, cord2.yCord);

		ArrayList<Cord> points = getPositions(hex1, hex2);

		ArrayList<Cord> hexes = new ArrayList<>();

		for (Cord point : points) {
			int[] cords = hexGrid.getHexFromPoint(point.xCord, point.yCord);
			if(cords == null || Cord.containsHex(cords, hexes))
				continue;
			hexes.add(new Cord(cords[0], cords[1]));
		}

		for (Cord hex : hexes) {
			System.out.println("Hex: " + hex.xCord + ", " + hex.yCord);
		}

		return hexes;
	}

	
	
	public static ArrayList<Cord> getPositions(Cord cord1, Cord cord2) {
		int x1 = cord1.xCord, y1 = cord1.yCord, x2 = cord2.xCord, y2 = cord2.yCord;
		return bresenham(y1, x1, y2, x2);
	}

	static ArrayList<Cord> bresenham(int x0, int y0, int x1, int y1) {
		ArrayList<Cord> line = new ArrayList<Cord>();

		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;

		int err = dx - dy;
		int e2;

		while (true) {
			Cord cord = new Cord(y0, x0);
			//System.out.println("Cord: "+cord.xCord+", "+cord.yCord);
			line.add(cord);

			if (x0 == x1 && y0 == y1)
				break;

			e2 = 2 * err;
			if (e2 > -dy) {
				err = err - dy;
				x0 = x0 + sx;
			}

			if (e2 < dx) {
				err = err + dx;
				y0 = y0 + sy;
			}
		}

		return line;
	}

	public static ArrayList<Cord> plotPixel(int x1, int y1, int x2, int y2) {
		//System.out.println("Plot Pixel Start: (" + x1 + ", " + y1 + "), " + "(" + x2 + "," + y2 + ")");
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		int decide = dx > dy ? 0 : 1;
		ArrayList<Cord> cords = new ArrayList<>();

		// pk is initial decision making parameter
		// Note:x1&y1,x2&y2, dx&dy values are interchanged
		// and passed in plotPixel function so
		// it can handle both cases when m>1 & m<1
		int pk = 2 * dy - dx;
		for (int i = 0; i <= dx; i++) {
			cords.add(new Cord(x1, y1));
			//System.out.println("Cord: " + x1 + "," + y1 + "\n");
			// checking either to decrement or increment the
			// value if we have to plot from (0,100) to
			// (100,0)
			if (x1 < x2)
				x1++;
			else
				x1--;
			if (pk < 0) {
				// decision value will decide to plot
				// either x1 or y1 in x's position
				if (decide == 0) {
					pk = pk + 2 * dy;
				} else
					pk = pk + 2 * dy;
			} else {
				if (y1 < y2)
					y1++;
				else
					y1--;
				pk = pk + 2 * dy - 2 * dx;
			}
		}

		return cords;
	}

}
