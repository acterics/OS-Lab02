package com.dreamteam.lab02.locks;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;

abstract class DefaultFixnumLock implements FixnumLock {

    static int threadNumber = 10;
    static ArrayList<Boolean> pidList = new ArrayList<>(threadNumber);
    int pid = -1;

    public DefaultFixnumLock() {
    }

    public int getId() {
        return pid;
    }

    public void lock() {
        throw new NoSuchMethodError("Method does not implemented.");
    }

    public void unlock() {
        throw new NoSuchMethodError("Method does not implemented.");
    }

    public boolean register() {
        if(!getPid()) {
            return false;
        }

        pidList.add(pid, true);

        return true;
    }

    public void unregister() {
        pidList.add(pid, false);
        resetPid();
    }

    private boolean getPid() {
        for(int i = 0; i < threadNumber; ++i) {
            if(pidList.get(i)) {
                pid = i;
                return true;
            }
        }

        return false;
    }

    private void resetPid() {
        pid = -1;
    }

    public Condition newCondition() {
        throw new UnsupportedOperationException("Conditions does not supports by this type of lock");
    }

    public void lockInterruptibly() throws InterruptedException {
        throw new InterruptedException("just that's why");
    }
}
