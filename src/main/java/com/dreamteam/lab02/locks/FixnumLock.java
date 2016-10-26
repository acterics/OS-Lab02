package com.dreamteam.lab02.locks;

import java.util.concurrent.locks.Lock;

/**
 * Develop new interface FixnumLock (or concept FixnumLockable) that allows mutual exclusion between limited (fixed)
 * number of threads. It should extend interface java.util.concurrent.Lock (refine concept Lockable) and additionally
 * include methods getId/register/unregister for obtaining id by a thread. Provide logic for these methods in abstract
 * class that can be used to derive classes for mutual exclusion primitives with limited number of threads. Consider
 * additional complexity necessary to implement reset method.
 */
public interface FixnumLock extends Lock {


    /**
     * Obtaining id by a thread
     *
     * @return current lock id;
     */
    int getId();

    /**
     *  Add thread in queue
     *  @return result of registration
     */
    boolean register();

    /**
     *  Delete thread from queue
     */
    void unregister();

}
