package JUnitTests;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CorditeExpansion.Cord;
import HexGrid.HexDirectionUtility;

public class HexDirectionTests {

	@Test
	public void HexNeighbours() {
		
		var neighbours = HexDirectionUtility.getHexNeighbours(new Cord(5,21));
		
		assertEquals(4,neighbours.get(0).xCord);
		
	}

}
