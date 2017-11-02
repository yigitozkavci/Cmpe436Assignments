# Starvation
Starvation of a process is possible in this promela model, because a process can repeatedly enter its critical section without allowing others.

State will be represented with the tuple (y0, y1, s) in each notable point.

Here is the scenario:

Process 0 comes to point [1] earlier than process 1, hence when they are at point [1], state is (1, 1, 1).

Because of the block on line 9, process 0 enters to its critical section.

When process 0 sets y[0] to 0, process 1 leaves its block since (y[1-1] == 0 || s != 1) holds.

At this point, we have the state (0, 1, 1).

Process 1 sets y[1] to 0, and goes back to line 14. At this point, process 0 is not necessarily moving, since this is the worst case.

At this point, we have the state (0, 0, 1).

Process 1 completes atomic transaction at line 14, then we have the state (0, 1, 1).

Process 1 sees the block at line 9, but it is executed immediately since (y[1-1] == 0) holds.

Since we have the same state, theoretically process 1 can enter its critical section without ever allowing process 0.
