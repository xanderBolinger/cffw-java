package Unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import Actions.Spot;
import Artillery.FireMission;
import Conflict.GameWindow;
import Conflict.OpenUnit;
import HexGrid.Waypoint.WaypointData;
import Hexes.Building;
import Hexes.Hex;
import Hexes.HexWindow;
import Items.Weapons;
import Trooper.Trooper;
import Trooper.generateSquad;

public class Unit implements Serializable {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public String side = "Empty";
	public String company = "Empty";
	public boolean closeCombat = false; 
	public int initiative = 0;
	public String callsign; 
	public int X; 
	public int Y; 
	public int effectiveBurden;
	
	public ArrayList<Unit> lineOfSight = new ArrayList<Unit>();
	public ArrayList<Trooper> individuals; 
	public ArrayList<Weapons> staticWeapons; 
	public int organization; 
	public int suppression; 
	public int moral; 
	public int fatiuge; 
	public int cohesion; 
	public int radius; 
	public int commandValue;
	public String speed; 
	public String behavior;
	public int timeSinceContact = 0; 
	public String concealment; 
	public boolean active;
	public boolean soughtCover = false; 
	public String identifier;
	
	public int individualsInCover = 0;
	public boolean inBuilding = false; 
	public int floor = 0;
	
	public ArrayList<FireMission> fireMissions = new ArrayList<FireMission>();
	
	public UnitType unitType = UnitType.INFANTRY;
	public WaypointData waypointData;
	
	public enum UnitType implements Serializable {
		INFANTRY, ARMOR, SF, HQ
		
	}
	
	public Unit(String callsign, int X, int Y, ArrayList<Trooper> individuals, int organization, int suppression, int moral, int fatiuge, int cohesion, int radius, int commandValue, String behavior) {
		this.callsign = callsign; 
		this.X = X; 
		this.Y = Y;
		
		this.individuals = individuals;
		this.staticWeapons = new ArrayList<>();
		this.organization = organization;
		this.suppression = suppression;
		this.moral = moral;
		this.fatiuge = fatiuge;
		this.cohesion = cohesion;
		this.radius = 0;
		this.commandValue = commandValue;
		
		this.speed = "None";
		this.behavior = behavior;
		this.concealment = "Level 0";
		
		this.identifier = identifier();
		this.active = true;
		waypointData = new WaypointData();
	}
	
	public Unit() {
		// TODO Auto-generated constructor stub
	}

	// Copies unit 
	public Unit copyUnit(Unit unit) {
		
		Unit newUnit = new Unit(unit.callsign, unit.X, unit.Y, unit.getTroopers(), unit.organization, unit.suppression, unit.moral, unit.fatiuge, unit.cohesion, unit.radius, unit.commandValue, unit.behavior);

		newUnit.side = unit.side;
		newUnit.initiative = unit.initiative;
		newUnit.speed = unit.speed; 
		newUnit.behavior = unit.behavior; 
		newUnit.concealment = unit.concealment; 
		
		return newUnit; 
	}
	
	// Checks inidividuals 
	// Sets CV from the top trooper 
	public void getCommandValue() {
		if(individuals == null || individuals.size() < 1 || getLeader() == null) {
			return;
		}
		int command = getLeader().getSkill("Command");
		this.commandValue = command / 10; 
	}
	
	
	public boolean entirelyMechanical() {
		
		for(Trooper trooper : individuals) {
			
			if(!trooper.entirelyMechanical)
				return false;
			
		}
		
		return true; 
		
	}
	
	// Adds/Equips a new static weapon with the unit 
	public void addStaticWeapon(Weapons staticWep) {
		this.staticWeapons.add(staticWep);
	}
	
