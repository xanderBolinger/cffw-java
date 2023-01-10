package ShipCreation;

import java.util.ArrayList;

import Ship.Cell;
import Ship.Cell.CellType;
import Ship.Component;
import Ship.Component.ComponentType;
import Ship.Description;
import Ship.ElectronicCell;
import Ship.ElectronicCell.ElectronicType;
import Ship.Electronics;
import Ship.Fuel;
import Ship.HardPoint;
import Ship.HitLocation;
import Ship.HitLocation.LocationType;
import Ship.HitTable;
import Ship.Ship;
import Ship.Weapon.FireType;
import Ship.Weapon.WeaponType;

public class Munificent implements ShipTemplate {

	public Ship ship; 
	
	public Munificent(Ship ship) {
		this.ship = ship;
		createShip();
	}
	
	@Override
	public void createShip() {
		description();
		components();
		hardPoints();
		hitTable();
		electronics();
		fuel();
		pivot();
		roll();
	}

	@Override
	public void description() {
		ship.description = new Description(""
				+ "Hoersch-Kessel Drive inc. Gwori Revolutionary Industries.", 
				"Munificent-class star frigate", 
				825, 426, 243, 
				"Light warship armed for heavy combat with oversized guns, a massive twin turbolaser cannon, and two long-range ion cannons for use against capital ships\n, "
				+ "these long-range ion cannons demonstrating the capability to shoot across a star system in some instances.");
	}

