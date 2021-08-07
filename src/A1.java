import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class A1
{
    private static int DISP;
    private ArrayList<Integer> randomValues;


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
        while (input.hasNext())
        {
            line = input.nextLine();
            if (line.startsWith(("DISP:")))
            {
                System.out.println(line);
                DISP = Integer.valueOf(line.substring(6));
                System.out.println("Disp is " + DISP);
            }
            else {
                //System.out.println(input.next());
            }
        }
        return true;
    }


    /*
            try
        {
            // Read Queues
            queues = new ArrayList<>();
            // While there are still Queues (Starting with "Q") to be read, loop
            while (input.hasNext("Q.*"))
            {
                // Add a new queue, labelling it with the name given in the file
                queues.add(new StorageQueue<Item>(Qmax, input.nextLine()));
            }

            // Read Stages
            stages = new ArrayList<>();

            // Assume the first stage is the InitialStage
            if (input.hasNext("S.*"))
            {
                // Grab the elements from the line
                String id = input.next();
                String nextId = input.next();

                // Get a reference to the Queue that will be the next of the Stage
                StorageQueue<Item> next = getQueueByID(nextId);
                // If the Queue requested doesn't exist, the load has failed
                if (next == null)
                {
                    return false;
                }

                // Assume the processingFactor is 1 unless explicitly specified
                double processingFactor = 1;
                // If a double exists next, assume it's a processingFactor
                if (input.hasNextDouble())
                {
                    processingFactor = input.nextDouble();
                }
                // Add a new InitialStage with the data gathered
                stages.add(new InitialStage(next, id, M, N, rd, processingFactor));
            }

            // While there are still Stages (Starting with "S") to be read, loop
            while (input.hasNext("S.*"))
            {
                // Grab the elements from the line
                String id = input.next();
                String prevId = input.next();
                String nextId = input.next();

                // Get a reference to the Queue that will be the next and prev of the Stage
                StorageQueue<Item> prev = getQueueByID(prevId);
                StorageQueue<Item> next = getQueueByID(nextId);
                // If either Queue requested doesn't exist, the load has failed
                if (next == null || prev == null)
                {
                    return false;
                }

                // Assume the processingFactor is 1 unless explicitly specified
                double processingFactor = 1;
                // If a double exists next, assume it's a processingFactor
                if (input.hasNextDouble())
                {
                    processingFactor = input.nextDouble();
                }
                // Add a new InitialStage with the data gathered
                stages.add(new MidStage(prev, next, id, processingFactor));
            }

            // Final stage indicated by escape character "F"
            if (input.next().compareTo("F") == 0)
            {
                // Grab the elements from the line
                String id = input.next();
                String prevId = input.next();

                // Get a reference to the Queue that will be the prev of the Stage
                StorageQueue<Item> prev = getQueueByID(prevId);
                // If the Queue requested doesn't exist, the load has failed
                if (prev == null)
                {
                    return false;
                }

                // Assume the processingFactor is 1 unless explicitly specified
                double processingFactor = 1;
                // If a double exists next, assume it's a processingFactor
                if (input.hasNextDouble())
                {
                    processingFactor = input.nextDouble();
                }
                // Add a new InitialStage with the data gathered
                stages.add(new FinalStage(prev, id, processingFactor));

                // Label which stages are identified as the start and end of the line
                startStage = (InitialStage) stages.get(0);
                endStage = (FinalStage) stages.get(stages.size() - 1);
            }
            // Notify user that a file has been loaded
            System.out.println("Interpretting production line from text file...\n");
            return true;
        }
            catch (Exception e)
        {
            // If any error occurs during load, notify user and load default line
            System.err.println("Invalid text file format");
            return false;
        }



        //Get the current path of the source directory
        Path dir = Paths.get(System.getProperty("user.dir"));
        //Start the menu with a prompting message
        String menuOutput = "What file do you wish to load from?\n";
        //The menu item the user picks will be stored here
        String userInput;
        //Keep track of all text file names in the source directory
        String[] files = new String[5];
        //Logical size of the array
        int numberOfFiles = 0;
        //As each file is added to the menu it will be temporarily stored here
        String tempFilename;

        //Start by finding all text files in the source directory by opening a DirectoryStream
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir))
        {
            //Loop through each file found in the DirectoryStream
            for (Path entry : stream)
            {
                //If the file name is a text file, it will end in the extension .txt
                if (String.valueOf(entry.getFileName()).endsWith(".txt"))
                {
                    //Save the file name and add it to the files array
                    tempFilename = String.valueOf(entry.getFileName());
                    files[numberOfFiles] = tempFilename;

    //                    Add it to the menu by takeing the substring of the first part of the name in order to remove
    the
    //                    .txt from the end

                    menuOutput += (numberOfFiles + 1) + ". " + tempFilename.substring(0, tempFilename.length() - 4) +
    "\n";
                    //Increase the logical size of the array
                    numberOfFiles++;

    //                    If the logical size of the array is the same as the physical size of the array, increase the
    //                    physical size using the resizeStringArray method

                    if (numberOfFiles == files.length)
                    {
                        files = resizeStringArray(files);
                    }
                }
            }
        }
            catch (IOException e)
        {
            //If there is an error loading any of the files, just return which will initialise with an empty collection
            return;
        }

        //If there are no text files in the source directory, just return which will initialise with an empty collection
            if (numberOfFiles == 0)
        {
            return;
        }
        //Add an option to initialise with an empty collection
        numberOfFiles++;
        menuOutput += numberOfFiles + ". No file - start with an empty collection";

        //Present the menu to the user and ask them to select an option
            do
        {
            userInput = input(menuOutput);
        }
        //Loop until the user enters a valid integer that is an option on the menu
            while (!validMenuEntry(userInput, 1, numberOfFiles));

        //If the user selects no file load, simply return which will simply initialise with an empty collection
            if (Integer.parseInt(userInput) == numberOfFiles)
        {
            return;
        }


    //    Save the filename by comparing the user input to the files array, and save this name in the global
    //    collectionName variable

        String filename = files[Integer.parseInt(userInput) - 1];
        collectionName = filename;

        //Declare a Scanner to read the text file
        Scanner inputStream;
            try
        {
            //Try opening the Scanner with the filename selected
            inputStream = new Scanner(new File(filename));
        }
            catch (FileNotFoundException e)
        {
            //If there is an error opening the Scanner, return which will initiallise with an empty collection
            return;
        }

        //Each line of text will be stored here as it is read so that it can be interpretted
        String textLine;

        //Variables to hold the album info
        String currentAlbumName = "";
        Album currentAlbum = new Album("");

        //Variables to hold the song info
        String songName = "";
        String artist = "";
        int duration = 0;
        String genre = "";

        //This array will ensure that all information has been read about a song before it is added to the collection
        boolean[] dataCounter = {false, false, false, false};
        //Loop while there is still information to read from the text file
            while (inputStream.hasNext())
        {
            //Read the text file line by line
            textLine = inputStream.nextLine();
            //If the line starts with the word "Album", create a new album with the name being whatever follows "Album"
            if (textLine.startsWith("Album"))
            {
                currentAlbumName = textLine.substring(6);

    //                If the album has already been created (Say the collection is out of order and there are some songs
    from
    //                one album and then from another and then some from the first again), only create the album once.
    Also
    //                ensure that there is space in the albums array before creating the album

                if ((!albumExists(currentAlbumName)) && (albumsSize != albums.length))
                {
                    makeAlbum(currentAlbumName);
                }
            }
            //If the line starts with the word "Name", assume it's the name of a song in the current album
            else if (textLine.startsWith("Name"))
            {
                songName = textLine.substring(5);
                dataCounter[0] = true;
            }
            //If the line starts with the word "Artist", assume it's the Artist of a song in the current album
            else if (textLine.startsWith("Artist"))
            {
                artist = textLine.substring(7);
                dataCounter[1] = true;
            }
            //If the line starts with the word "Duration", assume it's the Duration of a song in the current album
            else if (textLine.startsWith("Duration"))
            {
                String temp = textLine.substring(9);

                //First assume it's in the format mm:ss
                duration = convertDurationToSecs(temp);
                if (duration == -1)
                {
                    //If duration is now -1, the duration must have been in the format of an integer number of seconds
                    if (isInteger(temp))
                    {
                        //In this case parse it straight in
                        duration = Integer.parseInt(temp);
                    }
                }
                if (duration != -1)
                {
                    dataCounter[2] = true;
                }
            }
            //If the line starts with the word "Genre", assume it's the Genre of a song in the current album
            else if (textLine.startsWith("Genre"))
            {

                genre = textLine.substring(6);
                String[] genres = {"Rock", "Pop", "Hip-hop", "Bossa nova"};
                //Loop through to determine which genre it is referring to, and then add it in
                for (int i = 0; i < 4; i++)
                {
                    if (genre.toLowerCase().compareTo(genres[i].toLowerCase()) == 0)
                    {
                        genre = genres[i];
                        dataCounter[3] = true;
                    }
                }
            }

            //If all 4 items in the dataCounter array are true, the complete information for a song has been read
            if ((dataCounter[0]) && (dataCounter[1]) && (dataCounter[2]) && (dataCounter[3]))
            {
                //Loop through the albums and find the name of the current album that is being read
                for (int i = 0; i < albumsSize; i++)
                {
                    if (albums[i].getName().compareTo(currentAlbumName) == 0)
                    {
                        //Save the album that is currently being added to in the currentAlbum variable
                        currentAlbum = albums[i];
                    }
                }

    //                Ensure that the album is not full, and if not, add the song to the album using the makeSong
    method.
    //                Ignore any error codes. If there is an error such as the song already existing or going for too
    long,
    //                the song will simply not be added to the collection

                if (!currentAlbum.isFull())
                {
                    makeSong(songName, artist, genre, duration, currentAlbum);
                }
                //Reset the dataCounter array ready for a new song
                dataCounter[0] = false;
                dataCounter[1] = false;
                dataCounter[2] = false;
                dataCounter[3] = false;
            }
        }
        //After the text file has been read, close the Scanner and show a success message to the user
            inputStream.close();
        output("Song collection successfully loaded!");
    }

    */
}
