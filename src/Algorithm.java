import java.util.ArrayList;

public abstract class Algorithm
{
    protected static int DISP;
    protected ArrayList<Process> processList;
    protected ArrayList<Process> totalProcesses;
    protected ArrayList<Process> unfinishedProcesses;
    protected ArrayList<Process> finishedProcesses;
    protected int currentTime;

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
        processList = new ArrayList<>(processes_);
        totalProcesses = new ArrayList<>(processes_);
    }

    protected void addNewProcesses()
    {
        for (Process p : processList)
        {
            if (currentTime >= p.getArrive())
            {
                unfinishedProcesses.add(p);
                processList.remove(p);
            }
        }
    }
}
