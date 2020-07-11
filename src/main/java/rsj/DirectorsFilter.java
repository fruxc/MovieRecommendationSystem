package rsj;

import java.util.ArrayList;
import java.util.Arrays;

public class DirectorsFilter implements Filter {
	private String directors;

	public DirectorsFilter(String listOfDirectors) {
		directors = listOfDirectors;
	}

	public boolean satisfies(String id) {
		ArrayList<String> directorsList = new ArrayList<String>(Arrays.asList(directors.split(",")));
		for (String director : directorsList) {
			if (MovieDatabase.getDirector(id).contains(director))
				return true;
		}
		return false;
	}

}
