import java.util.ArrayList;
import java.util.List;

public class BackendPlaceholderFrontend implements BackendInterface {

    /**
     * Reads song data from a CSV file and transfers it to the backend.
     *
     * @param filePath The location of the CSV File.
     */
    public void readCSVFile(String filePath) throws java.io.FileNotFoundException {
    }

    /**
     * Calculates and returns the average danceability score of all songs in the dataset.
     *
     * @return The average danceability score.
     */
    public double getAverageDanceability() {
    	return 30.0;
    }

    /**
     * Returns a list of songs with minimum danceability score
     * that is at or above the specified threshold.
     *
     * @return A list of songs with minimum danceability.
     */
    public List<Song> getMinDanceabilitySongs(double threshold) {
    	return new ArrayList<Song>();
    }
}
