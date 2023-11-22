package HexGrid.Tests;

import java.util.Arrays;
import java.util.List;

import CorditeExpansion.Cord;
import HexGrid.HexDirectionUtility;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class HexDirectionUtilityTests {
	
	@Test
	public void NeighboursTest() {
		
		// Neighbours of 1,1: [0,1, 0,2, 1,2, 2,1, 1,0, 0,0]
        Cord pos1 = new Cord(1, 1);
        List<Cord> neighbors1 = HexDirectionUtility.getHexNeighbourCords(pos1);
        List<Cord> expectedNeighbors1 = Arrays.asList(
                new Cord(0, 1),
                new Cord(0, 2),
                new Cord(1, 2),
                new Cord(2, 1),
                new Cord(1, 0),
                new Cord(0, 0)
        );
        

        // Neighbours of 2,2: [1,2, 2,3, 3,3, 3,2, 3,1, 2,1]
        Cord pos2 = new Cord(2, 2);
        List<Cord> neighbors2 = HexDirectionUtility.getHexNeighbourCords(pos2);
        List<Cord> expectedNeighbors2 = Arrays.asList(
                new Cord(1, 2),
                new Cord(2, 3),
                new Cord(3, 3),
                new Cord(3, 2),
                new Cord(3, 1),
                new Cord(2, 1)
        );


        // Neighbours of 2,1: [1,1, 1,2, 2,2, 3,1, 2,0, 1,0]
        Cord pos3 = new Cord(2, 1);
        List<Cord> neighbors3 = HexDirectionUtility.getHexNeighbourCords(pos3);
        List<Cord> expectedNeighbors3 = Arrays.asList(
                new Cord(1, 1),
                new Cord(1, 2),
                new Cord(2, 2),
                new Cord(3, 1),
                new Cord(2, 0),
                new Cord(1, 0)
        );


        // Neighbours of 1,2: [0,2, 1,3, 2,3, 2,2, 2,1, 1,1]
        Cord pos4 = new Cord(1, 2);
        List<Cord> neighbors4 = HexDirectionUtility.getHexNeighbourCords(pos4);
        List<Cord> expectedNeighbors4 = Arrays.asList(
                new Cord(0, 2),
                new Cord(1, 3),
                new Cord(2, 3),
                new Cord(2, 2),
                new Cord(2, 1),
                new Cord(1, 1)
        );

        testNeighbours(neighbors1, expectedNeighbors1);
        testNeighbours(neighbors2, expectedNeighbors2);
        testNeighbours(neighbors3, expectedNeighbors3);
        testNeighbours(neighbors4, expectedNeighbors4);
	}
	
	private void testNeighbours(List<Cord> neighbors1,  List<Cord> expectedNeighbors1) {
		assertEquals(expectedNeighbors1.get(0).xCord, neighbors1.get(0).xCord);
        assertEquals(expectedNeighbors1.get(0).yCord, neighbors1.get(0).yCord);
        assertEquals(expectedNeighbors1.get(1).xCord, neighbors1.get(1).xCord);
        assertEquals(expectedNeighbors1.get(1).yCord, neighbors1.get(1).yCord);
        assertEquals(expectedNeighbors1.get(2).xCord, neighbors1.get(2).xCord);
        assertEquals(expectedNeighbors1.get(2).yCord, neighbors1.get(2).yCord);
        assertEquals(expectedNeighbors1.get(3).xCord, neighbors1.get(3).xCord);
        assertEquals(expectedNeighbors1.get(3).yCord, neighbors1.get(3).yCord);
        assertEquals(expectedNeighbors1.get(4).xCord, neighbors1.get(4).xCord);
        assertEquals(expectedNeighbors1.get(4).yCord, neighbors1.get(4).yCord);
        assertEquals(expectedNeighbors1.get(5).xCord, neighbors1.get(5).xCord);
        assertEquals(expectedNeighbors1.get(5).yCord, neighbors1.get(5).yCord);
	}
	
}
