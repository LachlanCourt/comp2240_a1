/*******************************************************************************
 ****    COMP2240 Assignment 1
 ****    c3308061
 ****    Lachlan Court
 ****    08/08/2021
 ****    This class is a config object used to store data read from a file
 *******************************************************************************/

import java.util.ArrayList;

public class Config
{
    // Member variables, data read from file
    private ArrayList<Process> processes;
    private ArrayList<Integer> randomValues;
    private static int DISP;

    // Constructor
    public Config(ArrayList<Process> processes_, ArrayList<Integer> randomValues_, int DISP_)
    {
        this.processes = processes_;
        this.randomValues = randomValues_;
        this.DISP = DISP_;
    }

    // Getters
    public static int getDISP()
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
