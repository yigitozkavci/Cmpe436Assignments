# Cmpe 436 Assignment 2 Part 3

Without expected race condition, we would expect data to flow like this:

0 -> 2 -> 4 -> 6 -> 8 -> 10

But because of the race condition, we get intermediate values like 5 or 7.

