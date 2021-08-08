import java.util.ArrayList;

public class Process
{
    private String id;
    private int arrive;
    private int tickets;
    private int remainingTime;
    private int lowPriorityTime;  // FBV

    private ArrayList<ProcessEvent> processHistory;

    public Process(String id_, int arrive_, int execSize_, int tickets_)
    {
        id = id_;
        arrive = arrive_;
        remainingTime = execSize_;
        tickets = tickets_;

        processHistory = new ArrayList<>();
    }

    public void addEvent(ProcessEvent event)
    {
        processHistory.add(event);
    }

    public ArrayList<ProcessEvent> getEvents()
    {
        return processHistory;
    }

    public String getId()
    {
        return id;
    }

    public int getArrive()
    {
        return arrive;
    }

    public int getRemainingTime()
    {
        return remainingTime;
    }

    public void decRemainingTime()
    {
        remainingTime -= 1;
    }

    public void setLowPriorityTime(int time_)
    {
        lowPriorityTime = time_;
    }

    public int getLowPriorityTime()
    {
        return lowPriorityTime;
    }

    public int getTickets()
    {
        return tickets;
    }

    public String reportFull()
    {
        String report = "";

        // ID
        report += id + " ".repeat(9 - id.length());

        // Turnaround Time
        String turnaroundTime = String.valueOf(getTurnaround());
        report += turnaroundTime + " ".repeat(17 - turnaroundTime.length());

        // Waiting Time
        String waitingStr = String.valueOf(getWaiting());
        report += waitingStr + "\n";

        return report;
    }

    public int getTurnaround()
    {
        int endTime = processHistory.get(processHistory.size() - 1).getEndTime();
        return endTime - arrive;
    }

    public int getWaiting()
    {
        int waitingTime = 0;
        int lastFinishTime = arrive;
        for (ProcessEvent p : processHistory)
        {
            waitingTime += p.getStartTime() - lastFinishTime;
            lastFinishTime = p.getEndTime();
        }
        return waitingTime;
    }
}
