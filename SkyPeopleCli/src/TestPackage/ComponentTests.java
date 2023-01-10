package TestPackage;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Ship.Cell;
import Ship.Component;
import Ship.Ship;
import Ship.Ship.ShipType;

public class ComponentTests {
	
	Ship venator; 
	
	@Before
	public void before() {
		venator = new Ship(ShipType.VENATOR);
	}
	
	@Test
	public void shieldTest() {
		assertEquals(true, venator.shieldStrength == 500.0);
	}
	
	@Test
	public void thrustTest() {
		
		Component thrust = venator.components.get(0);
		
		assertEquals(17, thrust.cells.size());
		assertEquals(true, thrust.cells.get(0).number == 4.0);
		assertEquals(true, thrust.cells.get(16).number == 0);
	}
	
	@Test 
	public void reactorTest() {
		
		Component reactors = venator.components.get(1);
		
		assertEquals(31, reactors.cells.size());
		assertEquals(true, reactors.cells.get(0).number == 15.0);
		assertEquals(true, reactors.cells.get(30).number == 0.0);
	}

	@Test
	public void siTest() {
		Component si = venator.components.get(2);
		
		assertEquals(48, si.cells.size());
		
	}
	
	@Test
	public void bridgeTest() {
		
		Component bridge = venator.components.get(3);
		
		assertEquals(12, bridge.cells.size());
		
	}
	
	@Test
	public void lifeSupportTest() {
		
		Component lifeSupport = venator.components.get(4);
		
		assertEquals(9, lifeSupport.cells.size());
		
	}

	@Test
	public void hyperDrive() {
		Component hyperDrive = venator.components.get(5);
		assertEquals(8, hyperDrive.cells.size());
	}
	
	@Test 
	public void radiatorTest() {
		Component radiator = venator.components.get(6);
		assertEquals(11, radiator.cells.size());
	}
	
	@Test
	public void batteryTest() {
		Component battery = venator.components.get(7);
		assertEquals(45, battery.cells.size());
	}
	
	@Test
	public void heatSinkTest() {
		Component heatSink = venator.components.get(8);
		assertEquals(42, heatSink.cells.size());
	}
	
	@Test
	public void testCargo() {
		Component cargo = venator.components.get(9);
		
		assertEquals(58, cargo.cells.size());
		
		ArrayList<Cell> foundCells = new ArrayList<>(); 
		
		for(Cell cell : cargo.cells) {
			if(cell.character != ' ' 
					|| cell.character == 'M' 
					|| cell.character == 'R') {
				foundCells.add(cell);
			}
		}
		
		assertEquals(6, foundCells.size());
	}
	
	@Test
	public void testQuarters() {
		Component quarters = venator.components.get(10);
		assertEquals(40, quarters.cells.size());
	}
	
	@Test
	public void testHangar() {
		Component hangar = venator.components.get(11);
		assertEquals(40, hangar.cells.size());
	}
	
	@Test
	public void testVehicleBay() {
		Component bays = venator.components.get(12);
		assertEquals(24, bays.cells.size());
	}
	
	@Test
	public void testPointDefense() {
		Component comp = venator.components.get(13);
		assertEquals(12, comp.cells.size());
	}
	
	@Test
	public void testShield() {
		Component comp = venator.components.get(14);
		assertEquals(5, comp.cells.size());
	}
	
	@Test
	public void getEcm() {
		assertEquals(6, venator.getEcm());
	}
	
	@Test
	public void getEccm() {
		assertEquals(6, venator.getEccm());
	}
	
}
