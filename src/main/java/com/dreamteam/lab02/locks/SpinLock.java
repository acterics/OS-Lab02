package com.dreamteam.lab02.locks;

import java.util.concurrent.atomic.AtomicReference;


/**
 *
 *
 * This is a lock designed to protect VERY short sections of
 * critical code.  Threads attempting to take the lock will spin
 * forever until the lock is available, thus it is important that
 * the code protected by this lock is extremely simple and non
 * blocking. The reason for this lock is that it prevents a thread
 * from giving up a CPU core when contending for the lock
 *
 *
 * Usage:
 * try(SpinLock.Lock lock = spinlock.lock())
 * {
 *   // something very quick and non blocking
 * }
 *
 */
public class SpinLock
{
    private final AtomicReference<Thread> _lock = new AtomicReference<>(null);
    private final Lock _unlock = new Lock();

    private void log(String message) {
        System.out.println("Log: " + message);
    }

    public Lock lock()
    {
        //log("Enter lock()");
        Thread thread = Thread.currentThread();
        while(true)
        {
            if (!_lock.compareAndSet(null,thread)) //if lock equals null then lock become thread and return unlock
            {
                if (_lock.get()==thread) //if lock equals currant thread then throw exception
                    throw new IllegalStateException("SpinLock is not reentrant");
                continue;
            }
            //log("Lock equals null, exit lock()");
            return _unlock;
        }
    }

    public boolean isLocked()
    {
        return _lock.get()!=null;
    }

    public boolean isLockedThread()
    {
        return _lock.get()==Thread.currentThread();
    }

    public class Lock implements AutoCloseable
    {
        @Override
        public void close()
        {
            //log("Lock closed");
            _lock.set(null);
        }
    }
}
