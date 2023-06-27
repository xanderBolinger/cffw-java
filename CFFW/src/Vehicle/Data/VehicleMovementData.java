package Vehicle.Data;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import CorditeExpansion.Cord;
import HexGrid.HexDirectionUtility.HexDirection;
import Hexes.Feature;
import Hexes.Hex;

public class VehicleMovementData {
	public Cord location;
	public HexDirection facing;
	public Map<String, Integer> movementSpeeds;  
	public int acceleration;
	public int deceleration;
	
	public boolean boostUsed;
	public int boostAcceleration;
	public int boostRecovery;

	public int velocty;
	
	public int hullTurnRateFullSpeed;
	public int hullTurnRateHalfSpeed;
	public int hullTurnRateNoSpeed;
	
	public VehicleMovementData() {
		
		movementSpeeds = new HashMap<String, Integer>();
		
	}
	
	public int GetMoveSpeed(Hex hex) {
		
		for(Feature f : hex.features) {
			var t = f.featureType;
			if(t.equals("Paved Road") || t.equals("Dirt Road") 
					|| t.equals("Mud") || t.equals("Deep Mud")) {
				return movementSpeeds.get(f.featureType);
			} 
			
		}
		
		return movementSpeeds.get("Soil");
	}
	
}
