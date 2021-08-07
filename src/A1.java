import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class A1
{
    private static int DISP;
    private ArrayList<Integer> randomValues = new ArrayList<>();
    private ArrayList<Process> processes = new ArrayList<>();


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
        FCFS processorFCFS = new FCFS();
        processorFCFS.setDISP(DISP);
        processorFCFS.loadProcesses(processes);
        processorFCFS.run();

        // SRT
//        SRT processorSRT = new SRT();
//        processorSRT.loadProcesses(processes);
//        processorSRT.run();

        // FBV
//        FBV processorFBV = new FBV();
//        processorFBV.loadProcesses(processes);
//        processorFBV.run();

        // LTR
//        LTR processorLTR = new LTR();
//        processorLTR.loadProcesses(processes);
//        processorLTR.run();        

        String report = getReport(processorFCFS);
        System.out.println(report);
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
        String tempID = "";
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
                tempID = line.substring(4);
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
            }
            else if (line.startsWith(("ENDRANDOM")) && (readingValues))
            {
                readingValues = false;
            }
            else if (readingValues)
            {
                randomValues.add(Integer.valueOf(line));
            }
            else if (line.startsWith("EOF"))
            {
                return true;
            }
        }
        return false;
    }

    public String getReport(Algorithm processorFCFS)
    {
        String report = "";
        report += processorFCFS.reportFull();
        // SRT report full
        // FBV report full
        // LTR report full
        report += processorFCFS.reportAvg();
        // SRT report avg
        // FBV report avg
        // LTR report avg
        return report;
    }
}
