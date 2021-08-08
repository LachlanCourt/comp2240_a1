/*******************************************************************************
 ****    COMP2240 Assignment 1
 ****    c3308061
 ****    Lachlan Court
 ****    08/08/2021
 ****    This class houses the main run of the program. It creates an instance
 ****    of each scheduling algorithm, runs them one by one, and then outputs
 ****    the result
 *******************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class A1
{
    // Member variables, data read from file
    private static int DISP;
    private ArrayList<Integer> randomValues;
    private ArrayList<Process> processes;

    // Main function
    public static void main(String[] args)
    {
        // If a filename is not specified, the program cannot run
        if (args.length != 1)
        {
            System.err.println("Invalid input data. Terminating...");
            System.exit(-1);
        }

        // If there is a suitable number of arguments, pass those arguments to the run() method
        A1 assign = new A1();
        assign.run(args);
    }

    /**
     * The main run of the program. It takes the arguments of the terminal command and runs four scheduling algorithms
     * @param args the arguments of the terminal command
     * Precondition: args should not be null
     * Postcondition: The algorithms have been simulated and the result outputted to console
     */
    public void run(String[] args)
    {
        // Check that the file is valid, if not, no point continuing
        if (!readData(args[0]))
        {
            System.out.println("Error loading text file! Terminating");
            System.exit(-2);
        }

        // Declare an ArrayList to hold the four scheduling algorithms being simulated
        ArrayList<Algorithm> algorithms = new ArrayList<>();

        // Create a new instance of each algorithm and add it to the algorithms ArrayList

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
        LTR processorLTR = new LTR(randomValues);
        algorithms.add(processorLTR);

        // Loop through the algorithms and load a new set of processes, then run each algorithm
        for (Algorithm a : algorithms)
        {
            readData(args[0]);
            a.loadProcesses(processes);
            a.run();
        }

        // Get and print the report at the end of the simulation
        String report = getReport(algorithms);
        System.out.println(report);
    }

    /**
     * Read simulation data from a file
     * @param filename name of the file to read from
     * @return flag indicating successful or unsuccessful read
     * Precondition: filename should be specified
     * Postcondition: DISP, randomValues, and processes member variables will be filled with values from file
     */
    public boolean readData(String filename)
    {
        // Declare member variables
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

        // Line that has been read
        String line;

        // Status flags used during read
        boolean readingProcesses = false;
        boolean readingValues = false;

        // Values used to create a process
        String tempID = "";
        int tempArrive = 0;
        int tempExecSize = 0;
        int tempTickets = 0;
        while (input.hasNext())
        {
            line = input.nextLine();

            // Load DISP
            if (line.startsWith(("DISP:")))
            {
                DISP = Integer.valueOf(line.substring(6));
                // Clear the END of this section
                input.nextLine();
                // Assume the next part of the file is about processes
                readingProcesses = true;
            }
            // Load data about processes
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
            // If we are reading processes and we reach an END, that is the end of a single process. Create it and add
            // it to the processes list
            else if (line.startsWith(("END")) && (readingProcesses))
            {
                Process newProcess = new Process(tempID, tempArrive, tempExecSize, tempTickets);
                processes.add(newProcess);
            }
            // Processes are done, start reading random numbers
            else if (line.startsWith(("BEGINRANDOM")))
            {
                readingProcesses = false;
                readingValues = true;
            }
            // Random numbers are done
            else if (line.startsWith(("ENDRANDOM")) && (readingValues))
            {
                readingValues = false;
            }
            // Read a random number and add it to the randomValues list
            else if (readingValues)
            {
                randomValues.add(Integer.valueOf(line));
            }
            // We have only been completely successful if we reach this line
            else if (line.startsWith("EOF"))
            {
                return true;
            }
        }
        // A file without the EOF indicator is treated as invalid
        return false;
    }

    /**
     * Get the entire report from the simulation of the scheduling algorithms
     * @param algorithms the algorithms that were simulated
     * @return the full report of the simulation
     * Precondition: The algorithms should have run else report will be empty
     * Postcondition: Return value
     */
    public String getReport(ArrayList<Algorithm> algorithms)
    {
        // Loop through each algorithm to grab the report
        String fullReport = "";
        String summaryReport = "\nSummary\nAlgorithm  Average Turnaround Time  Waiting Time\n";
        for (Algorithm a : algorithms)
        {
            // Add the full report and the summary line for each algorithm
            fullReport += a.reportFull();
            summaryReport += a.reportAvg();
        }
        return fullReport + summaryReport;
    }
}

