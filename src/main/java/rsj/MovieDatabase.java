package rsj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MovieDatabase {
	private static HashMap<String, Movie> ourMovies;

	public static void initialize(String movieFile) {
		ourMovies = new HashMap<String, Movie>();
		try {
			loadMovies("data/" + movieFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialize() {
		ourMovies = new HashMap<String, Movie>();
		try {
			loadMovies("data/ratedmoviesfull.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void loadMovies(String fileName) throws IOException {
		FirstRatings fr = new FirstRatings();
		ArrayList<Movie> list = fr.loadMovies(fileName);
		for (Movie m : list) {
			ourMovies.put(m.getID(), m);
		}
	}

	public static boolean containsID(String id) {
		return ourMovies.containsKey(id);
	}

	public static int getYear(String id) {
		return ourMovies.get(id).getYear();
	}

	public static String getTitle(String id) {
		return ourMovies.get(id).getTitle();
	}

	public static Movie getMovie(String id) {
		return ourMovies.get(id);
	}

	public static String getPoster(String id) {
		return ourMovies.get(id).getPoster();
	}

	public static int getMinutes(String id) {
		return ourMovies.get(id).getMinutes();
	}

	public static String getCountry(String id) {
		return ourMovies.get(id).getCountry();
	}

	public static String getGenres(String id) {
		return ourMovies.get(id).getGenres();
	}

	public static String getDirector(String id) {
		return ourMovies.get(id).getDirector();
	}

	public static int size() {
		return ourMovies.size();
	}

	public static ArrayList<String> filterBy(Filter f) {
		initialize();
		ArrayList<String> list = new ArrayList<String>();
		for (String id : ourMovies.keySet()) {
			if (f.satisfies(id)) {
				list.add(id);
			}
		}

		return list;
	}
}
