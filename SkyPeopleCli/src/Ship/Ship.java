package Ship;

import java.util.ArrayList;

import Ship.Component.ComponentType;
import ShipCreation.Venator;

public class Ship {

	public String shipName;
	public boolean destroyed;
	public char pivotChart;
	public char rollChart;
	public Description description;
	public ArrayList<Component> components;
	public ArrayList<HardPoint> hardPoints;
	public HitTable hitTable;
	public Electronics electronics;
	
	public Fuel fuel;
	
	public Compliment complement;
	public Ammunition ammunition;

	public double power;
	public double heat;
	
	public enum ShipType {
		VENATOR
	}

	public Ship(ShipType shipType) {

		destroyed = false;
		
		switch (shipType) {
		case VENATOR:
			new Venator(this);
			break;
		}

	}

	public void destroyComponent(ComponentType compType) {

		for (Component component : components) {
			for (Cell cell : component.cells) {
				if (!cell.destroyed) {
					cell.destroyed = true;
					break;
				}
			}
		}

	}

	@Override
	public String toString() {
		String rslts = ""; 
		
		
		rslts += shipName + "\n";
		if(destroyed)
			rslts += "DESTROYED\n";
		rslts += "Pivot: "+pivotChart+", Roll: "+ rollChart + "\n";
		rslts += description.toString() + "\n";

		for(Component comp : components) {
			rslts += comp.toString() + "\n\n";
		}
		
		int i = 1; 
		for(HardPoint hardPoint : hardPoints) {
			rslts += hardPoint.toString(i) + "\n";
			i++;
		}
		
		rslts += hitTable.toString() + "\n";
		
		rslts += fuel.toString() + "\n";
		
		return rslts; 
	}
	
	
}
