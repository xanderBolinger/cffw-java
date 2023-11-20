package HexGrid.Vehicle;

import CeHexGrid.Chit;
import Conflict.GameWindow;
import HexGrid.HexDirectionUtility.HexDirection;
import Vehicle.Vehicle;
import Vehicle.Windows.VehicleCombatWindow;

public class HexGridVehicleUtility {
	public static void updateVehicleChits(VehicleCombatWindow cw) {

		for(var vic : cw.vehicles) {
			
			var vehicleChit = getVehicleChit(vic.identifier);

			if(vehicleChit == null) {
				var blufor = bluforVehicle(vic);
				var chit = new Chit("Unit Images\\"+(blufor ? "BLUFOR" : "OPFOR")+"_"
						+vic.getVehicleClass()+".png", 20, (blufor ? 12 : 20));
				chit.labeled = true; 
				chit.chitIdentifier = vic.identifier;
				chit.chitLabel = vic.getVehicleCallsign();
				GameWindow.gameWindow.game.chits.add(chit);
				continue;
			}
			
			vehicleChit.xCord = vic.movementData.location.xCord;
			vehicleChit.yCord = vic.movementData.location.yCord;
			vehicleChit.facing = HexDirection.getFacing(vic.movementData.facing);
			vehicleChit.chitLabel = vic.getVehicleCallsign();
				
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
			if(chit.labeled && chit.chitIdentifier.equals(idenfitier))
				return chit;
		}
		
		return null;
	}
	
}
