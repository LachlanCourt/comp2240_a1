/*******************************************************************************
 ****    COMP2240 Assignment 1
 ****    c3308061
 ****    Lachlan Court
 ****    08/08/2021
 ****    This class represents a single process event of a process being loaded
 ****    into the CPU, having time processing, and being loaded out of the CPU.
 ****    It has member variables to record data during the simulation to report
 ****    at the end
 *******************************************************************************/

public class ProcessEvent
{
    // Member variables
    private int startTime;
    private int endTime;
    private String processID;

    // Constructor
    public ProcessEvent(int startTime_, int endTime_, String id_)
    {
        this.startTime = startTime_;
        this.endTime = endTime_;
        this.processID = id_;
    }

    // Getters
    public int getStartTime()
    {
        return startTime;
    }

    public int getEndTime()
    {
        return endTime;
    }

    public String getProcessID()
    {
        return processID;
    }
}
