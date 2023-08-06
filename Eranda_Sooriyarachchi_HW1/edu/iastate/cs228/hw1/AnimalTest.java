package edu.iastate.cs228.hw1;

/**
 * 
 * @author Eranda Sooriyarachchi 
 * 
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class FoxTest {

	@Test
	void test0() {
		Plain pl = new Plain(3);
		Fox fox = new Fox(p, 0, 0, 6);
		assertEquals(State.EMPTY, fox.next(pl).who());
	}

	
	@Test
	void test1() throws FileNotFoundException {
		Plain pl = new Plain("FoxCase2And3");
		Fox fox1 = (Fox) pl.grid[1][1];
		Fox fox2 = (Fox) pl.grid[4][4];
		assertEquals(State.BADGER, fox1.next(pl).who());
		assertEquals(State.EMPTY, fox2.next(pl).who());
	}
}
