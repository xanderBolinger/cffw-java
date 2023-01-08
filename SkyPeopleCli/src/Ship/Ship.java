package Ship;

import java.util.ArrayList;

import ShipCreation.Venator;

public class Ship {

	public String shipName;
	public Description description;
	public ArrayList<Component> components;
	public ArrayList<HardPoint> hardPoints;
	public HitTable hitTable;
	public Electronics electronics;
	public Fuel fuel;
	public char pivotChart;
	public char rollChart;
	
	public Compliment complement;
	public Ammunition ammunition;
	
	public double power;
	
	public enum ShipType {
		VENATOR
	}
	
	public Ship(ShipType shipType) {
		
		switch(shipType) {
			case VENATOR:
				new Venator(this);
				break;
		}
		
	}
	
}
