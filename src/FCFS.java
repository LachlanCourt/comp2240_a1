/*******************************************************************************
 ****    COMP2240 Assignment 1
 ****    c3308061
 ****    Lachlan Court
 ****    08/08/2021
 ****    This class is a child class of Algorithm.java. It represents a First
 ****    Come First Serve algorithm.
 *******************************************************************************/

public class FCFS extends Algorithm
{
    // Default Constructor, set name of Algorithm
    public FCFS()
    {
        name = "FCFS";
    }

    // Overridden run method
    @Override public void run()
    {
        // Initialise the algorithm and add any processes that arrive at T=0
        currentTime = 0;
        Process currentProcess;
        addNewProcesses();
        // Loop while there are unfinished processes
        while (unfinishedProcesses.size() > 0)
        {
            // Add the dispatch time and get a process from the list
            currentTime += DISP;
            currentProcess = unfinishedProcesses.get(getNextProcess());
            // Generate a new ProcessEvent and add the entire remaining time. FCFS algorithm does not timeout processes
            // So we can assume that it will continue processing until it is complete
            ProcessEvent event =
                new ProcessEvent(currentTime, currentTime + currentProcess.getRemainingTime(), currentProcess.getId());
            // Add the event to the process's processHistory list and the algorithm's processEventRecord list
            currentProcess.addEvent(event);
            processEventRecord.add(event);
            // Update the current time and then move the process from unfinished to finished
            currentTime += currentProcess.getRemainingTime();
            unfinishedProcesses.remove(currentProcess);
            finishedProcesses.add(currentProcess);
            // Check for any processes that have arrived in the meantime
            addNewProcesses();
        }
    }

    // Overridden getNextProcess method
    @Override protected int getNextProcess()
    {
        // First come first serve runs the processes as they arrive. Always take the first from the list
        return 0;
    }
}
