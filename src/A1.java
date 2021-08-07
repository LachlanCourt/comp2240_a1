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

        ArrayList<Algorithm> algorithms = new ArrayList<>();

        // FCFS
        FCFS processorFCFS = new FCFS();
        processorFCFS.setDISP(DISP);
        algorithms.add(processorFCFS);
        // SRT
        SRT processorSRT = new SRT();
        algorithms.add(processorSRT);
        // FBV
        FBV processorFBV = new FBV();
        algorithms.add(processorFBV);
        // LTR
//        LTR processorLTR = new LTR();
//        algorithms.add(processorLTR);


        for (Algorithm a : algorithms) {
            readData(args[0]);
            a.loadProcesses(processes);
            a.run();
        }


        String report = getReport(algorithms);
        System.out.println(report);
    }


    public boolean readData(String filename)
    {
        randomValues = new ArrayList<>();
        processes = new ArrayList<>();

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

    public String getReport(ArrayList<Algorithm> algorithms)
    {
        String report = "";
        for (Algorithm a : algorithms) {
            report += a.reportFull();
        }

        report += "\nSummary\nAlgorithm  Average Turnaround Time  Waiting Time\n";
        for (Algorithm a : algorithms) {
            report += a.reportAvg();
        }
        return report;
    }
}
