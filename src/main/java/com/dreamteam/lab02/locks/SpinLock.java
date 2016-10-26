package com.dreamteam.lab02.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;


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
 * FixnumLock interface implementations have used only for compatibility
 * with other locks in test programs
 *
 */
public class SpinLock implements FixnumLock {

    private static final AtomicReference<Thread> _lock = new AtomicReference<>(null);

    @Override
    public void lock() {
        try {
            Thread thread = Thread.currentThread();

            while (true) {
                if (!_lock.compareAndSet(null, thread)) //if lock equals null then lock become thread and return unlock
                {
                    if (_lock.get() == thread) //if lock equals currant thread then throw exception
                        throw new IllegalStateException("SpinLock is not reentrant");
                    continue;
                }
                return;
            }
        } catch (Exception ignored) {

        }
    }
    @Override
    public void unlock() {
        _lock.set(null);
    }


    public boolean isLocked()
    {
        return _lock.get()!=null;
    }

    public boolean isLockedThread()
    {
        return _lock.get()==Thread.currentThread();
    }



    @Deprecated
    public Condition newCondition() {
        throw new UnsupportedOperationException("Conditions does not supports by this type of lock");
    }

    @Deprecated
    public void lockInterruptibly() throws InterruptedException {
        throw new InterruptedException("just that's why");
    }

    @Deprecated
    public boolean tryLock() {
        return false;
    }

    @Deprecated
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }






    @Override
    public int getId() {
        System.out.println("Lock haven't id");
        return -1;
    }

    @Override
    public boolean register() {
//        System.out.println("Registration is unnecessary");
        return true;
    }

    @Override
    public void unregister() {
//        System.out.println("Registration is unnecessary");
    }
}
