package com.dreamteam.lab02;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

abstract class DefaultFixnumLock implements FixnumLock {

    static int threads = 10;
    static ArrayList<Integer> pidList;
    int pid;

    public int getId() {
        return 0;
    }

    public void register() {

    }

    public void unregister() {

    }
}
