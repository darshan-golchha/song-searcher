import java.util.Scanner;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * This class implements the functionality of the frontend for the app.
 *
 */
public class Frontend implements FrontendInterface {


	BackendInterface backendObj; // instance variable to store reference to Backend Object
	Scanner s; // instance variable to store scanner to read input
	Boolean fileLoaded; // instance variable to keep track if a file is loaded or not
	/**
	 * A constructor that accepts a reference to the backend and a java.util.Scanner instance
	 * to read user input. This constructor also initializes fileLoaded to false.
	 *
	 * @parem backendObj reference to the backend
	 * @param s Scanner instance to read user input
	 */
	public Frontend(BackendInterface backendObj, Scanner s) {
		this.backendObj = backendObj;
		this.s = s;
		this.fileLoaded = false;
	}

	/**
	 * command to start the main command loop for the user. The implemented method should display a
	 * menu of all available commands and call methods that correspond to valid command selections.
	 */
	public void startCommandLoop() {
		String input = "";
		while (true) {
			System.out.println("Choose one of the following commands by inputing the corresponding "
				+	"number along with required parameters.");
			System.out.println("1. Load csv file containing songs database (Usage: 1 songs.csv)");
			System.out.println("2. List all songs that have a dancebility score at or above a threshold. (Usage: 2 80)");
			System.out.println("3. Output the average danceability score in the loaded dataset. (Usage: 3)");
			System.out.println("4. Exit. (Usage: 4)");
			input = s.nextLine();
			input = input.trim();
			if (input.charAt(0) == '1') {
				this.fileLoaded = loadFile(input.substring(1).trim());
			}
			else if (input.charAt(0) == '2') {
				String dancebility_str = input.substring(1).trim();
				Double dancebility_double = 0.0;
				try {
					dancebility_double = Double.parseDouble(dancebility_str);
				}
				catch(NumberFormatException e) {
					System.out.println("input command does not contain a parsable double.");
					continue;
				}
				catch(Exception e) {
					System.out.println("invalid command to select dancebility." + 
							"enter a number with at most two decimal points");
					continue;
				}
				selectDancebilitySong(dancebility_double);
			}
			else if (input.charAt(0) == '3') {
			    if (input.length() != 1) {
				System.out.println("incorrect command format. see usage.");
			    }
				avgDancebility();
			}
			else if (input.charAt(0) == '4') {
			    if (input.length() != 1) {
				System.out.println("incorrect command format. see usage.");
			    }
				exit();
				break;
			}
			else {
				System.out.println("Invalid command. See usage.");
			}

		}  
			
	}

	/**
	 * specify and load a data file. if an error is encountered when loading file, provide
	 * instructive feedback and return false to signify error
	 *
	 * @param filename the name of the file to load
	 * @return true if success, false if any error
	 */
	public boolean loadFile(String filename) {
		if (filename.isEmpty()) {
		    System.out.println("Invalid Filename.");
		    return false;
		}
		if (!filename.contains(".csv")) {
		    System.out.println("Invalid File");
		    return false;
		}
		File f = new File(filename);
		if(!f.isFile() || f.isDirectory()) {
		    System.out.println("Fail to read "  + filename + ". Check if there's a typo.");
		    return false;
		}
		try {
            backendObj.readCSVFile(filename);
        }
        catch(FileNotFoundException e) {
            System.out.println("Fail to read "  + filename + ". Check if there's a typo.");
            return false;
        }
        catch(Exception e) {
            System.out.println("Error");
            return false;
        }
		System.out.println("File " + filename + " successfully read");
		return true;
	}
	
	/**
	 * command to list all songs that have a danceability score greater than the provdied threshold. if user enters a
	 * dancebility score out of range, output an error message with the correct range of
	 * dancebility score. if a file has not been loaded, output an error message instructing user
	 * to upload a dataset
	 *
	 * @param dancebility the dancebility score for the songs to list
	 */
	public void selectDancebilitySong(Double dancebility) {
		if (!fileLoaded) {
			System.out.println("Load a dataset first with Command 1");
			return;
		}
		if (dancebility < 0 || dancebility > 100) {
			System.out.println("Enter a dancebility score within the valid range");
			return;
		}
		List<Song> list = backendObj.getMinDanceabilitySongs(dancebility);
		System.out.println(list);
	}

	/**
	 * command that shows the average danceability score in the loaded dataset.
	 * output error message if no dataset is loaded.
	 */
	public void avgDancebility() {
		if (!fileLoaded) {
			System.out.println("Load a dataset first with Command 1");
			return;
		}
		System.out.println(backendObj.getAverageDanceability());
	}

	/**
	 * command to exit the app
	 */
	public void exit() {
		System.out.println("Thanks for using!");
	}
}
