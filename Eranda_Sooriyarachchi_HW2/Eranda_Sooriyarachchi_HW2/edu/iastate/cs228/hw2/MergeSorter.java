package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;
import java.util.InputMismatchException;

/**
 *
 * @author Eranda Sooriyrachchi
 *
 */

/**
 *
 * This class implements the mergesort algorithm.
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if needed


	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also
	 * set the instance variables algorithm in the superclass.
	 *
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts)
	{
		super(pts);
		// Done
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 *
	 */
	@Override
	public void sort()
	{
		// Done
		mergeSortRec(points);
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
		if(pts.length <=1)
		{
			return;
		}

		int n = points.length; // length of array
		int l = n / 2; // length of left sub array or mid point
		int r = n - l; // length of right sub array

		// left and right sub arrays
		Point[] leftSub = new Point[l];
		Point[] rightSub = new Point[r];

		for(int i = 0; i < l; i++) // Copy left side of array to left sub array
		{
			leftSub[i] = points[i];
		}

		for(int j = n - 1; j >= l; j--) // start from the end and go down to the median
		{
			rightSub[j] = points[j];
		}

		mergeSortRec(leftSub); // sort left half
		mergeSortRec(rightSub); // sort right half
		merge(leftSub, rightSub); // merges the two sorted halves
	}


	private Point[] merge(Point[] left, Point[] right)
	{

		Point[] mergedArray = new Point[left.length + right.length]; // merged array

		int i = 0; // starting index in left
		int j = 0; // starting index in right
		final int leftMax = left.length; // max index in left subarray
		final int rightMax = right.length; // max index in right subaray
		int k = 0; // index in merged array

		while (i < leftMax && j < rightMax) {
			if (pointComparator.compare(left[i], right[j]) == 0 // checks if they are equal
					|| pointComparator.compare(left[i], right[j]) < 0) { // checks if first is less than second
				mergedArray[k] = left[i];
				++i;
				++k;
			} else {
				mergedArray[k] = right[j];
				++j;
				++k;
			}
		}
		while (i < leftMax) {
			mergedArray[k] = left[i];
			++i;
			++k;
		}
		while (j < rightMax) {
			mergedArray[k] = right[j];
			++j;
			++k;
		}

		return mergedArray;
	}

	

}
