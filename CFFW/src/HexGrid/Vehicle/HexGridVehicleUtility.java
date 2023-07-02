package HexGrid.Vehicle;

import java.util.ArrayList;

import CeHexGrid.Chit;
import Conflict.GameWindow;
import UtilityClasses.ExcelUtility;
import Vehicle.Vehicle;
import Vehicle.Windows.VehicleCombatWindow;

public class HexGridVehicleUtility {
	public static void updateVehicleChits(VehicleCombatWindow cw) {
		
		for(var vic : cw.vehicles) {
			
			var oldChit = getVehicleChit(vic.identifier);

			var blufor = bluforVehicle(vic);
			var chit = new Chit(ExcelUtility.path 
					+ "\\Unit Images\\"+(blufor ? "BLUFOR" : "OPFOR")+"_ARMOR.png", 20, (blufor ? 12 : 20));
			chit.vehicle = true; 
			chit.vicIdentifier = vic.identifier;
			chit.vicCallsign = vic.getVehicleCallsign();
			
			if(oldChit != null) {
				chit.xCord = oldChit.xCord;
				chit.yCord = oldChit.yCord;
				chit.xPoint = oldChit.xPoint;
				chit.yPoint = oldChit.yPoint;
				chit.shiftX = oldChit.shiftX;
				chit.shiftY = oldChit.shiftY;
				chit.facing = oldChit.facing;
				GameWindow.gameWindow.game.chits.remove(oldChit);
			}
			
			GameWindow.gameWindow.game.chits.add(chit);
		}
		
		ArrayList<Chit> removeChits = new ArrayList<Chit>();
		
		for(var chit : GameWindow.gameWindow.game.chits) {
			
			if(!chit.vehicle)
				continue;
			
			boolean found = false; 
			
			for(var vic : cw.vehicles) {
				if(vic.identifier.equals(chit.vicIdentifier))
					found = true;
			}
			
			if(!found)
				removeChits.add(chit);
			
		}
		
		for(var chit : removeChits) {
			GameWindow.gameWindow.game.chits.remove(chit);
		}
		
	}
	
	private static boolean bluforVehicle(Vehicle vic) {
		
		for(var c : GameWindow.gameWindow.companies) {
			if(c.vehicles.contains(vic) && c.getSide().equals("BLUFOR"))
				return true;
		}
		
		return false;
	}
	
	private static Chit getVehicleChit(String idenfitier) {
		for(var chit : GameWindow.gameWindow.game.chits) {
			if(chit.vehicle && chit.vicIdentifier.equals(idenfitier))
				return chit;
		}
		
		return null;
	}
	
}
