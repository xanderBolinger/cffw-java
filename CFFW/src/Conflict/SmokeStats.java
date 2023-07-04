package Conflict;

import java.io.Serializable;

import CorditeExpansion.Cord;

public class SmokeStats implements Serializable {

	
	
	public SmokeType smokeType;
	public int diameter; 
	public int duration;
	private int elapsedActions = 0;
	
	public Cord deployedHex;
	
	public enum SmokeType {
		SMOKE_GRENADE, Howitzer155mm, Howitzer105mm, Mortar81mm, Mortal120mm,VehicleSmokeLauncher,VehicleTrailingSmoke
	}
	
	public SmokeStats(String smokeType) throws Exception {
		
		for(var e : SmokeType.values()) {
			if(e.toString().equals(smokeType)) {
				setSmokeStats(e);
				return;
			}
		}

		throw new Exception("Smoke type not found for smoke type: "+smokeType);
	}
	
	public SmokeStats(SmokeType smokeType) {
		setSmokeStats(smokeType);
	}
	
	private void setSmokeStats(SmokeType smokeType) {
		this.smokeType = smokeType;
		switch(smokeType) {
		case SMOKE_GRENADE:
			diameter = 3; 
			duration = 6;
			break;
		case Howitzer155mm:
			diameter = 8; 
			duration = 15;
			break;
		case Mortar81mm:
			diameter = 5; 
			duration = 9;
			break;
		case Mortal120mm:
			diameter = 8; 
			duration = 12;
			break;
		case VehicleTrailingSmoke:
			diameter = 6; 
			duration = 10;
			break;
		case VehicleSmokeLauncher:
			diameter = 8; 
			duration = 12;
			break;
		case Howitzer105mm:
			diameter = 8; 
			duration = 12;
			break;
		default:
			break;
		
		}
	}
	
	public void increaseElapsedActions() {
		elapsedActions++;
	}
	
	public int getElapsedActionsTotal() {
		return elapsedActions;
	}
	
	public int getElapsedActionsAfterDuration() {
		if(duration >= elapsedActions)
			return 0;
		
		return elapsedActions - duration;
	}
	
}
