package edu.iastate.cs228.hw1;

/**
 * 
 * @author Eranda Sooriyarachchi 
 *
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/*
 * Grass class test cases
 */
class GrassTest {

	@Test
	void testCase1() throws FileNotFoundException {
		Plain plain = new Plain("GrassCase1");
		Grass grass = (Grass) plain.grid[2][0];
		assertEquals(State.EMPTY, grass.next(plain).who());
	}

	@Test
	void testCase2() throws FileNotFoundException {
		Plain plain = new Plain("GrassCase2");
		Grass grass = (Grass) plain.grid[1][1];
		assertEquals(State.RABBIT, grass.next(plain).who());
	}
}
