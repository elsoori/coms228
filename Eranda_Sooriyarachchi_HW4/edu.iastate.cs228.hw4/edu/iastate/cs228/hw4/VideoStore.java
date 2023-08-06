package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner; 

/**
 * 
 * @author Eranda Sooriyarachchi
 *
 */

public class VideoStore 
{
	protected SplayTree<Video> inventory;     // all the videos at the store
	
	// ------------
	// Constructors 
	// ------------
	
    /**
     * Default constructor sets inventory to an empty tree. 
     */
    public VideoStore()
    {
    	// no need to implement- this gave me errors. So I had to.
    	// Empty splay tree created
    	inventory = new SplayTree<Video>();
    }
    
    
	/**
	 * Constructor accepts a video file to create its inventory.  Refer to Section 3.2 of  
	 * the project description for details regarding the format of a video file. 
	 * 
	 * Calls setUpInventory(). 
	 * 
	 * @param videoFile  no format checking on the file
	 * @throws FileNotFoundException
	 */
    public VideoStore(String videoFile) throws FileNotFoundException  
    {
    	//Initialize splay tree
    	this();
    	
    	// Set up the video store inventory
    	setUpInventory(videoFile);
    }
    
    
   /**
    * Sets up the initial inventory. 
    * 
    * Accepts a video file to initialize the splay tree inventory.  To be efficient, 
    * add videos to the inventory by calling the addBST() method, which does not splay. 
    * 
    * Refer to Section 3.2 for the format of video file. 
    * 
    * @param  videoFile  correctly formated if exists
    * @throws FileNotFoundException 
    */
    public void setUpInventory(String videoFile) throws FileNotFoundException
    {
    	inventory.clear();
    	
    	Scanner read = new Scanner(new File(videoFile));
    	
    	while(read.hasNextLine()) {
    		String line = read.nextLine();
    		// As long as lines aren't empty 
    		if (!line.equals("\\n") && !line.equals("") && !line.equals("\\s+")) {
	    		String title = parseFilmName(line);	    		
	    		int copies = parseNumCopies(line);
	    		
	    		if (copies <= 0) {
	    			copies = 1;
	    		}
	    		
	    		// Add the video to the inventory
	    		inventory.addBST(new Video(title, copies));
    		}
    	}
    	
    	read.close();
    }
    

    // ------------------
    // Inventory Addition
    // ------------------
    
    /**
     * Find a Video object by film title. 
     * 
     * @param film  
     * @return 
     */
	public Video findVideo(String film) 
	{
		if (film != null && !film.isEmpty()) {
			// Find the Video based on film title
			Video v = inventory.findElement(new Video(film));
			
			if (v != null && v.getFilm().equals(film)) {
				return v;
			}
		}
		
		return null; 
	}

	/**
	 * Updates the splay tree inventory by adding a number of video copies of the film.  
	 * (Splaying is justified as new videos are more likely to be rented.) 
	 * 
	 * Calls the add() method of SplayTree to add the video object.  
	 * 
	 *     a) If true is returned, the film was not on the inventory before, and has been added.  
	 *     b) If false is returned, the film is already on the inventory. 
	 *     
	 * The root of the splay tree must store the corresponding Video object for the film. Update 
	 * the number of copies for the film.  
	 * 
	 * @param film  title of the film
	 * @param n     number of video copies 
	 */
	public void addVideo(String film, int n)  
	{
		if (film != null && !film.isEmpty()) {
			// If the film wasn't in the inventory before, it is now
			boolean added = inventory.add(new Video(film, n));
			
			// Update the number of copies
			if (!added) {
				inventory.root.data.addNumCopies(n);
			}
		}
	}
	
	/**
	 * Add one video copy of the film. 
	 * 
	 * @param film  title of the film
	 */
	public void addVideo(String film)
	{
		String title = parseFilmName(film);	    		
		int copies = parseNumCopies(film);
		addVideo(title, copies);
	}
	


	/**
     * UPDATE (vs setUpInventory) the splay trees inventory by adding videos.  Perform binary search additions by 
     * calling addBST() without splaying. 
     * 
     * The videoFile format is given in Section 3.2 of the project description. 
     * 
     * @param videoFile  correctly formated if exists 
     * @throws FileNotFoundException
     */
    public void bulkImport(String videoFile) throws FileNotFoundException 
    {
    	// Create a new Scanner to read the video file
    	Scanner read = new Scanner(new File(videoFile));
    	
    	while(read.hasNextLine()) {
    		String line = read.nextLine();
    		
    		// Catch empty lines
    		if (!line.equals("\\n") && !line.equals("") && !line.equals("\\s+")) {
	    		String title = parseFilmName(line);	    		
	    		int copies = parseNumCopies(line);
	    		
	    		// Add the video to the inventory or update qty if it already exists
	    		inventory.addBST(new Video(title, copies));
    		}
    	}
    	
    	read.close();
    }

    
    // ----------------------------
    // Video Query, Rental & Return 
    // ----------------------------
    
