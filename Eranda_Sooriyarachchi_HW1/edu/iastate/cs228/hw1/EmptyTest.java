package edu.iastate.cs228.hw1;

/**
 * 
 * @author Eranda Sooriyarachchi 
 * 
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;


class EmptyTest {

	@Test
	void testCase1() throws FileNotFoundException {
		Plain plain1 = new Plain("public2-6x6.txt");
		Empty empty = (Empty) plain1.grid[4][4];
		assertEquals(State.RABBIT, empty.next(plain1).who());
	}

	
	@Test
	void testCase3() throws FileNotFoundException {
		Plain plain3 = new Plain("public3-10x10.txt");
		Empty empty = (Empty) plain3.grid[3][4];
		assertEquals(State.GRASS, empty.next(plain3).who());
	}

}
