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
        this.name = "FCFS";
    }

    // Overridden run method
    @Override public void run()
    {
        // Initialise the algorithm and add any processes that arrive at T=0
        currentTime = 0;
        Process currentProcess;
        addNewProcesses();
        // Loop while there are ready processes
        while (finishedProcesses.size() < totalProcesses.size())
        {
            if (readyProcesses.size() > 0)
            {
                // Get a process from the list and add the dispatch time
                currentProcess = readyProcesses.get(getNextProcess());
                currentTime += DISP;
                // Generate a new ProcessEvent and add the entire remaining time. FCFS algorithm does not timeout
                // processes So we can assume that it will continue processing until it is complete
                ProcessEvent event = new ProcessEvent(currentTime,
                                                      currentTime + currentProcess.getRemainingTime(),
                                                      currentProcess.getId());
                // Add the event to the process's processHistory list and the algorithm's processEventRecord list
                currentProcess.addEvent(event);
                processEventRecord.add(event);
                // Update the current time and then move the process from ready to finished
                currentTime += currentProcess.getRemainingTime();
                readyProcesses.remove(currentProcess);
                finishedProcesses.add(currentProcess);
                // Check for any processes that have arrived in the meantime
                addNewProcesses();
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
        // First come first serve runs the processes as they arrive. Assume the first in the list is the earliest
        int earliestArrivalTime = readyProcesses.get(0).getArrive();
        int earliestArrivalIndex = 0;
        // Loop through the processes to find the one that arrived earliest
        for (int i = 0; i < readyProcesses.size(); i++)
        {
            // If the arrival time is explicitly less, update the earliestArrivalTime and index
            if (readyProcesses.get(i).getArrive() < earliestArrivalTime)
            {
                earliestArrivalTime = readyProcesses.get(i).getArrive();
                earliestArrivalIndex = i;
            }
            // If they are the same, only update if the process ID is lower (A lower process ID gets higher priority
            // as per spec)
            if (readyProcesses.get(i).getArrive() == earliestArrivalTime)
            {
                if (readyProcesses.get(i).getIntID() < readyProcesses.get(earliestArrivalIndex).getIntID())
                {
                    earliestArrivalTime = readyProcesses.get(i).getArrive();
                    earliestArrivalIndex = i;
                }
            }
        }
        return earliestArrivalIndex;
    }
}
