package com.company;

/**
 * This exception class is thrown to check if probability is valid.
 *
 *  @author  Tracy Yip 
 */
public class InvalidProbabilityException extends Exception
{
    /**
     * Constructs an InvalidProbabilityException that passes
     * a string to its super class (Exception)
     */
    public InvalidProbabilityException()
    {
        super("The probability you entered should be between 0.0 and 1.0. Please try again.");
    }

    /**
     * Constructs an InvalidProbabilityException that passes
     * a specified string given in the parameter to its
     * super class (Exception)
     *
     * @param input
     *    A specified string of message that is desired to be printed
     */
    public InvalidProbabilityException(String input)
    {
        super(input);
    }
}
