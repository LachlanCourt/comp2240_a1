import java.util.ArrayList;

public abstract class Algorithm
{
    protected static int DISP;
    protected ArrayList<Process> upcomingProcessList;
    protected ArrayList<Process> totalProcesses;
    protected ArrayList<Process> unfinishedProcesses = new ArrayList<>();
    protected ArrayList<Process> finishedProcesses = new ArrayList<>();
    protected int currentTime;
    protected String name = "";

    protected abstract int getNextProcess();
    protected abstract void run();

    public String reportFull()
    {
        String report = "\n" + name + "\n";
        for (Process p : finishedProcesses)
        {
            int startTime = p.getEvents().get(0).getStartTime();
            report += "T" + startTime + ": " + p.getId() + "\n";
        }
        report += "\nProcess  Turnaround Time  Waiting Time\n";
        for (Process p : totalProcesses) // Print in read order, not in process finish order
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
