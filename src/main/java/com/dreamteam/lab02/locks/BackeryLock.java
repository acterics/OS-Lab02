package com.dreamteam.lab02.locks;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BackeryLock extends DefaultFixnumLock {

    static ArrayList<Boolean> entering = new ArrayList<>(threadNumber); // 1 when thread entering in line
    static ArrayList<Integer> ticket = new ArrayList<>(threadNumber);
    // ticket for threads in line, n - number of threads
    // Java initializes each element of 'ticket' to 0

    // Java initializes each element of 'entering' to 0
    public void lock(int pid) // thread ID
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
    }

    public void unlock(int pid) {
        ticket.set(pid, 0);
    }

    public boolean tryLock() {
        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }
}