	// Checks if the trooper is equiped to a static weapon 
	// Returns true if trooper is equiped
	public boolean equipped(Trooper trooper) {
		
		for(int i = 0; i < staticWeapons.size(); i++) {
			
			Weapons staticWep = staticWeapons.get(i);
			ArrayList<Trooper> troopers = staticWep.equipedTroopers;
			for(int x = 0; x < troopers.size(); x++) {
				
				if(trooper.compareTo(troopers.get(x))) {
					return true;
				}
				
			}
			
		}
		
		
		
		return false; 
		
		
		
	}
	
	
	// Gets individuals and returns an array 
	public ArrayList<Trooper> getTroopers() {
		return individuals;
	}
	
	// Gets size of unit 
	public int getSize() {
		return individuals.size();
	}
	// Sets unit index equal to individual 
	public void setIndividual(Trooper trooper, int index) {
		index--; // ????
		individuals.set(index, trooper);
	}
	// Sets all individuals 
	public void overwriteIndividuals(ArrayList<Trooper> troopers) {
		// May need to loop through troopers and set than add individualy to individuals
		individuals = troopers;  
	}
	
	// Delete individual 
	public Trooper removeFromUnit(int index) {
		if(index >= individuals.size()) {
			return null; 
		}
		
		return individuals.remove(index);
	}
	
	public Trooper removeFromUnit(Trooper trooper) {
		if(!individuals.contains(trooper)) {
			return null; 
		}
		
		if(individuals.remove(trooper)) {
			return trooper; 
		} else {
			return null;
		}
		

	}
		
	// Adds trooper to array list 
	public void addToUnit(Trooper trooper) {
		individuals.add(trooper);
		
		for(int i = 0; i < individuals.size(); i++) {
			individuals.get(i).number = i + 1; 
		}
	}
	
