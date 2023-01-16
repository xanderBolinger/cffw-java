package Ship;

import java.util.ArrayList;

import javax.swing.RowFilter.ComparisonType;

import CLI.Command.CommandType;
import Game.GameMaster;
import Mechanics.DiceRoller;
import Mechanics.HeatIndex;
import Ship.Cell.CellType;
import Ship.Component.ComponentType;
import ShipCreation.Dreadnaught;
import ShipCreation.Munificent;
import ShipCreation.Recusant;
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

	//public Compliment complement;
	//public Ammunition ammunition;

	public double power;
	public double drawnReactorPower;
	public double heat;
	public double heatIndex;
	public double shieldStrength;
	public double shieldGenerationValue;

	public double reactorSet; 
	public boolean extendedRadiators;
	
	public enum ShipType {
		VENATOR,MUNIFICENT,DREADNAUGHT,RECUSANT
	}

	public Ship(ShipType shipType) {

		destroyed = false;

		switch (shipType) {
		case VENATOR:
			new Venator(this);
			break;
		case MUNIFICENT:
			new Munificent(this);
			break;
		case DREADNAUGHT:
			new Dreadnaught(this);
			break;
		case RECUSANT:
			new Recusant(this);
			break;
		}

		shieldStrength = getShieldStrength();
		power = getPower();
		shieldGenerationValue = getNumberComponent(ComponentType.REACTORS);
		this.reactorSet = getNumberComponent(ComponentType.REACTORS);
		this.extendedRadiators = false;
	}

	public Ship(Ship other) {
		this.shipName = other.shipName;
		this.destroyed = other.destroyed;
		this.pivotChart = other.pivotChart;
		this.rollChart = other.rollChart;
		this.description = new Description(other.description);
		this.components = new ArrayList<>(other.components);
		this.hardPoints = new ArrayList<>(other.hardPoints);
		this.hitTable = new HitTable(other.hitTable);
		this.electronics = new Electronics(other.electronics);
		this.fuel = new Fuel(other.fuel);
		//this.complement = new Compliment(other.complement);
		//this.ammunition = new Ammunition(other.ammunition);
		this.power = other.power;
		this.drawnReactorPower = other.drawnReactorPower;
		this.heat = other.heat;
		this.heatIndex = other.heatIndex;
		this.shieldStrength = other.shieldStrength;
		this.shieldGenerationValue = other.shieldGenerationValue;
		this.reactorSet = other.reactorSet;
		this.extendedRadiators = other.extendedRadiators;
	}

	public void destroyComponent(ComponentType compType) {

		for (Component component : components) {
			if (component.componentType != compType)
				continue;

			for (Cell cell : component.cells) {

				if (!cell.destroyed) {

					siCheck(compType, cell);

					cell.destroyed = true;
					if (ComponentType.BATTERY == compType) {
						double newPower = getPower();
						if (newPower < power) {
							power = newPower;
						}
					}

					break;
				}

			}
		}

	}

	public void siCheck(ComponentType compType, Cell cell) {
		if (compType == ComponentType.SI) {
			if (cell.cellType == CellType.NUMBER) {
				int roll = DiceRoller.d10();
				if (roll <= cell.number) {
					destroyed = true;
					System.out.println("SHIP: "+shipName+", DESTROYED");
					if(GameMaster.game != null && GameMaster.game.destroyed != null
							&& !GameMaster.game.destroyed.contains(this)) {
						GameMaster.game.destroyed.add(this);
						GameMaster.game.ships.remove(this);
					}
				}
			}
		}
	}

	public double getHeat() {
		for (Component component : components) {
			if (component.componentType != ComponentType.HEATSINKS)
				continue;

			for (Cell cell : component.cells) {

				if (!cell.destroyed && cell.cellType == CellType.NUMBER) {
					return cell.number;
				}

			}
		}

		return -1;
	}

	public double getPower() {
		for (Component component : components) {
			if (component.componentType != ComponentType.BATTERY)
				continue;

			for (Cell cell : component.cells) {

				if (!cell.destroyed && cell.cellType == CellType.NUMBER) {
					return cell.number;
				}

			}
		}

		return -1;
	}

	public double getShieldStrength() {
		for (Component component : components) {
			if (component.componentType != ComponentType.SHIELD)
				continue;

			for (Cell cell : component.cells) {

				if (!cell.destroyed && cell.cellType == CellType.NUMBER) {

					return cell.number;
				}

			}
		}

		return -1;
	}

	public double getNumberComponent(ComponentType compType) {
		for (Component component : components) {
			if (component.componentType != compType)
				continue;

			for (Cell cell : component.cells) {

				if (!cell.destroyed && cell.cellType == CellType.NUMBER) {

					return cell.number;
				}

			}
		}

		return -1;
	}

	public int getEcm() {
		int ecm = 0;

		for (ElectronicCell ec : electronics.cells) {
			if (ec.ecm > ecm) {
				ecm = ec.ecm;
			}
		}

		return ecm;
	}

	public int getEccm() {
		int eccm = 0;

		for (ElectronicCell ec : electronics.cells) {
			if (ec.eccm > eccm) {
				eccm = ec.eccm;
			}
		}

		return eccm;
	}

	public void generationStep() {
		if(reactorSet == 0) {
			heatCheck();
			radiatorCheck();
			return;
		}
		
		double toGenerate = getPower() - power;
		double toGenerateShield = getShieldStrength() - shieldStrength;
		double reactor = getNumberComponent(ComponentType.REACTORS);

		if(reactor < shieldGenerationValue) {
			shieldGenerationValue = reactor;
		}
		
		if(reactorSet < reactor) {
			reactor = reactorSet;
		}
		
		if (toGenerateShield > 0) {
			double shieldGenerationValue = reactorSet < this.shieldGenerationValue ? reactorSet : this.shieldGenerationValue;
			shieldGenerationValue = toGenerateShield < shieldGenerationValue ? toGenerateShield : shieldGenerationValue;
			
			shieldStrength += shieldGenerationValue;
			reactor -= shieldGenerationValue;
			heat += shieldGenerationValue;
		}

		if (reactor > toGenerate) {
			power = getPower();
			heat += toGenerate;
		} else {
			power += reactor;
			heat += reactor;
		}

		heatCheck();
		radiatorCheck();
	}
	
	public void heatCheck() {
		double heatIndex = HeatIndex.heatIndex(this);
		
		if(heatIndex > 0) {
			System.out.println("WARNING Ship: "+shipName+", Heat Index: "+heatIndex);
		}
		
	}
	
	public void radiatorCheck() {
		
		if(!extendedRadiators)
			return;
		
		double radiatedHeat = getNumberComponent(ComponentType.RADIATORS);
		
		heat -= radiatedHeat;
		
		if(heat < 0)
			heat = 0;
		
	}

	public static ShipType getType(String shipType) {
		shipType = shipType.toUpperCase();
		if (shipType.equals("VENATOR")) {
			return ShipType.VENATOR;
		} else if (shipType.equals("MUNIFICENT")) {
			return ShipType.MUNIFICENT;
		} else if (shipType.equals("DREADNAUGHT")) {
			return ShipType.DREADNAUGHT;
		} else if (shipType.equals("RECUSANT")) {
			return ShipType.RECUSANT;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		String rslts = "";

		rslts += shipName + "\n";
		if (destroyed)
			rslts += "DESTROYED\n";
		
		rslts += "Shield Strength: " + shieldStrength + "\n";
		rslts += "Recharge Rate: " + shieldGenerationValue + "\n";
		rslts += "Power: " + power + "\n";
		rslts += "Set Reactor Rate: " + reactorSet + "\n";
		rslts += "Heat: " + heat + "\n";
		rslts += "Extended Radiators: "+extendedRadiators+"\n";
		rslts += "Pivot: " + pivotChart + ", Roll: " + rollChart + "\n";
		rslts += description.toString() + "\n";

		for (Component comp : components) {
			rslts += comp.toString() + "\n\n";
		}

		int i = 1;
		for (HardPoint hardPoint : hardPoints) {
			rslts += hardPoint.toString(i) + "\n";
			i++;
		}

		rslts += hitTable.toString() + "\n";

		rslts += fuel.toString() + "\n";

		return rslts;
	}

}