	/**
	 * Search the splay tree inventory to determine if a video is available. 
	 * 
	 * @param  film
	 * @return true if available
	 */
	public boolean available(String film)
	{
		Video v = inventory.findElement(new Video(film));
		
		if (v != null && v.getFilm().equals(film) == true) {
			return true;
		} else {
			return false;
		}
	}

	
	
	/**
	 * Rent an individual video
	 * 
     * Update inventory. 
     * 
     * Search if the film is in inventory by calling findElement(new Video(film, 1)). 
     * 
     * If the film is not in inventory, prints the message "Film <film> is not 
     * in inventory", where <film> shall be replaced with the string that is the value 
     * of the parameter film.  If the film is in inventory with no copy left, prints
     * the message "Film <film> has been rented out".
     * 
     * If there is at least one available copy but n is greater than the number of 
     * such copies, rent all available copies. In this case, no AllCopiesRentedOutException
     * is thrown.  
     * 
     * @param film   
     * @param n 
     * @throws IllegalArgumentException      if n <= 0 or film == null or film.isEmpty()
	 * @throws FilmNotInInventoryException   if film is not in the inventory
	 * @throws AllCopiesRentedOutException   if there is zero available copy for the film.
	 */
	public void videoRent(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException,  
									     			 AllCopiesRentedOutException 
	{
		String compFilm = "";
		
		if (film != null) {
			// Remove all blank spaces to make sure the film is not empty
			compFilm = film.replaceAll("\\s", "");
		}
		
		if (n <= 0 || film == null || film.isEmpty() || compFilm.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		// Locate the video in inventory
		Video v = inventory.findElement(new Video(film, 1));
		
		// Video is not in inventory
		if (v == null) {
			throw new FilmNotInInventoryException();
		}
		
		int available = v.getNumAvailableCopies();
		
		// All copies are rented out
		if (available == 0) {
			throw new AllCopiesRentedOutException();
		}
		
		if (n >= available) {
			// Number of copies available is less that requested, but greater than zero
			// Rent the remaining copies
			v.rentCopies(available);
		} else {
			v.rentCopies(n);
		}
		
	}

	
	

	/**
	 * Update inventory.
	 * 
	 *    1. Calls videoRent() repeatedly for every video listed in the file.  
	 *    2. For each requested video, do the following: 
	 *       a) If it is not in inventory or is rented out, an exception will be 
	 *          thrown from videoRent().  Based on the exception, prints out the following 
	 *          message: "Film <film> is not in inventory" or "Film <film> 
	 *          has been rented out." In the message, <film> shall be replaced with 
	 *          the name of the video. 
	 *       b) Otherwise, update the video record in the inventory.
	 * 
	 * For details on handling of multiple exceptions and message printing, please read Section 3.4 
	 * of the project description. 
	 *       
	 * @param videoFile  correctly formatted if exists
	 * @throws FileNotFoundException
     * @throws IllegalArgumentException     if the number of copies of any film is <= 0
	 * @throws FilmNotInInventoryException  if any film from the videoFile is not in the inventory 
	 * @throws AllCopiesRentedOutException  if there is zero available copy for some film in videoFile
	 */
	
	public void bulkRent(String videoFile) throws FileNotFoundException, IllegalArgumentException, 
												  FilmNotInInventoryException, AllCopiesRentedOutException 
	{
		Scanner read;
		try {
			read = new Scanner(new File(videoFile));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}

		while(read.hasNextLine()) {
			String line = read.nextLine();
			
			String error = "";
			String msg = "";
			
			
    		// Catch empty lines
    		if (!line.equals("\\n") && !line.equals("") && !line.equals("\\s+")) {
	    		String title = parseFilmName(line);	    		
	    		int copies = parseNumCopies(line);
	    		
	    		// Number of copies is invalid
	    		if (copies <= 0) {
	    			error = "IllegalArgumentException";
	    			msg = msg + "\nFilm " + title + " has an invalid request";
	    		}
	    		
	    		Video v = inventory.findElement(new Video(title, 1));
	    		
	    		// Video not found
	    		if (v == null) {
	    			if (error.isEmpty()) {
	    				error = "FilmNotInInventoryException";
	    			}
	    			msg = msg + "\nFilm " + title + " is not in inventory";
	    		} else {
	    			int available = v.getNumAvailableCopies();
	    			
	    			// All copies rented out
	    			if (available == 0) {
	    				if (error.isEmpty()) {
	    					error = "AllCopiesRentedOutException";
	    				}
	    				msg = msg + "\nFilm " + title + " has been rented out";
	    			}
	    	    		
	    	    	videoRent(title, copies);
	    		}
    		}
    		
    		if (!error.isEmpty()) {
    			if (error.equals("IllegalArgumentException")) {
    				throw new IllegalArgumentException(msg);
    			} else if (error.equals("FilmNotInInventoryException")) {
    				throw new FilmNotInInventoryException(msg);
    			} else {
    				throw new AllCopiesRentedOutException(msg);
    			}
    		}
		}
		
		read.close();
	}
	
	/**
	 * Update inventory.
	 * 
	 * If n exceeds the number of rented video copies, accepts up to that number of rented copies
	 * while ignoring the extra copies. 
	 * 
	 * @param film
	 * @param n
	 * @throws IllegalArgumentException     if n <= 0 or film == null or film.isEmpty()
	 * @throws FilmNotInInventoryException  if film is not in the inventory
	 */
	public void videoReturn(String film, int num) throws IllegalArgumentException, FilmNotInInventoryException 
	{
		String compFilm = "";
		
		if (film != null) {
			// Compress film name by removing all blank spaces to make sure the film is not empty
			compFilm = film.replaceAll("\\s", "");
		}
		
		if (num <= 0 || film == null || film.isEmpty() || compFilm.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		// Locate film in the inventory
		Video v = inventory.findElement(new Video(film));
		
		// Film was not in inventory
		if (v == null) {
			throw new FilmNotInInventoryException();
		}
		
		
		if (num > v.getNumRentedCopies()) {
			// Returning too many copies (more copies than were checked out)
			v.returnCopies(v.getNumRentedCopies());
		} 
		else
		{
			// Return n number of copies
			v.returnCopies(num);
		}
	}
	
	
	
	
	/**
	 * Update inventory. 
	 * 
	 * Handles excessive returned copies of a film in the same way as videoReturn() does.  See Section 
	 * 3.4 of the project description on how to handle multiple exceptions. 
	 * 
	 * @param videoFile
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException    if the number of return copies of any film is <= 0
	 * @throws FilmNotInInventoryException if a film from videoFile is not in inventory
	 */
	public void bulkReturn(String videoFile) throws FileNotFoundException, IllegalArgumentException,
													FilmNotInInventoryException												
	{
		Scanner read;
		try 
		{
			read = new Scanner(new File(videoFile));
		} 
		catch (FileNotFoundException e) 
		{
			throw new FileNotFoundException();
		}

		while(read.hasNextLine())
		{
			String line = read.nextLine();
			
			String error = "";
			String msg = "";
			
			
    		// Catch empty lines
    		if (!line.equals("\\n") && !line.equals("") && !line.equals("\\s+"))
    		{
	    		String title = parseFilmName(line);	    		
	    		int copies = parseNumCopies(line);
	    		
	    		// Number of copies is invalid
	    		if (copies <= 0)
	    		{
	    			error = "IllegalArgumentException";
	    			msg = msg + "\nFilm " + title + " has an invalid request";
	    		}
	    		
	    		Video v = inventory.findElement(new Video(title, 1));
	    		
	    		// Video not found
	    		if (v == null)
	    		{
	    			if (error.isEmpty())
	    			{
	    				error = "FilmNotInInventoryException";
	    			}
	    			msg = msg + "\nFilm " + title + " is not in inventory";
	    		}
	    				
	    				msg = msg + "\nFilm " + title + " has been rented out";
	    			
	    	    		
	    	    	videoReturn(title, copies);
	    		
    		}
    		
    		if (!error.isEmpty()) 
    		{
    			if (error.equals("IllegalArgumentException"))
    			{
    				throw new IllegalArgumentException(msg);
    			} 
    			else if (error.equals("FilmNotInInventoryException")) 
    			{
    				throw new FilmNotInInventoryException(msg);
    			} 
    		}
		}
		
		read.close();
	}
		
	

	// ------------------------
	// Methods without Splaying
	// ------------------------
		
	/**
	 * Performs inorder traversal on the splay tree inventory to list all the videos by film 
	 * title, whether rented or not.  Below is a sample string if printed out: 
	 * 
	 * Use the iterator
	 * 
	 * Films in inventory: 
	 * 
	 * A Streetcar Named Desire (1) 
	 * Brokeback Mountain (1) 
	 * Forrest Gump (1)
	 * Psycho (1) 
	 * Singin' in the Rain (2)
	 * Slumdog Millionaire (5) 
	 * Taxi Driver (1) 
	 * The Godfather (1) 
	 * 
	 * 
	 * @return
	 */
	public String inventoryList()
	{
		// Create the return string
		String returnString = "Films in inventory:\n\n";
		
		// Create the iterator
		Iterator<Video> iter = inventory.iterator();
		
		while (iter.hasNext()) {
			Video v = iter.next();
			
			returnString = returnString + v.getFilm() + " (" + v.getNumCopies() + ")"+ "\n";
		}
		// the videos in inventory with their total copies
		return returnString; 
	}

	
	/**
	 * Calls rentedVideosList() and unrentedVideosList() sequentially.  For the string format, 
	 * see Transaction 5 in the sample simulation in Section 4 of the project description. 
	 *   
	 * @return 
	 */
	public String transactionsSummary()
	{
		return (rentedVideosList() + "\n\n" + unrentedVideosList());
	}	
	
	/**
	 * Performs inorder traversal on the splay tree inventory.  Use a splay tree iterator.
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * Rented films: 
	 * 
	 * Brokeback Mountain (1)
	 * Forrest Gump (1) 
	 * Singin' in the Rain (2)
	 * The Godfather (1)
	 * 
	 * 
	 * @return
	 */
	private String rentedVideosList()
	{
		String returnString = "Rented films:\n";
		
		// Create the inventory iterator
		Iterator<Video> iter = inventory.iterator();
		
		// traverse in order
		while(iter.hasNext()) {
			// Get the video
			Video v = iter.next();
			int copiesRentedOut  = v.getNumRentedCopies();

			// copies rented out
			if (copiesRentedOut > 0) {
				returnString = returnString + "\n" + v.getFilm() + " (" + copiesRentedOut + ")";
			}
		}
		
		return returnString; 
	}

	
	/**
	 * Performs inorder traversal on the splay tree inventory.  Use a splay tree iterator.
	 * Prints only the films that have unrented copies. 
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * 
	 * Films remaining in inventory:
	 * 
	 * A Streetcar Named Desire (1) 
	 * Forrest Gump (1)
	 * Psycho (1) 
	 * Slumdog Millionaire (4) 
	 * Taxi Driver (1) 
	 * 
	 * 
	 * @return
	 */
	private String unrentedVideosList()
	{
		String returnString = "Films remaining in inventory:\n";
		
		// Create the inventory iterator
		Iterator<Video> iter = inventory.iterator();
		
		// traverse in order
		while(iter.hasNext()) {
			// Obtain the video
			Video v = iter.next();
			int copiesAvailable  = v.getNumAvailableCopies();

			// copies rented out
			if (copiesAvailable != 0) {
				returnString = returnString + "\n" + v.getFilm() + " (" + copiesAvailable + ")";
			}
		}		
		
		return returnString;
	}	

	
	/**
	 * Parse the film name from an input line. 
	 * 
	 * @param line
	 * @return the title of the video.
	 */
	public static String parseFilmName(String line) 
	{
		String[] movie = line.split("\\s\\("); 
		String title = movie[0];
		
		// Extra - remove any blank lines or return characters at the end of the title, if they are present
		title = title.replaceAll("\\s+$", "");
		title = title.replaceAll("\n", "").replaceAll("\r", "");
				
		return title;
	}
	
	
	
	// Separate the film name into the title and number of copies, if copies exist
	
	
	/**
	 * Parse the number of copies from an input line. 
	 * 
	 * @param line
	 * @return 
	 */
	//Gives the number of copies of films, if copies exist.
	public static int parseNumCopies(String line) 
	{
		try {
			line = line.replaceAll("\\s+$", "");
			line = line.replaceAll("\n", "").replaceAll("\r", "");
			
			String[] movie = line.split("\\s\\(");			
			String[] details = movie[1].split("\\)$");
			
			int copies = Integer.parseInt(details[0]);
			return copies;		
		} catch (ArrayIndexOutOfBoundsException e) {
			return 1;
		}
	}
	
}
