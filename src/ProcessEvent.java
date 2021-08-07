public class ProcessEvent
{
    private int startTime;
    private int endTime;

    public ProcessEvent(int startTime_, int endTime_)
    {
        startTime = startTime_;
        endTime = endTime_;
    }

    public int getStartTime()
    {
        return startTime;
    }

    public int getEndTime()
    {
        return endTime;
    }
}
