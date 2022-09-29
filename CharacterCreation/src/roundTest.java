import static org.junit.Assert.*;

import org.junit.Test;

public class roundTest {

	
	Character character = new Character();
	
	@Test
	public void test() {
		assertEquals(10, roundToNearestTen(99));
		assertEquals(4, roundToNearestTen(44));
		assertEquals(5, roundToNearestTen(49));
		assertEquals(6, roundToNearestTen(45));
		
	}

	public int roundToNearestTen(int val) {
		return (int) Math.round(((double)val / 10.0)); 
	}
	
}