	@Override
	public void components() {
		ArrayList<Component> components = new ArrayList<>();
		
		Component thrust = new Component(ComponentType.THRUST);
		
		thrust.cells.add(new Cell(4));
		thrust.cells.add(new Cell(4));
		thrust.cells.add(new Cell(3.5));
		thrust.cells.add(new Cell(3.5));
		thrust.cells.add(new Cell(3));
		thrust.cells.add(new Cell(3));
		thrust.cells.add(new Cell(2.5));
		thrust.cells.add(new Cell(2.5));
		thrust.cells.add(new Cell(2));
		thrust.cells.add(new Cell(2));
		thrust.cells.add(new Cell(1.5));
		thrust.cells.add(new Cell(1.5));
		thrust.cells.add(new Cell(1));
		thrust.cells.add(new Cell(1));
		thrust.cells.add(new Cell(0.5));
		thrust.cells.add(new Cell(0.5));
		thrust.cells.add(new Cell(0));
		
		components.add(thrust);
		
		Component reactor = new Component(ComponentType.REACTORS);
		
		for(double i = 11; i >= 0; i -= 0.5) {
			reactor.cells.add(new Cell(i));
		}
		
		components.add(reactor);
		
		Component si = new Component(ComponentType.SI);
		
		for(int i = 0; i < 10; i++) {
			si.cells.add(new Cell(0));
		}
		
		si.cells.add(new Cell(4));
		
		for(int i = 0; i < 6; i++) {
			si.cells.add(new Cell(0));
		}
		
		si.cells.add(new Cell(5));
		
		for(int i = 0; i < 6; i++) {
			si.cells.add(new Cell(0));
		}
		
		si.cells.add(new Cell(6));
		
		for(int i = 0; i < 3; i++) {
			si.cells.add(new Cell(0));
		}
		
		si.cells.add(new Cell(7));
		si.cells.add(new Cell(8));
		si.cells.add(new Cell(9));
		si.cells.add(new Cell(10));
		si.cells.add(new Cell(0));
		
		components.add(si);
		
		ship.components = components;
		
		Component bridge = new Component(ComponentType.BRIDGE);
		
		bridge.cells.add(new Cell(CellType.BLANK));
		bridge.cells.add(new Cell(CellType.BLANK));
		bridge.cells.add(new Cell(6));
		bridge.cells.add(new Cell(CellType.BLANK));
		bridge.cells.add(new Cell(CellType.BLANK));
		bridge.cells.add(new Cell(3));
		bridge.cells.add(new Cell(CellType.BLANK));
		bridge.cells.add(new Cell(CellType.BLANK));
		bridge.cells.add(new Cell(CellType.BLANK));
		bridge.cells.add(new Cell(1));
		bridge.cells.add(new Cell(CellType.BLANK));
		bridge.cells.add(new Cell(CellType.BLANK));
		
		components.add(bridge);
		
		
		Component lifeSupport = new Component(ComponentType.LIFESUPPORT);
		
		lifeSupport.cells.add(new Cell(CellType.BLANK));
		lifeSupport.cells.add(new Cell(CellType.BLANK));
		lifeSupport.cells.add(new Cell(CellType.BLANK));
		lifeSupport.cells.add(new Cell(CellType.BLANK));
		lifeSupport.cells.add(new Cell(CellType.BLANK));
		lifeSupport.cells.add(new Cell(CellType.BLANK));
		
		components.add(lifeSupport);
		
		Component hyperDrive = new Component(ComponentType.HYPERDRIVE);
		hyperDrive.cells.add(new Cell(CellType.BLANK));
		hyperDrive.cells.add(new Cell(CellType.BLANK));
		hyperDrive.cells.add(new Cell(1));
		hyperDrive.cells.add(new Cell(1));
		hyperDrive.cells.add(new Cell(CellType.BLANK));
		hyperDrive.cells.add(new Cell(CellType.BLANK));
		hyperDrive.cells.add(new Cell(12));
		hyperDrive.cells.add(new Cell(12));
		
		components.add(hyperDrive);
		
		Component radiators = new Component(ComponentType.RADIATORS);
		
		for(double i = 12; i > 0; i -= 1.2)
			radiators.cells.add(new Cell(i));
		
		components.add(radiators);
		
		Component battery = new Component(ComponentType.BATTERY);
		
		for(int i = 232; i > 0; i -= 8) {
			battery.cells.add(new Cell(i));
		}
		
		components.add(battery);
		
		Component heatSink = new Component(ComponentType.HEATSINKS);
		
		for(int i = 150; i > 0; i -= 5) {
			heatSink.cells.add(new Cell(i));
		}
		
		components.add(heatSink);
		
		Component cargo = new Component(ComponentType.CARGO);
		
		for(int i = 25; i > 0; i -=2) {
			cargo.cells.add(new Cell(i));
			
			if(i == 15 || i == 5 ) {
				cargo.cells.add(new Cell('R'));
			}
			
			if(i == 18 || i == 10) {
				cargo.cells.add(new Cell('M'));
			}
			
		}
		
		components.add(cargo);
		
		Component quarters = new Component(ComponentType.QUARTERS);
		
		for(int i = 0; i < 10; i++) {
			
			if(i % 3 == 0) {
				quarters.cells.add(new Cell(CellType.WRENCH));
			} else if (i % 10 == 0) {
				quarters.cells.add(new Cell(CellType.CREW));
			} else {
				quarters.cells.add(new Cell(CellType.BLANK));
			}
			
		}
		
		components.add(quarters);
		
		Component hangar = new Component(ComponentType.HANGARBAY);
		
		for(int i = 0; i < 3; i++) {
			hangar.cells.add(new Cell(CellType.BLANK));
		}
		
		components.add(hangar);
		
		Component bay = new Component(ComponentType.VEHICLEBAY);
		
		for(int i = 0; i < 4; i++) {
			bay.cells.add(new Cell(CellType.BLANK));
		}
		
		components.add(bay);
		
		Component pointDefense = new Component(ComponentType.POINTDEFENSE);
		
		for(int i = 40; i > 0; i -= 5) {
			pointDefense.cells.add(new Cell(i));
		}
		
		components.add(pointDefense);
		
		Component shield = new Component(ComponentType.SHIELD);
		
		for(int i = 200; i > 0; i-= 100) {
			shield.cells.add(new Cell(i));
		}
		
		components.add(shield);
		
	}

