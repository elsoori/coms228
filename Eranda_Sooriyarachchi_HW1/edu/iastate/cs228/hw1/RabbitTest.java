package edu.iastate.cs228.hw1;

/**
 * 
 * @author Eranda Sooriyarachchi 
 * 
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class RabbitTest {

	@Test
	void testCase1And2() throws FileNotFoundException {
		Plain plain = new Plain("RabbitCase1And2");
		Rabbit rabbit1 = (Rabbit) plain.grid[0][1];
		Rabbit rabbit2 = (Rabbit) plain.grid[2][2];
		assertEquals(State.EMPTY, rabbit1.next(plain).who());
		assertEquals(State.EMPTY, rabbit2.next(plain).who());
	}

	@Test
	void testCase3() throws FileNotFoundException {
		Plain pl = new Plain("RabbitCase3And4");
		Rabbit rabbit1 = (Rabbit) pl.grid[1][1];
		Rabbit rabbit2 = (Rabbit) pl.grid[5][5];
		assertEquals(State.FOX, rabbit1.next(pl).who());
		assertEquals(State.BADGER, rabbit2.next(pl).who());
	}
}
