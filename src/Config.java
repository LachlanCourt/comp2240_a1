/*******************************************************************************
 ****    COMP2240 Assignment 1
 ****    c3308061
 ****    Lachlan Court
 ****    08/08/2021
 ****    This class is a config object used to store data read from a file
 *******************************************************************************/

import java.util.ArrayList;
import java.util.Collections;

public class Config
{
    // Member variables, data read from file
    private ArrayList<Process> processes;
    private ArrayList<Integer> randomValues;
    private int DISP;

    // Constructor
    public Config(ArrayList<Process> processes_, ArrayList<Integer> randomValues_, int DISP_)
    {
        this.processes = processes_;
        this.randomValues = randomValues_;
        this.DISP = DISP_;

        // Sort the process list. Ugly incoming :$
        // First sort by arrival time, an earlier arrival time comes before, later arrival time comes after
        // In the case of two processes with the same arrival time, use the process ID as a tiebreaker.
        Collections.sort(this.processes, (a, b) -> {
            int i = a.getArrive() < b.getArrive() ? -1 : a.getArrive() == b.getArrive() ? a.getIntID() < b.getIntID() ? -1 : 1 : 1; return i;
        });
    }

    // Getters
    public int getDISP()
    {
        return DISP;
    }

    public ArrayList<Integer> getRandomValues()
    {
        return randomValues;
    }

    public ArrayList<Process> getProcesses()
    {
        return processes;
    }
}
