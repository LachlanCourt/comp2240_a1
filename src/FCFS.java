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

    @Override protected int getNextProcess()
    {
        return 0;
    }
}
