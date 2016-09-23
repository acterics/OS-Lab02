# Operation Systems and System Programming
## Problem Critical region
This lab should be done in a team with each student assigned individual item. It is preferable to use the same implementation language by all team members. Supposed implementation languages are Java and C++. Implementation of some items, e.g. 6, in C requires modeling interfaces with structures consisting of function pointers or extensive usage of macros.

Experiment with threads synchronously accessing counter using atomic, spinlock, monitor primitives etc. Utilize java.util.concurrent.Lock interface or Lockable concept to implement benchmarking framework. Implement missing primitives (such as spinlock in Java) and use primitives implemented in 1, 3, 5. Compare performances of different primitives. Try to estimate thread contention. 
