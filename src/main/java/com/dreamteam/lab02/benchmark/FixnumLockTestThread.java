package com.dreamteam.lab02.benchmark;

import com.dreamteam.lab02.locks.FixnumLock;




public class FixnumLockTestThread extends Thread {
    private FixnumLock lock;

    public FixnumLockTestThread(FixnumLock lock) {
        super();
        System.out.println("Creating thread...");
        this.lock = lock;
    }
    @Override
    public void run() {

        while(!lock.register()) {
            Thread.yield();
        }

        lock.lock();
        System.out.println("Locked thread: " + Thread.currentThread().getName());
        f(30);
        lock.unlock();
        System.out.println("Unlocked thread: " + Thread.currentThread().getName());

        lock.unregister();
    }

    private int f(int i) {
        if(i == 0) return 0;
        if(i == 1) return 1;
        return f(i-2) + f(i-1);
    }
}
