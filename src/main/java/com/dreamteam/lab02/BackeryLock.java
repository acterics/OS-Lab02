package com.dreamteam.lab02;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class BackeryLock extends DefaultFixnumLock {

    private ArrayList<Integer> ticket = new ArrayList<Integer>(DefaultFixnumLock.threads);
    private ArrayList<Boolean> entering = new ArrayList<Boolean>(DefaultFixnumLock.threads); // 1 when thread entering in line


    // ticket for threads in line, n - number of threads
    // Java initializes each element of 'ticket' to 0


    // Java initializes each element of 'entering' to 0
    public void lock() // thread ID
    {
        entering.set(pid, true);
        int max = 0;

        for (int i = 0; i < threads; i++) {
            int current = ticket.get(i);
            if (current > max) {
                max = current;
            }
        }

        ticket.set(pid, 1 + max);
        entering.set(pid, false);
        for (int i = 0; ticket.size() > i; ++i) {
            if (i != pid) {

                while (entering.get(i)) {
                    Thread.yield();
                } // wait while other thread picks a ticket

                while (ticket.get(i) != 0 && (ticket.get(pid) > ticket.get(i) ||
                        (ticket.get(pid) == ticket.get(i) && pid > i))) {
                    Thread.yield();
                }
            }
        }
        // The critical section goes here...

    }

    public void lockInterruptibly() throws InterruptedException {

    }

    public boolean tryLock() {
        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public void unlock() {
        ticket.set(pid, 0);
    }

    public Condition newCondition() {
        return null;
    }

}
