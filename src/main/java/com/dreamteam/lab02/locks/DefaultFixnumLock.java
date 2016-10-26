package com.dreamteam.lab02.locks;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;


/**
 * Default implementation of basic FixnumLock methods without locking algorithm
 */
public abstract class DefaultFixnumLock implements FixnumLock {

    /**
     * Max count of threads in queue
     */
    protected final static int threadNumber = 20;



    static ArrayList<Boolean> pidList = getFilledList(threadNumber, false);

    /**
     * Thread id in queue
     */
    int pid = -1;

    private static final Object sync = new Object();


    /**
     * Utility static method for getting filled list
     * @param size the size of list
     * @param value list elements value
     * @param <T> list elements class
     * @return filled list
     */
    public static <T> ArrayList<T> getFilledList(int size, T value) {
        ArrayList<T> list = new ArrayList<>();
        for(int i = 0; i < size; ++i) {
            list.add(value);
        }
        return list;
    }

    @Override
    public int getId() {
        return pid;
    }

    @Override
    public boolean register() {

        if(!takePid()) {
            return false;
        }

        pidList.set(pid, true);
        System.out.println("Registrate thread: " + Thread.currentThread().getName());
        return true;
    }

    @Override
    public void unregister() {
        pidList.set(pid, false);
        resetPid();
        System.out.println("Unregistrate thread: " + Thread.currentThread().getName());
    }


    /**
     * Take free id from list
     * @return {@code true} if there's free id {@code false} if there's maximum threads count in queue
     */
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

    @Deprecated
    public Condition newCondition() {
        throw new UnsupportedOperationException("Conditions does not supports by this type of lock");
    }

    @Deprecated
    public void lockInterruptibly() throws InterruptedException {
        throw new InterruptedException();
    }

    @Deprecated
    public boolean tryLock() {
        return false;
    }

    @Deprecated
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }
}
