public class LTR extends Algorithm {

    public LTR()
    {
        name = "LTR";
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
}
