package Hexes;

import java.io.Serializable;
import java.util.ArrayList;

import Hexes.Building.Floor;
import Hexes.Building.Room;
import Trooper.Trooper;
import Unit.Unit;

public class Hex implements Serializable {

	public int xCord; 
	public int yCord; 
	public ArrayList<Feature> features = new ArrayList<>(); 
	public ArrayList<Building> buildings = new ArrayList<>(); 
	public int obscuration; 
	public int concealment; 
	public int elevation; 
	public int coverPositions;
	public int usedPositions;
	
	// Copy constructor 
	public Hex(int xCord, int yCord, Hex oldHex) {
		
		this.xCord = xCord;
		this.yCord = yCord; 
	
		for(Feature feature : oldHex.features) {
			features.add(new Feature(feature));
		}
		
		for(Building building : oldHex.buildings) {
			buildings.add(new Building(building));
		}
		
		this.obscuration = oldHex.obscuration;
		this.concealment = oldHex.concealment;
		this.elevation = oldHex.elevation;
		this.coverPositions = oldHex.coverPositions;
		this.usedPositions = oldHex.usedPositions;
		
	}
	
	public Hex(int xCord, int yCord, ArrayList<Feature> features, int obscuration, int concealment, int elevation) {
		
		this.xCord = xCord;
		this.yCord = yCord; 
		this.features = features; 
		this.obscuration = obscuration; 
		this.concealment = concealment; 
		this.elevation = elevation;
		
	}
	
	public boolean containsTrooper(Trooper trooper) {
		
		for(Building building : buildings) {
			
			for(Floor floor : building.floors) {
				
				for(Room room : floor.rooms)
					if(room.containsTrooper(trooper)) {
						//System.out.println("Return true");
						return true;
					}
				
			}
			
		}
		
		//System.out.println("Return false");
		return false; 
		
	}
	
	public ArrayList<Trooper> getUnembarkedTroopers(Unit unit) {
		
		ArrayList<Trooper> unembarkedTroopers = new ArrayList<>();
		
		for(Trooper trooper : unit.individuals) {
			if(!containsTrooper(trooper))
				unembarkedTroopers.add(trooper);
		}
		
		return unembarkedTroopers;
	}
	
	public Building getTrooperBuilding(Trooper trooper) {
		
		for(Building building : buildings) {
			
			for(Trooper otherTrooper : building.getAllOccupants()) {
				
				if(trooper.compareTo(otherTrooper))
					return building; 
				
			}
			
		}
		
		
		return null;
		
	}
	
	
	public int getTrooperRoomNumber(Trooper trooper) {
		
		Building building = getTrooperBuilding(trooper);
		
		return building.getTrooperRoomNumber(trooper);
		
	}
	
	public int getTrooperFloorNumber(Trooper trooper) {
		
		Building building = getTrooperBuilding(trooper);
		
		return building.getTrooperFloorNumber(trooper);
		
	}
	
	
	public void addBuilding(String name, int floors, int rooms, int diameter) {
		
		
		if(floors < 1)
			return; 
		
		buildings.add(new Building(name, floors, rooms, diameter));
		
		
	}
	
	
	/*Light Forest
Medium Forest 
Heavy Forest
Brush
Heavy Brush
Light Rock
Medium Rock
Heavy Rock 
Rubble
Small Depression 
Large Depression*/
	

	public void setConcealment() {
		
		concealment = 0; 
		
		for(Feature feature : features) {
			
			//System.out.println("Feature Type: "+feature.featureType);
			
			if(feature.featureType.equals("Light Forest")) {
				//System.out.println("Pass 1");
				concealment += 1; 
			} else if(feature.featureType.equals("Medium Forest")) {
				//System.out.println("Pass 2");
				concealment += 2; 
			} else if(feature.featureType.equals("Heavy Forest")) {
				//System.out.println("Pass 3");
				concealment += 3; 
			} else if(feature.featureType.equals("Brush")) {
				//System.out.println("Pass 1");
				concealment += 1; 
			} else if(feature.featureType.equals("Heavy Brush")) {
				concealment += 2; 
				//System.out.println("Pass 2");
			}
			
			
		}
		
		
		//System.out.println("Set Concealment Equal To: "+concealment);
	}
	
	public void setTotalPositions() {
		int cover = 0; 
		
		for(Feature feature : features) {
			cover += feature.coverPositions;
		}
		
		this.coverPositions = cover;  
		//System.out.println("Set Cover Positions Equal To: "+this.coverPositions);
	}
	
	public String toString() {
		
		return "("+xCord+", "+yCord+"), "+"Con: "+concealment+", "+"Elv: "+elevation+", Obs: "+obscuration+"%";
		
	}
	
}
