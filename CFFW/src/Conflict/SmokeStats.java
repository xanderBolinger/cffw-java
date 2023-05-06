package Conflict;

import java.io.Serializable;

import CorditeExpansion.Cord;

public class SmokeStats implements Serializable {

	public int diameter; 
	public int duration;
	private int elapsedActions = 0;
	
	public Cord deployedHex;
	
	public enum SmokeType {
		SMOKE_GRENADE, Howitzer155mm, Howitzer105mm, Mortar81mm, Mortal120mm
	}
	
	public SmokeStats(SmokeType smokeType) {
		
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
