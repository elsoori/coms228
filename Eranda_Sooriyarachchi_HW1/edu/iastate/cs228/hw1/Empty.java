package edu.iastate.cs228.hw1;

/**
 *
 * @author Eranda Sooriyarachchi
 *
 */

/**
 * Empty squares are competed by various forms of life.
 */
public class Empty extends Living
{
    public Empty (Plain p, int r, int c)
    {
        // Done
        plain = p;
        row = r;
        column = c;
    }

    public State who()
    {
        // Done
        return State.EMPTY;
    }

    /**
     * An empty square will be occupied by a neighboring Badger, Fox, Rabbit, or Grass, or remain empty.
     * @param pNew     plain of the next life cycle.
     * @return Living  life form in the next cycle.
     */
    public Living next(Plain pNew)
    {
        //Done

        // See Living.java for an outline of the function.
        // See the project description for the survival rules

        // Obtains counts of life forms in the 3x3 neighborhood of the class object.
        int[] population = new int[NUM_LIFE_FORMS];
        census(population);

        //Applying survival rules.

        //Rabbit, if more than one neighboring Rabbit
        if(population[RABBIT] > 1)
        {
            return new Rabbit(pNew, row, column, 0);
        }
        //otherwise, Fox, if more than one neighboring Fox
        else if(population[FOX] > 1)
        {
            return new Fox(pNew, row, column, 0);
        }
        //otherwise, Badger, if more than one neighboring Badger
        else if(population[BADGER] > 1)
        {
            return new Badger(pNew, row, column, 0);
        }
        //otherwise, Grass, if at least one neighboring Grass
        else if(population[GRASS] > 0)
        {
            return new Grass(pNew, row, column);
        }
        //otherwise, Empty.
        else
        {
            return new Empty(pNew, row, column);
        }
    }
}
