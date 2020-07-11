package rsj;

public class GenreFilter implements Filter {
	private String genre;

	public GenreFilter(String value) {
		genre = value;
	}

	public boolean satisfies(String id) {
		return MovieDatabase.getGenres(id).contains(genre);
	}

}
