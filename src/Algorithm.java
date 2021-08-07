import java.util.ArrayList;

public abstract class Algorithm
{
    protected static int DISP;
    protected ArrayList<Process> totalProcesses;
    protected ArrayList<Process> unfinishedProcesses;
    protected ArrayList<Process> finishedProcesses;

    public abstract void run();
    public abstract String reportFull();
    public abstract String reportAvg();

    protected abstract int getNextProcess();

    public void setDISP(int DISP_)
    {
        DISP = DISP_;
    }

    public void loadProcesses(ArrayList<Process> processes_)
    {
        totalProcesses = processes_;
        unfinishedProcesses = processes_;
    }
}
