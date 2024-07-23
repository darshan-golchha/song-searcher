/**
 * An interface for a class that implements the functionality of the frontend for the app.
 */
interface FrontendInterface {

	/**
	 * A constructor that accepts a reference to the backend and a java.util.Scanner instance
	 * to read user input
	 *
	 * @parem backendObj reference to the backend
	 * @param s Scanner instance to read user input
	 */
	// public UserCommands(Backend backendObj, Scanner s)

	/**
	 * command to start the main command loop for the user. The implemented method should display a
	 * menu of all available commands and call methods that correspond to valid command selections.
	 */
	public void startCommandLoop();

	/**
	 * specify and load a data file. if an error is encountered when loading file, provide
	 * instructive feedback and return false to signify error
	 *
	 * @param filename the name of the file to load
	 * @return true if success, false if any error
	 */
	public boolean loadFile(String filename);

	/**
	 * command to list all songs that have a specific danceability score. if user enters a
	 * dancebility score out of range, output an error message with the correct range of
	 * dancebility score. if a file has not been loaded, output an error message instructing user
	 * to upload a dataset
	 *
	 * @param dancebility the dancebility score for the songs to list
	 */
	public void selectDancebilitySong(Double dancebility);

	/**
	 * command that shows the average danceability score in the loaded dataset.
	 * output error message if no dataset is loaded.
	 */
	public void avgDancebility();

	/**
	 * command to exit the app
	 */
	public void exit();
}
