package com.dreamteam.lab02.benchmark;


import com.dreamteam.lab02.locks.FixnumLock;
import java.util.ArrayList;

public class Pool {
    private ArrayList<Thread> threads = new ArrayList<>();
    private int poolSize;
    boolean isInited = false;



    public Pool(int poolSize) {
        this.poolSize = poolSize;
    }

    public void threadInitialization(Class<? extends FixnumLock> fixnumLockClass)
            throws IllegalAccessException, InstantiationException {
        if(isInited) {
            clearThreads();
        }
        isInited = true;
        System.out.println("Creating pool with " + poolSize + " threads");
        System.out.println("Lock class: " + fixnumLockClass.toString());
        for(int i = 0; i < poolSize; ++i) {
            threads.add(new FixnumLockTestThread(fixnumLockClass.newInstance())); // choosing type of lock
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
        for(Thread thread: threads) {
            thread.join();
        }
    }



    public void runThreads() throws Exception {
        if(!isInited)
            throw new Exception("Threads isn't inited");
        long time = System.nanoTime();
        float dif;
        System.out.println("**********************Pool task started**********************");
        for(Thread thread: threads) {
            thread.start();
        }
        joinAll();
        dif = (float)(System.nanoTime() - time) / 1000000000;
        System.out.println("**********************Pool task finished with time " + dif +"sec **********************");


    }


    public void testLock(Class<? extends FixnumLock> fixnumLockClass)
            throws Exception {
        threadInitialization(fixnumLockClass);
        runThreads();
    }


}
