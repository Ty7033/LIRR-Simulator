package com.company;

/**
 * This class represents a station object for the LIRR. It contains separate queues for first and second class
 * Passengers, along with two instances of BooleanSource to generate the probability of a Passenger arriving each
 * minute. This class also keeps track of the total number of embarked passengers and their wait time.
 *
 * @author  Tracy Yip 
 */
public class Station
{
    private PassengerQueue firstClass;//A queue that holds all the first class passengers at the station
    private PassengerQueue secondClass;//A queue that holds all the second class passengers at the station
    private BooleanSource firstArrival;//A BooleanSource that generates arrivals for first class
    private BooleanSource secondArrival;//A BooleanSource that generates arrivals for second class
    private String stationName;//A String that holds the station's name
    private boolean addedFirst;//A boolean to indicate if a first class Passenger was added to the station's queue
    private boolean addedSecond;//A boolean to indicate if a second class Passenger was added to the station's queue
    private int totalFirstEmbarkedPassenger;//An integer to count the number of first class Passengers that got on a train
    private int totalFirstWaitTime;//An integer to keep track of the first class Passengers' wait time at the station
    private int totalSecEmbarkedPassenger;//An integer to count the number of second class Passengers that got on a train
    private int totalSecWaitTime;//An integer to keep track of the second class Passengers' wait time at the station

    /**
     * A constructor for an instance of Station that initializes its two PassengerQueues.
     */
    public Station()
    {
        firstClass= new PassengerQueue();
        secondClass=new PassengerQueue();
    }

    /**
     * A constructor for an instance of Station that initializes its two PassengerQueues, passes the
     * probabilities entered by users to the two instance of BooleanSource, and sets the station name.
     *
     * @param firstProbability
     *    A probability of the arrival of first class passengers entered by users
     * @param secondProbability
     *    A probability of the arrival of second class passengers entered by users
     * @param name
     *    A string that indicates the station's name
     */
    public Station(double firstProbability, double secondProbability, String name)
    {
        firstClass= new PassengerQueue();
        secondClass=new PassengerQueue();
        firstArrival=new BooleanSource(firstProbability);
        secondArrival=new BooleanSource(secondProbability);
        stationName=name;
    }

    /**
     * This is a method that simulates the station, where passengers are added to the correct queues
     * based on their class type.
     */
    public void simulateTimeStep()
    {
       if (firstArrival.occurs())
       {
           Passenger x=new Passenger(true);
           firstClass.enqueue(x);
       }
       if (secondArrival.occurs())
       {
           Passenger x=new Passenger(false);
           secondClass.enqueue(x);
       }
    }

    /**
     * This is a method that simulates the station and checks if passengers can still arrive at the station at the
     * current time.They are then added to the correct queues based on their class type.
     *
     * @param currentTime
     *    The current time of the stimulation
     * @param lastTime
     *    The last arrival time of Passengers at the station
     */
    public void simulateTimeStep(int currentTime, int lastTime)
    {
        addedFirst=false;
        addedSecond=false;
        if(currentTime<=lastTime)
        {
            if (firstArrival.occurs())
            {
                Passenger x=new Passenger(currentTime,true);
                firstClass.enqueue(x);
                addedFirst=true;
            }
            if (secondArrival.occurs())
            {
                Passenger x=new Passenger(currentTime, false);
                secondClass.enqueue(x);
                addedSecond=true;
            }
        }
    }

    /**
     * This method handles the removal of the Passengers from the station's queue. Upon removal, the
     * Passengers are added to the correct list in an array that keeps track of all the Passengers
     * embarking the Train. It first checks if there are available space in the Train and gives the
     * first class Passenger priority in filling the second class seats before letting the second
     * class Passengers embark.
     *
     * @param firstSpace
     *    Available first class seats in train
     * @param secondSpace
     *     Available second class seats in train
     * @param currentTime
     *     Current time of the simulation
     * @return
     *    A PassengerQueue array that has the queues of first and second class Passengers embarking
     *    the train at the station
     */
    public PassengerQueue[] removePassengers(int firstSpace, int secondSpace, int currentTime)
    {
        PassengerQueue [] list=new PassengerQueue[2];
        PassengerQueue embarkingFirst = new PassengerQueue();
        list[0]=embarkingFirst;
        PassengerQueue embarkingSecond = new PassengerQueue();
        list[1]=embarkingSecond;
        int numFirstClassSeatToFill = Math.min(firstSpace, firstClass.size());
        int numSecClassSeatRemain = secondSpace;
        embarkPassengers(numFirstClassSeatToFill,firstClass,embarkingFirst,true,currentTime);
        if (firstClass.size() > 0 && secondSpace> 0)
        {
            int numSecondClassSeatToFill = Math.min(secondSpace, firstClass.size());
            embarkPassengers(numSecondClassSeatToFill,firstClass,embarkingSecond,true,currentTime);
            numSecClassSeatRemain = secondSpace - numSecondClassSeatToFill;
        }
        if (numSecClassSeatRemain > 0)
        {
            int numSecondClassSeatToFill = Math.min(numSecClassSeatRemain, secondClass.size());
            embarkPassengers(numSecondClassSeatToFill,secondClass,embarkingSecond,false,currentTime);
        }
        return list;
    }

