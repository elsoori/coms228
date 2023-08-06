package edu.iastate.cs228.hw1;

/**
 * 
 * @author Eranda Sooriyarachchi 
 * 
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;


class PlainTest {

	@Test
	void testPlainConstructor() throws FileNotFoundException {
		Plain plain = new Plain("public1-3x3.txt");
		assertEquals(State.GRASS, plain.grid[0][0].who());
	}

	@Test
	void testPlainConstructor2() {
		Plain plain = new Plain(3);
		plain.grid[1][1] = new Badger(plain, 1, 1, 0);
		assertEquals(State.BADGER, plain.grid[1][1].who());
	}

	@Test
	void testRandom() {
		Plain pl = new Plain(3);
		pl.randomInit();
		String first = pl.toString();
		Plain pl1 = new Plain(3);
		pl1.randomInit();
		String second = pl1.toString();
		
		assertEquals(true, first != second);

	}
	@After
	@Test
	void testWrite() throws FileNotFoundException {
		Plain plain = new Plain("public1-3x3.txt");
		plain.write("write.txt");
		Plain pn = new Plain("write.txt");
		assertEquals(State.GRASS, pn.grid[0][0].who());
	}

}
