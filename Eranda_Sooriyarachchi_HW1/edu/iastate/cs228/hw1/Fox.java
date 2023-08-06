package edu.iastate.cs228.hw1;

/**
 *
 * @author Eranda Sooriyarachchi
 *
 */

/**
 * A fox eats rabbits and competes against a badger.
 */
public class Fox extends Animal
{
    /**
     * Constructor
     * @param p: plain
     * @param r: row position
     * @param c: column position
     * @param a: age
     */
    public Fox (Plain p, int r, int c, int a)
    {
        super(p, r, c, a);
    }

    /**
     * A fox occupies the square.
     */
    public State who()
    {
        // Done
        return State.FOX;
    }

    /**
     * A fox dies of old age or hunger, or from attack by numerically superior badgers.
     * @param pNew     plain of the next cycle
     * @return Living  life form occupying the square in the next cycle.
     */
    public Living next(Plain pNew)
    {
        //Done

        // See Living.java for an outline of the function.
        // See the project description for the survival rules for a fox.


        // Obtains counts of life forms in the 3x3 neighborhood of the class object.
        int[] population = new int[NUM_LIFE_FORMS];
        this.census(population);

        //Applying survival rules.

        //Empty if the Fox is currently at age 6
        if(age >= FOX_MAX_AGE)
        {
            return new Empty(pNew, row, column);
        }

        //otherwise, Badger, if there are more Badgers than Foxes in the neighborhood
        else if(population[BADGER] > population[FOX])
        {
            return new Badger(pNew, row, column, 0);
        }

        //otherwise, Empty, if Badgers and Foxes together out number Rabbits in the neighborhood
        else if(population[BADGER] + population[FOX] > population[RABBIT])
        {
            return new Empty(pNew, row, column);
        }
        //otherwise, Fox
        else
        {
            return new Fox(pNew, row, column, age + 1);
        }

    }
}