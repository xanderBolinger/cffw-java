package ShipCreation;

import java.util.ArrayList;

import Ship.Cell;
import Ship.Cell.CellType;
import Ship.Component;
import Ship.Component.ComponentType;
import Ship.Description;
import Ship.Fuel;
import Ship.HardPoint;
import Ship.Ship;
import Ship.Weapon.FireType;
import Ship.Weapon.WeaponType;

public class Venator implements ShipTemplate {

	public Ship ship; 
	
	public Venator(Ship ship) {
		this.ship = ship;
		createShip();
	}
	
	@Override
	public void createShip() {
		description();
		components();
		hardPoints();
		fuel();
		
	}

	@Override
	public void description() {
		ship.description = new Description("Kuat Drive Yards, Allanteen Six Shipyards", "Venator-class Star Destroyer", 1137, 558, 268, "he Venator-class Star Destroyer,[1] also known as the Venator-class Destroyer,[20] Republic attack cruiser,[3] and later Imperial attack cruiser,[21] was one of the capital ships used extensively by the Galactic Republic during the later parts of the Clone Wars, as well as by the Galactic Empire. It was designed and constructed by Kuat Drive Yards and Allanteen Six shipyards.");
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
		
		for(double i = 15; i >= 0; i -= 0.5) {
			reactor.cells.add(new Cell(i));
		}
		
		components.add(reactor);
		
		Component si = new Component(ComponentType.SI);
		
		for(int i = 0; i < 17; i++) {
			si.cells.add(new Cell(0));
		}
		
		si.cells.add(new Cell(4));
		
		for(int i = 0; i < 10; i++) {
			si.cells.add(new Cell(0));
		}
		
		si.cells.add(new Cell(5));
		
		for(int i = 0; i < 10; i++) {
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
		
		for(int i = 360; i > 0; i -= 8) {
			battery.cells.add(new Cell(i));
		}
		
		components.add(battery);
		
		Component heatSink = new Component(ComponentType.HEATSINKS);
		
		for(int i = 210; i > 0; i -= 5) {
			heatSink.cells.add(new Cell(i));
		}
		
		components.add(heatSink);
		
		Component cargo = new Component(ComponentType.CARGO);
		
		for(int i = 104; i > 0; i -=2) {
			cargo.cells.add(new Cell(i));
			
			if(i == 66 || i == 34 || i == 12) {
				cargo.cells.add(new Cell('R'));
			}
			
			if(i == 76 || i == 50 || i == 26) {
				cargo.cells.add(new Cell('M'));
			}
			
		}
		
		components.add(cargo);
		
		Component quarters = new Component(ComponentType.QUARTERS);
		
		for(int i = 0; i < 40; i++) {
			
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
		
		for(int i = 0; i < 40; i++) {
			hangar.cells.add(new Cell(CellType.BLANK));
		}
		
		components.add(hangar);
		
		Component bay = new Component(ComponentType.VEHIClEBAY);
		
		for(int i = 0; i < 24; i++) {
			bay.cells.add(new Cell(CellType.BLANK));
		}
		
		components.add(bay);
		
		Component pointDefense = new Component(ComponentType.POINTDEFENSE);
		
		for(int i = 60; i > 0; i -= 5) {
			pointDefense.cells.add(new Cell(i));
		}
		
		components.add(pointDefense);
		
		Component shield = new Component(ComponentType.SHIELD);
		
		for(int i = 500; i > 0; i-= 100) {
			shield.cells.add(new Cell(i));
		}
		
		components.add(shield);
		
	}

	@Override
	public void hardPoints() {
		ArrayList<HardPoint> hardPoints = new ArrayList<>();
		
		HardPoint turboLaserPort1 = new HardPoint(6,1);
		turboLaserPort1.addWeapon(WeaponType.MEDIUM_TURBO_LASER, FireType.TWIN);
		hardPoints.add(turboLaserPort1);
		
		HardPoint turboLaserPort2 = new HardPoint(6,1);
		turboLaserPort2.addWeapon(WeaponType.MEDIUM_TURBO_LASER, FireType.TWIN);
		hardPoints.add(turboLaserPort2);
		
		HardPoint turboLaserStarboard1 = new HardPoint(6,2);
		turboLaserStarboard1.addWeapon(WeaponType.MEDIUM_TURBO_LASER, FireType.TWIN);
		hardPoints.add(turboLaserStarboard1);
		
		HardPoint turboLaserStarboard2 = new HardPoint(6,2);
		turboLaserStarboard2.addWeapon(WeaponType.MEDIUM_TURBO_LASER, FireType.TWIN);
		hardPoints.add(turboLaserStarboard2);
		
		HardPoint heavyProtonTorpedoLauncher = new HardPoint(10,3);
		heavyProtonTorpedoLauncher.addWeapon(WeaponType.HEAVY_PROTON_TORPEDO, FireType.SINGLE);
		hardPoints.add(heavyProtonTorpedoLauncher);
		
		HardPoint heavyProtonTorpedoLauncher2 = new HardPoint(10,3);
		heavyProtonTorpedoLauncher2.addWeapon(WeaponType.HEAVY_PROTON_TORPEDO, FireType.SINGLE);
		hardPoints.add(heavyProtonTorpedoLauncher2);
		
		HardPoint heavyProtonTorpedoLauncher3 = new HardPoint(10,3);
		heavyProtonTorpedoLauncher3.addWeapon(WeaponType.HEAVY_PROTON_TORPEDO, FireType.SINGLE);
		hardPoints.add(heavyProtonTorpedoLauncher3);
		
		HardPoint heavyProtonTorpedoLauncher4 = new HardPoint(10,3);
		heavyProtonTorpedoLauncher4.addWeapon(WeaponType.HEAVY_PROTON_TORPEDO, FireType.SINGLE);
		hardPoints.add(heavyProtonTorpedoLauncher4);
		
		HardPoint portDeckGuns = new HardPoint(8,4);
		portDeckGuns.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		portDeckGuns.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		hardPoints.add(portDeckGuns);
		
		HardPoint portDeckGuns2 = new HardPoint(8,4);
		portDeckGuns2.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		portDeckGuns2.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		hardPoints.add(portDeckGuns2);
		
		HardPoint portDeckGuns3 = new HardPoint(8,4);
		portDeckGuns3.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		portDeckGuns3.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		hardPoints.add(portDeckGuns3);
		
		HardPoint starboardDeckGuns = new HardPoint(8,5);
		starboardDeckGuns.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		starboardDeckGuns.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		hardPoints.add(starboardDeckGuns);
		
		HardPoint starboardDeckGuns1 = new HardPoint(8,5);
		starboardDeckGuns1.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		starboardDeckGuns1.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		hardPoints.add(starboardDeckGuns1);
		
		HardPoint starboardDeckGuns2 = new HardPoint(8,5);
		starboardDeckGuns2.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		starboardDeckGuns2.addWeapon(WeaponType.DECK_GUN, FireType.SINGLE);
		hardPoints.add(starboardDeckGuns2);
		
		ship.hardPoints = hardPoints;
	}

	@Override
	public void hitTable() {
		
	}

	@Override
	public void electronics() {
		
	}

	@Override
	public void fuel() {
		ship.fuel = new Fuel(8, 150, 10);
		
	}

	@Override
	public void pivot() {
		
	}

	@Override
	public void roll() {
		
	}

}
