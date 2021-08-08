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
    protected static int DISP;
    protected ArrayList<Process> upcomingProcessList;
    protected ArrayList<Process> totalProcesses;
    protected ArrayList<Process> unfinishedProcesses = new ArrayList<>();
    protected ArrayList<Process> finishedProcesses = new ArrayList<>();
    protected ArrayList<ProcessEvent> processEventRecord = new ArrayList<>();
    protected int currentTime;
    protected String name = "";

    protected abstract int getNextProcess();
    protected abstract void run();

    public String reportFull()
    {
        String report = "\n" + name + "\n";
        for (ProcessEvent p : processEventRecord)
        {
            int startTime = p.getStartTime();
            report += "T" + startTime + ": " + p.getProcessID() + "\n";
        }
        report += "\nProcess  Turnaround Time  Waiting Time\n";
        for (Process p : totalProcesses)  // Print in read order, not in process finish order
        {
            report += p.reportFull();
        }
        return report;
    }

    public String reportAvg()
    {
        String report = name + " ".repeat(11 - name.length());

        double totalTurnaround = 0;
        double totalWaiting = 0;

        for (Process p : finishedProcesses)
        {
            totalTurnaround += p.getTurnaround();
            totalWaiting += p.getWaiting();
        }

        String avgTurnaround = String.format("%2.2f", totalTurnaround / finishedProcesses.size());
        report += avgTurnaround + " ".repeat(25 - avgTurnaround.length());

        String avgWaiting = String.format("%2.2f", totalWaiting / finishedProcesses.size());
        report += avgWaiting + " ".repeat(25 - avgWaiting.length()) + "\n";

        return report;
    }

    public void setDISP(int DISP_)
    {
        DISP = DISP_;
    }

    public void loadProcesses(ArrayList<Process> processes_)
    {
        upcomingProcessList = new ArrayList<>(processes_);
        totalProcesses = new ArrayList<>(processes_);
    }

    protected void addNewProcesses()
    {
        Process p;
        while ((upcomingProcessList.size() > 0) && (upcomingProcessList.get(0).getArrive() <= currentTime))
        {
            p = upcomingProcessList.get(0);
            unfinishedProcesses.add(p);
            upcomingProcessList.remove(p);
        }
    }
}