	@Override
	public void hardPoints() {
		ArrayList<HardPoint> hardPoints = new ArrayList<>();
		
		HardPoint bowTurboLasers = new HardPoint(6,3);
		bowTurboLasers.addWeapon(WeaponType.HEAVY_TURBO_LASER, FireType.TWIN);
		bowTurboLasers.addWeapon(WeaponType.HEAVY_TURBO_LASER, FireType.TWIN);
		hardPoints.add(bowTurboLasers);
		
		HardPoint lightTurboLasers = new HardPoint(5,1);
		for(int i = 0; i < 13; i++)
			lightTurboLasers.addWeapon(WeaponType.LIGHT_TURBO_LASER, FireType.TWIN);
		hardPoints.add(lightTurboLasers);
		
		HardPoint lightTurboLasers2 = new HardPoint(5,2);
		for(int i = 0; i < 13; i++)
			lightTurboLasers2.addWeapon(WeaponType.LIGHT_TURBO_LASER, FireType.TWIN);
		hardPoints.add(lightTurboLasers2);
		
		
		HardPoint portDeckGuns = new HardPoint(8,4);
		portDeckGuns.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		portDeckGuns.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		portDeckGuns.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		portDeckGuns.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		hardPoints.add(portDeckGuns);
		
		
		HardPoint lightTurboLasers3 = new HardPoint(5,4);
		for(int i = 0; i < 10; i++)
			lightTurboLasers3.addWeapon(WeaponType.LIGHT_TURBO_LASER, FireType.TWIN);
		hardPoints.add(lightTurboLasers3);
		
		HardPoint starboardDeckGuns = new HardPoint(8,5);
		starboardDeckGuns.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		starboardDeckGuns.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		starboardDeckGuns.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		starboardDeckGuns.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		
		HardPoint lightTurboLasers4 = new HardPoint(5,5);
		for(int i = 0; i < 10; i++)
			lightTurboLasers4.addWeapon(WeaponType.LIGHT_TURBO_LASER, FireType.TWIN);
		hardPoints.add(lightTurboLasers4);
		
		ship.hardPoints = hardPoints;
	}

