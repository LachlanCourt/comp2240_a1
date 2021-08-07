import java.util.ArrayList;

public class Process
{
    private String id;
    private int arrive;
    private int execSize;
    private int tickets;

    private ArrayList<ProcessEvent> processHistory;

    public Process(String id_, int arrive_, int execSize_, int tickets_)
    {
        id = id_;
        arrive = arrive_;
        execSize = execSize_;
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

    public String getId() {
        return id;
    }

    public int getArrive() {
        return arrive;
    }

    public int getExecSize() {
        return execSize;
    }

    public int getTickets() {
        return tickets;
    }
}
