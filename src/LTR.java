/*******************************************************************************
 ****    COMP2240 Assignment 1
 ****    c3308061
 ****    Lachlan Court
 ****    08/08/2021
 ****    This class is a child class of Algorithm.java. It represents a Lottery
 ****    algorithm.
 *******************************************************************************/

import java.util.ArrayList;

public class LTR extends Algorithm
{
    // Instance variables
    private ArrayList<Integer> randomValues;

    // Constructor, set name of Algorithm and take in the random values read from text file
    public LTR(ArrayList<Integer> values_)
    {
        this.name = "LTR";
        this.randomValues = values_;
    }

    // Overridden run method
    @Override public void run()
    {
        // Initialise the algorithm and add any processes that arrive at T=0
        currentTime = 0;
        Process currentProcess;
        addNewProcesses();
        // Loop while there are unfinished processes
        while (finishedProcesses.size() < totalProcesses.size())
        {
            if (readyProcesses.size() > 0)
            {
                // Get a process from the list and add the dispatch time
                int nextProcessIndex = getNextProcess();
                currentTime += DISP;
                currentProcess = readyProcesses.get(nextProcessIndex);
                // Record the time the algorithm started working on this process
                int startTime = currentTime;
                // Loop while the current process has not reached its allocated time quanta (hard coded 4 timesteps)
                while (currentTime - startTime < 4)
                {
                    // Increment the global time and decrement the time left on the current process
                    currentTime++;
                    currentProcess.decRemainingTime();
                    // Check for any processes that have arrived in the meantime
                    addNewProcesses();
                    // If a process has finished, move it to the finished list and break to pick a new process
                    if (currentProcess.getRemainingTime() == 0)
                    {
                        readyProcesses.remove(currentProcess);
                        finishedProcesses.add(currentProcess);
                        break;
                    }
                }
                // Whether the event finished or was interrupted, generate a new ProcessEvent and add the time it spent
                // processing
                ProcessEvent event = new ProcessEvent(startTime, currentTime, currentProcess.getId());
                // Add the event to the process's processHistory list and the algorithm's processEventRecord list
                currentProcess.addEvent(event);
                processEventRecord.add(event);

                // If the process has not finished processing, move process to back of the list
                if (currentProcess.getRemainingTime() != 0)
                {
                    readyProcesses.remove(currentProcess);
                    readyProcesses.add(currentProcess);
                }
            }
            else
            {
                currentTime++;
                addNewProcesses();
            }
        }
    }

    // Overridden getNextProcess method
    @Override protected int getNextProcess()
    {
        // Implement the lottery scheduling system as per the algorithm suggested per spec
        int counter = 0;
        int i = 0;
        // Get a new winner from the list of values and remove it so that it is not used again
        int winner = randomValues.get(0);
        randomValues.remove(0);
        // Loop while we have not found a winner
        while (counter + readyProcesses.get(i).getTickets() <= winner)
        {
            // Add the tickets of the current process to the counter and increment the iterator
            counter += readyProcesses.get(i).getTickets();
            i++;
            // If we have reached the end of the list of processes in ready, reset the iterator to 0. Keep looping
            // until we find a winner
            if (i > readyProcesses.size() - 1)
            {
                i = 0;
            }
        }
        // We have a winner!
        return i;
    }
}
