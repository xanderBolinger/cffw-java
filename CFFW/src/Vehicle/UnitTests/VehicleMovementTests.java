package Vehicle.UnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Vehicle.Data.VehicleMovementData;
import Vehicle.HullDownPositions.HullDownPosition;
import Vehicle.HullDownPositions.HullDownPosition.HullDownStatus;

public class VehicleMovementTests {

	
	@Test
	public void EnterHullDownPositionTest() {
		
		var data = new VehicleMovementData();
		var hullDownPosition = new HullDownPosition(HullDownStatus.HIDDEN, HullDownStatus.PARTIAL_HULL_DOWN);
		
		data.enterHullDownPosition(hullDownPosition);
		assertEquals(hullDownPosition, data.hullDownPosition);
		assertEquals(HullDownStatus.HIDDEN, data.hullDownStatus);
	}
	
	@Test 
	public void MovementHullDownTest() {
		
		var data = new VehicleMovementData();
		var hullDownPosition = new HullDownPosition(HullDownStatus.HIDDEN, HullDownStatus.PARTIAL_HULL_DOWN);
		
		data.enterHullDownPosition(hullDownPosition);
		
		data.inchBack();
		
		assertEquals(HullDownStatus.HIDDEN, data.hullDownStatus);
		
		data.inchForward();
		
		assertEquals(HullDownStatus.TURRET_DOWN, data.hullDownStatus);
		
		data.inchForward();
		data.exitHullDownPosition();
		assertEquals(true, data.hullDown());
		assertEquals(HullDownStatus.HULL_DOWN, data.hullDownStatus);
		
		data.inchForward();
		
		assertEquals(HullDownStatus.PARTIAL_HULL_DOWN, data.hullDownStatus);
		
		data.inchForward();
		
		assertEquals(HullDownStatus.PARTIAL_HULL_DOWN, data.hullDownStatus);
		
		data.inchBack();
		
		assertEquals(HullDownStatus.HULL_DOWN, data.hullDownStatus);
		
		data.inchBack();
		
		assertEquals(HullDownStatus.TURRET_DOWN, data.hullDownStatus);
		
		data.inchBack();
		
		assertEquals(HullDownStatus.HIDDEN, data.hullDownStatus);
		
		data.exitHullDownPosition();
		assertEquals(false, data.hullDown());
		
		
		data.enterHullDownPosition(hullDownPosition);
		data.inchForward();
		data.inchForward();
		data.inchForward();
		data.exitHullDownPosition();
		assertEquals(false, data.hullDown());
	}
	
	
	
}
