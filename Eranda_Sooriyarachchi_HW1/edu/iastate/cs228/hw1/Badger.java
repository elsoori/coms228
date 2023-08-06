package edu.iastate.cs228.hw1;

/**
 *
 * @author Eranda Sooriyarachchi
 *
 */

/**
 * A badger eats a rabbit and competes against a fox.
 */
public class Badger extends Animal
{
    /**
     * Constructor
     * @param p: plain
     * @param r: row position
     * @param c: column position
     * @param a: age
     */
    public Badger (Plain p, int r, int c, int a)
    {
        // Done.
        super(p, r, c, a);
    }

    /**
     * A badger occupies the square.
     */
    public State who()
    {
        // Done
        return State.BADGER;
    }

    /**
     * A badger dies of old age or hunger, or from isolation and attack by a group of foxes.
     * @param pNew     plain of the next cycle
     * @return Living  life form occupying the square in the next cycle.
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

        //Empty if the Badger is currently at age 4
        if(age >= BADGER_MAX_AGE)
        {
            return new Empty(pNew, row, column);
        }

        //otherwise, Fox, if there is only one Badger but more than one Fox in the neighborhood
        else if(population[FOX] > 1 && population[BADGER] == 1)
        {
            return new Fox(pNew, row, column, 0);
        }

        //otherwise, Empty, if Badgers and Foxes together out number Rabbits in the neighborhood
        else if(population[BADGER] + population[FOX] > population[RABBIT])
        {
            return new Empty(pNew, row, column);
        }

        //otherwise, Badger
        else
        {
            return new Badger(pNew, row, column, age + 1);
        }
    }
}
