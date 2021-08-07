public class Process
{
    private String id;
    private int arrive;
    private int execSize;
    private int tickets;

    public Process(String id_, int arrive_, int execSize_, int tickets_)
    {
        id = id_;
        arrive = arrive_;
        execSize = execSize_;
        tickets = tickets_;
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
