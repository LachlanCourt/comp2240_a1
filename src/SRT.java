public class SRT extends Algorithm
{
    public SRT()
    {
        name = "SRT";
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
            int nextProcessIndex = getNextProcess();
            currentProcess = unfinishedProcesses.get(nextProcessIndex);
            int startTime = currentTime;
            while (getNextProcess() == nextProcessIndex)
            {
                currentTime++;
                currentProcess.decRemainingTime();
                if (currentProcess.getRemainingTime() == 0)
                {
                    unfinishedProcesses.remove(currentProcess);
                    finishedProcesses.add(currentProcess);
                    break;
                }
                addNewProcesses();
            }
            ProcessEvent event = new ProcessEvent(startTime, currentTime, currentProcess.getId());
            currentProcess.addEvent(event);
            processEventRecord.add(event);
        }
    }

    @Override protected int getNextProcess()
    {
        int shortestTime = unfinishedProcesses.get(0).getRemainingTime();
        int shortestIndex = 0;
        for (int i = 0; i < unfinishedProcesses.size(); i++)
        {
            if (unfinishedProcesses.get(i).getRemainingTime() < shortestTime)
            {
                shortestTime = unfinishedProcesses.get(i).getRemainingTime();
                shortestIndex = i;
            }
        }
        return shortestIndex;
    }
}
