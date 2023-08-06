package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;
import java.util.InputMismatchException;

/**
 *
 * @author Eranda Sooriyarachchi
 *
 */

/**
 *
 * This class implements the mergesort algorithm.
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if you need ...

	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also
	 * set the instance variables algorithm in the superclass.
	 *
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts)
	{
		// superclass constructor
		super(pts);

		// Set the instance variable algorithm
		algorithm = "mergesort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 *
	 */
	@Override
	public void sort()
	{
		mergeSortRec(this.points);
	}


	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them,
	 * and merge the two sorted subarrays into pts[].
	 *
	 * @param pts	point array
	 */
	private void mergeSortRec(Point[] pts)
	{
		// The length of the array pts
		int n = pts.length;
		int m = n / 2;

		// If n <= 1, there is nothing to sort
		if (n <= 1)
		{
			return;
		}

		// Create the new arrays for the left and right side
		Point[] left = new Point[m];
		Point[] right = new Point[n-m];

		//fill the left array
		for (int i = 0; i < m; i++)
		{
			left[i] = pts[i];
		}

		// fill the right array
		int c = 0;
		for (int i = m; i < n; i++)
		{
			right[c] = pts[i];
			c++;
		}

		// Recursively call mergeSortRec() for each new array
		mergeSortRec(left);
		mergeSortRec(right);

		//  call merge to combine two sorted arrays
		Point[] temp = new Point[pts.length];
		temp = merge(left, right);

		for (int i = 0; i < temp.length; i++) {
			pts[i] = temp[i];
		}

	}

	/**
	 * Merge two Point arrays
	 * @param B the first Point array
	 * @param C the second Point array
	 */
	private Point[] merge(Point[] B, Point[] C)
	{
		int p = B.length;
		int q = C.length;

		// Create a new Point array
		Point[] Q = new Point[p+q];

		int i = 0; int j = 0; int iter = 0;
		while ((i < p) && (j < q))
		{

			if (pointComparator.compare(B[i], C[j]) <= 0)
			{
				Q[iter++] = B[i];
				i++;
			}
			else
			{
				Q[iter++] = C[j];
				j++;
			}
		}

		if (i >= p)
		{
			for (int z = j; z < q; z++)
			{
				Q[iter++] = C[z];
			}
		}
		else
		{
			for (int z = i; z < p; z++)
			{
				Q[iter++] = B[z];
			}
		}

		return(Q);
	}



}
