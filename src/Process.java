/*******************************************************************************
 ****    COMP2240 Assignment 1
 ****    c3308061
 ****    Lachlan Court
 ****    08/08/2021
 ****    This class represents a process that is running through the CPU. It is
 ****    initially read from a text file and has member variables to record data
 ****    during the simulation to report at the end
 *******************************************************************************/

import java.util.ArrayList;

public class Process
{
    // Member variables
    private String id;
    private int arrive;
    private int tickets;
    private int remainingTime;
    private int lowPriorityTime;  // FBV
    private int intID;            // FCFS and SRT

    private ArrayList<ProcessEvent> processHistory;

    // Constructor
    public Process(String id_, int arrive_, int execSize_, int tickets_)
    {
        this.id = id_;
        this.arrive = arrive_;
        this.remainingTime = execSize_;
        this.tickets = tickets_;

        this.processHistory = new ArrayList<>();

        this.intID = Integer.valueOf(id_.substring(1));
    }

    /**
     * Adds a ProcessEvent into the local cache
     * @param event An event indicating a process being loaded into and loaded out of the cache
     * Precondition: None
     * Postcondition: @param is added to processHistory ArrayList
     */
    public void addEvent(ProcessEvent event)
    {
        this.processHistory.add(event);
    }

    // Getters and Setters
    public String getId()
    {
        return id;
    }

    public int getArrive()
    {
        return arrive;
    }

    public int getRemainingTime()
    {
        return remainingTime;
    }

    public void decRemainingTime()
    {
        remainingTime -= 1;
    }

    public void setLowPriorityTime(int time_)
    {
        this.lowPriorityTime = time_;
    }

    public int getLowPriorityTime()
    {
        return lowPriorityTime;
    }

    public int getTickets()
    {
        return tickets;
    }

    public int getIntID()
    {
        return intID;
    }

    /**
     * Produces a full output of how this moved through the algorithm
     * @return full report
     * Precondition: processHistory cannot be empty (addEvent must have been run at least once)
     * Postcondition: return value
     */
    @Override
    public String toString()
    {
        String report = "";

        // ID
        report += id + " ".repeat(9 - id.length());

        // Turnaround Time
        String turnaroundTime = String.valueOf(getTurnaround());
        report += turnaroundTime + " ".repeat(17 - turnaroundTime.length());

        // Waiting Time
        String waitingStr = String.valueOf(getWaiting());
        report += waitingStr + "\n";

        return report;
    }

    /**
     * Get the turnaround time of the process
     * @return the time passed between the process arriving and the process finishing processing
     * Precondition: processHistory cannot be empty (addEvent must be called at least once)
     * Postcondition: return value
     */
    public int getTurnaround()
    {
        int endTime = processHistory.get(processHistory.size() - 1).getEndTime();
        return endTime - arrive;
    }

    /**
     * Get the waiting time of the process
     * @return the time passed when the process is in a queue and not being actively processed
     * Precondition: None
     * Postcondition: return value
     */
    public int getWaiting()
    {
        int waitingTime = 0;
        int lastFinishTime = arrive;
        // Loop through the processHistory ArrayList at calculate the difference between the start time of a process
        // and the end time of the process before it (assuming the end time of the process behind the first one is the
        // time it arrives
        for (ProcessEvent p : processHistory)
        {
            waitingTime += p.getStartTime() - lastFinishTime;
            lastFinishTime = p.getEndTime();
        }
        return waitingTime;
    }
}
