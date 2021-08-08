/*******************************************************************************
 ****    COMP2240 Assignment 1
 ****    c3308061
 ****    Lachlan Court
 ****    08/08/2021
 ****    This class is a parent class of a scheduling algorithm. It has some
 ****    abstract methods to ensure that all algorithms operate similarly, and it
 ****    has functionality to produce the report at the end of program execution
 *******************************************************************************/

import java.util.ArrayList;

public abstract class Algorithm
{
    // Dispatch time is the same for all algorithms so it is static
    protected static int DISP;

    // Member variables by all algorithms
    protected ArrayList<Process> upcomingProcessList;
    protected ArrayList<Process> totalProcesses;
    protected ArrayList<Process> unfinishedProcesses = new ArrayList<>();
    protected ArrayList<Process> finishedProcesses = new ArrayList<>();
    protected ArrayList<ProcessEvent> processEventRecord = new ArrayList<>();
    protected int currentTime;
    protected String name = "";

    /**
     * Gets the index of the next process (or process queue in the case of FBV) that is due to be run
     * @return the index of the next process to be run
     * Precondition: addNewProcesses must have run to ensure that there are processes in unfinishedProcesses
     * Postcondition: Return value
     */
    protected abstract int getNextProcess();

    /**
     * Main run of the algorithm
     * Precondition: loadProcesses must have been run to ensure that there are processes to be run
     * Postcondition: Each process will have data about how it was processed saved so that it can be reported on
     */
    public abstract void run();

    /**
     * Produces a full output of how the processes moved through this algorithm
     * @return full report
     * Precondition: run method must have been called otherwise report will be empty
     * Postcondition: return value
     */
    @Override public String toString()
    {
        // Start report with the name
        String report = "\n" + name + ":\n";
        // Output the order that the processes moved through the simulation using ProcessEvents
        for (ProcessEvent p : processEventRecord)
        {
            int startTime = p.getStartTime();
            report += "T" + startTime + ": " + p.getProcessID() + "\n";
        }
        report += "\nProcess  Turnaround Time  Waiting Time\n";
        // Output the data for each process from start to finish through the simulation
        // Print in read order, not in process finish order
        for (Process p : totalProcesses)
        {
            report += p.toString();
        }
        return report;
    }

    /**
     * Produces a summary output of how the processes moved through this algorithm as averages
     * @return summary output
     * Precondition: run method must have been called otherwise report will be empty
     * Postcondition: return value
     */
    public String reportAvg()
    {
        // Start report with name
        String report = name + " ".repeat(11 - name.length());

        // Calculate the total times of each process to calculate the average
        double totalTurnaround = 0;
        double totalWaiting = 0;

        for (Process p : finishedProcesses)
        {
            totalTurnaround += p.getTurnaround();
            totalWaiting += p.getWaiting();
        }

        // Add the average turnaround time to the report
        String avgTurnaround = String.format("%2.2f", totalTurnaround / finishedProcesses.size());
        report += avgTurnaround + " ".repeat(25 - avgTurnaround.length());

        // Add the average waiting time to the report
        String avgWaiting = String.format("%2.2f", totalWaiting / finishedProcesses.size());
        report += avgWaiting + " ".repeat(25 - avgWaiting.length()) + "\n";

        return report;
    }

    // Setter
    public void setDISP(int DISP_)
    {
        if (DISP_ > 0)
        {
            // Only positive dispatch times will be considered
            this.DISP = DISP_;
        }
        else
        {
            // In the case of an invalid dispatch time, default to 0
            this.DISP = 0;
        }
    }

    /**
     * Loads processes into the algorithm from an arraylist
     * @param processes_ List of processes to be run through the scheduling algorithm
     * Precondition: None
     * Postcondition: Algorithm has processes loaded and is ready to have run method called
     */
    public void loadProcesses(ArrayList<Process> processes_)
    {
        upcomingProcessList = new ArrayList<>(processes_);
        totalProcesses = new ArrayList<>(processes_);
    }

    /**
     * Moves processes from upcomingProcessList into unfinishedProcesses if the process is due to arrive according to
     * the currentTime
     * Precondition: None
     * Postcondition: Algorithm has processes loaded and is ready to have run method called
     */
    protected void addNewProcesses()
    {
        // Loop while there are still processes in upcomingProcessList and the first item in that list is due to arrive
        // according to the currentTime
        while ((upcomingProcessList.size() > 0) && (upcomingProcessList.get(0).getArrive() <= currentTime))
        {
            // Grab the process out and move it from upcomingProcessList to unfinishedProcesses
            Process p = upcomingProcessList.get(0);
            unfinishedProcesses.add(p);
            upcomingProcessList.remove(p);
        }
    }
}
