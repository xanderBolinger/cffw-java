package Company;

import java.io.Serializable;
import java.util.ArrayList;

import Trooper.Trooper;
import Unit.Unit;
import Vehicle.Vehicle;

public class Company implements Serializable {
	// Basic infomration
	// Units
	// Indiviudals
	private String name;
	private String side;
	private int platoons;
	private int squads;
	// Stat block
	private int commandPoints;
	private int commandValue;
	private int attrition;
	private int supplyPoints;
	// Roster and units arrays
	private ArrayList<Trooper> roster = new ArrayList<Trooper>();
	private ArrayList<Unit> units = new ArrayList<Unit>();
	public ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	// Notes
	private String notes;
	// Activated
	private boolean activated;

	public Company(String side, String name, int platoons, int squads, int commandPoints, int commandValue,
			int attrition, int supplyPoints, ArrayList<Trooper> roster, ArrayList<Unit> units) {
		// Basic information
		this.side = side;
		this.name = name;
		this.platoons = platoons;
		this.squads = squads;
		// Stat block
		this.commandPoints = commandPoints;
		this.commandValue = commandValue;
		this.attrition = attrition;
		this.supplyPoints = supplyPoints;
		// Sets roster
		this.roster = roster;
		this.units = units;
	}

	public Company() {
		
	}
	
	public Trooper getTrooper(String identifier) {
		for(var unit : units) 
			for(var trooper : unit.getTroopers())
				if(trooper.identifier.equals(identifier))
					return trooper;
		return null;
	}
	
	// SETTERS
	// Sets the values to equal the parameter
	public void setSide(String side) {
		this.side = side;
	}

	public void setName(String param) {
		name = param;
	}

	public void setPlatoons(int param) {
		platoons = param;
	}

	public void setSquads(int param) {
		squads = param;
	}

	public void setCommandPoints(int CP) {
		commandPoints = CP;
	}

	public void setCommandValue(int CV) {
		commandValue = CV;
	}

	public void setAttrition(int ATR) {
		attrition = ATR;
	}

	public void setSupplyPoints(int SP) {
		supplyPoints = SP;
	}

	public void setRoster(ArrayList<Trooper> roster) {
		this.roster = roster;
	}

	public void setUnits(ArrayList<Unit> units) {
		this.units = units;
	}

	public void addUnit(Unit unit) {
		this.units.add(unit);
	}
	
	// Removes unit 
	public void removeUnit(Unit unit) {
		for(int i = 0; i < units.size(); i++) {
			if(units.get(i).compareTo(unit)) {
				units.remove(i);
			}
		} 
	}

	// Overwrites and updates a unit in a compnay
	public void updateUnit(Unit unit) {
		for(int i = 0; i < units.size(); i++) {
			if(units.get(i).compareTo(unit)) {
				//System.out.println("Match <ade");
				units.set(i, unit);
			}
			
		}
	}

	public void setNotes(String param) {
		notes = param;
	}

	public void setActivated(boolean param) {
		activated = param;
	}

	// GET MEHTODS
	public String getSide() {
		return side;
	}

	public String getName() {
		return name;
	}

	public int getPlatoons() {
		return platoons;
	}

	public int getSquads() {
		return squads;
	}

	public int getCommandPoints() {
		return commandPoints;
	}

	public int getCommandValue() {
		return commandValue;
	}

	public int getAttrition() {
		return attrition;
	}

	public int getSupplyPoints() {
		return supplyPoints;
	}

	public ArrayList<Trooper> getRoster() {
		return roster;
	}

	public ArrayList<Unit> getUnits() {
		return units;
	}

	public String getNotes() {
		return notes;
	}

	public int getRosterLength() {
		return roster.size();
	}

	public int getUnitsLength() {
		return units.size();
	}

	public boolean getActivated() {
		return activated;
	}

	// Adds parameter change to the values
	// Decreases value if change is negative
	// Increases value if change is positive
	public void changeCommandPoints(int change) {
		this.commandPoints = this.commandPoints + change;
	}

	public void changeCommandValue(int change) {
		this.commandValue = this.commandValue + change;
	}

	public void changeAttrition(int change) {
		this.attrition = this.attrition + change;
	}

	public void changeSupplyPoints(int change) {
		this.supplyPoints = this.supplyPoints + change;
	}

	@Override
	public String toString() {
		String company = "";
		if (activated) {
			company += "ACTIVATED::    ";
		}
		company += side + "; " + name;
		return company;
	}
}
