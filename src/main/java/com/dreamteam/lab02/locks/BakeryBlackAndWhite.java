package com.dreamteam.lab02.locks;


import java.util.ArrayList;

public class BakeryBlackAndWhite extends DefaultFixnumLock{
    /**
     * Black and white flag, defines the the color of the open queue.
     * false - black, true - white.
     */
    static boolean color;
    /**
     * Flag which indicate if the thread wants to enter the critical region
     * false - no interest, true - entering the line
     * Java initializes each element of 'entering' to false
    **/
    static ArrayList<Boolean> entering = getFilledList(threadNumber, false);



    /**
     * Ticket for threads in line, n - number of threads
     * Java initializes each element of 'ticket' to 0
     */
    static ArrayList<Integer> ticket = getFilledList(threadNumber, 0);


    /**
     * Specify the color of each thread, the abstract queue which it joined to.
     * false - black, true - white
     */
    static ArrayList<Boolean> myColor = getFilledList(threadNumber, false);

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
//        System.out.println("Inside Critical Section, ticket number = " + ticket.get(pid));
//        System.out.println("Color of the ticket: " + myColor.get(pid));
    }

    @Override
    public void unlock() {
        color = myColor.get(pid).equals(false);
        ticket.set(pid,0);
    }
}