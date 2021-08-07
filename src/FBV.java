import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class FBV extends Algorithm
{
    private ArrayList<Queue<Process>> processQueues;
    private ArrayList<Integer> timeQuanta;

    public FBV()
    {
        name = "FBV";
        processQueues = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Queue<Process> newQueue = new LinkedList<Process>();
            processQueues.add(newQueue);
        }
        timeQuanta = new ArrayList<Integer>(Arrays.asList(new Integer[]{1, 2, 4, 4}));
    }

    @Override
    protected int getNextProcess() {
        return 0;
    }

    @Override
    protected void run() {
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

    @Override
    protected void addNewProcesses()
    {
        Process p;
        while ((upcomingProcessList.size() > 0) && (upcomingProcessList.get(0).getArrive() <= currentTime))
        {
            p = upcomingProcessList.get(0);
            unfinishedProcesses.add(p);
            processQueues.get(0).add(p);
            upcomingProcessList.remove(p);
        }
    }
}