	public String identifier() {
		int count = 10;
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	// Compares identifers of unit 
	public boolean compareTo(Unit otherUnit) {
		if(this.identifier.equals(otherUnit.identifier)) {
			return true; 
		} else {
			return false; 
		}
	}
	
	
	// Creates a new unit from a number of fleeing individuals 
	public void flee(GameWindow window, Unit unit, ArrayList<Trooper> flee) {
		if(unit.callsign.toLowerCase().contains("rout"))
			return; 
		
		// Adds new unit 
		// Splits unit 
		ArrayList<Trooper> individuals = new ArrayList<Trooper>();
		generateSquad squad = new generateSquad("Clone Trooper Phase 1", "Empty");
		individuals = squad.getSquad();
		
		String newCallsign = "ROUTED - "+unit.callsign;
		int count = 1; 
		for(int i = 0; i < window.initiativeOrder.size(); i++) {
			if(newCallsign.equals(window.initiativeOrder.get(i).callsign)) {
				newCallsign = newCallsign+Integer.toString(count);
				count++;
			}
		}
		
		Unit newUnit = new Unit(newCallsign, 0, 0, individuals, 100, 0, 100, 0, 0, 20, 0, unit.behavior);
		
		newUnit.side = unit.side;
		newUnit.initiative = unit.initiative;
		newUnit.organization = unit.organization / 2;
		unit.organization = unit.organization / 2; 
		
		newUnit.suppression = unit.suppression;
		newUnit.moral = unit.moral;
		newUnit.cohesion = unit.cohesion;
		newUnit.company = unit.company;
		newUnit.X = unit.X;
		newUnit.Y = unit.Y;
		
		if(unit.closeCombat) {
			newUnit.closeCombat = true;
		}
		
		// Removes fleeing individuals 
		for(int i = 0; i < unit.getTroopers().size(); i++) {
			
			ArrayList<Trooper> troopers = unit.getTroopers();
			
			for(int x = 0; x < flee.size(); x++) {
				
				if(flee.get(x).compareTo(troopers.get(i))) {
					unit.individuals.remove(i);
					newUnit.addToUnit(flee.get(x));
				}
				
			}
			
		}
		
		
		window.initiativeOrder.add(newUnit);
		
		window.rollInitiativeOrder();
		window.refreshInitiativeOrder();
		
		// Finds newUnit's company 
		// Adds unit to company 
		for(int i = 0; i < window.companies.size(); i++) {
			
			if(window.companies.get(i).getUnits().contains(this)) {
				window.companies.get(i).updateUnit(unit);
				window.companies.get(i).addUnit(newUnit);
				// Adds companies to setupWindow
				window.confirmCompany(window.companies.get(i), i);
				
				
				
			}
			
		}
	}
	
	// Removes LOS unit from index 
	// Removes spotted troopers from this unit to that unit which has left LOS 
	public void removeLOS(int index, GameWindow game) {
		
		lineOfSight.remove(index);
		
		// Removes spot actions from units no longer in LOS 
		for(Trooper individual : individuals) {
			
			for(Iterator<Spot> iterator = individual.spotted.iterator(); iterator.hasNext();) {
				
				Spot spotAction = iterator.next();
				
				if(!individual.returnTrooperUnit(game).lineOfSight.contains(spotAction.spottedUnit)) {
					iterator.remove();
				}
				
				
			}
		}
		
		
		
	}
	
	
	
	// Sets the stance for the trooper
	public void setStance(Trooper targetTrooper, GameWindow game) {
		
		/*boolean moving = false; 
		
		if(speed.equals("Rush")) {
			targetTrooper.stance = "Standing";
			targetTrooper.manualStance = false; 
		} else if(speed.equals("Walk")) {
			
			int action = game.game.getCurrentAction(); 
			int trooperMovingInAction = 0; 
	
			
			// Sets counts 
			int count = 1; 
			for(Trooper trooper : individuals) {
				
				if(count == 1) {
					if(trooper.compareTo(targetTrooper)) {
						trooperMovingInAction = 1; 
						break; 
					}
						
				} else if(count == 2) {
					if(trooper.compareTo(targetTrooper)) {
						trooperMovingInAction = 2;
						break; 
					}
				} else {
					if(trooper.compareTo(targetTrooper)) {
						trooperMovingInAction = 3;
						break; 
					}
					count = 0; 
				}
				
				count++; 
				
					
			}
			
			if(action == 1) {
				if(trooperMovingInAction == 1) {
					moving = true; 
				}
			} else if(action == 2 )  {
				if(trooperMovingInAction == 2) {
					moving = true; 
				}
			} else if(action == 3) {
				if(trooperMovingInAction == 3) {
					moving = true; 
				}
			} else {
			
				int roll = new Random().nextInt(3) + 1; 
				if(trooperMovingInAction == roll) {
					moving = true; 
				}
				
			}
			
		} 
		
		int roll = new Random().nextInt(100) + 1; 
		
		if(moving) {
			targetTrooper.stance = "Standing";
			targetTrooper.manualStance = false; 
		} else {
			
			if(targetTrooper.manualStance)
				return; 
			
			if(behavior.equals("Contact")) {
				if(roll == 1) {
					targetTrooper.stance = "Standing";
				} else if(roll <= 12) {
					targetTrooper.stance = "Crouched";
				} else {
					targetTrooper.stance = "Prone";
				}
			} else if(behavior.equals("Recent Contact")) {
				if(roll <= 3) {
					targetTrooper.stance = "Standing";
				} else if(roll <= 35) {
					targetTrooper.stance = "Crouched";
				} else {
					targetTrooper.stance = "Prone";
					//targetTrooper.prone = true;
				}
			} else if(behavior.equals("No Contact")) {
				if(roll <= 95) {
					targetTrooper.stance = "Standing";
				} else if(roll <= 98) {
					targetTrooper.stance = "Crouched";
				} else {
					targetTrooper.stance = "Prone";
				}
			} 
			
		}*/
		
	}
	
	// Gets hex 
	// Determines amount of avaiable cover 
	// Assigns individuals to cover in decending order of fighter skill
	public void seekCover(Hex hex, GameWindow gameWindow) {
		//System.out.println("Seek Cover --> Sought Cover: "+soughtCover);
		//System.out.println("Seek Cover --> Hex: "+hex);
		
		if(soughtCover)
			return; 
		
		if(hex == null)
			return; 
		
		soughtCover = true; 
		
		//System.out.println("hex.coverPositions: "+hex.coverPositions);
		//System.out.println("hex.usedPositions: "+hex.usedPositions);
		int positions = hex.coverPositions - hex.usedPositions;
		//System.out.println("Seeking Cover Positions: "+positions);
		int troopersGoingIntoCover = 0; 
		individualsInCover = 0;
		ArrayList<Trooper> unembarkedTroopers = new ArrayList<>();
		
		for(Trooper trooper : individuals) {
			
			if(!trooper.inBuilding(gameWindow)) {
				trooper.inCover = false; 
				unembarkedTroopers.add(trooper);
			} else {
				trooper.inCover = true; 
			}
			
		}
		
		if(positions == 0 || behavior.equals("No Contact"))
			return; 
		
		if(speed.equals("Walk")) {

			
			troopersGoingIntoCover = unembarkedTroopers.size() / 3;
			
			
		} else if(speed.equals("Rush")) {
		
			troopersGoingIntoCover = 0;
			
			
		} else {
			
			if(unembarkedTroopers.size() > positions) {
				troopersGoingIntoCover = positions; 
			} else {
				troopersGoingIntoCover = unembarkedTroopers.size(); 
			}
			
			
		}
		
		/*System.out.println("Speed: "+speed
				+", Troopers Going Into Cover: "+troopersGoingIntoCover
				+", Unembarked Troopers: "+unembarkedTroopers.size());*/
		
		if(troopersGoingIntoCover <= 0)
			return; 
		
		
		
		Trooper[] sortedIndividuals = new Trooper[unembarkedTroopers.size()];
		
		for(int i = 0; i < unembarkedTroopers.size(); i++) {
			
			sortedIndividuals[i] = unembarkedTroopers.get(i);
			
		}
		
		//System.out.println("Prior to Sorting: ");
		
		/*for(Trooper trooper : sortedIndividuals)
			System.out.println("Trooper: "+trooper.number+" "+trooper.name+" Fighter: "+trooper.fighter);*/
		
		mergeSort(sortedIndividuals);
		
		//System.out.println("After Sorting: ");
		
		/*for(Trooper trooper : sortedIndividuals)
			System.out.println("Trooper: "+trooper.number+" "+trooper.name+" Fighter: "+trooper.fighter);*/
		
		
		
		int count = 0; 
		for(int i = sortedIndividuals.length - 1; i >= 0; i--) {
			
			sortedIndividuals[i].inCover = true; 
			
			count++; 
			if(count == troopersGoingIntoCover) 
				break; 
			
				 
		}
		
		
		individualsInCover = troopersGoingIntoCover;
		hex.usedPositions += troopersGoingIntoCover;
		//System.out.println("Invdividuals in cover: "+individualsInCover);
		
		for(var trooper : individuals) {
			
			if(behavior.equals("No Contact") || !speed.equals("None")) {
				trooper.stance = "Standing";
			} else {
				trooper.stance = "Prone";
			}
			
		}
		
	}
	
	
	public static void mergeSort(Trooper[] arr) {
		
		if(arr.length < 2)
			return;
		
		int mid = arr.length / 2; 
		
		Trooper L[] = Arrays.copyOfRange(arr, 0, mid);
		
		Trooper R[] = Arrays.copyOfRange(arr, mid, arr.length);
		
		
		mergeSort(L);
		
		mergeSort(R);
		
		int i = 0;
		int j = 0; 
		int k = 0; 
		
		while( i < L.length && j < R.length ) {
			if(L[i].getSkill("Fighter") < R[j].getSkill("Fighter")) {
				arr[k] = L[i];
				i++;
				k++;
			} else {
				arr[k] = R[j];
				j++;
				k++;
			}
			
		}
		
		
		while( i < L.length ) {
			arr[k] = L[i];
			i++;
			k++;
		}
		
		while( j < R.length ) {
			arr[k] = R[j];
			j++;
			k++;
		}
		
		
		
	}
	
	public static void sort(ArrayList<Trooper> sortedTroopers) {
		
	}
	
	// Calcaultes base burden and sets effective burden 
	public void calculateBurden() {
		if(individuals.size() == 0)
			return; 
		
		int baseBurden = 0; 
		int tempEncum = 0;
		for(Trooper trooper : individuals) {
			tempEncum = trooper.encumberance;
			if(trooper.encumberance - trooper.carryingCapacity > 0 && trooper.alive) {
				if(!trooper.conscious)
					tempEncum = 0; 
				baseBurden += tempEncum - trooper.carryingCapacity;
			}
			
		}
		
		effectiveBurden = baseBurden / individuals.size();
		//System.out.println("Base Burden: "+baseBurden+" efb: "+effectiveBurden);
		
	}
	
	
	
	public void unembark(GameWindow gameWindow) {
		
		ArrayList<Trooper> embarkedTroopers = new ArrayList<>();
		
		for(Trooper trooper : individuals) {
			
			if(trooper.inBuilding(gameWindow)) {
				embarkedTroopers.add(trooper);
			}
			
		}
		
		Hex hex = gameWindow.findHex(X, Y);
		
		if(hex == null) {
			return; 
		}
		
		for(Building building : hex.buildings) {
			
			for(Trooper t : embarkedTroopers) {
				
				for(Trooper trooper : building.getAllOccupants()) {
					
					if(trooper.compareTo(t)) {
						building.removeOccupant(trooper);
					}
					
				}
			}
			
		}
		
	}

	public void move(GameWindow gameWindow, int xCord, int yCord, OpenUnit openUnitWindow) {
		
		
		
		if(radius < 3 && speed.equals("Crawl")) {
			radius++;
			return;
		}
		radius = 0;
		
		soughtCover = false; 			
		unembark(gameWindow);
		
		for(Trooper trooper : individuals) {
			
			trooper.pcRanges.clear();
			
			for(Unit otherUnit : gameWindow.initiativeOrder) {
				
				for(Trooper otherTrooper : otherUnit.individuals) {
					
					if(otherTrooper.pcRanges.containsKey(trooper)) 
						otherTrooper.pcRanges.remove(trooper);
					
				}
				
			}
			
			trooper.spottingDifficulty = 0;
			
		}
		
		Hex leftHex = null; 
		for(Hex hex : gameWindow.hexes) {
			
			if(hex.xCord == X && hex.yCord == Y) {
				leftHex = hex; 
				break; 
			}
			
		}
		
		
	
		if(leftHex != null) {
			//System.out.println("Move unit.individualsInCover: "+individualsInCover);
			leftHex.usedPositions -= individualsInCover;
			
			/*if(leftHex.usedPositions < 0)
				leftHex.usedPositions = 0;*/
			
			for(Unit adjacentUnit : gameWindow.initiativeOrder) {
				
				if(leftHex.xCord == adjacentUnit.X && leftHex.yCord == adjacentUnit.Y && !adjacentUnit.compareTo(this)) {
					if(!adjacentUnit.behavior.equals("No Contact")) {
						adjacentUnit.seekCover(leftHex, gameWindow);
					} else {
						//System.out.println("Pass No Contact");
					}
					
				}
			}
		} 
		
		X = xCord;
		Y = yCord;
		

		Hex newHex = null;
		for(Hex hex : gameWindow.hexes) {
			
			if(hex.xCord == X && hex.yCord == Y) {
				newHex = hex; 
				break; 
			}
			
		}
		
		
		for(Trooper trooper : individuals) {
			if(trooper.inBuilding(gameWindow))
				trooper.inCover = true; 
			else
				trooper.inCover = false; 
		}
		individualsInCover = 0;
		
		gameWindow.closeCombatCheck();
		
		if(newHex == null && !GameWindow.gameWindow.hexGrid.panel.hideU) {
			
			new HexWindow(true, X, Y, this, openUnitWindow);
			
			
		} else {
			
			// Sets unit cover 
			//System.out.println("Seek Cover Open Unit");
			if(!behavior.equals("No Contact")) {
				seekCover(newHex, gameWindow);
			} else {
				//System.out.println("Pass 2 No Contact");
			}
			
			
		}
		
		for(Trooper trooper : individuals) {
			
			float time; 
			
			if(gameWindow.game.getPhase() == 1) {
				if(trooper.P1 != 0)
					time = 60 / trooper.P1;
				else 
					time = 60; 
			} else {
				if(trooper.P2 != 0)
					time = 60 / trooper.P2;
				else 
					time = 60; 
			}
			
			if(time < 20) {
				time = 20; 
			}
			
			if(speed.equals("Crawl")) {
				trooper.fatigueSystem.AddLightActivityTime(time);
			} else if(speed.equals("Crawl") && trooper.fatigueSystem.analeticValue <= 40) {
				trooper.fatigueSystem.AddStrenuousActivityTime(time);
			} else if(speed.equals("Rush")) {
				trooper.fatigueSystem.AddStrenuousActivityTime(time);
			} else if(speed.equals("Walk")) {
				trooper.fatigueSystem.AddLightActivityTime(time);
			}
			
		}
		
		if(openUnitWindow != null) {
			openUnitWindow.refreshIndividuals();
			openUnitWindow.refreshSpinners();
		}
		
		GameWindow.gameWindow.CalcLOS(this);
	}
	
	public void setFiredWeapons() {
		
		for(Trooper trooper : individuals) {
		
			if(!trooper.firedWeapons)
				trooper.firedTracers = false; 
			
			trooper.firedWeapons = false; 
			
		}
		
	}
	
	public int active() {
		int c = 0; 
		for(Trooper trooper : individuals) {
			if(trooper.alive && trooper.conscious && trooper.physicalDamage <= 0) 
				if(!trooper.entirelyMechanical || trooper.ionDamage <= 0)
					c++; 
			
		}
		
		return c; 
		
	}
	
	public int inactive() {
		int d = 0; 
		for(Trooper trooper : individuals)
			if(!trooper.alive || !trooper.conscious)
				d++;
		return d; 
	}
	
	public int woundedActive() {
		int w = 0; 
		for(Trooper trooper : individuals)
			if(trooper.physicalDamage > 0 && trooper.alive && trooper.conscious)
				if(!trooper.entirelyMechanical || trooper.ionDamage <= 0)
					w++;
			
		return w; 
	}
	
	public Hex getHex(GameWindow gameWindow) {
		
		for(Hex hex : gameWindow.hexes)
			if(hex.xCord == X && hex.yCord == Y)
				return hex; 
		
		
		return null; 
	}
	
	public Trooper getLeader() {
		
		for(Trooper trooper : individuals) {
			if(!trooper.alive || !trooper.conscious)
				continue; 
			return trooper; 
		}
		
		return null; 
		
	}
	
	//Gets and prints out unit details 
	@Override
	   public String toString(){
	      String unit = "";
	      if(side.equals("Empty")) {
	    	  unit = this.callsign+"; Orgnaization: "+this.organization+"; Moral: "+this.moral+"; Suppression: "+this.suppression+";";
	      } else {
	    	  unit = "Init: "+this.initiative+"; "+this.side+"::    "+this.callsign+"; Orgnaization: "+this.organization+"; Moral: "+this.moral+"; Suppression: "+this.suppression+";";
	      }
	    		  
	      return unit;
	   } 
	
	
}
