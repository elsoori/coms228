package edu.iastate.cs228.hw2;

/**
 *
 * @author  Eranda Sooriyarachchi
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y
 * coordinates are respectively the medians of the x and y coordinates of the original points.
 *
 * It records the employed sorting algorithm as well as the sorting time for comparison.
 *
 */
public class PointScanner
{
	private Point[] points;

	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of
	// the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;


	protected long scanTime; 	       // execution time in nanoseconds.

	private String fileName;



	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy
	 * the points into the array points[].
	 *
	 * @param  pts  input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		int n = pts.length;
		sortingAlgorithm = algo;
		points = new Point[n];

		for(int i = 0; i < n - 1; i++)
		{
			points[i] = pts[i];
		}
	}


	/**
	 * This constructor reads points from a file.
	 *
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		fileName = inputFileName;
		sortingAlgorithm = algo;
		ArrayList<Point> temp = new ArrayList<>();
		File fl = new File(inputFileName);
		Scanner s = new Scanner(fl);

		while(s.hasNextInt())
		{
			try
			{
				temp.add(new Point(s.nextInt(), s.nextInt()));

			}
			catch(NullPointerException e)
			{
				throw new InputMismatchException("input file error");
			}
		}
		points = new Point[temp.size()];
		temp.toArray(points);
		s.close();
	}


	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:
	 *
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate.
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.
	 *
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		AbstractSorter aSorter = null;

		if(sortingAlgorithm == Algorithm.InsertionSort)
		{
			InsertionSorter insertion = new InsertionSorter(points);
			aSorter = insertion;
		}
		if(sortingAlgorithm == Algorithm.MergeSort)
		{
			MergeSorter mergeSort = new MergeSorter(points);
			aSorter = mergeSort;
		}
		if(sortingAlgorithm == Algorithm.QuickSort)
		{
			QuickSorter quickSort = new QuickSorter(points);
			aSorter = quickSort;
		}
		if(sortingAlgorithm == Algorithm.SelectionSort)
		{
			SelectionSorter selection = new SelectionSorter(points);
			aSorter = selection;
		}

		long startTime = System.nanoTime();
		for(int i = 0; i < 2; i++) // two rounds of sorting
		{
			aSorter.setComparator(i); // call setComparator() with an argument 0 or 1.
			aSorter.sort(); // call sort().
			Point medCoord = new Point(); // use a new Point object to store the coordinates of the medianCoordinatePoint
			medianCoordinatePoint = aSorter.getMedian();
			medCoord = medianCoordinatePoint; //use a new Point object to store the coordinates of the medianCoordinatePoint

		}
		long endTime = System.nanoTime();
		scanTime = endTime - startTime; // sum up the times spent on the two sorting rounds and set the instance variable scanTime



	}


	/**
	 * Outputs performance statistics in the format:
	 *
	 * <sorting algorithm> <size>  <time>
	 *
	 * For instance,
	 *
	 * selection sort   1000	  9200867
	 *
	 * Use the spacing in the sample run in Section 2 of the project description.
	 */
	public String stats()
	{
		String size = ("" + "points.length" + "");
		return sortingAlgorithm + size + scanTime;
	}


	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space
	 * in between.
	 */
	@Override
	public String toString()
	{
		return "MCP: (" + medianCoordinatePoint.getX() + ", " + medianCoordinatePoint.getY() + ")";
	}


	/**
	 *
	 * This method, called after scanning, writes point data into a file by outputFileName. The format
	 * of data in the file is the same as printed out from toString().  The file can help you verify
	 * the full correctness of a sorting result and debug the underlying algorithm.
	 *
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		System.out.print("File name: ");
		Scanner input = new Scanner(System.in);
		fileName = input.next();
		File fl = new File(fileName);

		try
		{
			fl.createNewFile();
			System.out.println("File created: " + fl.getName());
		}
		catch(IOException e)
		{
			System.out.println("write error");
			e.printStackTrace();
		}
		input.close();
	}



}
