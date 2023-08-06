package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author Eranda Sooriyarachchi
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 */
public class MultiList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  
  /**
   * Default number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public MultiList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public MultiList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0)
    {
    	throw new IllegalArgumentException();
    }
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public MultiList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head;
	  this.tail = tail;
	  this.nodeSize = nodeSize;
	  this.size = size;
  }
  

  @Override
  public int size()
  {
    return size;
  }
  // This is used as helper function to link new nodes
  public void link(Node current, Node newNode)
  {
	  current.next.previous = newNode;
	  newNode.next = current.next;
	  current.next = newNode;
	  newNode.previous = current;
  }
  
  // used as a helper fxn to unlink 
  public void unlink(Node current)
  {
    current.previous.next = current.next;
    current.next.previous = current.previous;
  }
  
  @Override
  public boolean add(E item)
  {
      if(item == null)
      {
          throw new NullPointerException();
      }
      if(tail.previous == head){
          Node newNode = new Node();
          newNode.previous = head;
          head.next = newNode;
          tail.previous = newNode;
          newNode.next = tail;
          newNode.addItem(item);
          ++size;
          return true;
      }
 
      else if(tail.previous.count < nodeSize)
      {
    	 tail.previous.addItem(tail.previous.count, item); 
    	 size++;
    	 return true;
      }
      else // if last node is full
      {
          Node newNode = new Node();
 
          link(tail.previous, newNode);
          newNode.addItem(item);
          ++size;
          return true;
      }
      
  }
// add E item at position pos, cursor position.
  @Override
  public void add(int pos, E item) {
		//Done
		// if pos is out of bound, then throw Exception
		if (pos < 0 || pos > size)
			throw new IndexOutOfBoundsException();

		// if the list is empty, create a new node and put X at offset 0
		if (head.next == tail)
			add(item);

		NodeInfo nodeInfo = find(pos);
		Node temp = nodeInfo.node;
		int off = nodeInfo.offset;

		// otherwise if off = 0, 2 possibilities.
		if (off == 0) {
			// If previous node isn't full.
			if (temp.previous.count < nodeSize && temp.previous != head) {
				temp.previous.addItem(item);
				size++;
				return;
			}
			// if n is the tail node and previous node is full
			else if (temp == tail) {
				add(item);
				size++;
				return;
			}
		}
		// Add to current node if the node is not full.
		if (temp.count < nodeSize) {
			temp.addItem(off, item);
		}
		// Perform the split operation
		else {
			Node newNode = new Node();
			int splitSize = nodeSize / 2;
			int count = 0;
			while (count < splitSize) {
				newNode.addItem(temp.data[splitSize]);
				temp.removeItem(splitSize);
				count++;
			}

			Node oldNode = temp.next;

			temp.next = newNode;
			newNode.previous = temp;
			newNode.next = oldNode;
			oldNode.previous = newNode;

			// if off <= M/2, put X in node n at offset off
			if (off <= nodeSize / 2) {
				temp.addItem(off, item);
			}
			// if off >= M/2, put X in node n' at offset (off - M/2)
			if (off > nodeSize / 2) {
				newNode.addItem((off - nodeSize / 2), item);
			}

		}
		// increase the size of list
		size++;
	}
// removes items from logical cursor position pos while fulfilling the various conditions that need to be checked for
@Override
public E remove(int pos) {
	//Done
	if (pos < 0 || pos > size)
		throw new IndexOutOfBoundsException();
	NodeInfo nodeInfo = find(pos);
	Node temp = nodeInfo.node;
	int off = nodeInfo.offset;
	E nodeValue = temp.data[off];

	// if in last node and only item, delete the node.
	if (temp.next == tail && temp.count == 1) 
	{
		Node predecessor = temp.previous;
		predecessor.next = temp.next;
		temp.next.previous = predecessor;
		temp = null;
	}
	// otherwise, if n is the last node with more than one element, or more than half filled.
	else if (temp.next == tail || temp.count > nodeSize / 2) 
	{
		temp.removeItem(off);
	}
	// merge operation
	else {
		temp.removeItem(off);
		Node succNode = temp.next;
		
		// mini-merge
		if (succNode.count > nodeSize / 2) {
			temp.addItem(succNode.data[0]);
			succNode.removeItem(0);
		}
		// full-merge
		else if (succNode.count <= nodeSize / 2) {
			for (int i = 0; i < succNode.count; i++) {
				temp.addItem(succNode.data[i]);
			}
			temp.next = succNode.next;
			succNode.next.previous = temp;
			succNode = null;
		}
	}
	// decrease the size of list
	size--;
	return nodeValue;
}

/**
 * Sort all elements in the Multi list in NON-DECREASING order. You may do the following. 
 * Traverse the list and copy its elements into an array, deleting every visited node along 
 * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
 * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
 * back to the Multi list, creating new nodes for storage. After sorting, all nodes but 
 * (possibly) the last one must be full of elements.  
 *  
 * Comparator<E> must have been implemented for calling insertionSort().    
 */
  public void sort()
  {
	  Comparator<E> comp = new Comparator<E>() 
	  {
		  @Override
		  public int compare(E c1, E c2)
		  {
			  return c1.compareTo(c2);
		  }
	  };
	  int amount = 0;
	  for(Node allNodes = head.next; allNodes != null; allNodes = allNodes.next)
	  {
		  amount += allNodes.count;
	  }
	  E[] newData = (E[]) new Comparable[amount];
	  Node pending = head.next;
	  Node remove = head;
	  int j = 0;
	  int i = 0;
	  
	  while(i < size)
	  {
		  for(E object : pending.data)
		  {
			  if(object != null)
			  {
				  newData[j++] = object;
				  i++;
			  }
		  }
		  remove = remove.next;
		  remove.previous.next = null;
		  pending.previous = null;
		  pending = pending.next;
	  }
	  pending.next = null;
	  head.next = tail;
	  tail.previous = head;
	  size = 0;
	  
	  insertionSort(newData, comp);
	  for(E k : newData)
	  {
		  add(k);
	  }
  }
  
  /**
   * Sort all elements in the Multi list in NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() 
  {
	  int amount = 0;
	  for(Node allNodes = head.next; allNodes != null; allNodes = allNodes.next)
	  {
		  amount += allNodes.count;
	  }
	  E[] newData = (E[]) new Comparable[amount];
	  Node pending = head.next;
	  Node remove = head;
	  int j = 0;
	  int i = 0;
	  
	  while(i < size)
	  {
		  for(E object : pending.data)
		  {
			  if(object != null)
			  {
				  newData[j++] = object;
				  i++;
			  }
		  }
		  remove = remove.next;
		  remove.previous.next = null;
		  pending.previous = null;
		  pending = pending.next;
	  }
	  pending.next = null;
	  head.next = tail;
	  tail.previous = head;
	  size = 0;
	  bubbleSort(newData);
	  for(E k : newData)
	  {
		  add(k);
	  }
  }
  
  @Override
  public Iterator<E> iterator()
  {
	  return new MultiListIterator();
  }

  @Override
  public ListIterator<E> listIterator()
  {
	  return new MultiListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index)
  {
	  return new MultiListIterator(index);
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }


  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;
    
    
    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
      if (count >= nodeSize)
      {
        return;
      }
      data[count++] = item;
      //useful for debugging
      //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
    }
    
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      ++count;
      data[offset] = item;
      //useful for debugging 
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset)
    {
      E item = data[offset];
      for(int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      --count;
    }    
  }
  

 
  private class MultiListIterator implements ListIterator<E>
{
	private static final int BEHIND = -1;
    private static final int AHEAD = 1;
	private static final int NONE = 0;

	/**
	 * cursor of iterator
	 */
	int cursor;
	
	/**
	 * data of iterator in array form
	 */
	public E[] dataArr;
	
	/**
	 *tracks the direction of iterator for remove(), set() and add() methods
	 */
	int direction;

	 /**
     * Default constructor. Sets the cursor to 0 and direction to NONE.
     */
	public MultiListIterator() {
		cursor = 0;
		direction = NONE;
		update();
	}

	 /**
     * Constructor finds node at a given position.
     * @param pos
     */
	public MultiListIterator(int pos) {
		// Done
		cursor = pos;
		direction = NONE;
		update();
	}

	/**
	 * Takes the MultiList data and inserts it into dataArr array.
	 */
	private void update() {
		dataArr = (E[]) new Comparable[size];

		int tmpIndex = 0;
		Node temp = head.next;
		while (temp != tail) {
			for (int i = 0; i < temp.count; i++) {
				dataArr[tmpIndex] = temp.data[i];
				tmpIndex++;
			}
			temp = temp.next;
		}
	}

	/**
	 * Returns true if next element available 
	 */
	@Override
	public boolean hasNext() {
		if (cursor >= size)
			return false;
		else
			return true;
	}

	/**
	 * Returns the next value and increases the cursor by one
	 */
	@Override
	public E next() {
		if (!hasNext())
			throw new NoSuchElementException();
		direction = BEHIND;
		return dataArr[cursor++];
	}

	/**
	 * Removes from the list the last element returned by next() or previous().
	 *removes the element from the MultiList
	 */
	@Override
	public void remove() {
		// Done
		if (direction == BEHIND) {
			MultiList.this.remove(cursor - 1);
			update();
			direction = NONE;
			cursor--;
			if (cursor < 0)
				cursor = 0;
		} else if (direction == AHEAD) {
			MultiList.this.remove(cursor);
			update();
			direction = NONE;
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 *Returns true if there is a previous element
	 */
	@Override
	public boolean hasPrevious()
	{
		//Done
		if (cursor > 0)
			return true;
		else
			return false;
	}

	/**
	 * Returns index of next available element
	 */
	@Override
	public int nextIndex() 
	{
		//Done
		return cursor;
	}
	
	/**
	 * Returns previous available element and shifts pointer by -1
	 */
	@Override
	public E previous() 
	{
		//Done
		if (!hasPrevious())
			throw new NoSuchElementException();
		direction = AHEAD;
		cursor--;
		return dataArr[cursor];
	}

	/**
	 * Returns index of previous element
	 */
	@Override
	public int previousIndex() 
	{
		//Done
		return cursor - 1;
	}

	/**
	 * Replaces the element at the cursor
	 */
	@Override
	public void set(E item) 
	{
		//Done
		if (direction == BEHIND) 
		{
			NodeInfo nodeInfo = find(cursor - 1);
			nodeInfo.node.data[nodeInfo.offset] = item;
			dataArr[cursor - 1] = item;
			direction = NONE;
		} 
		else if (direction == AHEAD) {
			NodeInfo nodeInfo = find(cursor);
			nodeInfo.node.data[nodeInfo.offset] = item;
			dataArr[cursor] = item;
			direction = NONE;
		} 
		else 
		{
			throw new IllegalStateException();
		}

	}

	/**
	 * Adds an element to the end of the list
	 */
	@Override
	public void add(E item)
	{
		//Done
		if (item == null)
			throw new NullPointerException();

		MultiList.this.add(cursor, item);
		cursor++;
		update();
		direction = NONE;

	}
}

	// Other methods you may want to add or override that could possibly facilitate
	// other operations, for instance, addition, access to the previous element,
	// etc.
	//
	// ...
	//

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
	  int j = 0;
	  for(int i = 1; i < arr.length; i++)
	  {
		  j = i;
		  while(j > 0 && comp.compare(arr[j], arr[j - 1]) < 0)
		  {
			  E temp = arr[j];
			  arr[j] = arr[j - 1];
			  arr[j - 1] = temp;
			  j--;
		  }
	  }
  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
	  for(int i = 0; i < arr.length; i++)
	  {
		  for(int j = 0; j < arr.length - i - 1; j++)
		  {
			  if(arr[j].compareTo(arr[j + 1]) < 0)
			  {
				  E temp = arr[j];
				  arr[j] = arr[j + 1];
				  arr[j + 1] = temp;
			  }
		  }
	  }
  }
  private class NodeInfo
  {
	  public Node node;
	  public int offset;
	  
	  public NodeInfo(Node node, int offset)
	  {
	  	this.node = node;
	  	this.offset = offset;
	  }
  }
  
  // finds offset and node at position pos
  private NodeInfo find(int pos)
  {
	  if (pos == -1)
	  {
		  NodeInfo info = new NodeInfo(head, 0);
		  return info;
	 
	  }
	  if (pos == size)
	  {
		  NodeInfo info = new NodeInfo(tail, 0);
		  return info;
			 
	  }
	  Node current = head.next;
	  int currentPos = 0;
	  
	  
	  while (current != tail)
	  {
		  if(currentPos + current.count <= pos)
		  {
			  currentPos = currentPos + current.count;
			  current = current.next;
			
			  continue;
		  }
			  NodeInfo info = new NodeInfo(current, (pos - currentPos));
	
			  return info; 	 
	  }
	  if (current == tail)
	  {
		  currentPos = currentPos + 1;
		  if(pos == currentPos)
		  {
			  NodeInfo info = new NodeInfo(tail, 0);
	
			  return info;
			 
		  }
	  }
	  
	  return null;
	} 
  
  
}