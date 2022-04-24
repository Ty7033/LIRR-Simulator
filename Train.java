package com.company;

/**
 * This class represents a Train object and its simulation. It contains instance variables that stores the first and
 * second class capacity for the train car. It also keeps track of the time for the train to reach the next station.
 * It is initialized to contain a list of stations it has to reach.
 *
 * @author  Tracy Yip Student ID: 114527635 Recitation: R-03
 */
public class Train {
    private int firstCapacity;//A variable that indicates the max number of first class seats on the Train
    private int secondCapacity;//A variable that indicates the max number of second class seats on the Train
    private int timeUntilNextArrival;//A variable that keeps track of the number of minutes until the Train reaches the next station
    private int trainId;//An integer that stores the Train's number
    private int currentFirst=0;//An integer that stores the number of first class seats taken in the Train
    private int currentSecond=0;//An integer that stores the number of second class seats taken in the Train
    private int previousFirst=0;//An integer that stores the number of first class seats taken in the Train before arriving at the station
    private int previousSecond=0;//An integer that stores the number of second class seats taken in the Train before arriving at the station
    private Station currentStation;//The current station the train is heading towards or is at
    private PassengerQueue[] embarkingList;//An array of PassengerQueue that holds the Passengers boarding at the current Station
    private Station[] stations;//A list of the stations the train has to stop at

    /**
     * A constructor for the Train object that sets the capacities and time until next arrival to zero. It also
     * initializes the stations array to a size of four.
     */
    public Train()
    {
        firstCapacity=0;
        secondCapacity=0;
        timeUntilNextArrival=0;
        stations=new Station[4];
    }

    /**
     * This is a constructor for the Train object that sets the train ID, first & second capacity, timeUntilNextArrival,
     * and the list of stations to the values passed in the parameter.
     *
     * @param trainId
     *    An integer that indicates the Train number
     * @param firstCapacity
     *    An integer that indicates the number of first class seats in the Train
     * @param secondCapacity
     *    An integer that indicates the number of second class seats in the Train
     * @param timeUntilNextArrival
     *    An integer that indicates the number of minutes until the Train reaches the next station
     * @param initStations
     *    An array that stores the stations that the Train needs to stop at
     */
    public Train(int trainId , int firstCapacity, int secondCapacity, int timeUntilNextArrival, Station [] initStations)
    {
        this.firstCapacity=firstCapacity;
        this.secondCapacity=secondCapacity;
        this.timeUntilNextArrival=timeUntilNextArrival;
        this.trainId=trainId;
        stations=initStations.clone();
        currentStation= stations[stations.length-1];
    }

    /**
     * This method is used to stimulate the Train each minute. It determines the available space in the first and
     * second class and embarks the appropriate number of Passengers from the station when the train arrives at
     * a station.
     */
    public void simulateTimeStep()
    {
        if(timeUntilNextArrival==0)
        {
            int availableFirstCapacity = firstCapacity - currentFirst;
            int availableSecCapacity = secondCapacity - currentSecond;
            embarkingList = currentStation.removePassengers(availableFirstCapacity,availableSecCapacity,0);
            removeStation();
            previousFirst=currentFirst;
            previousSecond=currentSecond;
            currentFirst=currentFirst + embarkingList[0].size();
            currentSecond=currentSecond+embarkingList[1].size();
            timeUntilNextArrival = 5;
        }
        else
        {
            timeUntilNextArrival--;
        }
    }

    /**
     * This method is used to stimulate the Train each minute. It determines the available space in the first and
     * second class and embarks the appropriate number of Passengers from the station when the train arrives
     * at a station.It passes the current time from the parameter to the removePassenger method in Stations to
     * correctly calculate the wait time of each Passenger.
     *
     * @param time
     *    The current time of the simulation
     */
    public void simulateTimeStep(int time)
    {
        if(timeUntilNextArrival==0)
        {
            int availableFirstCapacity = firstCapacity - currentFirst;
            int availableSecCapacity = secondCapacity - currentSecond;
            embarkingList = currentStation.removePassengers(availableFirstCapacity,availableSecCapacity, time);
            previousFirst=currentFirst;
            previousSecond=currentSecond;
            currentFirst=currentFirst + embarkingList[0].size();
            currentSecond=currentSecond +embarkingList[1].size();
            System.out.println(this.toString());
            timeUntilNextArrival = 4;
            removeStation();
        } else {
            System.out.println(this.toString());
            timeUntilNextArrival--;
        }
        if(stations.length>0)
        {
            currentStation=stations[stations.length-1];
        }

    }

    /**
     * This method is used to remove a station from the list of stations the train still needs to stop at
     * after leaving a station.
     */
    public void removeStation()
    {
        if(stations.length>0)
        {
            Station[] newStation = new Station[stations.length - 1];
            for(int i=0; i<stations.length - 1; i++)
            {
                newStation[i] = stations[i];
            }
            stations = newStation;
        }
    }

    /**
     * This method organizes the Train's information such as the station it is at or approaching, the Passengers
     * embarking, and the time it still needs to get to the next station.
     *
     * @return
     *    A string that contains all the information to be printed for the Train overview
     */
    public String toString()
    {
        String output="";
        if(stations.length==0)
        {
            System.out.println("Train " + trainId + " has stopped picking up passengers.");
        }
        else if(timeUntilNextArrival==0)
        {
            output+="Train " + trainId + " arrives at " + currentStation.getStationName() + ". There are "
                + previousFirst + " passengers in first class and " +previousSecond + " in second class.\nPassengers "+
                "embarking " + "in first class: " + printOut(0) + "\nPassengers embarking in second class: "
                + printOut(1) + '\n';
        }
        else
        {
            output+="Train " + trainId+ " will arrive at " + currentStation.getStationName() + " in ";
            if(timeUntilNextArrival>1)
            {
                output+= timeUntilNextArrival + " minutes.\n";
            }
            else
            {
                output+= timeUntilNextArrival + " minute.\n";
            }
        }
        return output;
    }

    /**
     * This method gets the Passengers in the indicated PassengerQueue of embarkingList and organizes them into
     * a string to be printed out.
     * @param index
     *    The index of the embarkingList array that is being looked at
     * @return
     *    A string that holds the Passengers embarking on the Train
     */
    public String printOut(int index)
    {
        String out="";
        if(embarkingList[index].isEmpty())
        {
            out+="none  ";
        }
        else
        {
            int embarkingListTotal=embarkingList[index].size();
            for(int i=0; i<embarkingListTotal; i++)
                out+= embarkingList[index].dequeue().toShortString() + ", ";
        }
        return out.substring(0,out.length()-2);
    }
}
