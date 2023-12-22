package Vehicle.Data;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import Conflict.GameWindow;
import CorditeExpansion.Cord;
import HexGrid.CalculateLOS;
import HexGrid.HexDirectionUtility;
import HexGrid.HexDirectionUtility.HexDirection;
import HexGrid.Roads.RoadUtil;
import Hexes.Feature;
import Hexes.Hex;
import Vehicle.Vehicle;
import Vehicle.VehicleMovement;
import Vehicle.Damage.VehicleCollision;
import Vehicle.HullDownPositions.HullDownPosition;
import Vehicle.HullDownPositions.HullDownPosition.HullDownDecision;
import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;

public class VehicleMovementData implements Serializable {
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
	public HullDownDecision hullDownDecision;
	public HullDownPosition selectedHullDownPosition;
	
	Vehicle vehicle;
	
	public VehicleMovementData(Vehicle vehicle) {
		this.vehicle = vehicle;
		movementSpeeds = new HashMap<String, Integer>();
		facing = HexDirection.A;
		location = new Cord(0,0);
		hullDownDecision = HullDownDecision.NOTHING;
	}
	
	public void changeFacing(boolean clockwise) {
		if(hullTurnRate <= changedFaces)
			return;
		
		facing = HexDirectionUtility.getFaceInDirection(facing, clockwise);
		changedFaces++; 
		speed = speed - 1 > 0 ? speed - 1 : 0;
		VehicleMovement.updateChit(vehicle);
	}
	
	public void enterHex(Hex hex) {
		if(VehicleCollision.hiddenObstaclesCheck(hex, vehicle)) {
			speed = 0;
			if(GameWindow.gameWindow != null && GameWindow.gameWindow.conflictLog != null)
				GameWindow.gameWindow.conflictLog.addNewLine(vehicle.getVehicleCallsign()+"'s path has been blocked and they are no longer moving.");
		} else {
			location = new Cord(hex.xCord, hex.yCord);
			
		}
		
		if(vehicle.smokeData.trailingSmokeActive) {
			vehicle.smokeData.deployTrailingSmoke();
		}
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
		
		if(RoadUtil.hexIsHighway(hex)) {
			return movementSpeeds.get("Paved Road");
		} else if(RoadUtil.hexIsPath(hex)) {
			return movementSpeeds.get("Dirt Road");
		}
		
		return movementSpeeds.get("Soil");
	}
	
	public boolean hullDown() {
		return hullDownPosition != null;
	}
	
	public void enterHullDownPosition(HullDownPosition hullDownPosition) {
		if(hullDownPosition.occupants >= hullDownPosition.capacity)
			return;
		
		this.hullDownPosition = hullDownPosition;
		this.hullDownStatus = hullDownPosition.minimumHullDownStatus;
		this.hullDownPosition.occupants++;
		CalculateLOS.calcVehicles(vehicle);
	}
	
	public void exitHullDownPosition() {
		
		if(hullDownPosition == null || 
				(hullDownStatus != hullDownPosition.minimumHullDownStatus && hullDownStatus != hullDownPosition.maximumHullDownStatus))
			return;
		hullDownPosition.occupants--;
		hullDownPosition = null;
		CalculateLOS.calcVehicles(vehicle);
	}
	
	public void inchBack() {

		try {
			hullDownStatus = VehicleMovement.moveBackwardHD(hullDownStatus, hullDownPosition.minimumHullDownStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		CalculateLOS.calcVehicles(vehicle);
	}
	
	public void inchForward() {
		
		try {
			hullDownStatus = VehicleMovement.moveForwardHD(hullDownStatus, hullDownPosition.maximumHullDownStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CalculateLOS.calcVehicles(vehicle);
		
	}
	
}
