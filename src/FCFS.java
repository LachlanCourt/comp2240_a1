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
            ArrayList<ProcessEvent> eventList = p.getEvents();

            // ID
            String id = p.getId();
            report += id + " ".repeat(9 - id.length());

            // Turnaround Time
            int endTime = eventList.get(eventList.size() - 1).getEndTime();
            String turnaroundTime = String.valueOf(endTime - p.getArrive());
            report += turnaroundTime + " ".repeat(17 - turnaroundTime.length());

            // Waiting Time
            int waitingTime = 0;
            int lastFinishTime = p.getArrive();
            for (ProcessEvent q : eventList)
            {
                waitingTime += q.getStartTime() - lastFinishTime;
                lastFinishTime = q.getEndTime();
            }
            String waitingStr = String.valueOf(waitingTime);
            report += waitingStr + "\n";
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
