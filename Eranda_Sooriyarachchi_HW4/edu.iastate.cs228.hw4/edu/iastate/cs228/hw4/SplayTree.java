package edu.iastate.cs228.hw4;


import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * 
 * @author Eranda Sooriyarachchi
 *
 */


/**
 * 
 * This class implements a splay tree.  Add any helper methods or implementation details 
 * you'd like to include.
 *
 */


public class SplayTree<E extends Comparable<? super E>> extends AbstractSet<E>
{
	protected Node root; 
	protected int size; 

	public class Node  // made public for grading purpose
	{
		public E data;
		public Node left;
		public Node parent;
		public Node right;

		public Node(E data) {
			this.data = data;
		}

		@Override
		public Node clone() {
			return new Node(data);
		}	
	}

	
	/**
	 * Default constructor constructs an empty tree. 
	 */
	public SplayTree() 
	{
		size = 0;
	}
	

	/**
	 * Needs to call addBST() later on to complete tree construction.
	 */
	public SplayTree(E data) {
		root = new Node(data);
		size = 1;
	}

	/**
	 * Copies over an existing splay tree. Deep copying must be done. No splaying.
	 *
	 * @param tree
	 */
	public SplayTree(SplayTree<E> tree) {
		root = cloneTreeRec(tree.root);
		size = tree.size;
	}

	public Node cloneTreeRec(Node subTree) {
		if (subTree == null)
			return null;
		Node clone = subTree.clone();
		clone.left = cloneTreeRec(subTree.left);
		if (clone.left != null)
			clone.left.parent = clone;
		clone.right = cloneTreeRec(subTree.right);
		if (clone.right != null)
			clone.right.parent = clone;
		return clone;
	}