    /**
     * This method contains a loop used to remove the Passengers from the correct queue and embark them onto the Train.
     * It increments total embarked Passengers and total wait time accordingly for each class.
     *
     * @param seatsToFill
     *    The number of Passengers embarking in that class
     * @param classType
     *    The indication of whether the Passenger is a first or second class Passenger
     * @param embarkingList
     *    The list that keeps track of the Passengers embarked depending on class type
     * @param isToIncrementFirst
     *    A boolean that indicates whether to increment the total Passengers and waitTime for the first or second class
     * @param currentTime
     *    The current time of the simulation
     */
    public void embarkPassengers(int seatsToFill, PassengerQueue classType, PassengerQueue embarkingList, boolean isToIncrementFirst, int currentTime )
    {
        for(int i =0; i<seatsToFill; i++) {
            Passenger classPassenger = classType.dequeue();
            embarkingList.enqueue(classPassenger);
            if(isToIncrementFirst)
            {
                this.totalFirstEmbarkedPassenger++;
                this.totalFirstWaitTime+=currentTime - classPassenger.getArrivalTime();
            }
            else
            {
                this.totalSecEmbarkedPassenger++;
                this.totalSecWaitTime+=currentTime - classPassenger.getArrivalTime();
            }
        }
    }

    /**
     * This method organizes the stations' information such as passengers who arrived at the station and are waiting in
     * the first and second class queues into a string format.
     *
     * @return
     *    A string that contains the information of the station's overview
     */
    public String toString()
    {
        String result="\n" + stationName + ":";
        if(firstClass.isEmpty() || !addedFirst)
        {
          result+="\nNo first class passenger arrives";
        }
        else
        {
            result+="\nFirst class passenger ID " + firstClass.peekLast() + " arrives";
        }
        if(secondClass.isEmpty()|| !addedSecond)
        {
            result+="\nNo second class passenger arrives";
        }
        else
        {
            result+="\nSecond class passenger ID " + secondClass.peekLast() + " arrives";
        }
        result+=("\nQueues:\nFirst [" + firstClass + "\nSecond ["+secondClass);
        return result;
    }

    /**
     * This is a getter method in the Station class.
     *
     * @return
     *    The total number of first class Passengers that boarded a train
     */

    public int getTotalFirstEmbarkedPassenger()
    {
        return totalFirstEmbarkedPassenger;
    }

    /**
     * This is a getter method in the Station class.
     *
     * @return
     *    The total number of second class Passengers that boarded a train
     */
    public int getTotalSecEmbarkedPassenger()
    {
        return totalSecEmbarkedPassenger;
    }

    /**
     * This is a getter method in the Station class.
     *
     * @return
     *    The total wait time of first class Passengers
     */
    public int getTotalFirstWaitTime()
    {
        return totalFirstWaitTime;
    }

    /**
     * This is a getter method in the Station class.
     *
     * @return
     *    The total wait time of second class Passengers
     */
    public int getTotalSecWaitTime()
    {
        return totalSecWaitTime;
    }

    /**
     * This is a getter method in the Station class.
     *
     * @return
     *    The queue that holds all the first class Passengers at the station
     */
    public PassengerQueue getFirstClass()
    {
        return firstClass;
    }

    /**
     * This is a getter method in the Station class.
     *
     * @return
     *    The queue that holds all the second class Passengers at the station
     */
    public PassengerQueue getSecondClass()
    {
        return secondClass;
    }

    /**
     * This is a getter method in the Station class.
     *
     * @return
     *    The string that stores the station's name
     */
    public String getStationName()
    {
        return stationName;
    }
}
