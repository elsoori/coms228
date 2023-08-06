package edu.iastate.cs228.hw1;

/**
 *
 * @author Eranda Sooriyarachchi
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * The plain is represented as a square grid of size width x width.
 *
 */
public class Plain {
    private int width; // grid size: width X width

    public Living[][] grid; // plain grid

    /**
     * Default constructor reads from a file
     */
    public Plain(String inputFileName) throws FileNotFoundException
    {
        File file = new File(inputFileName);
        Scanner sc = new Scanner(file);
        int count = 0;
        while (sc.hasNextLine())
        {
            count++;
            sc.nextLine();
        }
        sc.close();
        width = count;
        grid = new Living[width][width];
        Scanner scn = new Scanner(file);
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < width; j++)
            {
                String s = scn.next();
                if (s.charAt(0) == 'F')
                {
                    String age = s.substring(1);
                    int age1 = Integer.parseInt(age);
                    grid[i][j] = new Fox(this, i, j, age1);
                } else if (s.charAt(0) == 'B')
                {
                    String age = s.substring(1);
                    int age1 = Integer.parseInt(age);
                    grid[i][j] = new Badger(this, i, j, age1);
                } else if (s.charAt(0) == 'R')
                {
                    String age = s.substring(1);
                    int age1 = Integer.parseInt(age);
                    grid[i][j] = new Rabbit(this, i, j, age1);
                } else if (s.charAt(0) == 'E')
                {
                    grid[i][j] = new Empty(this, i, j);
                } else if (s.charAt(0) == 'G')
                {
                    grid[i][j] = new Grass(this, i, j);
                }
            }
        }
        scn.close();
    }

    /**
     * Constructor that builds a w x w grid without initializing it.
     *
     * @param w the grid
     */
    public Plain(int w) {
        grid = new Living[w][w];
        width = w;
    }

    public int getWidth() {
        return width; // to be modified
    }

    /**
     * Initialize the plain by randomly assigning to every square of the grid one of
     * BADGER, FOX, RABBIT, GRASS, or EMPTY.
     *
     * Every animal starts at age 0.
     */
    public void randomInit() {
        Random generator = new Random();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                int random = generator.nextInt(5);
                if (random == 0)
                {
                    grid[i][j] = new Badger(this, i, j, 0);
                }
                else if (random == 1)
                {
                    grid[i][j] = new Empty(this, i, j);
                }
                else if (random == 2)
                {
                    grid[i][j] = new Fox(this, i, j, 0);
                }
                else if (random == 3)
                {
                    grid[i][j] = new Grass(this, i, j);
                }
                else
                {
                    grid[i][j] = new Rabbit(this, i, j, 0);
                }
            }
        }
    }

    /**
     * Output the plain grid. For each square, output the first letter of the living
     * form occupying the square. If the living form is an animal, then output the
     * age of the animal followed by a blank space; otherwise, output two blanks.
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j].who() == (State.BADGER) && count < width) {
                    String str = "B";
                    int age = ((Animal) grid[i][j]).myAge();
                    result.append(str).append(age).append(" ");
                    count++;
                } else if (grid[i][j].who() == (State.EMPTY) && count < width) {
                    String str = "E";
                    result.append(str).append(" ").append(" ");
                    count++;
                } else if (grid[i][j].who() == (State.GRASS) && count < width) {
                    String str = "G";
                    result.append(str).append(" ").append(" ");
                    count++;
                } else if (grid[i][j].who() == (State.FOX) && count < width) {
                    String str = "F";
                    int age = ((Animal) grid[i][j]).myAge();
                    result.append(str).append(age).append(" ");
                    count++;
                } else if (grid[i][j].who() == (State.RABBIT) && count < width) {
                    String str = "R";
                    int age = ((Animal) grid[i][j]).myAge();
                    result.append(str).append(age).append(" ");
                    count++;
                }
            }
            if (count == width) {
                result.append('\n');
                count = 0;
            }
        }
        return result.toString();
    }

    /**
     * Write the plain grid to an output file. Also useful for saving a randomly
     * generated plain for debugging purpose.
     *
     * @throws FileNotFoundException
     */
    public void write(String outputFileName) throws FileNotFoundException {
        File file = new File(outputFileName);
        PrintWriter write = new PrintWriter(file);
        write.print(toString());
        write.close();
    }
}
