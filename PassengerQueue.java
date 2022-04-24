package com.company;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This is a queue class named PassengerQueue that inherits all the properties of the LinkedList class. It has
 * all the functions of a queues(enqueues, dequeue, peek, isEmpty).
 *
 * @author  Tracy Yip 
 */

public class PassengerQueue extends LinkedList<Object>
{
    /**
     * This is a method that adds a new Passenger object to the end of the queue.
     *
     * @param p
     *    A Passenger object that needs to be added
     */
    public void enqueue(Passenger p)
    {
        super.add(p);
    }

    /**
     * This is a method that removes a Passenger object from the front of the queue
     * and returns the object.
     *
     * @return
     *    The Passenger object removed
     */
    public Passenger dequeue()
    {
        return (Passenger) super.remove();
    }

    /**
     * This method organizes all the Passengers in the queue into a string format ready to be printed.
     *
     * @return
     *    A string that contains all the Passengers in the queue
     */
    public String toString()
    {
        String output="";
        Iterator iterator= this.iterator();
        if(this.isEmpty())
        {
            output+="empty]  ";
        }
        while(iterator.hasNext())
        {
            Passenger x=(Passenger) iterator.next();
            output+= x.toString() + ", ";
        }
        return output.substring(0,output.length()-2);
    }

    /**
     * This method looks at and returns the Passenger object at the front of the queue.
     *
     * @return
     *    The Passenger object at the top of the queue
     */
    public Passenger peek()
    {
        return (Passenger) super.peek();
    }

    /**
     * This method looks at and returns the Passenger object at the end of the queue.
     *
     * @return
     *    The Passenger object at the end of the queue
     */
    public Passenger peekLast()
    {
        return (Passenger) super.peekLast();
    }

    /**
     * This method checks whether there are objects in the queue.
     *
     * @return
     *    A boolean that indicates if the queue is empty
     */
    public boolean isEmpty()
    {
        return this.size()==0;
    }
}
