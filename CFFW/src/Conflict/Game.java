package Conflict;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import Artillery.Artillery;
import Artillery.Artillery.Shot;
import CeHexGrid.Chit;
import Fortifications.FortificationRecords;
import HexGrid.ProcGen.ProcGenHexLoader.Map;
import HexGrid.Roads.RoadManager;
import HexGrid.Shields.ShieldManager;
import Hexes.Hex;
import Melee.MeleeCombatUnit;
import Melee.MeleeManager;
import Vehicle.VehicleManager;
import Artillery.Artillery.ShellType;;

public class Game implements Serializable {
	private int phase;
	private int round; 
	private int currentAction;
	private String daylightCondition = ""; 
	public String mapImageFileName = "refuge_33x33.png"; 
	public ShieldManager shieldManager;
	public boolean backgroundMap;
	
	public MeleeManager meleeManager;
	public ArrayList<Chit> chits;
	public HashMap<Artillery, HashMap<ShellType, Integer>> firedShots;
	public int chitCounter;
	public VehicleManager vehicleManager;
	public FortificationRecords fortifications;
	public Wind wind;
	public Smoke smoke;
	// public timne object 
	public Map procGenMap;
	public RoadManager roadManager;

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
		firedShots = new HashMap<Artillery, HashMap<ShellType, Integer>>();
		roadManager = new RoadManager();
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
	
	public void printShots() {
		
		String shotOutput = "Shot Output: \n";
		
		for(var bat : firedShots.keySet()) {
			
			var batMap = firedShots.get(bat);
			
			for(var shellType : batMap.keySet()) {
				
				var count = batMap.get(shellType);
				
				shotOutput += bat.batteryType+": "
						+bat.batteryDisplayName+" "+shellType+" "+count+"x\n";
				
			}
			
		}
		
		System.out.println(shotOutput);
		GameWindow.gameWindow.conflictLog.addNewLineToQueue(shotOutput);
	}
	
	
	@Override 
	public String toString() {
		String string = "::   Rounds: "+this.round+"; Phase: "+this.phase+"; CurrentAction: "+this.currentAction;
		return string; 
	}
}
