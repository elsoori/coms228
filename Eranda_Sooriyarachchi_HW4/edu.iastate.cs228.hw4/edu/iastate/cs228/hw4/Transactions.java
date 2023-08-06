package edu.iastate.cs228.hw4;


import java.io.FileNotFoundException;
import java.util.Scanner; 

/**
 *  
 * @author Eranda Sooriyarachchi
 *
 */

/**
 * 
 * The Transactions class simulates video transactions at a video store. 
 *
 */
public class Transactions 
{
	
	/**
	 * The main method generates a simulation of rental and return activities.  
	 *  
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException
	{	
		// Done
		//
		// 1. Construct a VideoStore object.
		// 2. Simulate transactions as in the example given in Section 4 of the
		// the project description.
		VideoStore videoStore = new VideoStore("videoList1.txt");
		Scanner scn = new Scanner(System.in);
		boolean EOF = false;
		int input = 0;	
		
		System.out.println("Transactions at a Video Store");
		System.out.println("keys: 1 (rent)     2 (bulk rent)\n      3 (return)   4 (bulk return)\n      5 (summary)  6 (exit)");
		
		while(EOF != true) 
		{
			System.out.print("Transaction: ");
			
			input = scn.nextInt();
			
			// rent film
			if(input == 1) 
			{		
				scn.nextLine();
				
				System.out.print("Film to rent: ");
				
				String ln = scn.nextLine();
				String name = videoStore.parseFilmName(ln);
				int copies = videoStore.parseNumCopies(ln);
				
				try 
				{
					videoStore.videoRent(name, copies);
				} 
				catch (IllegalArgumentException | FilmNotInInventoryException | AllCopiesRentedOutException e) 
				{
					System.out.println(e.getMessage());
				}
				
				System.out.print("\n");	
			}
			// bulk rent
			else if(input == 2) 
			{	
				System.out.print("Video file (rent): ");
				
				try 
				{
					videoStore.bulkRent(scn.next());
				} 
				catch (IllegalArgumentException | FilmNotInInventoryException | AllCopiesRentedOutException e) 
				{
					System.out.println(e.getMessage());
				}	
			}
			// return films
			else if(input == 3) 
			{								
				scn.nextLine();
				
				System.out.print("Film to return: ");
				
				String ln = scn.nextLine();
				String name = videoStore.parseFilmName(ln);
				int copies = videoStore.parseNumCopies(ln);

				try 
				{
					videoStore.videoReturn(name, copies);
				} 
				catch (IllegalArgumentException | FilmNotInInventoryException e) 
				{
					System.out.println(e.getMessage());
				}
				
				System.out.print("\n");		
			}
			// bulk return
			else if(input == 4) 
			{
				System.out.print("Video file (return): ");
				
				try 
				{
					videoStore.bulkReturn(scn.next());
				} 
				catch (IllegalArgumentException | FilmNotInInventoryException e) 
				{
					System.out.println(e.getMessage());
				}
			}
			//summary of transaction
			else if(input == 5) 
			{
				System.out.println(videoStore.transactionsSummary());	
			}
			// exit
			else if(input == 6) 
			{
				EOF = true;
				break;
			}				
		}
		// closes the scanner
		scn.close();	
	}
}
