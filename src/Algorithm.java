import java.util.ArrayList;

public abstract class Algorithm {
    protected static int DISP;
    protected ArrayList<Process> processList;
    protected ArrayList<Process> totalProcesses;
    protected ArrayList<Process> unfinishedProcesses = new ArrayList<>();
    protected ArrayList<Process> finishedProcesses = new ArrayList<>();
    protected int currentTime;
    protected String name = "";

    public abstract void run();
    protected abstract int getNextProcess();

    public String reportFull()
    {
        String report = name + "\n";
        for (Process p : finishedProcesses)
        {
            int startTime = p.getEvents().get(0).getStartTime();
            report += "T" + startTime + ": " + p.getId() + "\n";
        }
        report += "\nProcess  Turnaround Time  Waiting Time\n";
        for (Process p : finishedProcesses)
        {
            report += p.reportFull();
        }
        return report;
    }

    public String reportAvg()
    {
        String report = "";
        return report;
    }

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
            System.out.println("Adding Process. Current Time:" + currentTime);
            p = processList.get(0);
            unfinishedProcesses.add(p);
            processList.remove(p);
        }
    }
}
