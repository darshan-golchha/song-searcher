/**
 * Song class that represents a song object with its title, artist, genre, danceability
 * and year of release.
 */
public class Song implements Comparable<Song>{

	private int year;// year of release
	private String artist; // artist name
	private String title; // song title
	private String genre; // genre of the song
	private double danceability; // danceability score of the song

	/**
	 * Constructor for the Song class.
	 * @param title
	 * @param artist
	 * @param genre
	 * @param danceability
	 * @param year
	 */
	public Song(String title, String artist, String genre, double danceability, int year) {
		this.artist = artist;
		this.title = title;
		this.genre = genre;
		this.danceability = danceability;
		this.year = year;
	}

	/**
	 * This method returns the artist name of the song.
	 * @return String
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * This method returns the genre of the song.
	 * @return String
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * This method returns the title of the song.
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method returns the danceability score of the song.
	 * @return double
	 */
	public double getDanceability() {
		return danceability;
	}

	/**
	 * This method sets the artist name of the song.
	 * @param artist
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * This method sets the genre of the song.
	 * @param genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * This method sets the title of the song.
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method sets the danceability score of the song.
	 * @param danceability
	 */
	public void setDanceability(double danceability) {
		this.danceability = danceability;
	}

	/**
	 * This method returns the year of release of the song.
	 * @return int
	 */
	public int getYear() {
		return year;
	}

	/**
	 * This method sets the year of release of the song.
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * This method returns a string representation of the song.
	 * @return String
	 */
	@Override
	public int compareTo(Song o) {
		double danceability = this.getDanceability();
		double otherDanceability = o.getDanceability();
		if (danceability > otherDanceability) {
			return 1;
		} else if (danceability < otherDanceability) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
        public String toString() {
                return "\nyear : " + year + " | artist : " + artist + " | title : " + title + " | genre : " + genre
                                + " | danceability : " + danceability;
        }

}
