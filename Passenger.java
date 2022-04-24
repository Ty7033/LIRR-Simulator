package com.company;

/**
 * This class represents the Passenger object that has an integer id, integer arrivalTime, and boolean that indicates whether
 * it is a first class passenger.
 *
 * @author  Tracy Yip 
 */
public class Passenger {
    private static int idCounter=0;//A counter used to keep track of the passengers' ID generated
    private int id;//An instance variable that holds the passenger's id number
    private int arrivalTime;//An instance variable that holds the passenger's arrival time to the station
    private boolean isFirstClass;//A boolean that indicates if the passenger is first class

    /**
     * A constructor that creates a Passenger object. It initializes the passenger's arrivalTime to zero and
     * sets the boolean to false.
     */
    public Passenger()
    {
        idCounter++;
        id=idCounter;
        arrivalTime=0;
        isFirstClass=false;
    }

    /**
     * A constructor that creates a Passenger object. It initializes the passenger's arrivalTime to zero and
     * sets the boolean to value passed in the parameter.
     *
     * @param isFirstClass
     *    A boolean that indicates whether the passenger is a first class passenger.
     */
    public Passenger(boolean isFirstClass)
    {
        idCounter++;
        this.id=idCounter;
        arrivalTime=0;
        this.isFirstClass=isFirstClass;
    }

    /**
     * A constructor that creates a Passenger object. It initializes the passenger's arrivalTime and the isFirstClass
     * boolean to values passed in the parameter.
     *
     * @param arrivalTime
     *    An integer that represents the passenger's arrival time at the station.
     * @param isFirstClass
     *    A boolean that indicates whether the passenger is a first class passenger.
     */
    public Passenger(int arrivalTime, boolean isFirstClass)
    {
        idCounter++;
        this.id=idCounter;
        this.arrivalTime=arrivalTime;
        this.isFirstClass=isFirstClass;
    }

    /**
     * This is a getter method in the Passenger class.
     *
     * @return
     *    The id of the passenger
     */
    public int getId()
    {
        return id;
    }
    /**
     * This is a getter method in the Passenger class.
     *
     * @return
     *    The arrival time of the passenger
     */
    public int getArrivalTime()
    {
        return arrivalTime;
    }

    /**
     * This is a getter method in the Passenger class.
     *
     * @return
     *    An indication of whether the passenger belongs in the first class
     */
    public boolean isFirstClass()
    {
        return isFirstClass;
    }

    /**
     * This method sets the passenger's id number.
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * This method sets the passenger's arrival time.
     */
    public void setArrivalTime(int arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    /**
     * This method sets the passenger's class type.
     */
    public void setFirstClass(boolean firstClass)
    {
        isFirstClass = firstClass;
    }

    /**
     * This method organizes the passenger's id and arrival time in a string format.
     *
     * @return
     *    A string that contains the passenger's information
     */
    public String toString()
    {
        return "P"+ id + "@T"+arrivalTime;
    }

    /**
     * This method organizes the passenger's id in a string format.
     *
     * @return
     *    A string that contains the passenger's information
     */
    public String toShortString()
    {
        return "P"+ id;
    }
}
