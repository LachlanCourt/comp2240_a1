public class ProcessEvent
{
    private int startTime;
    private int endTime;
    private String processID;

    public ProcessEvent(int startTime_, int endTime_, String id_)
    {
        startTime = startTime_;
        endTime = endTime_;
        processID = id_;
    }

    public int getStartTime()
    {
        return startTime;
    }

    public int getEndTime()
    {
        return endTime;
    }

    public String getProcessID()
    {
        return processID;
    }
}
