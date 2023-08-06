package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Eranda Sooriyarachchi
 *
 */

/**
 *
 * The Wildlife class performs a simulation of a grid plain with squares
 * inhabited by badgers, foxes, rabbits, grass, or none.
 *
 */
public class Wildlife {
    /**
     * Update the new plain from the old plain in one cycle.
     *
     * @param pOld old plain
     * @param pNew new plain
     */
    public static void updatePlain(Plain pOld, Plain pNew)
    {
        for (int i = 0; i < pOld.grid.length; i++) {
            for (int j = 0; j < pNew.grid[0].length; j++) {
                pNew.grid[i][j] = pOld.grid[i][j].next(pNew);
            }
        }
    }

    /**
     * Repeatedly generates plains either randomly or from reading files. Over each
     * plain, carries out an input number of cycles of evolution.
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {


        int trialnum = 0;
        System.out.println("Wildlife simulation on a plain");
        System.out.println("Please enter the key corresponding to the desired plain simulation method: ");
        int input;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("1 (Random plain)  2 (File input)  3(Exit)");
            input = sc.nextInt();
        }
        while (input < 1 || input > 3);

        while (input == 1) {
            trialnum++;
            System.out.print("Trial" + " " + trialnum + ": Random plain\n");
            System.out.println("Enter grid width:");
            int w = sc.nextInt();
            while (w < 2)       // Minimum siza accepted is 2 squares of width.
                w = sc.nextInt();
            System.out.println("Enter no. of cycles:");
            int c = sc.nextInt();
            while (c < 1) {
                c = sc.nextInt();
            }
            System.out.println(" ");
            System.out.println("Initial plain");
            Plain even = new Plain(w);
            Plain odd = new Plain(w);
            even.randomInit();
            System.out.println(even.toString());
            System.out.println(" ");
            int cycles = 0;
            while (cycles != c) {
                if (cycles < c) {
                    updatePlain(even, odd);
                    cycles++;
                }
                if (cycles != c) {
                    updatePlain(odd, even);
                    cycles++;
                }
            }
            System.out.println("Final plain:");
            System.out.println(" ");
            if (cycles % 2 == 0) {
                System.out.println(even.toString());
            }
            if (cycles % 2 != 0) {
                System.out.println(odd.toString());
            }
            System.out.println("Next trial type?");
            input = sc.nextInt();

        }
        while (input == 2) {
            trialnum++;
            System.out.print("Trial" + " " + trialnum + ":");
            System.out.println(" Plain input from a file");
            System.out.println("File name:");
            String file = sc.next();
            System.out.println("Enter no. of cycles:");
            int c = sc.nextInt();
            while (c < 1) {
                c = sc.nextInt();
            }
            System.out.println(" ");
            System.out.println("Initial plain:");
            Plain even = new Plain(file);
            Plain odd = new Plain(file);
            System.out.println(" ");
            System.out.println(even.toString());
            System.out.println(" ");
            int cycles = 0;
            while (cycles != c) {
                if (cycles < c) {
                    updatePlain(even, odd);
                    cycles++;
                } else {
                    updatePlain(odd, even);
                    cycles++;
                }
            }
            System.out.println("Final plain:");
            System.out.println(" ");
            if (cycles % 2 == 0) {
                System.out.println(even.toString());
            }
            if (cycles % 2 != 0) {
                System.out.println(odd.toString());
            }
            System.out.println("Next trial type?");
            input = sc.nextInt();
        }
        if (input == 3) {
            return;
        }
        if (trialnum > 3)
        {
            if (input != 1 || input != 2) {
                sc.close();
                return;
            }
        }

    }
}
