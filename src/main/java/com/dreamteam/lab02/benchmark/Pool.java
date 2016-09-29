package com.dreamteam.lab02.benchmark;


import com.dreamteam.lab02.locks.BackeryLock;
import com.dreamteam.lab02.locks.DefaultFixnumLock;
import com.dreamteam.lab02.locks.FixnumLock;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class Pool {
    private ArrayList<LockTestThread> threads = new ArrayList<>();
    private int poolSize;

    String testResource = "untouched";


    public void onResourceUsed() {
        System.out.println("resource status: " + testResource);
    }

    public Pool(int poolSize) {
        System.out.println("Creating pool with " + poolSize + " threads");
        for(int i = 0; i < poolSize; ++i) {
            threads.add(new LockTestThread(new BackeryLock()));
        }

    }



    public void runThreads() throws InterruptedException {
        for(LockTestThread thread: threads) {
            thread.start();
        }

    }


    public class LockTestThread extends Thread {
        private FixnumLock lock;

        public LockTestThread(FixnumLock lock) {
            super();
            System.out.println("Creating task...");
            this.lock = lock;
        }
        @Override
        public void run() {
            System.out.println("Registrate thread: " + Thread.currentThread().getName());
            lock.register();
            System.out.println("Run task...");
            int threadNum = new Random().nextInt(10);
            while(!(threadNum == 5)){
                System.out.println("Lock thread: " + Thread.currentThread().getName());
                lock.lock();
                System.out.print("Previous status: ");
                onResourceUsed();
                try {
                    Thread.sleep(threadNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                testResource = "currnet thread: " + Thread.currentThread().getName();

                onResourceUsed();
                System.out.println("Unlock thread: " + Thread.currentThread().getName());
                threadNum = new Random().nextInt(10);
                lock.unlock();

            }
            System.out.println("Unregistrate thread: " + Thread.currentThread().getName());
            lock.unregister();
        }
    }



}
