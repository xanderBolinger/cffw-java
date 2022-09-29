package testPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Artillery.FireMission;

class ArtilleryTest {

	@Test
	void scatterTest() {
		
		/*FireMission fireMission = new FireMission(null, null, false, 20, 20, true, null);
		System.out.println("Starting Tile: 20,20");
		System.out.println("Direction 1, Scatter: 4 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 4, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 4, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 1).get(0), 20);
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 1).get(1), 16);
		System.out.println("Direction 1, Scatter: 5 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 5, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 5, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 1).get(0), 20);
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 1).get(1), 15);
		
		System.out.println("Direction 2, Scatter: 4 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 4, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 4, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 2).get(0), 22);
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 2).get(1), 17);
		System.out.println("Direction 2, Scatter: 5 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 5, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 5, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 2).get(0), 23);
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 2).get(1), 17);
		
		
		
		System.out.println("Direction 3, Scatter: 4 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 4, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 4, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 3).get(0), 24);
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 3).get(1), 18);
		System.out.println("Direction 3, Scatter: 5 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 5, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 5, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 3).get(0), 25);
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 3).get(1), 18);
		
		System.out.println("Direction 4, Scatter: 4 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 4, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 4, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 4).get(0), 24);
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 4).get(1), 20);
		System.out.println("Direction 4, Scatter: 5 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 5, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 5, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 4).get(0), 25);
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 4).get(1), 21);
		
		System.out.println("Direction 5, Scatter: 4 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 4, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 4, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 5).get(0), 24);
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 5).get(1), 22);
		System.out.println("Direction 5, Scatter: 5 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 5, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 5, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 5).get(0), 25);
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 5).get(1), 23);
		
		System.out.println("Direction 5, Scatter: 1 tile, Start Tile 23, 23");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 5, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 5, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(23, 23, 1, 5).get(0), 24);
		assertEquals(fireMission.returnScatteredHex(23, 23, 1, 5).get(1), 23);
		
		System.out.println("Direction 6, Scatter: 4 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 4, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 4, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 6).get(0), 22);
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 6).get(1), 23);
		System.out.println("Direction 6, Scatter: 5 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 5, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 5, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 6).get(0), 23);
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 6).get(1), 24);
		
		System.out.println("Direction 7, Scatter: 4 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 4, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 4, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 7).get(0), 20);
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 7).get(1), 24);
		System.out.println("Direction 7, Scatter: 5 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 5, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 5, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 7).get(0), 20);
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 7).get(1), 25);
		
		System.out.println("Direction 8, Scatter: 4 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 4, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 4, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 8).get(0), 18);
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 8).get(1), 23);
		System.out.println("Direction 8, Scatter: 5 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 5, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 5, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 8).get(0), 17);
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 8).get(1), 24);
		
		
		System.out.println("Direction 9, Scatter: 4 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 4, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 4, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 9).get(0), 16);
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 9).get(1), 22);
		System.out.println("Direction 9, Scatter: 5 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 5, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 5, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 9).get(0), 15);
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 9).get(1), 23);
		
		System.out.println("Direction 10, Scatter: 4 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 4, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 4, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 10).get(0), 16);
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 10).get(1), 20);
		System.out.println("Direction 10, Scatter: 5 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 5, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 5, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 10).get(0), 15);
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 10).get(1), 21);
		
		System.out.println("Direction 11, Scatter: 4 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 4, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 4, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 11).get(0), 16);
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 11).get(1), 18);
		System.out.println("Direction 11, Scatter: 5 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 5, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 5, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 11).get(0), 15);
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 11).get(1), 18);
		
		System.out.println("Direction 12, Scatter: 4 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 4, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 4, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 12).get(0), 18);
		assertEquals(fireMission.returnScatteredHex(20, 20, 4, 12).get(1), 17);
		System.out.println("Direction 12, Scatter: 5 tiles");
		System.out.println("End Tile: "+fireMission.returnScatteredHex(20, 20, 5, 1).get(0)+", "+
				fireMission.returnScatteredHex(20, 20, 5, 1).get(1));
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 12).get(0), 18);
		assertEquals(fireMission.returnScatteredHex(20, 20, 5, 12).get(1), 16);*/
	}

}
