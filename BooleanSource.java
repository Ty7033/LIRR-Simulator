package com.company;

/**
 * This class generates a probability for the Passenger's arrival at a certain station and checks whether there
 * would be a new Passenger arriving at the station.
 *
 * @author  Tracy Yip 
 */
public class BooleanSource
{
    private double probability;//The probability that the passenger arriving at the station

    /**
     * This is a constructor for BooleanSource that sets the probability
     * double value passed in the parameters
     *
     * @param p
     *    The arrival probability for the station entered by users
     * @throws IllegalArgumentException
     *    When the value passed in the parameter is negative or greater than one
     */
    public BooleanSource(double p) throws IllegalArgumentException
    {
        if(p<0.0||p>1.0)
        {
            throw new IllegalArgumentException();
        }
        probability=p;
    }

    /**
     * This is a method that checks whether the randomly produced value is less than the probability.
     *
     * @return
     *    A boolean that indicates whether a Passenger arrives at the station
     */
    public boolean occurs()
    {
        return (Math.random()<probability);
    }
}
