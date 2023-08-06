package edu.iastate.cs228.hw1;

/**
 *
 * @author Eranda Sooriyarachchi 
 *
 */
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class WildlifeTest {

	@Test
	void testUpdatePlain() throws FileNotFoundException {
		Plain plain1 = new Plain(3);
		Plain plain2 = new Plain("GrassCase1");
		Wildlife.updatePlain(plain2, plain1);
		assertEquals(State.EMPTY, plain1.grid[2][0].who());
	}
	
}
