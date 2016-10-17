// Java implementation of modified Bakery algorithm - Black and White Bakery Algorithm ( G. Taubenfeld, 2004 )
// Description of the algorithm (page 223): https://books.google.com.ua/books?id=jHyHdHSZSGkC&lpg=PA224&ots=JY6qD1MRJK&dq=black%20and%20white%20bakery%20algorithm%20java&hl=uk&pg=PA223#v=onepage&q=black%20and%20white%20bakery%20algorithm%20java&f=false
// Pseudo code: http://www.cs.tau.ac.il/~afek/gadi.pdf

package com.dreamteam.lab02.locks;
import java.util.*;

public class BakeryBlackAndWhite extends DefaultFixnumLock{
    // @color - black and white flag, defines the the color of the open queue.
    // false - black, true - white.
    static boolean color;

    // @entering - flag which indicate if the thread wants to enter the critical region
    // false - no interest, true - entering the line
    // Java initializes each element of 'entering' to false
    static ArrayList<Boolean> entering = getFilledBoolList(threadNumber);

    // @ticket - ticket for threads in line, n - number of threads
    // Java initializes each element of 'ticket' to 0
    static ArrayList<Integer> ticket = getFilledIntList(threadNumber);

    // @myColor - specify the color of each thread, the abstract queue which it joined to.
    // false - black, true - white
    static ArrayList<Boolean> myColor = getFilledBoolList(threadNumber);

    public BakeryBlackAndWhite() {
        color = true;
    }

    @Override
    public void lock(){
        entering.set(pid,true);
        myColor.set(pid,color);
        int max = 0;
        for (int i = 0; i < threadNumber; i++) {
            int current = ticket.get(i);
            if (current > max && myColor.get(pid).equals(myColor.get(i))) {
                max = current;
            }
        }
        ticket.set(pid, 1 + max);
        entering.set(pid,false);

        for (int i = 0; i < threadNumber; i++) {
            while (entering.get(i))
                Thread.yield();
            if (myColor.get(i) == myColor.get(pid)){
                while (!ticket.get(i).equals(0)
                        && (ticket.get(pid) > ticket.get(i) || (ticket.get(pid).equals(ticket.get(i)) && pid > i))
                        && myColor.get(i).equals(myColor.get(pid))) {
                    Thread.yield();
                }
            }
            else {
                while (!ticket.get(i).equals(0) && myColor.get(pid).equals(color) && !myColor.get(i).equals(myColor.get(pid))) {
                    Thread.yield();
                }
            }
        }

        // critical section goes here ...
        System.out.println("Inside Critical Section, ticket number = " + ticket.get(pid));
        System.out.println("Color of the ticket: " + myColor.get(pid));
    }

    @Override
    public void unlock() {
        if (myColor.get(pid).equals(false)){
            color = true;
        }
        else {
            color = false;
        }

        ticket.set(pid,0);
    }
}