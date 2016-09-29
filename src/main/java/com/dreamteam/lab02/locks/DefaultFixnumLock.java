package com.dreamteam.lab02.locks;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public abstract class DefaultFixnumLock implements FixnumLock {

    static int threadNumber = 10;
    static ArrayList<Boolean> pidList = getFilledBoolList(threadNumber);
    int pid = -1;
    static final Object sync = new Object();

    protected static ArrayList<Boolean> getFilledBoolList(int size) {
        ArrayList<Boolean> list = new ArrayList<>();
        for(int i = 0; i < size; ++i) {
            list.add(false);
        }
        return list;
    }

    protected static ArrayList<Integer> getFilledIntList(int size) {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < size; ++i) {
            list.add(0);
        }
        return list;
    }

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
        if(!takePid()) {
            return false;
        }

        pidList.set(pid, true);

        return true;
    }

    public void unregister() {
        pidList.set(pid, false);
        resetPid();
    }

    private boolean takePid() {
        synchronized (sync) {
            for (int i = 0; i < threadNumber; ++i) {
                if (!pidList.get(i)) {
                    pid = i;
                    return true;
                }
            }

            return false;
        }
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

    public boolean tryLock() {
        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }
}
