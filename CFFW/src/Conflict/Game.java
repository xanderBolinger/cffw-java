package Conflict;

import java.io.Serializable;

public class Game implements Serializable {
	private int phase;
	private int round; 
	private int currentAction;
	private String daylightCondition = ""; 
	public String mapImageFileName = "refuge_33x33.png"; 
	// public timne object 

	public Game(int phase, int round, int currentAction) {
		this.phase = phase; 
		this.round = round; 
		this.currentAction = currentAction;
		
	} 
	
	// SETTERS 
	public void setDaylightCondition(String daylightCondition) {
		this.daylightCondition = daylightCondition; 
	}
	
	public void setPhase(int phase) {
		this.phase = phase; 
	}
	
	public void setRound(int round) {
		this.round = round; 
	}
	
	public void setCurrentAction(int currentAction) {
		this.currentAction = currentAction; 
	}
	
	// GETTERS 
	public int getPhase() {
		return phase; 
	}
	public int getRound() {
		return round; 
	}
	public int getCurrentAction() {
		return currentAction;
	}
	
	public String getDaylightCondition() {
		return daylightCondition;
	}
	
	@Override 
	public String toString() {
		String string = "::   Rounds: "+this.round+"; Phase: "+this.phase+"; CurrentAction: "+this.currentAction;
		return string; 
	}
}
