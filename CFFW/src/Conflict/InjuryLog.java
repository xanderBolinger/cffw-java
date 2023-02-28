package Conflict;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import Hexes.Building.Room;
import Trooper.Trooper;
import Unit.Unit;


/*
 Description: allows for an instance of injury log to be created before an attack is launched. Tracks who is injured, killed, ect in the attack. 
 */
public class InjuryLog {


	ArrayList<Trooper> dead = new ArrayList<>();
	ArrayList<Trooper> unconscious = new ArrayList<>();
	ArrayList<Trooper> injured = new ArrayList<>();
	Map<Trooper, Integer> alreadyInjured = new HashMap<Trooper, Integer>();
	Map<Trooper, Boolean> alreadyDead = new HashMap<Trooper, Boolean>();
	Map<Trooper, Boolean> alreadyUnconscious = new HashMap<Trooper, Boolean>();
	
	public static InjuryLog InjuryLog = new InjuryLog();
	
	
	public InjuryLog() {
		
		
	} 
	
	public void addAlreadyInjured() {
		
		for(Unit unit : GameWindow.gameWindow.initiativeOrder) {
			
			for(Trooper trooper : unit.individuals) {
				if(trooper.physicalDamage > 0)
					alreadyInjured.put(trooper, trooper.physicalDamage);
				if(!trooper.alive)
					alreadyDead.put(trooper, false);
				if(!trooper.conscious)
					alreadyUnconscious.put(trooper, false);
			}
			
		}
		
	}
	
	public void addAlreadyInjured(Room room) {

		for(Trooper trooper : room.occupants) {
			System.out.println("Pass 1, trooper: "+trooper.name);
			if(trooper.physicalDamage > 0)
				alreadyInjured.put(trooper, trooper.physicalDamage);
			if(!trooper.alive)
				alreadyDead.put(trooper, false);
			if(!trooper.conscious)
				alreadyUnconscious.put(trooper, false);
		}
	}
	
	public void addAlreadyInjured(int x, int y) {

		for(Unit unit : GameWindow.gameWindow.getUnitsInHexExcludingSide("None", x, y)) {
			System.out.println("Pass 1");
			for(Trooper trooper : unit.individuals) {
				System.out.println("Pass 1, trooper: "+trooper.name);
				if(trooper.physicalDamage > 0)
					alreadyInjured.put(trooper, trooper.physicalDamage);
				if(!trooper.alive)
					alreadyDead.put(trooper, false);
				if(!trooper.conscious)
					alreadyUnconscious.put(trooper, false);
			}
		}
	}
	
	// Called from injury/attack method
	public void addTrooper(Trooper trooper) {
		
		if(trooper.physicalDamage > 0 && !injured.contains(trooper) && (!alreadyInjured.containsKey(trooper) 
				|| alreadyInjured.get(trooper) != trooper.physicalDamage))
			injured.add(trooper);

		if(trooper.conscious == false && !unconscious.contains(trooper) && (!alreadyUnconscious.containsKey(trooper) 
				|| alreadyUnconscious.get(trooper) != trooper.conscious))
			unconscious.add(trooper);
		
		//System.out.println("Already Dead: "+alreadyDead.containsKey(trooper) +", Trooper Name: "+trooper.name);
		
		if(trooper.alive == false && !dead.contains(trooper) && (!alreadyDead.containsKey(trooper) 
				|| alreadyDead.get(trooper) != trooper.alive))
			dead.add(trooper);
		
	}
	
	public void printResultsToLog() {
		String text = "\nSITREP:\n"; 
		
		if(dead.size() > 0) {
			text += "DEAD("+dead.size()+"): [";
			for(Trooper trooper : dead) {
				text += GameWindow.getLogHead(trooper);
				
				// If not last trooper adds space and comma 
				if(!dead.get(dead.size()-1).compareTo(trooper))
					text += ", ";
				
				injured.remove(trooper);
				unconscious.remove(trooper);
			}
			text += "]\n";
		}
		
		if(unconscious.size() > 0) {
			text += "UNCONSCIOUS("+unconscious.size()+"): [";
			for(Trooper trooper : unconscious) {
				if(dead.contains(trooper))
					continue; 
				
				text += GameWindow.getLogHead(trooper)+" PD: "+trooper.physicalDamage;
				
				// If not last trooper adds space and comma 
				if(!unconscious.get(unconscious.size()-1).compareTo(trooper))
					text += ", ";
				
				injured.remove(trooper);
			}
			text += "]\n";
			
			
		}
		
		
		if(injured.size() > 0) {
			text += "INJURED("+injured.size()+"): [";
			for(Trooper trooper : injured) {
				if(dead.contains(trooper) || unconscious.contains(trooper))
					continue; 
				
				text += GameWindow.getLogHead(trooper)+" PD: "+trooper.physicalDamage;
				
				// If not last trooper adds space and comma 
				if(!injured.get(injured.size()-1).compareTo(trooper))
					text += ", ";
			}
			text += "]\n";
		}
		
		GameWindow.gameWindow.conflictLog.addNewLineToQueue(text);
		
		System.out.println("Print results to log, injured size: "+injured.size());
		dead.clear();
		injured.clear();
		unconscious.clear();
	}
	
	
}
