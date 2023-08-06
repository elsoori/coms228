package edu.iastate.cs228.hw1;

/**
 *
 * @author Eranda Sooriyarachchi
 *
 */

/*
 * This class is to be extended by the Badger, Fox, and Rabbit classes.
 */
public abstract class Animal extends Living implements MyAge
{
    protected int age;   // age of the animal

    protected Animal(Plain p, int r, int c, int a)
    {
        plain = p;
        row = r;
        column = c;
        age = a;
    }

    @Override
    /**
     *
     * @return age of the animal
     */
    public int myAge()
    {
        // Completed. Returns age of animal
        return age;
    }
}
