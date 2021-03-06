package com.dreamteam.lab02.locks;

import java.util.ArrayList;

public class BakeryLock extends DefaultFixnumLock {

    static ArrayList<Boolean> entering = getFilledList(threadNumber, false); // 1 when thread entering in line
    static ArrayList<Integer> ticket = getFilledList(threadNumber, 0);

    @Override
    public void lock() // thread ID
    {
        /*
            Set max+1 ticket number for thread
         */
        entering.set(pid, true); // set entering state for thread
        int max = 0;
        for (int i = 0; i < threadNumber; i++) {
            int current = ticket.get(i);
            if (current > max) {
                max = current;
            }
        }
        ticket.set(pid, 1 + max);
        entering.set(pid, false);



        for (int i = 0; i < ticket.size(); ++i) {
            // go by registred threads
            if (i != pid) {

                // wait if curent thread in entering state
                while (entering.get(i)) {
                    Thread.yield();
                } // wait while other thread picks a ticket

                // Wait if thread[i] have ticket & thread[i]'s ticket number > current thread's
                // or
                // Tickets are equals and current thread created later than thread[i]
                while (!ticket.get(i).equals(0) && (ticket.get(pid) > ticket.get(i) ||
                        (ticket.get(pid).equals(ticket.get(i)) && pid > i))) {
                    Thread.yield();
                }
            }
        }
        // The critical section goes here...
        System.out.println("Inside Critical Section, ticket number = " + ticket.get(pid));
    }

    public void unlock() {
        ticket.set(pid, 0);
    }
}
