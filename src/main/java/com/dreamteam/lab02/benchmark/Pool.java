package com.dreamteam.lab02.benchmark;


import com.dreamteam.lab02.locks.FixnumLock;


import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class Pool {
    private ArrayList<LockTestThread> threads = new ArrayList<>();
    private int poolSize;

    String testResource = "untouched";
    boolean isInited = false;

    public void onResourceUsed() {
//        System.out.println("resource status: " + testResource);
    }

    public Pool(int poolSize) {
        this.poolSize = poolSize;

    }

    public void initThreads(Class lockClass) throws IllegalAccessException, InstantiationException {
        if(isInited) {
            clearThreads();
        }
        isInited = true;
        System.out.println("Creating pool with " + poolSize + " threads");
        System.out.println("Lock class: " + lockClass.toString());
        for(int i = 0; i < poolSize; ++i) {
            threads.add(new LockTestThread((FixnumLock)lockClass.newInstance())); // choosing type of lock
        }
    }
    public void clearThreads() {
        isInited = false;
        for(Thread thread: threads) {
            thread.interrupt();
        }
        threads.clear();
    }

    private void joinAll() throws InterruptedException {
        for(LockTestThread thread: threads) {
            thread.join();
        }
    }



    public void runThreads() throws Exception {
        if(!isInited)
            throw new Exception("Threads isn't inited");
        long time = System.nanoTime();
        float dif;
        System.out.println("**********************Pool task started**********************");
        for(LockTestThread thread: threads) {
            thread.start();
        }
        joinAll();
        dif = (float)(System.nanoTime() - time) / 1000000000;
        System.out.println("**********************Pool task finished with time " + dif +" **********************");


    }


    public class LockTestThread extends Thread {
        private FixnumLock lock;

        public LockTestThread(FixnumLock lock) {
            super();
            System.out.println("Creating thread...");
            this.lock = lock;
        }
        @Override
        public void run() {
//            System.out.println("Registrate thread: " + Thread.currentThread().getName());
            while(!lock.register()) {
                Thread.yield();
            }
            lock.lock();
            f(50);
            lock.unlock();
//            System.out.println("Register pid: " + lock.getId());
//            System.out.println("Run task...");

//            System.out.println("Unregistrate thread: " + Thread.currentThread().getName());
            lock.unregister();
        }

        private int f(int i) {
            if(i == 0) return 0;
            if(i == 1) return 1;
            return f(i-2) + f(i-1);
        }
    }



}
