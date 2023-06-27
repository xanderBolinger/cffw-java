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
	
	public void enterHullDownPosition(HullDownPosition hullDownPosition) {
		this.hullDownPosition = hullDownPosition;
		
		if(hullDownPosition.hiddenHullDown)
			hullDownStatus = HullDownStatus.HIDDEN;
		else if(hullDownPosition.turretDown)
			hullDownStatus = HullDownStatus.TURRET_DOWN;
		else 
			hullDownStatus = HullDownStatus.HULL_DOWN;
		
	}
	
	public void inchBack() {
		if(!hullDownPosition.hiddenHullDown && !hullDownPosition.turretDown) {
			hullDownPosition = null;
		}
			
		try {
			var status = VehicleMovement.MoveBackward(hullDownStatus);
			
			if(status == hullDownStatus)
				hullDownPosition = null;
			else 
				hullDownStatus = status;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void inchForward() {
		
		try {
			var status = VehicleMovement.MoveForward(hullDownStatus);
			hullDownStatus = status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
