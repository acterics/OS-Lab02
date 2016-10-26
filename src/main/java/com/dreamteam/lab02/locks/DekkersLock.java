package com.dreamteam.lab02.locks;

import java.util.ArrayList;

public class DekkersLock extends DefaultFixnumLock {

    static int threadNumber = 2;
    private static ArrayList<Boolean> flag = getFilledList(threadNumber, false);
    private static int turn = 0;

    @Override
    public void lock() {

        flag.set(pid, true);
        while( flag.get(invertedPid())) {
            if (turn != pid) {
                flag.set(pid, false);
                while (turn != pid) {
                    Thread.yield();
                }
                flag.set(pid, true);
            }
        }
    }

    private int invertedPid() {
        return pid^1;
    }

    @Override
    public void unlock() {
        flag.set(pid, false);
        turn = invertedPid();
    }
}
