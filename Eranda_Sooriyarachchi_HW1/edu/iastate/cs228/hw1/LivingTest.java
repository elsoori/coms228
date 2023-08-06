package edu.iastate.cs228.hw1;

/**
 * 
 * @author Eranda Sooriyarachchi 
 * 
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;


class LivingTest {

	final int BADGER = 0;
	final int EMPTY = 1;
	final int FOX = 2;
	final int GRASS = 3;
	final int RABBIT = 4;

	@Test// Left top
	void testEdgeCase1() throws FileNotFoundException {
		int[] population = new int[5];
		Plain pl1 = new Plain("public1-3x3.txt");
		pl1.grid[0][0].census(population);
		assertEquals(1, population[BADGER]);
		assertEquals(2, population[FOX]);
	}

	@Test // Top
	void testEdgeCase2() throws FileNotFoundException {
		int[] population = new int[5];
		Plain pl2 = new Plain("public3-10x10.txt");
		pl2.grid[0][4].census(population);
		assertEquals(2, population[EMPTY]);
		assertEquals(1, population[FOX]);
		assertEquals(2, population[RABBIT]);


	}

	@Test //Right top 
	void testEdgeCase3() throws FileNotFoundException {
		int[] population = new int[5];
		Plain pl3 = new Plain("public2-6x6.txt");
		pl3.grid[0][5].census(population);
		assertEquals(2, population[EMPTY]);
		assertEquals(1, population[GRASS]);
		assertEquals(1, population[RABBIT]);
	}


	@Test
	void testEdgCase4() throws FileNotFoundException {
		int[] population = new int[5];
		Plain pl4 = new Plain("public3-10x10.txt");
		pl4.grid[1][9].census(population);
		assertEquals(1, population[EMPTY]);
		assertEquals(5, population[GRASS]);
		
	}
	
	@Test
	void testEdgeCase5() throws FileNotFoundException {
		int[] population = new int[5];
		Plain pl5 = new Plain("public3-10x10.txt");
		pl5.grid[9][0].census(population);
		assertEquals(3, population[GRASS]);
		assertEquals(1, population[EMPTY]);
	}

	@Test
	void testEdgeCase6() throws FileNotFoundException {
		int[] population = new int[5];
		Plain pl6 = new Plain("public3-10x10.txt");
		pl6.grid[9][2].census(population);
		assertEquals(2, population[EMPTY]);
		assertEquals(3, population[GRASS]);
		assertEquals(1, population[FOX]);
	}

	@Test
	void testEdgeCase7() throws FileNotFoundException {
		int[] population = new int[5];
		Plain pl7 = new Plain("public3-10x10.txt");
		pl7.grid[9][9].census(population);
		assertEquals(4, population[GRASS]);
	}

	@Test
	void testEdgeCase8() throws FileNotFoundException {
		int[] population = new int[5];
		Plain pl8 = new Plain("public3-10x10.txt");
		pl8.grid[8][9].census(population);
		assertEquals(5, population[GRASS]);
		assertEquals(1, population[EMPTY]);
	}

	@Test
	void testUsualCase() throws FileNotFoundException {
		int[] population = new int[5];
		Plain p1 = new Plain("public3-10x10.txt");
		p1.grid[1][1].census(population);
		assertEquals(2, population[EMPTY]);
		assertEquals(4, population[GRASS]);
		assertEquals(3, population[BADGER]);
	}
	
	@Test
	void testUsualCase2() throws FileNotFoundException {
		int[] population = new int[5];
		Plain p2 = new Plain("public3-10x10.txt");
		p2.grid[1][3].census(population);
		assertEquals(3, population[EMPTY]);
		assertEquals(2, population[GRASS]);
		assertEquals(3, population[BADGER]);
		assertEquals(1, population[FOX]);
	}
}
