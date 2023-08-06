package edu.iastate.cs228.hw1;

/**
 *
 * @author Eranda Sooriyarachchi
 *
 */

/**
 * Grass remains if more than rabbits in the neighborhood; otherwise, it is eaten.
 *
 */
public class Grass extends Living
{
    public Grass (Plain p, int r, int c) // Constructor for the class
    {
        // Done
        plain = p;
        row = r;
        column = c;
    }

    public State who()
    {
        // Done
        return State.GRASS;
    }

    /**
     * Grass can be eaten out by too many rabbits. Rabbits may also multiply fast enough to take over Grass.
     */
    public Living next(Plain pNew)
    {
        // Done
        //
        // See Living.java for an outline of the function.
        // See the project description for the survival rules for a rabbit.

        // Obtains counts of life forms in the 3x3 neighborhood of the class object.
        int[] population = new int[NUM_LIFE_FORMS];
        this.census(population);

        // Rules of survival applied
        if(population[RABBIT] >= (population[GRASS] * 3))
        {
            return new Empty(pNew, row, column);
        }
        //otherwise, Rabbit if there are at least three Rabbits in the neighborhood

        else if(population[RABBIT] >= 3)
        {
            return new Rabbit(pNew, row, column, 0);
        }

        //otherwise, Grass.

        else
        {
            return new Grass(pNew, row, column);
        }

    }
}
