package edu.iastate.cs228.hw2;

/**
 *
 * @author  Eranda Sooriyarachchi
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.Comparator;
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
	protected String outputFileName;
	protected Comparator<Point> pointComparator = null;

	protected long scanTime;  // execution time in ns.





	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy
	 * the points into the array points[].
	 *
	 * @param  pts  input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if (pts == null || pts.length == 0)
		{
			throw new IllegalArgumentException();
		}
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++)
		{
			Point p = new Point(pts[i]);
			points[i] = p;
		}
		sortingAlgorithm = algo;
		if (sortingAlgorithm == Algorithm.InsertionSort)
		{
			outputFileName = "insert.txt";
		}
		else if (sortingAlgorithm == Algorithm.MergeSort)
		{
			outputFileName = "merge.txt";
		}
		else if (sortingAlgorithm == Algorithm.QuickSort)
		{
			outputFileName = "quick.txt";
		}
		else if (sortingAlgorithm == Algorithm.SelectionSort)
		{
			outputFileName = "selection.txt";
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

		sortingAlgorithm = algo;
		if (sortingAlgorithm == Algorithm.InsertionSort)
		{
			outputFileName = "insert.txt";
		}
		else if (sortingAlgorithm == Algorithm.MergeSort)
		{
			outputFileName = "merge.txt";
		}
		else if (sortingAlgorithm == Algorithm.QuickSort)
		{
			outputFileName = "quick.txt";
		}
		else if (sortingAlgorithm == Algorithm.SelectionSort)
		{
			outputFileName = "selection.txt";
		}

		File file = new File(inputFileName);
		Scanner scan = new Scanner(file); // Scanner for getting length
		Scanner sc = new Scanner(file); // Scanner for getting points
		int numCount = 0;
		int length = 0;
		while (scan.hasNextInt())
		{
			scan.nextInt();
			numCount++;
		}
		if (numCount % 2 != 0)
		{
			scan.close();
			sc.close();
			throw new InputMismatchException();
		}
		length = numCount / 2;
		points = new Point[length];
		for (int i = 0; i < length; i++)
		{
			points[i] = new Point(sc.nextInt(), sc.nextInt());
		}
		scan.close();
		sc.close();
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
		if (sortingAlgorithm == Algorithm.InsertionSort)
		{
			aSorter = new InsertionSorter(points);
		}
		else if (sortingAlgorithm == Algorithm.MergeSort)
		{
			aSorter = new MergeSorter(points);
		}
		else if (sortingAlgorithm == Algorithm.QuickSort)
		{
			aSorter = new QuickSorter(points);
		}
		else if (sortingAlgorithm == Algorithm.SelectionSort)
		{
			aSorter = new SelectionSorter(points);
		}
		Point x1, y1;
		int x, y;
		// Sort by x
		long xStart = System.nanoTime();
		aSorter.setComparator(0);
		aSorter.sort();
		x1 = aSorter.getMedian();
		x = x1.getX();
		long xTime = System.nanoTime() - xStart;
		// Sort by y
		long yStart = System.nanoTime();
		aSorter.setComparator(1);
		aSorter.sort();
		y1 = aSorter.getMedian();
		y = y1.getY();
		long yTime = System.nanoTime() - yStart;
		// Set median coordinate point
		medianCoordinatePoint = new Point(x, y);
		aSorter.getPoints(points);
		scanTime = xTime + yTime;
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
		return sortingAlgorithm.toString() + " " + points.length + " " + scanTime;
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
		File f = new File(outputFileName);
		PrintWriter pw = new PrintWriter(f);
		for(Point point : points)
		{
			pw.write(point.getX() + ", " + point.getY() + "\n");
		}

		pw.write(toString()); //write toString()
		pw.close();
	}
}
