package Vehicle;

import java.io.Serializable;

import Conflict.GameWindow;

public class VehicleManager implements Serializable {

	public int turn = 1;
	
	public void NextTurn() {
		turn = turn == 1 ? 2 : 1; 
		
		for(var company : GameWindow.gameWindow.companies) {
			for(var vic : company.vehicles) {
				VehicleMovement.moveVehicle(vic);
			}
		}
		
	}
	
}
