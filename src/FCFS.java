import java.util.ArrayList;

public class FCFS extends Algorithm
{
    public FCFS()
    {
        name = "FCFS";
    }

    @Override public void run()
    {
        currentTime = 0;
        Process currentProcess;
        addNewProcesses();
        while (unfinishedProcesses.size() > 0)
        {
            currentTime += DISP;
            currentProcess = unfinishedProcesses.get(getNextProcess());
            ProcessEvent event =
                new ProcessEvent(currentTime, currentTime + currentProcess.getRemainingTime(), currentProcess.getId());
            currentProcess.addEvent(event);
            currentTime += currentProcess.getRemainingTime();
            unfinishedProcesses.remove(currentProcess);
            finishedProcesses.add(currentProcess);
            processEventRecord.add(event);
            addNewProcesses();
        }
    }

    @Override protected int getNextProcess()
    {
        return 0;
    }
}
