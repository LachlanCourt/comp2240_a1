/*******************************************************************************
 ****    COMP2240 Assignment 1
 ****    c3308061
 ****    Lachlan Court
 ****    08/08/2021
 ****    This class is a child class of Algorithm.java. It represents a
 ****    Multilevel Feedback Variable algorithm.
 *******************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class FBV extends Algorithm
{
    // Instance variables
    private ArrayList<Queue<Process>> processQueues;
    private ArrayList<Integer> timeQuanta;

    // Default Constructor, set name of Algorithm
    public FBV()
    {
        this.name = "FBV";
        // Initialise processQueues with four standard queues, and timeQuanta with the time quanta as per spec
        this.processQueues = new ArrayList<>();
        for (int i = 0; i < 4; i++)
        {
            Queue<Process> newQueue = new LinkedList<>();
            this.processQueues.add(newQueue);
        }
        this.timeQuanta = new ArrayList<>(Arrays.asList(new Integer[] {1, 2, 4, 4}));
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
            if (ready.size() > 0)
            {
                // Add the dispatch time and get a process from the list
                currentTime += DISP;
                int nextProcessQueueIndex = getNextProcess();
                currentProcess = processQueues.get(nextProcessQueueIndex).poll();
                // Record the time the algorithm started working on this process
                int startTime = currentTime;
                // Loop while the current process has not reached its allocated time quanta
                while (currentTime - startTime < timeQuanta.get(nextProcessQueueIndex))
                {
                    // Increment the global time and decrement the time left on the current process
                    currentTime++;
                    currentProcess.decRemainingTime();
                    // Check for any processes that have arrived in the meantime
                    addNewProcesses();
                    // If a process has finished, move it to the finished list and break to pick a new process
                    if (currentProcess.getRemainingTime() == 0)
                    {
                        ready.remove(currentProcess);
                        processQueues.get(nextProcessQueueIndex).remove(currentProcess);
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
                // If a process did not finish, move it into the lower priority queue below
                if (currentProcess.getRemainingTime() != 0)
                {
                    // Remove the process from its current queue
                    processQueues.get(nextProcessQueueIndex).remove(currentProcess);
                    // If the process isn't already in the lowest priority queue
                    if (nextProcessQueueIndex != processQueues.size() - 1)
                    {
                        processQueues.get(nextProcessQueueIndex + 1).add(currentProcess);
                        // If a process has been in the lowest priority queue for more than 32 timesteps, it should be
                        // moved back to the high priority queue. In this case, if the process has just been moved into
                        // the lowest priority queue, record the time it was moved there.
                        if (nextProcessQueueIndex + 1 == processQueues.size() - 1)
                        {
                            currentProcess.setLowPriorityTime(currentTime);
                        }
                    }
                    // If the process is already in the lowest priority queue, add it into the same queue, but on the
                    // back
                    else
                    {
                        processQueues.get(processQueues.size() - 1).add(currentProcess);  // Round robin
                    }
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
        // If a process has been in the lowest priority queue for more than 32 timesteps, it should be moved back to
        // the high priority queue. Loop through the processes in this queue to check if any have timed out
        Queue<Process> lowPriorityQueue = processQueues.get(processQueues.size() - 1);
        int size = lowPriorityQueue.size();
        for (int i = 0; i < size; i++)
        {
            // Take the process out of the queue and check if it has been there for more than 32 timesteps
            Process temp = lowPriorityQueue.remove();
            if (currentTime - temp.getLowPriorityTime() > 32)
            {
                // If so, add it to the high priority queue
                processQueues.get(0).add(temp);
            }
            else
            {
                // If the process has not timed out, add it back to the end of the queue
                lowPriorityQueue.add(temp);
            }
        }
        // Now that any processes that have timed out have been resolved, loop through the list of queues and return
        // the index of the first queue that has an item in it (note that in this case it does not return the index
        // of the item but instead the index of the queue the item should be taken from. The item will always be taken
        // from the front)
        for (int i = 0; i < processQueues.size(); i++)
        {
            if (processQueues.get(i).size() > 0)
            {
                return i;
            }
        }
        return 0;
    }

    /**
     * Overridden addNewProcesses method, as the FBV algorithm requires processes to be in four queues. The only
     * difference is that new processes also get added to the first queue in the processQueues list in addition
     * to ready
     */
    @Override protected void addNewProcesses()
    {
        Process p;
        while ((upcomingProcessList.size() > 0) && (upcomingProcessList.get(0).getArrive() <= currentTime))
        {
            p = upcomingProcessList.get(0);
            ready.add(p);
            processQueues.get(0).add(p);  // Add to the first, highest priority, queue
            upcomingProcessList.remove(p);
        }
    }
}
