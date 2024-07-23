import java.util.List;

/**
 * This interface exposes methods for the song searcher application's backend.
 */
public interface BackendInterface {

    /**
     * This is a constructor (added as a comment) that takes an instance of IterableMultiKeySortedCollectionInterface
     * so that we can pass an instance of your RBT into the backend.
     * @param tree
     */
    // public Backend(IterableMultiKeySortedCollectionInterface<SongInterface> tree);

    /**
     * Reads song data from a CSV file and transfers it to the backend.
     *
     * @param filePath The location of the CSV File.
     * @throws java.io.FileNotFoundException
     */
    public void readCSVFile(String filePath) throws java.io.FileNotFoundException;
    
    /**
     * Calculates and returns the average danceability score of all songs in the dataset.
     *
     * @return The average danceability score.
     */
    public double getAverageDanceability();

    /**
     * Returns a list of songs with minimum danceability score
     * that is at or above the specified threshold.
     *
     * @return A list of songs with minimum danceability.
     */
    public List<Song> getMinDanceabilitySongs(double threshold);
}
