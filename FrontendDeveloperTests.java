// --== CS400 Fall 2023 File Header Information ==--
// Name: Steven Shih
// Email: sshih6@wisc.edu
// Group: C37
// TA: Connor Bailey
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Scanner;

public class FrontendDeveloperTests {

	
	/**
	 *
	 * This method tests whether the main command loop is correctly started
	 * by checking whether there is the expected output at system out.
	 *
	 */
	@Test
	public void testStartMainCommandLoop() {
		// Only exit command required for only testing start main command loop
		TextUITester tester = new TextUITester("4\n");		
		// create new Frontend object and start its command loop
		Frontend test = new Frontend(new BackendPlaceholderFrontend(), new Scanner(System.in));				
		test.startCommandLoop();
		String output = tester.checkOutput();
		// Check whether the output printed to System.out matches expectations
		if (!output.contains("1. Load csv file containing songs database (Usage: 1 songs.csv)") 
		|| !output.contains("2. List all songs that have a dancebility score at or above a threshold. (Usage: 2 80)")
		|| !output.contains("3. Output the average danceability score in the loaded dataset. (Usage: 3)")
		|| !output.contains("4. Exit. (Usage: 4)")) {
			Assertions.fail("Main Command Loop not properly started");
		}
	}
	/**
	 *
	 * This method tests whether the command through system in to load a file
	 * successfully works by checking system output.
	 *
	 */
	@Test
	public void testLoadValidFile() {
		// Input command to load valid file via TextUITester
		TextUITester tester = new TextUITester("1 songs.csv\n4\n");	
		// create new Frontend object and start its command loop
		// takes the read file command from System in
		Frontend test = new Frontend(new BackendPlaceholderFrontend(), new Scanner(System.in));
		test.startCommandLoop();
		
		String output = tester.checkOutput();	
		// Check whether the output printed to System.out matches expectations
		if (!output.contains("File " + "songs.csv" + " successfully read")) {
			Assertions.fail("Fail to read a valid files. Output should be empty.");
		}
	}

	/**
	 *
	 * This method tests whether a command to load an invalid file fails
	 * and outputs the correct error messages.
	 *
	 *
	 */
	@Test
	public void testLoadInvalidFile() {
		// Input command to load invalid file via TextUITester
		TextUITester tester = new TextUITester("1 notSongs.csv\n4\n");	
		// create new Frontend object and start its command loop
		Frontend test = new Frontend(new BackendPlaceholderFrontend(), new Scanner(System.in));
		test.startCommandLoop();
		// Check whether the output printed to System.out matches expectations
		String output = tester.checkOutput();
		if (!output.contains("Fail to read ")) {
			Assertions.fail("Fail. Reads an invalid file and returns success");
		}
	}
	
	/**
	 *
	 * This method tests whether a command to list songs with invalid dancebility
	 * will fail and correctly outputs error messages to system out.
	 *
	 */
	@Test
	public void testInvalidParamDancebility() {
		// Input command to get songs with specific dancebility without previously loading a file
		// Then load file songs.csv
		// Input command to get songs with specific dancebility with invalid dancebility
		// Use TextUITester to get output and use \n to separate commands
		TextUITester tester = new TextUITester("2 30\n1 songs.csv\n2 -1\n4\n");	
		// create new Frontend object and start its command loop
		Frontend test = new Frontend(new BackendPlaceholderFrontend(), new Scanner(System.in));
		test.startCommandLoop();
		// Check whether the output printed to System.out matches expectations
		String output = tester.checkOutput();
		if (!output.contains("Load a dataset first with Command 1")) {
			Assertions.fail("Error. Command loop proceeds without a file loaded.");
		}
		if (!output.contains("Enter a dancebility score within the valid range")) {
			Assertions.fail("Error. Command loop takes an invalid dancebility score");
		}
	}
	
	/**
	 *
	 * This method tests if the command to list songs with a specific valid dancebility
	 * will correctly output the desired results.
	 *
	 *
	 */
	@Test
	public void testValidDancebility() {
		// Input command to load a file first then test get songs with specific dancebility with a
		// valid dancebility
		// Use TextUITester to get output and use \n to separate commands
		TextUITester tester = new TextUITester("1 songs.csv\n2 30\n4\n");
		Frontend test = new Frontend(new BackendPlaceholderFrontend(), new Scanner(System.in));
		test.startCommandLoop();
		// Check whether the output printed to System.out matches expectations
		if (!tester.checkOutput().contains("[]")) {
			Assertions.fail("Error. Songs with specified dancebility is not outputted");
		}
	}

	/**
	 *
	 * This method tests the command that gets average dancebility of the loaded dataset
	 * first without a file loaded and then with a file loaded. Should first output an 
	 * error message about no dataset loaded then correctly output after loading a dataset.
	 *
	 */
	@Test
	public void testAvgDancebility() {
		// Input command to get average dancebility while no file loaded
		// Then load file songs.csv
		// Get average dancebility after file loaded
		// Use TextUITester to get output and use \n to separate commands
		TextUITester tester = new TextUITester("3\n1 songs.csv\n3\n4\n");
		Frontend test = new Frontend(new BackendPlaceholderFrontend(), new Scanner(System.in));
		test.startCommandLoop();
		// Check whether the output printed to System.out matches expectations
		String output = tester.checkOutput();
		if (!output.contains("Load a dataset first with Command 1")) { 
			Assertions.fail("Error. Command loop proceeds without a file loaded.");
		}
		if (!output.contains("30.0")) {
			Assertions.fail("Error. Command loop does not print average dancebility score correctly.");
		}
	}
}
