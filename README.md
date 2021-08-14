# COMP2240 - Algorithms
## Assignment 1
### Task
Create a simulator that demonstrates the function of four different scheduling algorithms used in a CPU. The program reads a list of processes from a file and performs the simulation, outputting to console.

- The first algorithm is First Come First Serve, a standard algorithm that prioritises processes with the earliest arrival time
- The second algorithm is Shortest Remaining Time, a standard algorithm that prioritises processes that will be completed soonest
- The third algorithm is multilevel FeedBack Variable, consisting of four queues with a different time quanta in each. Processes are loaded into the highest priority queue, when they time out they are moved to the second, then third, then fourth. If a process has been in the fourth queue for more than 32 timesteps it is moved back to the first queue
- The last algorithm is the Lottery scheduling algorithm. Each process has a certain number of "Tickets" that indicate the likelihood of that process being allowed to run at any given time. The random numbers used for this algorithm are read from the file so that it always runs consistently
### Compile
`javac A1.java`
### Run
There are two sample files in this project that can be used to sample the simulator.
- `datafile1.txt` has all processes arrive at T=0
- `datafile2.txt` has processes arrive during the simulation
- `datafile3.txt`, `datafile4.txt`, `datafile5.txt` are further more complicated files
- `datafile6.txt` has idle CPU time where no processes are in the ready queue

Run with the following command

`java A1 <filename>`
