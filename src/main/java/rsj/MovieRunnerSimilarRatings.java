package rsj;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerSimilarRatings {

	private static DecimalFormat df = new DecimalFormat("0.00");

	public void printAverageRatings() throws IOException {
		int minimalRaters = 1;
		String movieFile = "ratedmovies_short.csv";
		String ratingsFile = "ratings_short.csv";
		FourthRatings fr = new FourthRatings();
		RaterDatabase.initialize(ratingsFile);
		System.out.println("Total Ratings = " + RaterDatabase.size());
		MovieDatabase.initialize(movieFile);
		System.out.println("Total Movies = " + MovieDatabase.size());
		ArrayList<Rating> list = fr.getAverageRatingsList(minimalRaters);
		Collections.sort(list);
		System.out.println("Found " + list.size() + " Movies");
		for (Rating rater : list) {
			System.out.println(df.format(rater.getValue()) + " " + MovieDatabase.getTitle(rater.getItem()));
		}
	}

	public void printAverageRatingsByDirectorsAndMinutes() throws IOException {

		int minimalRaters = 1;
		int min = 110;
		int max = 160;
		String director = "Nolan";
		String movieFile = "ratedmoviesfull.csv";
		String ratingsFile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		RaterDatabase.initialize(ratingsFile);
		System.out.println("Total Ratings = " + RaterDatabase.size());
		MovieDatabase.initialize(movieFile);
		System.out.println("Total Movies = " + MovieDatabase.size());

		Filter minutesFilter = new MinutesFilter(min, max);
		Filter directorsFilter = new DirectorsFilter(director);
		AllFilters filterList = new AllFilters();
		filterList.addFilter(minutesFilter);
		filterList.addFilter(directorsFilter);
		ArrayList<Rating> list = fr.getAverageRatingsByFilter(minimalRaters, filterList);
		Collections.sort(list);
		System.out.println("Found " + list.size() + " Movies");
		for (Rating rater : list) {
			System.out.println(df.format(rater.getValue()) + " " + MovieDatabase.getTitle(rater.getItem()));
			System.out.println("\t" + MovieDatabase.getMinutes(rater.getItem()));
			System.out.println("\t" + MovieDatabase.getDirector(rater.getItem()));
		}
	}

	public static void main(String[] arg) throws IOException {
		MovieRunnerSimilarRatings mr = new MovieRunnerSimilarRatings();
		mr.printAverageRatings();
		mr.printAverageRatingsByDirectorsAndMinutes();
	}

}
