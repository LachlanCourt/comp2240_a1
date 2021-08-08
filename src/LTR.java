import java.util.ArrayList;
import java.util.Random;
public class LTR extends Algorithm
{
    private ArrayList<Integer> randomValues;

    public LTR(ArrayList<Integer> values_)
    {
        name = "LTR";
        randomValues = values_;
    }

    @Override protected int getNextProcess()
    {
        int counter = 0;
        int i = 0;
        int winner = randomValues.get(0);
        randomValues.remove(0);
        while (true)
        {
            counter += unfinishedProcesses.get(i).getTickets();
            if (counter > winner)
            {
                return i;
            }
            i++;
            if (i > unfinishedProcesses.size() - 1)
            {
                i = 0;
            }
        }
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

            // Move process to back of the list
            if (currentProcess.getRemainingTime() != 0)
            {
                unfinishedProcesses.remove(currentProcess);
                unfinishedProcesses.add(currentProcess);
            }

        }
    }
}
