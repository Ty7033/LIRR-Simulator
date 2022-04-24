package com.company;

/**
 * This exception class is thrown to check if the input entered is valid.
 *
 *  @author  Tracy Yip Student ID: 114527635 Recitation: R-03
 */
public class InvalidInputException extends Exception
{
    /**
     * Constructs an InvalidInputException that passes
     * a string to its super class (Exception)
     */
    public InvalidInputException()
    {
        super("The input you enter will not work. Please try again.");
    }

    /**
     * Constructs an InvalidInputException that passes
     * a specified string given in the parameter to its
     * super class (Exception)
     *
     * @param input
     *    A specified string of message that is desired to be printed
     */
    public InvalidInputException(String input)
    {
        super(input);
    }
}
