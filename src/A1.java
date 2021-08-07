import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class A1
{
    private static int DISP;
    private ArrayList<Integer> randomValues;
    private ArrayList<Process> processes;


    // Main function
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.err.println("Invalid input data. Terminating...");
            System.exit(-1);
        }
        // If there is a suitable number of arguments, pass those arguments to the run() method
        A1 assign = new A1();
        assign.run(args);
    }

    public void run(String[] args)
    {
        if (!readData(args[0]))
        {
            System.out.println("Error loading text file! Terminating");
            System.exit(-2);
        }
        // FCFS

        // SRT

        // FBV

        // LTR
    }


    public boolean readData(String filename)
    {
        // Declare Scanner to read from the file
        Scanner input;
        try
        {
            input = new Scanner(new File(filename));
        }
        catch (FileNotFoundException e)
        {
            return false;
        }
        catch (NullPointerException e)
        {
            // If no filename is specified as an argument in program execution, the filename argument will be null
            return false;
        }

        String line;
        boolean readingProcesses = false;
        boolean readingValues = false;
        int tempID = 0;
        int tempArrive = 0;
        int tempExecSize = 0;
        int tempTickets = 0;
        while (input.hasNext())
        {
            line = input.nextLine();
            if (line.startsWith(("DISP:")))
            {
                DISP = Integer.valueOf(line.substring(6));
                input.nextLine();  // Clear the END of this section
                readingProcesses = true;
            }
            else if (line.startsWith(("ID:")))
            {
                tempID = Integer.valueOf(line.substring(4));
            }
            else if (line.startsWith(("Arrive:")))
            {
                tempArrive = Integer.valueOf(line.substring(8));
            }
            else if (line.startsWith(("ExecSize:")))
            {
                tempExecSize = Integer.valueOf(line.substring(10));
                continue;
            }
            else if (line.startsWith(("Tickets:")))
            {
                tempTickets = Integer.valueOf(line.substring(9));
            }
            else if (line.startsWith(("END")) && (readingProcesses))
            {
                Process newProcess = new Process(tempID, tempArrive, tempExecSize, tempTickets);
                processes.add(newProcess);
            }
            else if (line.startsWith(("BEGINRANDOM")))
            {
                readingProcesses = false;
                readingValues = true;
                continue;
            }
            else if (readingValues)
            {
                randomValues.add(Integer.valueOf(line));
            }
            else if (line.startsWith(("ENDRANDOM")) && (readingValues))
            {
                readingValues = false;
            }
            else if (line.startsWith("EOF"))
            {
                System.out.println("Dispatcher time is: " + DISP);
                return true;
            }
        }
        return false;
    }
}
