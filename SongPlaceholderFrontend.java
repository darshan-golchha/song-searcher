/**
 * This interface defines the Data Type Song and the available 
 * methods that come with the Song Class.
 */
public class SongPlaceholderFrontend implements SongInterface {
	
	/**
	 * This constructor creates a song object based on the given specification
	 * related to that song. 
	 * 
	 * @param artist
	 * @param title
	 * @param genre
	 * @param danceability
	 * @param year
	 * @return song
	 */
	public SongPlaceholderFrontend(String artist, String title, String genre, double danceability, int year) {
	
	}
	
	/**
	 * This method returns the artist of the song.
	 * @return artist
	 */
	public String getArtist() {
		return "";
	}
	
	/**
	 * This method sets the artist of the song.
	 * @param artist
	 */
	public void setArtist(String artist) {
	}
	
	/**
	 * This method returns the genre of the song.
	 * @return genre
	 */
	public String getGenre() {
		return "";
	}
	
	/**
	 * This method sets the genre of the song.
	 * @param genre
	 */
	public void setGenre(String genre) {
	}
	
	/**
	 * This method returns the title of the song.
	 * @return title
	 */
	public String getTitle() {
		return "";
	}
	
	/**
	 * This method sets the title of the song.
	 * @param title
	 */
	public void setTitle(String title) {
	}
	
	/**
	 * This method returns the danceability score of the song.
	 * @return danceability
	 */
	public double getDanceability() {
		return 1.0;
	}
	
	/**
	 * This method sets the danceability score of the song.
	 * @param danceability
	 */
	public void setDanceability(double danceability) {
	}
	
	/**
	 * This method returns the year of the song.
	 * @return year
	 */
	public int getYear() {
		return 0;
	}
	
	/**
	 * This method sets the year of the song.
	 * @param year
	 */
	public void setYear() {
	}

	@Override
	public int compareTo(SongInterface o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
	}
	
}
