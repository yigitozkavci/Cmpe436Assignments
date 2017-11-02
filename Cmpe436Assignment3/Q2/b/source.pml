bool s = 1;
bit y[2] = { 0, 0 };
bit critical[2];

proctype A(int i) {
  do
  :: /* noncritical section */
     atomic {
       y[i] = 1;
       s = i;
     }
     // Point [1]
     (y[1-i] == 0 || s != i)
     // Point [2]

     critical[i] = 1;
     /* critical section */
     critical[i] = 0;

     printf("\n%d", i);
     y[i] = 0;
     // Point [3]
  od
}

init {
  run A(0);
  run A(1);
}

ltl mutex { [] (!critical[0] || !critical[1]) }