	/**
	 * This function is here for grading purpose. It is not a good programming practice. This method is fully implemented and should not be modified.
	 *
	 * @return root of the splay tree
	 */
	public E getRoot() {
		return root == null ? null : root.data;
	}



	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		root = null;
		size = 0;
	}

	// ----------
	// BST method
	// ----------
		
	/**
	 * Adds an element to the tree without splaying.  The method carries out a binary search tree
	 * addition.  It is used for initializing a splay tree. 
	 * 
	 * Calls link(). 
	 * 
	 * @param data
	 * @return true  if addition takes place  
	 *         false otherwise (i.e., data is in the tree already)
	 */
	
	public boolean addBST(E data)
	{
		Node current = root, prev = null;
		while (current != null) 
		{
			prev = current;
			if (data.compareTo(current.data) == 0)
			{
				return false;
			}
			else if (data.compareTo(current.data) < 0)
			{
				current = current.left;
			}
			else
			{
				current = current.right;
			}
		}
		Node n = new Node(data);
		link(prev, n);
		size++;
		return true;
	}

	// ------------------
	// Splay tree methods 
	// ------------------
		
	/**
	 * Inserts an element into the splay tree. In case the element was not contained, this  
	 * creates a new node and splays the tree at the new node. If the element exists in the 
	 * tree already, it splays at the node containing the element. 
	 * 
	 * Calls link(). 
	 * 
	 * @param  data  element to be inserted
	 * @return true  if addition takes place 
	 *         false otherwise (i.e., data is in the tree already)
	 */
	@Override
	public boolean add(E data) 
	{
		Node exNode = findEntry(data);
		if (exNode != null && data.compareTo(exNode.data) == 0) 
		{
			splay(exNode);
			return false;
		}
		Node newNode = new Node(data);
		link(exNode, newNode);
		size++;
		splay(newNode);
		return true;
	}

	/**
	 * Determines whether the tree contains an element.  Splays at the node that stores the 
	 * element.  If the element is not found, splays at the last node on the search path.
	 * 
	 * @param  data  element to be determined whether to exist in the tree
	 * @return true  if the element is contained in the tree 
	 *         false otherwise
	 */
	
	public boolean contains(E data)
	{
		Node entry = findEntry(data);
		if (entry == null)
		{
			return false;
		}
		splay(entry);
		return entry.data.compareTo(data) == 0;
	}



	/**
	 * Finds the node that stores the data and splays at it.
	 *
	 * @param data
	 */
	public void splay(E data)
	{
		contains(data);
	}
	
	/**
	 * Removes the node that stores an element.  Splays at its parent node after removal
	 * (No splay if the removed node was the root.) If the node was not found, the last node 
	 * encountered on the search path is splayed to the root.
	 * 
	 * Calls unlink(). 
	 * 
	 * @param  data  element to be removed from the tree
	 * @return true  if the object is removed 
	 *         false if it was not contained in the tree 
	 */
	public boolean remove(E data) 
	{
		if (size == 0)
		{
			return false;
		}
		Node n = findEntry(data);
		if (n.data.compareTo(data) == 0) 
		{
			unlink(n);
			if (n.parent != null)
			{
				splay(n.parent);
			}
			return true;
		}
		splay(n);
		return false;
	}
	
	
	
	/**
	 * This method finds an element stored in the splay tree that is equal to data as decided 
	 * by the compareTo() method of the class E.  This is useful for retrieving the value of 
	 * a pair <key, value> stored at some node knowing the key, via a call with a pair 
	 * <key, ?> where ? can be any object of E.   
	 * 
	 * Calls findEntry(). Splays at the node containing the element or the last node on the 
	 * search path. 
	 * 
	 * @param  data
	 * @return element such that element.compareTo(data) == 0
	 */

	public E findElement(E data) {
		Node entry = findEntry(data);
		splay(entry);
		return entry == null || entry.data.compareTo(data) != 0 ? null : entry.data;
	}

	/**
	 * Finds the node that stores an element. It is called by methods such as contains(), add(), remove(), 
	 * and findElement(). 
	 * 
	 * No splay at the found node. 
	 *
	 * @param  data  element to be searched for 
	 * @return node  if found or the last node on the search path otherwise
	 *         null  if size == 0. 
	 */
	
	public Node findEntry(E data)
	{
		Node current = root, prev = null;
		while (current != null) 
		{
			prev = current;
			if (data.compareTo(current.data) == 0)
			{
				return current;
			}
			else if (data.compareTo(current.data) < 0)
			{
				current = current.left;
			}
			else
			{
				current = current.right;
			}
		}
		return prev;
	}

	/** 
	 * Join the two subtrees T1 and T2 rooted at root1 and root2 into one.  It is 
	 * called by remove(). 
	 * 
	 * Precondition: All elements in T1 are less than those in T2. 
	 * 
	 * Access the largest element in T1, and splay at the node to make it the root of T1.  
	 * Make T2 the right subtree of T1.  The method is called by remove(). 
	 * 
	 * @param root1  root of the subtree T1 
	 * @param root2  root of the subtree T2 
	 * @return the root of the joined subtree
	 */
	
	public Node join(Node root1, Node root2) {
		if (root1 == null)
			return root2;
		Node max = root1;
		while (max.right != null)
			max = max.right;
		splay(max);
		max.right = root2;
		if (root2 != null)
			root2.parent = max;
		return max;
	}

	/**
	 * Splay at the current node.  This consists of a sequence of zig, zigZig, or zigZag 
	 * operations until the current node is moved to the root of the tree.
	 * 
	 * @param current  node to splay
	 */
	
	protected void splay(Node current) 
	{
		if (current == null)
		{
			return;
		}
		while (current.parent != null)
		{
			if (current.parent.parent == null)
			{
				zig(current);
			}
			else if (current.parent.left == current && current.parent.parent.left == current.parent
					|| current.parent.right == current && current.parent.parent.right == current.parent)
			{
				zigZig(current);
			}
			else
			{
				zigZag(current);
			}
		}
	}

	/**
	 * This method performs the zig operation on a node. Calls leftRotate() 
	 * or rightRotate().
	 * 
	 * @param current  node to perform the zig operation on
	 */
	
	protected void zig(Node current)
	{
		if (current == null || current.parent == null)
		{
			throw new IllegalStateException();
		}
		else if (current.parent.right == current)
		{
			leftRotate(current);
		}
		else
		{
			rightRotate(current);
		}
	}

	/**
	 * This method performs the zig-zig operation on a node. Calls leftRotate() 
	 * or rightRotate().
	 * 
	 * @param current  node to perform the zig-zig operation on
	 */
	protected void zigZig(Node current)
	{
		if (current == null || current.parent == null || current.parent.parent == null)
		{
			throw new IllegalStateException();
		}
		// current & current.parent are both left child
		else if (current.parent.left == current && current.parent.parent.left == current.parent)
		{
			rightRotate(current.parent);
			rightRotate(current);
			return;
		}
		// current & current.parent are both right child
		else if (current.parent.right == current && current.parent.parent.right == current.parent) {
			leftRotate(current.parent);
			leftRotate(current);
			return;
		}
		// zig-zig invalid
		else
		{
			throw new IllegalStateException();
		}
	}

	/**
	 * This method performs the zig-zag operation on a node. Calls leftRotate() 
	 * and rightRotate().
	 * 
	 * @param current  node to perform the zig-zag operation on
	 */
	
	protected void zigZag(Node current)
	{
		if (current == null || current.parent == null || current.parent.parent == null)
		{
			throw new IllegalStateException();
		}
		// current is a left child, current.parent is a right child
		else if (current.parent.left == current && current.parent.parent.right == current.parent)
		{
			rightRotate(current);
			leftRotate(current);
			return;
		}
		// current is a right child, current.parent is a left child
		else if (current.parent.right == current && current.parent.parent.left == current.parent) 
		{
			leftRotate(current);
			rightRotate(current);
			return;
		}
	}

	/**
	 * Carries out a left rotation at a node such that after the rotation 
	 * its former parent becomes its left child. 
	 * 
	 * Calls link(). 
	 * 
	 * @param current
	 */
	public void leftRotate(Node current) 
	{
		if (current == null || current.parent == null || current == current.parent.left)
		{
			throw new IllegalStateException();
		}
		Node parent = current.parent;

		if (parent == root)
		{
			root = current;
		}

		link(parent.parent, current);

		parent.right = current.left;
		if (current.left != null)
		{
			current.left.parent = parent;
		}
		current.left = parent;
		parent.parent = current;
	}

	/**
	 * Carries out a right rotation at a node such that after the rotation 
	 * its former parent becomes its right child. 
	 * 
	 * Calls link(). 
	 * 
	 * @param current
	 */
	
	public void rightRotate(Node current)
	{
		if (current == null || current.parent == null || current == current.parent.right)
		{
			throw new IllegalStateException();
		}

		Node parent = current.parent;

		if (parent == root)
		{
			root = current;
		}
		link(parent.parent, current);

		parent.left = current.right;
		if (current.right != null)
		{
			current.right.parent = parent;
		}

		current.right = parent;
		parent.parent = current;
	}
	
	/**
	 * Establish the parent-child relationship between two nodes. 
	 * 
	 * Called by addBST(), add(), leftRotate(), and rightRotate(). 
	 * 
	 * @param parent
	 * @param child
	 */

	private void link(Node parent, Node child) 
	{
		if (child == null)
		{
			return;
		}
		child.parent = parent;

		if (parent == null) 
		{
			if (root == null)
			{
				root = child;
			}
			return;
		}

		if (child.data.compareTo(parent.data) == 0)
		{
			return;
		}
		if (child.data.compareTo(parent.data) < 0)
		{
			parent.left = child;
		}
		else
		{
			parent.right = child;
		}
	}
	
	/** 
	 * Removes a node n by replacing the subtree rooted at n with the join of the node's
	 * two subtrees.
	 * 
	 * Called by remove().   
	 * 
	 * @param n
	 */

	private void unlink(Node n) 
	{
		if (n == null)
		{
			return;
		}
		// when n is leaf
		if (n.left == null && n.right == null) 
		{
			if (n == root)
			{				
				root = null;
			}
			else if (n.parent.left == n)
			{
				n.parent.left = null;
			}
			else if (n.parent.right == n)
			{
				n.parent.right = null;
			}
			size--;
			return;
		}

		// make n unaccessible from its children
		if (n.left != null)
		{
			n.left.parent = null;
		}
		if (n.right != null)
		{
			n.right.parent = null;
		}

		// join subtrees of n
		if (n == root)
		{
			root = join(n.left, n.right);
		}
		else
		{
			link(n.parent, join(n.left, n.right));
		}
		size--;
	}
	
	
	/**
	 * Perform BST removal of a node. 
	 * 
	 * Called by the iterator method remove(). 
	 * @param n
	 */
	public void unlinkBST(Node n)
	{
		if (n.left != null && n.right != null)
		{
			Node s = successor(n);
			n.data = s.data;
			n = s;
		}

		// n has at most one child
		Node exNode = null;
		if (n.left != null)
		{
			exNode = n.left;
		}
		else if (n.right != null)
		{
			exNode = n.right;
		}

		// link exNode into tree in place of node n
		if (n.parent == null) {
		{
			root = exNode;
		}
			
		}
		else if (n == n.parent.left) 
		{
			n.parent.left = exNode;
			
		}
		else 
		{
			n.parent.right = exNode;
			
		}
		if (exNode != null)
		{
			exNode.parent = n.parent;
		}
		size--;
	}
	
	/**
	 * Called by unlink() and the iterator method next(). 
	 * 
	 * @param n
	 * @return successor of n 
	 */

	public Node successor(Node n)
	{
		if (n == null)
		{
			return null;
		}
		if (n.right == null) 
		{
			while (n != root && n.parent.right == n)
			{
				n = n.parent;
			}
			if (n == root)
			{
				n = null;
			}
			else
			{
				n = n.parent;
			}
		} 
		else 
		{
			n = n.right;
			while (n.left != null)
			{
				n = n.left;
			}
		}
		return n;
	}

	@Override
	public Iterator<E> iterator() {
		return new SplayTreeIterator();
	}

	/**
	 * Write the splay tree according to the format specified in Section 2.2 of the project 
	 * description.
	 * 	
	 * Calls toStringRec(). 
	 *
	 */
	@Override
	public String toString() 
	{
		return toStringRec(root, 0);
	}
	

	private String toStringRec(Node n, int depth) 
	{
		String s = "";
		for (int i = 0; i < depth; i++)
		{
			s += "    ";
		}
		if (n == null)
		{
			return s + n + "\n";
		}
		s += n.data + "\n";
		if (n.left == null && n.right == null) 
		{
			return s;
		}
		s += toStringRec(n.left, depth + 1);
		s += toStringRec(n.right, depth + 1);
		return s;
	}

	

	/**
	   *
	   * Iterator implementation for this splay tree.  The elements are returned in 
	   * ascending order according to their natural ordering.  The methods hasNext()
	   * and next() are exactly the same as those for a binary search tree --- no 
	   * splaying at any node as the cursor moves.  The method remove() method
        * should not splay.
	   */
	
	private class SplayTreeIterator implements Iterator<E> 
	{
		Node cursor;
		Node pending;

		public SplayTreeIterator() 
		{
			cursor = root;
			while (cursor != null && cursor.left != null)
				cursor = cursor.left;
		}

		@Override
		public boolean hasNext() 
		{
			return cursor != null;
		}

		@Override
		public E next() 
		{
			if (!hasNext())
				throw new NoSuchElementException();
			pending = cursor;
			cursor = successor(cursor);
			return pending.data;
		}

		 /**
	     * This method will join the left and right subtrees of the node being removed, 
	     * but will not perform a splay operation. 
	     * 
	     * Calls unlinkBST(). 
	     * 
	     */
		@Override
		public void remove() 
		{
			if (pending == null)
				throw new IllegalStateException();
			unlinkBST(pending);
			
			pending = null;
		}
	}
}

