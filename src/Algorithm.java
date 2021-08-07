import java.util.ArrayList;

public abstract class Algorithm
{
    protected static int DISP;
    protected ArrayList<Process> processList;
    protected ArrayList<Process> totalProcesses;
    protected ArrayList<Process> unfinishedProcesses = new ArrayList<>();
    protected ArrayList<Process> finishedProcesses = new ArrayList<>();
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
        Process p;
        while ((processList.size() > 0) && (processList.get(0).getArrive() <= currentTime))
        {
            p = processList.get(0);
            unfinishedProcesses.add(p);
            processList.remove(p);
        }
    }
}