	@Override
	public void hitTable() {
		ArrayList<HitLocation> nose = new ArrayList<HitLocation>();
		nose.add(new HitLocation(ComponentType.BRIDGE, 6));
		nose.add(new HitLocation(ComponentType.QUARTERS, 8));
		nose.add(new HitLocation(ComponentType.CARGO, 8));
		nose.add(new HitLocation(ComponentType.HANGARBAY, 8));
		nose.add(new HitLocation(0, 4));
		nose.add(new HitLocation(1, 4));
		nose.add(new HitLocation(2, 4));
		nose.add(new HitLocation(ComponentType.BATTERY, 8));
		nose.add(new HitLocation(ComponentType.BATTERY, 8));
		nose.add(new HitLocation(ComponentType.POINTDEFENSE, 6));
		
		ArrayList<HitLocation> aft = new ArrayList<HitLocation>();
		aft.add(new HitLocation(ComponentType.HYPERDRIVE, 6));
		aft.add(new HitLocation(ComponentType.HYPERDRIVE, 6));
		aft.add(new HitLocation(ComponentType.THRUST, 6));
		aft.add(new HitLocation(ComponentType.THRUST, 6));
		aft.add(new HitLocation(ComponentType.THRUST, 6));
		aft.add(new HitLocation(ComponentType.HEATSINKS, 6));
		aft.add(new HitLocation(ComponentType.HEATSINKS, 6));
		aft.add(new HitLocation(LocationType.FUEL, 6));
		aft.add(new HitLocation(LocationType.FUEL, 6));
		aft.add(new HitLocation(ComponentType.POINTDEFENSE, 6));
		
		ArrayList<HitLocation> port = new ArrayList<HitLocation>();
		port.add(new HitLocation(1, 4));
		port.add(new HitLocation(3, 4));
		port.add(new HitLocation(4, 4));
		port.add(new HitLocation(ComponentType.QUARTERS, 8));
		port.add(new HitLocation(ComponentType.CARGO, 8));
		port.add(new HitLocation(ComponentType.REACTORS, 8));
		port.add(new HitLocation(ComponentType.BATTERY, 8));
		port.add(new HitLocation(ComponentType.BATTERY, 8));
		port.add(new HitLocation(LocationType.FUEL, 8));
		port.add(new HitLocation(ComponentType.POINTDEFENSE, 6));
		
		ArrayList<HitLocation> starboard = new ArrayList<HitLocation>();
		starboard.add(new HitLocation(2, 4));
		starboard.add(new HitLocation(5, 4));
		starboard.add(new HitLocation(6, 4));
		starboard.add(new HitLocation(ComponentType.QUARTERS, 8));
		starboard.add(new HitLocation(ComponentType.CARGO, 8));
		starboard.add(new HitLocation(ComponentType.REACTORS, 8));
		starboard.add(new HitLocation(ComponentType.BATTERY, 8));
		starboard.add(new HitLocation(ComponentType.BATTERY, 8));
		starboard.add(new HitLocation(LocationType.FUEL, 8));
		starboard.add(new HitLocation(ComponentType.POINTDEFENSE, 8));
		
		
		ArrayList<HitLocation> top = new ArrayList<HitLocation>();
		top.add(new HitLocation(ComponentType.BRIDGE, 8));
		top.add(new HitLocation(ComponentType.HEATSINKS, 8));
		top.add(new HitLocation(ComponentType.HANGARBAY, 12));
		top.add(new HitLocation(ComponentType.HANGARBAY, 12));
		top.add(new HitLocation(ComponentType.SHIELD, 6));
		top.add(new HitLocation(ComponentType.RADIATORS, 8));
		top.add(new HitLocation(ComponentType.BATTERY, 8));
		top.add(new HitLocation(ComponentType.BATTERY, 8));
		top.add(new HitLocation(LocationType.FUEL, 8));
		top.add(new HitLocation(ComponentType.POINTDEFENSE, 6));
		
		ArrayList<HitLocation> bottom = new ArrayList<HitLocation>();
		bottom.add(new HitLocation(0, 4));
		bottom.add(new HitLocation(1, 4));
		bottom.add(new HitLocation(2, 4));
		bottom.add(new HitLocation(ComponentType.HYPERDRIVE, 6));
		bottom.add(new HitLocation(ComponentType.THRUST, 6));
		bottom.add(new HitLocation(ComponentType.BRIDGE, 6));
		bottom.add(new HitLocation(ComponentType.HEATSINKS, 6));
		bottom.add(new HitLocation(ComponentType.REACTORS, 6));
		bottom.add(new HitLocation(LocationType.FUEL, 6));
		bottom.add(new HitLocation(ComponentType.POINTDEFENSE, 6));
		
		ArrayList<HitLocation> core = new ArrayList<HitLocation>();
		core.add(new HitLocation(ComponentType.HYPERDRIVE, 6));
		core.add(new HitLocation(ComponentType.REACTORS, 6));
		core.add(new HitLocation(ComponentType.LIFESUPPORT, 6));
		core.add(new HitLocation(ComponentType.BRIDGE, 6));
		core.add(new HitLocation(ComponentType.QUARTERS, 6));
		core.add(new HitLocation(ComponentType.HYPERDRIVE, 6));
		core.add(new HitLocation(ComponentType.VEHICLEBAY, 6));
		core.add(new HitLocation(ComponentType.BATTERY, 6));
		core.add(new HitLocation(LocationType.ELECTRONICS, 6));
		core.add(new HitLocation(ComponentType.SI, 6));
		
		
		HitTable hitTable =  new HitTable(nose, aft, port, starboard, top, bottom, core);
		
		hitTable.noseSkinArmor = 12;
		hitTable.aftSkinArmor = 6;
		hitTable.portSkinArmor = 8; 
		hitTable.starboardSkinArmor = 8;
		hitTable.topSkinArmor = 8;
		hitTable.bottomSkinArmor = 6; 
		
		hitTable.noseAftDepth = ship.description.lengthMeters;
		hitTable.portStarboardDepth = ship.description.widthMeters;
		hitTable.topBottomDepth = ship.description.heightMeters;
		
		ship.hitTable = hitTable;
		
	}

	@Override
	public void electronics() {
		ArrayList<ElectronicCell> cells = new ArrayList<>();
		
		cells.add(new ElectronicCell(0, 0, 0, ElectronicType.COMMUNICATIONS));
		cells.add(new ElectronicCell(0, 0, 0, ElectronicType.NAVCOMPUTER));
		cells.add(new ElectronicCell(50, 1, 1, ElectronicType.SENSORS));
		cells.add(new ElectronicCell(200, 2, 2, ElectronicType.SENSORS));
		cells.add(new ElectronicCell(0, 5, 5, ElectronicType.SENSORS));
		
		ship.electronics = new Electronics(cells);
	}

	@Override
	public void fuel() {
		ship.fuel = new Fuel(8, 100, 10);
		
	}

	@Override
	public void pivot() {
		
		ship.pivotChart = 'M';
		
	}

	@Override
	public void roll() {
		ship.rollChart = 'K';
	}	

}
