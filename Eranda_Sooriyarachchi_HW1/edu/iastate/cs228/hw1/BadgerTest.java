package edu.iastate.cs228.hw1;

/**
 * 
 * @author Eranda Sooriyarachchi 
 * 
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class BadgerTest {

	@Test
	void test0() throws FileNotFoundException {
		Plain plain = new Plain("public1-3x3.txt");
		Badger badger = (Badger) plain.grid[0][1];
		assertEquals(State.FOX, badger.next(plain).who());
	}

	@Test
	void test1() throws FileNotFoundException {
		Plain plain = new Plain("public2-6x6.txt");
		Badger badger = (Badger) plain.grid[2][3];
		assertEquals(State.EMPTY, badger.next(plain).who());
	}
	