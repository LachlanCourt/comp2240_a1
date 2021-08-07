public class FCFS extends Algorithm
{

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
            currentTime += currentProcess.getExecSize();
            unfinishedProcesses.remove(0);
            finishedProcesses.add(currentProcess);
            addNewProcesses();
        }
    }

    @Override
    public String reportFull()
    {
        return null;
    }

    @Override
    public String reportAvg()
    {
        return null;
    }

    @Override
    protected int getNextProcess()
    {
        return 0;
    }
}
