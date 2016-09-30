# Operation Systems and System Programming
## Problem Critical region
This lab should be done in a team with each student assigned individual item. Supposed implementation languages are Java and C++. 

1. Develop new interface FixnumLock that allows mutual exclusion between limited (fixed) number of threads. It should extend interface java.util.concurrent.Lock and additionally include methods getId/register/unregister for obtaining id by a thread. Provide logic for these methods in abstract class that can be used to derive classes for mutual exclusion primitives with limited number of threads. Consider additional complexity necessary to implement reset method.

2. Demonstrate race condition on two threads manipulating counter. Use Dekker's algorithm to prevent data corruption. Develop DekkerLock type that provides FixnumLock interface. Provide framework that checks if a given locking primitive satisfies mutual exclusion condition. 

3. Implement Lamport algorithm (bakery algorithm). Develop BakeryLock type that provides FixnumLock interface. Check how counter value is drifting when some threads are increasing and some decreasing counter with average 0.

4. Using atomic variable improve Lamport mutual exclusion algorithm (bakery algorithm) so that every thread would receive unique ticket. Consider the problem of bounding the value of this variable. Develop ImrovedBakeryLock type that provides java.util.concurrent.Lock interface.

5. Experiment with threads synchronously accessing counter using atomic, spinlock, monitor primitives etc. Utilize java.util.concurrent.Lock interface or Lockable concept to implement benchmarking framework. Compare performances of different primitives such as Spinlock, DekkerLock, BakeryLock and ImprovedBakeryLock. Try to estimate thread contention. 


