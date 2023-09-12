package Conflict;

import java.io.Serializable;
import java.util.ArrayList;
import CeHexGrid.Chit;
import Fortifications.FortificationRecords;
import HexGrid.ProcGen.ProcGenHexLoader.Map;
import Hexes.Hex;
import Vehicle.VehicleManager;

public class Game implements Serializable {
	private int phase;
	private int round; 
	private int currentAction;
	private String daylightCondition = ""; 
	public String mapImageFileName = "refuge_33x33.png"; 
	
	public boolean backgroundMap;
	
	public ArrayList<Chit> chits;
	public int chitCounter;
	public VehicleManager vehicleManager;
	public FortificationRecords fortifications;
	public Wind wind;
	public Smoke smoke;
	// public timne object 
	public Map procGenMap;

	public Game(int phase, int round, int currentAction) {
		this.phase = phase; 
		this.round = round; 
		this.currentAction = currentAction;
		chits = new ArrayList<>();
		chitCounter = 1;
		wind = new Wind();
		smoke = new Smoke();
		vehicleManager = new VehicleManager();
		fortifications = new FortificationRecords();
		backgroundMap = true;
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
