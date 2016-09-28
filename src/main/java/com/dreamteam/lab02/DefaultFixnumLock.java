package com.dreamteam.lab02;

import java.util.ArrayList;

abstract class DefaultFixnumLock implements FixnumLock {

    static int threads = 10;
    static ArrayList<Integer> pidPool;
    int pid;

    public DefaultFixnumLock() {
        if(pidPool.size() < threads) return;

        for(int i = pidPool.size(); i < threads; i++) {
            pidPool.add(-1);
        }
    }

    public int getId() {
        return pid;
    }

    private int getFree() {
        for(int i = 0; i < pidPool.size(); i++) {
            if(pidPool.get(i).equals(0)) return i;
        }

        return 0;
    }

    public void register() {
        pid = getFree();
        pidPool.set(pid, pid);
    }

    public void unregister() {
        pidPool.set(pid, -1);
        pid = 0;
    }
}
