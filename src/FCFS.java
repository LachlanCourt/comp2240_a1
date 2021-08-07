import java.util.ArrayList;

public class FCFS extends Algorithm
{

    @Override public void run()
    {
        currentTime = 0;
        Process currentProcess;
        addNewProcesses();
        while (unfinishedProcesses.size() > 0)
        {
            System.out.println("TICK: Current Time: " + currentTime);
            currentTime += DISP;
            currentProcess = unfinishedProcesses.get(getNextProcess());
            currentProcess.addEvent(new ProcessEvent(currentTime, currentTime + currentProcess.getExecSize()));
            currentTime += currentProcess.getExecSize();
            unfinishedProcesses.remove(0);
            finishedProcesses.add(currentProcess);
            addNewProcesses();
        }
    }

    @Override public String reportFull()
    {
        String report = "FCFS:\n";
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

    @Override public String reportAvg()
    {
        return "";
    }

    @Override protected int getNextProcess()
    {
        return 0;
    }
}
