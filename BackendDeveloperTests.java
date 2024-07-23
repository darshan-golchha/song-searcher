import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class BackendDeveloperTests {

    IterableMultiKeyRBT<Song> tree = new IterableMultiKeyRBT<>();// 

    /**
     * This method tests the readCSVFile method by passing a file that does not
     * exist.
     * The method should throw a FileNotFoundException.
     * If any other exception is thrown, the test fails.
     */
    @Test
    void testReadCSVFile() {
        // Creating a Backend instance
        Backend backend = new Backend(tree);

        // Putting the method call in a try-catch block to catch any exceptions
        // and checking that an exception is thrown for a file that does not exist
        try {
            backend.readCSVFile("no.csv");
            Assertions.fail("The method should throw an exception for a non-existing file");
        } catch (FileNotFoundException e) { // Catching the exception for the file not existing
        } catch (Exception e) { // Catching any other exception
            Assertions.fail("A different exception was thrown");
        }
    }

    /**
     * This method tests the getAverageDanceability method by passing the songs.csv
     * file.
     * The method should return a value that is not 0.0 since the file contains
     * songs data.
     * If the value is 0.0, the test fails.
     */
    @Test
    void testGetAverageDanceability() {
        // Creating a Backend instance with some data
        Backend backend = new Backend(tree);
        try {
            backend.readCSVFile("songs.csv");
            // Calling the getAverageDanceability method
            double averageDanceability = backend.getAverageDanceability();

            // Since the songs.csv file contain data, the average should not be 0.0
            assertTrue(averageDanceability == 64.32333333333334);
        } catch (FileNotFoundException e) {
            Assertions.fail(e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * This method tests the getMinDanceabilitySongs method by passing the songs.csv
     * file.
     * The method should return a list that is not null since the file contains
     * songs data.
     * If the list is null, the test fails.
     */
    @Test
    void testGetMinDanceabilitySongs() {
        // Preparing a Backend instance with some data
        Backend backend = new Backend(tree);
        try {
            backend.readCSVFile("songs.csv");
            // Calling the getMinDanceabilitySongs method with a threshold
            double threshold = 0.5;
            List<Song> songs = backend.getMinDanceabilitySongs(threshold);
            // Testing that the returned list is not null
            assertTrue(songs != null);
        } catch (FileNotFoundException e) {
            Assertions.fail(e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * This method tests that the setter and getter methods for the artist, title,
     * year and genre
     * field are working correctly by creating a Song instance and setting the
     * fields to some
     * values and then checking that the values are set correctly.
     */
    @Test
    void testSetArtist() {
        // Checking for the field Artist

        // Creating a Song instance with some data
        Song song = new Song("Anonymus Artist", "Sample Title", "Sample Genre", 0.4, 2023);

        // Setting a new artist
        song.setArtist("Great Artist");

        // Checking if the artist is set correctly
        assertEquals("Great Artist", song.getArtist());

        // Checking for the field Title
        song.setTitle("Great Title");
        // Checking if the title is set correctly
        assertEquals("Great Title", song.getTitle());

        // Checking for the field Year
        song.setYear(2020);
        // Checking if the year is set correctly
        assertEquals(2020, song.getYear());

        // Checking for the field Genre
        song.setGenre("Great Genre");
        // Checking if the genre is set correctly
        assertEquals("Great Genre", song.getGenre());
    }

    /**
     * This method tests the getMinDanceabilitySongs method by passing the songs.csv
     * file.
     * The method should return a list that is empty since the threshold is high.
     * If the list is not empty, the test fails.
     */
    @Test
    void testGetMinDanceabilitySongsWithNoMatchingSongs() {
        // Creating a Backend instance with some data
        Backend backend = new Backend(tree);
        try {
            backend.readCSVFile("songs.csv");
            // Calling the getMinDanceabilitySongs method with a high threshold
            // so that the list is empty
            double threshold = 100.0;
            List<Song> songs = backend.getMinDanceabilitySongs(threshold);
            // Checking that the list is empty
            assertTrue(songs.isEmpty()==true);
            
        } catch (FileNotFoundException e) {
            Assertions.fail(e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * This method tests the getMinDanceabilitySongs method by passing the songs.csv
     * file.
     * The method should return a list that is not empty since the threshold is low.
     * If the list is empty, the test fails.
     */
    @Test
    void testGetMinDanceabilitySongsWithMatchingSongs() {
        // Creating a Backend instance with some data
        Backend backend = new Backend(tree);
        try {
            backend.readCSVFile("songs.csv");
            // Calling the getMinDanceabilitySongs method with a low threshold
            double threshold = 67; // Low threshold to match some songs
            List<Song> songs = backend.getMinDanceabilitySongs(threshold);
            // Checking that the list is not empty
            assertTrue(songs.isEmpty() == false);
        } catch (FileNotFoundException e) {
            Assertions.fail(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This method tests the getAverageDanceability method by passing a Backend
     * instance
     * with no data. The method should return 0.0 since there is no data.
     * If the value is not 0.0, the test fails.
     */
    @Test
    void testGetAverageDanceabilityWithNoData() {
        // Creating a Backend instance with no data
        Backend backend = new Backend(tree); // Replace with your actual Backend class

        // checking that the tree is empty
        assertTrue(tree.isEmpty());

        // Calling the getAverageDanceability method with no data
        double averageDanceability = backend.getAverageDanceability();

        // Checking that the average is 0.0
        assertEquals(0.0, averageDanceability);
    }

    // Integration tests

    /**
     * This method tests the loadFile and getAverageDanceability methods' integration
     * to the frontend by passing the songs.csv file. It checks that the average
     * danceability is not 0.0 since the file contains data. And that the frontend responds
     * with the correct message.
     */
    @Test
    void testLoadAndDanceabilityIntegration() {
        FrontendInterface frontend;
        Backend backend = new Backend(tree);
        // Sample user input to load a file
        String userInput = "1 songs.csv\n2 67\n4\n";
        // Expected output
        String expectedOutputLoad = "File songs.csv successfully read";

        // Using text UI tester to simulate user input and capture the frontend's response
        TextUITester tester = new TextUITester(userInput);
        frontend = new Frontend(backend,new Scanner(System.in));
        frontend.startCommandLoop();

        String actualOutput = tester.checkOutput();

        // Checking that the output matches the expected output
        assertTrue(actualOutput.contains(expectedOutputLoad));
    }

    @Test
    void testAvgDancebilityAndExitIntegration() {
        FrontendInterface frontend;
        Backend backend = new Backend(tree); 

        // Sample user input to get the average danceability and then exit
        String userInput = "1 songs.csv\n3\n4\n";
        String expectedOutput = "Thanks for using!\n";

        // Using text UI tester to simulate user input and capture the frontend's response
        TextUITester tester = new TextUITester(userInput);
        frontend = new Frontend(backend,new Scanner(System.in));
        frontend.startCommandLoop();

        String actualOutput = tester.checkOutput();

        // Checking that the output matches the expected output
        assertTrue(actualOutput.contains("64.32333333333334"));
    }

    /**
     * This method specifically tests the Frontend class to see
     * if it correctly handles the case where the user tries to
     * select a command that requires a file to be loaded, but
     * no file has been loaded yet.
     */
    @Test
    public void testFrontendWithoutFile() {
        // Creating a new TextUITester object with user input
        TextUITester tester = new TextUITester("2 80\n4\n");
        Backend backend = new Backend(tree);

        // Initializing Frontend with the backend object and Scanner object
        Frontend testFrontend = new Frontend(backend, new Scanner(System.in));

        // Calling the startCommandLoop method
        testFrontend.startCommandLoop();

        // Checking whether the output contains the expected error message
        String output = tester.checkOutput();
        assertTrue(output.contains("Load a dataset first with Command 1"));
    }

    /**
     * This method specifically tests the Frontend class where
     * an invalid command is entered by the user.
     * The frontend should respond with the correct error message.
     */
    @Test
    public void testFrontendInvalidUserInput() {
        // Creating a new TextUITester object with invalid user input
        TextUITester tester = new TextUITester("invalid\n4\n");

        Backend backend = new Backend(tree);

        // Initializing Frontend with the backend object and Scanner object
        Frontend testFrontend = new Frontend(backend, new Scanner(System.in));

        // Calling the startCommandLoop method
        testFrontend.startCommandLoop();

        // Checking whether the output contains the expected error message for invalid input
        String output = tester.checkOutput();
        assertTrue(output.contains("Invalid command. See usage."));
    }


}
