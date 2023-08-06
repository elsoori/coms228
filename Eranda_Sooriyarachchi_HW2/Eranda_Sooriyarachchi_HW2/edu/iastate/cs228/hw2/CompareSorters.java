package edu.iastate.cs228.hw2;

/**
 *
 * @author Eranda Sooriyarachchi
 *
 */

/**
 *
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the
 * execution times of these algorithms on the same input.
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;



public class CompareSorters
{
	private static Scanner input = new Scanner(System.in);
	private static String fileName = "";
	private static int order;
	private static Random r = new Random();
	private static int numPts = 0;
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files.
	 * Use them as coordinates to construct points.  Scan these points with respect to their
	 * median coordinate point four times, each time using a different sorting algorithm.
	 *
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning, Keys: 1 (random integers) 2 (file input) 3 (exit): ");
		int trial = 1;
		int selection = input.nextInt();

		while(selection != 3)
		{
			System.out.println("Trial " + trial + ": " + selection);
			while (!(selection > 0 && selection < 4))
			{
				selection = input.nextInt();
			}
			if(selection == 1)
			{
				System.out.print("Enter number of random points: ");
				while(numPts < 1)
				{
					numPts  = input.nextInt();
				}
			}
			else if(selection == 2)
			{
				System.out.println("Points from a file");
				System.out.print("File name: ");
				fileName = input.next();
			}
			else if(selection == 3)
			{
				System.out.println("Selection 3, exiting");
				break;
			}
			System.out.print("Sorting order: ");
			while (order < 1)
			{
				order = input.nextInt();
			}
			System.out.println("");
			System.out.println("algorithm" + "  " + "size" + "  " + "time (ns)");
			System.out.println("----------------------------------");
			trial =  trial + 1;

			PointScanner[] scanners = new PointScanner[4];
			try
			{
				if(selection == 1)
				{
					scanners[0] = new PointScanner(generateRandomPoints(numPts, r), Algorithm.InsertionSort);
					scanners[1] = new PointScanner(generateRandomPoints(numPts, r), Algorithm.MergeSort);
					scanners[2] = new PointScanner(generateRandomPoints(numPts, r), Algorithm.QuickSort);
					scanners[3] = new PointScanner(generateRandomPoints(numPts, r), Algorithm.SelectionSort);
				}
				else if(selection == 2)
				{
					scanners[0] = new PointScanner(fileName, Algorithm.InsertionSort);
					scanners[1] = new PointScanner(fileName, Algorithm.MergeSort);
					scanners[2] = new PointScanner(fileName, Algorithm.QuickSort);
					scanners[3] = new PointScanner(fileName, Algorithm.SelectionSort);
				}
				for (PointScanner scanner : scanners)
				{
					System.out.println("Scanning");
					scanner.scan();
					System.out.println(scanner.stats());
					scanner.writeMCPToFile();
				}
			}
			catch (FileNotFoundException e)
			{
				System.out.println("file error.");
			}
		}
	}



	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range
	 * [-50,50] × [-50,50]. Please refer to Section 3 on how such points can be generated.
	 *
	 * Ought to be private. Made public for testing.
	 *
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{
		if(numPts < 1)
		{
			throw new IllegalArgumentException("Not enough points");
		}

		Point[] p = new Point[numPts];

		for (int i = 0; i < numPts; i++)
		{
			p[i] = new Point((rand.nextInt(101) - 50), (rand.nextInt(101) - 50));
		}

		return p;
	}
}