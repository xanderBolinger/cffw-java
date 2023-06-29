package Vehicle.Data;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import CorditeExpansion.Cord;
import HexGrid.HexDirectionUtility;
import HexGrid.HexDirectionUtility.HexDirection;
import Hexes.Feature;
import Hexes.Hex;
import Vehicle.Vehicle;
import Vehicle.VehicleMovement;
import Vehicle.Damage.VehicleCollision;
import Vehicle.HullDownPositions.HullDownPosition;
import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;

public class VehicleMovementData {
	public Cord location;
	public HexDirection facing;
	public Map<String, Integer> movementSpeeds;  
	
	
	public boolean movedClockwise;
	public int speed;
	public int acceleration;
	public int deceleration;
	
	
	public boolean boostUsed;
	public int boostAcceleration;
	public int boostRecovery;

	public int hullTurnRate;
	public int changedFaces;
	
	public HullDownPosition hullDownPosition;
	public HullDownStatus hullDownStatus;
	
	Vehicle vehicle;
	
	public VehicleMovementData(Vehicle vehicle) {
		this.vehicle = vehicle;
		movementSpeeds = new HashMap<String, Integer>();
		facing = HexDirection.A;
		location = new Cord(0,0);
	}
	
	public void changeFacing(boolean clockwise) {
		if(hullTurnRate <= changedFaces)
			return;
		
		facing = HexDirectionUtility.getFaceInDirection(facing, clockwise);
		changedFaces++; 
		speed = speed - 1 > 0 ? speed - 1 : 0;
	}
	
	public void enterHex(Hex hex) {
		if(VehicleCollision.hiddenObstaclesCheck(hex, vehicle))
			speed = 0;
		location = new Cord(hex.xCord, hex.yCord);
	}
	
	public void accelerate(int desiredSpeed, Hex hex) {
		int change = desiredSpeed - speed;
		
		if(change > acceleration || desiredSpeed > getMaxMoveSpeed(hex))
			return;
		
		speed = desiredSpeed;
	}
	
	public void decelerate(int desiredSpeed) {
		int change = speed - desiredSpeed;
		
		if(change > deceleration)
			return;
		
		speed = desiredSpeed;
	}
	
	public int getMaxMoveSpeed(Hex hex) {
		
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
			hullDownStatus = VehicleMovement.moveBackwardHD(hullDownStatus, hullDownPosition.minimumHullDownStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void inchForward() {
		
		try {
			hullDownStatus = VehicleMovement.moveForwardHD(hullDownStatus, hullDownPosition.maximumHullDownStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
