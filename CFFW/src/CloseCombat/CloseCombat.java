package CloseCombat;

import java.util.*;

import Conflict.GameWindow;
import Trooper.Trooper;
import Unit.Unit;

public class CloseCombat {
	public ArrayList<Unit> units = new ArrayList();
	public GameWindow window; 
	
	public CloseCombat(ArrayList<Unit> units, GameWindow window) {
		
		// Apply close combat search test 
		
		// Sets window
		this.window = window;
		
		// Initiate Close Combat
		initiateCloseCombat();
	}
	
	
	// Sets all individuals in close combat to close combat 
	public void initiateCloseCombat() {
		
		// Loops through all individuals, sets Close Combat to true 
		for(int i = 0; i < units.size(); i++) {
			
			Unit unit = units.get(i);
			ArrayList<Trooper> troopers = unit.getTroopers();
			for(int x = 0; x < troopers.size(); x++) {
				troopers.get(x).CloseCombat = true;
			}
			
		}
		
		
	}
	
	
	// Creates new unit 
	// Loops through fleeing troopers 
	// Removes fleeing trooper from unit, adds trooper to new unit 
	public void flee(ArrayList<Trooper> troopers, Unit unit) {
		
		Unit fleeUnit = unit;
		int org = fleeUnit.organization;
		fleeUnit.organization -= org / 2; 
		
		// For each trooper, removes trooper from unit, adds trooper to new FleeUnit
		for(int i = 0; i < troopers.size(); i++) {
			
			Trooper trooper = troopers.get(i);
			
			for(int x = 0; x < unit.getTroopers().size(); x++) {
				
				if(trooper.compareTo(unit.getTroopers().get(x))) {
					
					unit.individuals.remove(x);
					fleeUnit.addToUnit(trooper);
					
					
				}
				
			}
			
		}
		
		
		window.initiativeOrder.add(fleeUnit);
		
		window.rollInitiativeOrder();
		window.refreshInitiativeOrder();
		
		// Finds newUnit's company 
		// Adds unit to company 
		for(int i = 0; i < window.companies.size(); i++) {
			
			if(window.companies.get(i).getName().equals(fleeUnit.company)) {
				window.companies.get(i).addUnit(fleeUnit);
				// Adds companies to setupWindow
				//window.confirmCompany(window.companies.get(i), i);
				//f.dispose();
				
			}
			
		}
		
		
	}
	
	
	
	
	
	
}
