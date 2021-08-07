import java.util.ArrayList;

public class FCFS extends Algorithm
{
    public FCFS()
    {
        name = "FCFS";
    }

    @Override
    public void run()
    {
        currentTime = 0;
        Process currentProcess;
        addNewProcesses();
        while (unfinishedProcesses.size() > 0)
        {
            currentTime += DISP;
            currentProcess = unfinishedProcesses.get(getNextProcess());
            currentProcess.addEvent(new ProcessEvent(currentTime, currentTime + currentProcess.getRemainingTime()));
            currentTime += currentProcess.getRemainingTime();
            unfinishedProcesses.remove(currentProcess);
            finishedProcesses.add(currentProcess);
            addNewProcesses();
        }
    }

    @Override protected int getNextProcess()
    {
        return 0;
    }
}
