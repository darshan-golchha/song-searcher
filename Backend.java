import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class implements the BackendInterface and is used to store the data from the csv file
 * and perform operations on it.
 */
public class Backend implements BackendInterface{

    private IterableMultiKeyRBT<Song> songs; // The data structure that stores the songs

    /**
     * This is a constructor that takes an instance of IterableMultiKeySortedCollectionInterface
     * and stores it in the songs variable.
     * @param tree
     */
    public Backend(IterableMultiKeySortedCollectionInterface<Song> tree){
        this.songs = (IterableMultiKeyRBT<Song>) tree;
    }

    /**
     * This method reads the csv file and stores the data in the backend.
     * @param filePath
     * @throws FileNotFoundException
     */
    @Override
    public void readCSVFile(String filePath) throws FileNotFoundException {
        // Extracting data from the csv file on the filePath
        // and storing it in the backend
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skipping the first line of the csv file but stroing it in a variable
            br.readLine();
            while ((line = br.readLine()) != null) {
                // Splitting the line by commas after checking that if a comma is inside a quote
                // then it is not a delimiter and checking for double quotes in the line and removing them
                String[] characters = line.split("");
                String title = "";
                String[] values = new String[14];
                // Viewing the occurence of the last " character in the line by traversing the line backwards
                for (int i = characters.length - 1; i >= 0; i--) {
                    if (characters[i].equals("\"")) {
                        // begin index is 1 because the first character is a "
                        title = line.substring(1, i);
                        // now we look for a " within the title which would mean that an explicit
                        // double quote is needed within the title name
                        for (int j = 0; j < title.length(); j++) {
                            if (title.charAt(j) == '\"') {
                                // if a " is found, then we remove it and add the next character
                                // to the title
                                title = title.substring(0, j) + title.substring(j + 1);
                            }
                        }
                        // now after the title has been extracted we add it as the first element of values, 
                        // and then we split the rest of line by commas
                        // and add it to the array values
                        values[0] = title;
                        String[] restOfLine = line.substring(i + 2).split(",");
                        for (int j = 0; j < restOfLine.length; j++) {
                            values[j + 1] = restOfLine[j];
                        }
                        break;
                    } // if the index reaches 0 and no " is found, then the line does not contain any "
                    else if (i == 0) {
                        values = line.split(",");
                    }
                }
                // Creating a song object from the data extracted from the line
                Song song = new Song(values[0], values[1], values[2], Double.parseDouble(values[6]), Integer.parseInt(values[3]));
                // Adding the song to the list of songs
                this.songs.insertSingleKey(song);
            }
        } catch (IOException e) {
            // Handle IOException, e.g., the file does not exist
            throw new FileNotFoundException("File not found: " + filePath);
        } 
        
    }

    /**
     * This method returns the average danceability of all the songs in the backend.
     * @return double
     */
    @Override
    public double getAverageDanceability() {
        // if the list of songs is empty, then the average danceability is 0                                                                         
        if (this.songs.size() == 0) return 0;
        // summing up the danceability of all the songs                                                                                              
        double sum = 0;
        for (Song song : this.songs) {
            sum += song.getDanceability();
        }
        // returning the average danceability
        return sum / this.songs.numKeys();
    }

    /**
     * This method returns a list of songs that have a danceability score less than the threshold.
     * @param threshold
     * @return List<Song>
     */
    @Override
    public List<Song> getMinDanceabilitySongs(double threshold) {
        // Creating a list of songs that have a danceability score more than or equal to the threshold
        List<Song> songs = new ArrayList<>();

	// iterating over the tree of songs
        for (Song song : this.songs) {

	    // adding the song to the list if the song has its danceability above the threshold
            if (song.getDanceability() >= threshold) {
                songs.add(song);
            }
        }
	// finally returning the list of songs
        return songs;
    }

    /**
     * This main method starts the command loop provided
     * by the frontend and displays a menu driver UI to the user.
     * @param args
     */
    public static void main(String[] args){
        Backend backend = new Backend(new IterableMultiKeyRBT<Song>());
        Frontend frontend = new Frontend(backend,new Scanner(System.in));
    frontend.startCommandLoop();
    }
}
