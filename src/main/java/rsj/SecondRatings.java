package rsj;

import java.io.IOException;
import java.util.*;

public class SecondRatings {
	private ArrayList<Movie> myMovies;
	private ArrayList<EfficientRater> myRaters;

	public SecondRatings() throws IOException {
		// default constructor
		this("data/ratedmoviesfull.csv", "data/ratings.csv");
	}

	public SecondRatings(String movieFile, String ratingsFile) throws IOException {
		FirstRatings fr = new FirstRatings();
		myMovies = fr.loadMovies(movieFile);
		myRaters = fr.loadRaters(ratingsFile);
	}

	public int getMovieSize() {
		return myMovies.size();
	}

	public int getRaterSize() {
		return myRaters.size();
	}

	public double getAverageByID(String movieId, int minimalRaters) {
		double average = 0, total = 0;
		int count = 0;
		for (EfficientRater rater : myRaters) {
			if (rater.hasRating(movieId)) {
				total = total + rater.getRating(movieId);
				count++;
			}
		}
		if (count >= minimalRaters) {
			average = total / count;
		}
		return average;
	}

	public ArrayList<Rating> getAverageRatingsList(int minimalRaters) {
		ArrayList<Rating> averageRatingList = new ArrayList<Rating>();

		for (Movie movie : myMovies) {
			double avg = getAverageByID(movie.getID(), minimalRaters);
			if (avg > 0.0) {
				Rating currRating = new Rating(movie.getID(), avg);
				averageRatingList.add(currRating);
			}
		}
		return averageRatingList;
	}

	public String getTitle(String id) {
		for (Movie movie : myMovies) {
			if (movie.getID().equals(id)) {
				return movie.getTitle();
			}
		}
		return "NO SUCH ID";
	}

	public String getID(String title) {
		for (Movie movie : myMovies) {
			if (movie.getTitle().equals(title)) {
				return movie.getID();
			}
		}
		return "NO SUCH TITLE";
	}

	public static void main(String ar[]) throws IOException {
		SecondRatings s = new SecondRatings();
		System.out.println(s.getTitle("2310332"));
	}
}