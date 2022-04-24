package com.company;
import java.util.Scanner;

/**
 * This class represents the overall simulator for LIRR. It is where the users get to interact with the program to
 * collect statistics such as the total number to Passengers served and their average wait time at each station.
 *
 * @author  Tracy Yip Student ID: 114527635 Recitation: R-03
 */
public class LIRRSimulator
{
    private static String[] stationNames={"Mineola", "Hicksville", "Syosset", "Hungtington"};//An array that stores the names of the stations
    private static Station [] stations =new Station[4];//An array that stores the stations
    private static int currentTime=0;//An instance variable that stores the currentTime of the simulation
    private static int lastTime;//An instance variable that stores the last time Passengers can arrive at the stations
    private static int numTrain;//An instance variable that stores the number of Trains
    private static Train [] trains;//An array of Trains
    private static int totalStimulation;//An instance variable that holds the max time the stimulation runs until
    private static int totalServed;//An instance variable that keeps tracks of the total number of Passengers that boarded the Trains
    private static int firstNoSeat;//An instance variable that stores the number of first class Passengers that are left at the stations
    private static int secondNoSeat;//An instance variable that stores the number of second class Passengers that are left at the stations

    /**
     * This is the main method where the users are asked to enter the arrival probabilities of Passengers to start
     * initialize the stations and trains and start the simulation.
     */
    public static void main (String[] args)
    {
       Scanner scan=new Scanner(System.in);
       System.out.println("\n" +
           "Welcome to the LIRR Simulator, Leaving Irate Riders Regularly");
       initializeStations(scan);
       System.out.println("Please enter first class capacity:");
       int first=scan.nextInt();
       System.out.println("Please enter second class capacity:");
       int second=scan.nextInt();
       System.out.println("Please enter number of trains:");
       numTrain=scan.nextInt();
       trains=new Train[numTrain];
       initializeTrain(first,second,numTrain, stations);
       getLastArrivalTime(scan);
       startStimulation();
    }

    /**
     * This is a method that asks the users to enter the last arrival time of Passengers at the stations.
     *
     * @param scan
     *    A scanner that is used to get user input
     */
    private static void getLastArrivalTime(Scanner scan)
    {
        System.out.println("Please enter last arrival time of passengers:");
        lastTime=scan.nextInt();
        try
        {
            checkArrivalTime();
        }
        catch(InvalidInputException x)
        {
            System.out.println("The time you entered will cause an error. Please try again.");
            getLastArrivalTime(scan);
        }
    }

    /**
     * This method checks if the value entered for the lastTime is valid.
     *
     * @throws InvalidInputException
     *    When the value entered for lastTime will cause an error in the simulation
     */
    public static void checkArrivalTime() throws InvalidInputException
    {
        if (lastTime > totalStimulation || lastTime<0)
        {
            throw new InvalidInputException();
        }
    }

    /**
     * This method is used to initialize and construct the Train objects with the information passed in the parameter.
     * It also sets the total simulation time.
     *
     * @param first
     *    The max number of seats in the first class
     * @param second
     *    The max number of seats in the second class
     * @param numTrain
     *    The number of trains running
     * @param stations1
     *    The array that holds the station the Train needs to stop at
     */
    public static void initializeTrain(int first, int second, int numTrain, Station [] stations1)
    {
        for(int i=0; i<numTrain; i++)
        {
            int time=i*5;
            trains[i]=new Train(i+1, first, second, time, stations1);
        }
        if(numTrain==0)
        {
            totalStimulation=0;
        }
        else
        {
            totalStimulation = ((numTrain - 1) * 5) + 15;
        }
    }

    /**
     * This method is used to set up the Station objects for the simulation. It uses the arrival probabilities
     * entered by users to construct the stations.
     *
     * @param scan
     *    A scanner used to get user inputs
     */
    public static void initializeStations(Scanner scan)
    {
        try
        {
            for (int i = 0; i < stations.length; i++)
            {
                System.out.println(stationNames[i]);
                System.out.println("Please enter first class arrival probability: ");
                double first = scan.nextDouble();
                checkProbability(first);
                System.out.println("Please enter second class arrival probability:");
                double second = scan.nextDouble();
                checkProbability(second);
                stations[i] = new Station(first, second, stationNames[i]);
            }
        }
        catch (InvalidProbabilityException| IllegalArgumentException a)
        {
            System.out.println("The input you enter will not work. Please try again.");
            initializeStations(scan);
        }
    }

    /**
     * This method checks if the probability entered by the users are valid.
     *
     * @param x
     *    The probability entered by users
     * @throws InvalidProbabilityException
     *    When the probability entered is less than 0.0 or greater than 1.0
     */
    public static void checkProbability(double x) throws InvalidProbabilityException
    {
        if(x>1.0 || x<0.0)
        {
            throw new InvalidProbabilityException();
        }
    }

    /**
     * This method is used to run the simulation where the trains will go to each stop and pick up passengers. The
     * simulation will end when the last train reaches the last stop, which is Mineola.
     */
    public static void startStimulation()
    {
        System.out.println("Begin Simulation:");
        System.out.println("---------------------------------------------");
        for(int i=0; i<=totalStimulation; i++)
        {
            System.out.println("Time " + i + ":");
            System.out.println("\nStation Overview:");
            currentTime = i;
            for (Station x : stations)
            {
                x.simulateTimeStep(currentTime,lastTime);
                System.out.println(x.toString());
            }
            System.out.println("\nTrains:\n");
            for (Train xTrain : trains)
            {
                xTrain.simulateTimeStep(currentTime);
            }
            System.out.println("-------");
        }
        for (Station x : stations)
        {
            totalServed += x.getTotalFirstEmbarkedPassenger() + x.getTotalSecEmbarkedPassenger();
            firstNoSeat += x.getFirstClass().size();
            secondNoSeat+= x.getSecondClass().size();
        }
        System.out.println("A total of " + totalServed+ " passengers were served, " + firstNoSeat +
            " first class passengers were left without a seat, " + secondNoSeat + " second class " +
            "passengers were left without a seat.");

        printStationSummary();
    }

    /**
     * This method prints out the summary and stats such as total Passengers served and average wait time
     * at the end of the simulation.
     */
    public static void printStationSummary()
    {
        int i=0;
        for(Station x: stations)
        {
            int totalFirstEmbarkedPassengers=x.getTotalFirstEmbarkedPassenger();
            int totalSecondEmbarkedPassengers=x.getTotalSecEmbarkedPassenger();
            double oneWait=totalFirstEmbarkedPassengers ==0 ? 0:Math.round ((double) x.getTotalFirstWaitTime()/
                totalFirstEmbarkedPassengers);
            double twoWait=totalSecondEmbarkedPassengers ==0 ? 0:Math.round((double) x.getTotalSecWaitTime()/
                totalSecondEmbarkedPassengers);
            System.out.println("At " + stationNames[i] + " " + totalFirstEmbarkedPassengers + " first class passengers"
                + " were served with an average wait time of " + (int) oneWait + " min, " + totalSecondEmbarkedPassengers
                + " second class passengers were served with an average wait time of " + (int)twoWait + " min. " +
                x.getFirstClass().size() + " first class passengers and " + x.getSecondClass().size() + " second class " +
                "passengers were left without a seat.");
            i++;
        }
    }
}
