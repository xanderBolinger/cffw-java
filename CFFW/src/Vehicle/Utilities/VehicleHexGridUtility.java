package Vehicle.Utilities;

import java.awt.Graphics2D;
import java.awt.Polygon;

import CeHexGrid.Chit;
import CeHexGrid.Chit.Facing;
import Conflict.GameWindow;
import HexGrid.HexDirectionUtility.HexDirection;

public class VehicleHexGridUtility {

	public static void drawVehicleTurretChevron(Graphics2D g2) {
		if(GameWindow.gameWindow.vehicleCombatWindow == null 
				|| GameWindow.gameWindow.vehicleCombatWindow.getSelectedTurret() == null)
			return;
		
		var turret = GameWindow.gameWindow.vehicleCombatWindow.getSelectedTurret();
		var vic = GameWindow.gameWindow.vehicleCombatWindow.selectedVehicle;
		var vicCord = vic.movementData.location;
		var hex = GameWindow.gameWindow.hexGrid.panel.hexMap.get(vicCord.xCord).get(vicCord.yCord);
		drawTurretChevron(g2, hex, HexDirection.getFacing(VehicleDataUtility.getTurretFacing(turret, vic)));
	}
	
	private static void drawTurretChevron(Graphics2D g2, Polygon hex, Facing facing) {
		var hexGrid = GameWindow.gameWindow.hexGrid;
		String path = "CeImages/chevRed.png";

		hexGrid.panel.setOpacity(0.65f, g2);

		Chit chevron = new Chit(path, 15, 15);
		chevron.facing = facing;
		int xShift = 0;
		int yShift = 0;

		if (facing == Facing.A) {
			yShift -= hex.getBounds().height / 4;
		} else if (facing == Facing.AB) {
			xShift += hex.getBounds().width / 10;
			yShift -= hex.getBounds().height / 3;
		} else if (facing == Facing.B) {
			xShift += hex.getBounds().width / 8;
			yShift -= hex.getBounds().height / 4;
		} else if (facing == Facing.BC) {
			xShift += hex.getBounds().width / 7;
		} else if (facing == Facing.C) {
			xShift += hex.getBounds().width / 9;
			yShift += hex.getBounds().height / 10;
		} else if (facing == Facing.CD) {
			xShift += hex.getBounds().width / 9;
			yShift += hex.getBounds().height / 10;
		}

		else if (facing == Facing.D) {
			yShift += hex.getBounds().height / 4;
		} else if (facing == Facing.DE) {
			xShift -= hex.getBounds().width / 6;
			yShift += hex.getBounds().height / 8;
		} else if (facing == Facing.E) {
			xShift -= hex.getBounds().width / 4;
			yShift += hex.getBounds().height / 9;
		} else if (facing == Facing.EF) {
			xShift -= hex.getBounds().width / 5;
		} else if (facing == Facing.F) {
			xShift -= hex.getBounds().width / 3;
			yShift -= hex.getBounds().height / 4;
		} else if (facing == Facing.FA) {
			xShift -= hex.getBounds().width / 5;
			yShift -= hex.getBounds().height / 5;
		}

		chevron.drawChit(hexGrid.panel.zoom, g2, hex, xShift, yShift);

		hexGrid.panel.setOpacity(1f, g2);
	}
	
}
