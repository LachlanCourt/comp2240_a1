/*******************************************************************************
 ****    COMP2240 Assignment 1
 ****    c3308061
 ****    Lachlan Court
 ****    08/08/2021
 ****    This class is a child class of Algorithm.java. It represents a Shortest
 ****    Remaining Time algorithm.
 *******************************************************************************/

public class SRT extends Algorithm
{
    // Default Constructor, set name of Algorithm
    public SRT()
    {
        this.name = "SRT";
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
            if (unfinishedProcesses.size() > 0)
            {
                // Add the dispatch time and get a process from the list
                currentTime += DISP;
                int nextProcessIndex = getNextProcess();
                currentProcess = unfinishedProcesses.get(nextProcessIndex);
                // Record the time the algorithm started working on this process
                int startTime = currentTime;
                // Loop while there isn't a new process that would finish earlier
                while (getNextProcess() == nextProcessIndex)
                {
                    // Increment the global time and decrement the time left on the current process
                    currentTime++;
                    currentProcess.decRemainingTime();
                    // Check for any processes that have arrived in the meantime
                    addNewProcesses();
                    // If a process has finished, move it to the finished list and break to pick a new process
                    if (currentProcess.getRemainingTime() == 0)
                    {
                        unfinishedProcesses.remove(currentProcess);
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
            }
        }
    }

    // Overridden getNextProcess method
    @Override protected int getNextProcess()
    {
        // Assume the process with the shortest time remaining is the first one in the list
        int shortestTime = unfinishedProcesses.get(0).getRemainingTime();
        int shortestIndex = 0;
        // Loop through unfinished processes
        for (int i = 0; i < unfinishedProcesses.size(); i++)
        {
            // Adjust the shortestIndex if the current process is due to finish before the current shortestTime
            if (unfinishedProcesses.get(i).getRemainingTime() < shortestTime)
            {
                shortestTime = unfinishedProcesses.get(i).getRemainingTime();
                shortestIndex = i;
            }
            // If they are the same, only update if the process ID is lower (A lower process ID gets higher priority
            // as per spec)
            if (unfinishedProcesses.get(i).getRemainingTime() == shortestTime)
            {
                if (unfinishedProcesses.get(i).getIntID() < unfinishedProcesses.get(shortestIndex).getIntID())
                {
                    shortestTime = unfinishedProcesses.get(i).getRemainingTime();
                    shortestIndex = i;
                }
            }
        }
        // The index of the process with the shortest remaining time
        return shortestIndex;
    }
}
