package Vehicle.UnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Vehicle.Data.VehicleMovementData;
import Vehicle.HullDownPositions.HullDownPosition;

public class VehicleMovementTests {

	
	@Test
	public void EnterHullDownPositionTest() {
		
		var data = new VehicleMovementData();
		var hullDownPosition = new HullDownPosition();
		
		data.enterHullDownPosition(hullDownPosition);
		assertEquals(hullDownPosition, data.hullDownPosition);
		
	}
	
}
