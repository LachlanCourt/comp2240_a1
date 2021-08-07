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
        for (int i = 0; i < 4; i++)
        {
            Queue<Process> newQueue = new LinkedList<>();
            processQueues.add(newQueue);
        }
        timeQuanta = new ArrayList<>(Arrays.asList(new Integer[] {1, 2, 4, 4}));
    }

    @Override protected int getNextProcess()
    {
        Queue<Process> lowPriorityQueue = processQueues.get(processQueues.size() - 1);
        int size = lowPriorityQueue.size();
        for (int i = 0; i < size; i++)
        {
            Process temp = lowPriorityQueue.remove();
            if (currentTime - temp.getLowPriorityTime() > 32)
            {
                processQueues.get(0).add(temp);  // Jump back up to the top if the process has been there for too long
            }
            else
            {
                lowPriorityQueue.add(temp);
            }
        }
        for (int i = 0; i < processQueues.size(); i++)
        {
            if (processQueues.get(i).size() > 0)
            {
                return i;
            }
        }
        return 0;
    }

    @Override protected void run()
    {
        currentTime = 0;
        Process currentProcess;
        addNewProcesses();
        while (unfinishedProcesses.size() > 0)
        {
            currentTime += DISP;
            int nextProcessQueueIndex = getNextProcess();
            currentProcess = processQueues.get(nextProcessQueueIndex).poll();
            int startTime = currentTime;
            while (currentTime - startTime < timeQuanta.get(nextProcessQueueIndex))
            {
                currentTime++;
                currentProcess.decRemainingTime();
                if (currentProcess.getRemainingTime() == 0)
                {
                    unfinishedProcesses.remove(currentProcess);
                    processQueues.get(nextProcessQueueIndex).remove(currentProcess);
                    finishedProcesses.add(currentProcess);
                    addNewProcesses();
                    break;
                }
                addNewProcesses();
            }
            ProcessEvent event = new ProcessEvent(startTime, currentTime, currentProcess.getId());
            currentProcess.addEvent(event);
            processEventRecord.add(event);
            if (currentProcess.getRemainingTime() != 0)
            {
                processQueues.get(nextProcessQueueIndex).remove(currentProcess);
                if (nextProcessQueueIndex != processQueues.size() - 1)
                {
                    processQueues.get(nextProcessQueueIndex + 1).add(currentProcess);
                    if (nextProcessQueueIndex + 1 == processQueues.size() - 1)
                    {
                        System.out.println("Current Time:" + currentTime);
                        currentProcess.setLowPriorityTime(currentTime);
                    }
                }
                else
                {
                    processQueues.get(processQueues.size() - 1).add(currentProcess);  // Round robin
                }
            }
        }
    }

    @Override protected void addNewProcesses()
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
