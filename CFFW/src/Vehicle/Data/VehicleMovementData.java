package Vehicle.Data;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import CorditeExpansion.Cord;
import HexGrid.HexDirectionUtility.HexDirection;
import Hexes.Feature;
import Hexes.Hex;
import Vehicle.VehicleMovement;
import Vehicle.HullDownPositions.HullDownPosition;
import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;

public class VehicleMovementData {
	public Cord location;
	public HexDirection facing;
	public Map<String, Integer> movementSpeeds;  
	
	public int speed;
	public int acceleration;
	public int deceleration;
	
	public boolean boostUsed;
	public int boostAcceleration;
	public int boostRecovery;

	public int velocty;
	
	public int hullTurnRateFullSpeed;
	public int hullTurnRateHalfSpeed;
	public int hullTurnRateNoSpeed;
	
	public HullDownPosition hullDownPosition;
	public HullDownStatus hullDownStatus;
	
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
	
	public boolean hullDown() {
		return hullDownPosition != null;
	}
	
	public void enterHullDownPosition(HullDownPosition hullDownPosition) {
		this.hullDownPosition = hullDownPosition;
		this.hullDownStatus = hullDownPosition.minimumHullDownStatus;
		
		
	}
	
	public void exitHullDownPosition() {
		
		if(hullDownStatus != hullDownPosition.minimumHullDownStatus && hullDownStatus != hullDownPosition.maximumHullDownStatus)
			return;
		hullDownPosition = null;
		
	}
	
	public void inchBack() {

		try {
			hullDownStatus = VehicleMovement.MoveBackward(hullDownStatus, hullDownPosition.minimumHullDownStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void inchForward() {
		
		try {
			hullDownStatus = VehicleMovement.MoveForward(hullDownStatus, hullDownPosition.maximumHullDownStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