// clang-format off

/*

                                .--
                               *@%@@*
                             -@@*+*%@%-
                            -@%*%*++*@@=
                            @@==%@#++*@@=
                           *@+---#@#++*@@*
                          *@*-----+@@*+*@@=
                         *@#------:=%@##%@@**----.
                        -@@=--=+*#%@@@@@%%@@@@@@@@@@%*--
                        #@+=*%@@@#*=+%%+#@@@@@@@@*++%@@@@#=
                       -@@%@@#++=======#@@@@@%*+=+#%@@@@@@@@**.
                     -*@@@@%**+========%@@%*===+#@@@@@@@@@@@@@%@=-           =*@
                  -*@@@#**#@@@@@#+===========+%@@@@@@@%#*+==**#@@@@=      =*@%+=
                -%@@+:     .@@@@@@#==========%@@@@@%*====+%@@@@@@@@@@=--%%*=----
    :@=        @@@@-       .@@@@@@@@=========+*##*+=====*@@@@@@@%##@@@@%-:------
      -**=   -%@@@@       .#@@@@@@@@%===================#@@@%#*+==+@@@@@#-------
  **.    =@*%@%*@@@#=--=*%@@@@@@@@@@@==============================*##*#@%=-----
   -=@*- =@@@*:=@@@@@@@+:..:+@@@@@@@%===================================+%%=----
      -@@@@*=#-:*@@@@@*      *@@@@@#==============+**###**+===============#@+-=+
       @@@@=..*:.#@@@@+      +@@@@%=============#@@@@@@@@@@%+=============+@@+++
      *@@=-++:...:#@@@%-    -%@@%*============*@@@#+=--+#@@@@@*============*#*++
     *@@#..........-#%@@%##%@@#*============+%@@=        +@@@@@@+===========++++
     @@@-.........=#@%+=-----:--===========#@@@:         #@@@@@@@#===========%%%
     @@@........=%@+ :+%%+=........:--====*@@@@        =%@@@@@@@@@%==========%@-
 =@@@@@%......=@@*      :#@@+..........-==#@@@@#:   :*@@@@@@@@@@@@@+=========*@:
@@%#%@@@#=..-#@#-          =#%+---=-:...:-#@@@@@@@@@@%#*#%@@@@@@@@@*=========+@=
@%*****#@@@%@@-     -+=.     =@@%#**-.....+@@@@@@@@@+    .+@@@@@@@@*=========*@-
*******#%%%@@@#-. -%@##@#=:=%*-:..........:@@@@@@@@#.      *@@@@@@@+========+#@
*******%%%%%%%@@@@@%:...=**=................+@@@@@@%-      *@@@@@@*========+*%*
******#%%%%%%%%%%@@@%=.......................-%@@@@@#-    =%@@@@%=========+*#@
*****#%%%%%%%@@@@%%@@@%+:......................-#@@@@@%*#%@@@%#+=========+*%@-
*****%%%%%%%@@###@@%#%@@@*-........................:-=====-....:-=======+*%@-
****%%%%%%@@%+++++*%@%%@@+...........................-...=+-:.....:=====**@@
***%%%%%%%@@*+++++++*%@@-............................-++=::=*%#*=:..===+*#@@
**%%%%%%%%@@++++++++++#@%:..............................=##=..-*%@@##++**@@=
*#%%%%%%%%@@+++++++++++*%@%=..............................-+#*=..-=*@@@%@@*
#%%%%%%%%%@@#+++++++++++++#@%=...............................:+%#=::+%@@@*%*-
%%%%%%%%%%%@@+++++++++++++++#%@#=..............................:%@@@@%*-   --
%%%%%%%%%%%%@@*+++++++++++++***#%@#+-......................-+#@@***=*@*-

*/
// clang-format on