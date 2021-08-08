import java.util.Random;
public class LTR extends Algorithm
{

    public LTR()
    {
        name = "LTR";
    }

    @Override protected int getNextProcess()
    {
        Random rand = new Random(844422);
        System.out.println(rand.nextInt(550));

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
            int nextProcessIndex = getNextProcess();
            currentProcess = unfinishedProcesses.get(nextProcessIndex);
            int startTime = currentTime;
            while (currentTime - startTime < 4)
            {
                currentTime++;
                currentProcess.decRemainingTime();
                addNewProcesses();
                if (currentProcess.getRemainingTime() == 0)
                {
                    unfinishedProcesses.remove(currentProcess);
                    finishedProcesses.add(currentProcess);
                    break;
                }
            }
            ProcessEvent event = new ProcessEvent(startTime, currentTime, currentProcess.getId());
            currentProcess.addEvent(event);
            processEventRecord.add(event);
        }
    }
}
