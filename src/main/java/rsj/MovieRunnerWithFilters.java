package rsj;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerWithFilters {

	private static DecimalFormat df = new DecimalFormat("0.00");

	public void printAverageRatings() throws IOException {
		int minimalRaters = 1;
		String movieFile = "ratedmovies_short.csv";
		String ratingsFile = "data/ratings_short.csv";
		ThirdRatings tr = new ThirdRatings(ratingsFile);
		System.out.println("Total Ratings = " + tr.getRaterSize());
		MovieDatabase.initialize(movieFile);
		System.out.println("Total Movies = " + MovieDatabase.size());
		ArrayList<Rating> list = tr.getAverageRatingsList(minimalRaters);
		Collections.sort(list);
		System.out.println("Found " + list.size() + " Movies");
		for (Rating rater : list) {
			System.out.println(df.format(rater.getValue()) + " " + MovieDatabase.getTitle(rater.getItem()));
		}
	}

	public void printAverageRatingsByYear() throws IOException {
		int minimalRaters = 1;
		String movieFile = "ratedmovies_short.csv";
		String ratingsFile = "data/ratings_short.csv";
		ThirdRatings tr = new ThirdRatings(ratingsFile);
		System.out.println("Total Ratings = " + tr.getRaterSize());
		MovieDatabase.initialize(movieFile);
		System.out.println("Total Movies = " + MovieDatabase.size());
		Filter yearFilter = new YearsAfterFilter(2000);
		ArrayList<Rating> list = tr.getAverageRatingsByFilter(minimalRaters, yearFilter);
		Collections.sort(list);
		System.out.println("Found " + list.size() + " Movies");
		for (Rating rater : list) {
			System.out.println(df.format(rater.getValue()) + " " + MovieDatabase.getTitle(rater.getItem()));
		}
	}

	public void printAverageRatingsByGenre() throws IOException {
		int minimalRaters = 1;
		String movieFile = "ratedmovies_short.csv";
		String ratingsFile = "data/ratings_short.csv";
		ThirdRatings tr = new ThirdRatings(ratingsFile);
		System.out.println("Total Ratings = " + tr.getRaterSize());
		MovieDatabase.initialize(movieFile);
		System.out.println("Total Movies = " + MovieDatabase.size());
		Filter genreFilter = new GenreFilter("Drama");
		ArrayList<Rating> list = tr.getAverageRatingsByFilter(minimalRaters, genreFilter);
		Collections.sort(list);
		System.out.println("Found " + list.size() + " Movies");
		for (Rating rater : list) {
			System.out.println(df.format(rater.getValue()) + " " + MovieDatabase.getTitle(rater.getItem()));
			System.out.println("\t" + MovieDatabase.getGenres(rater.getItem()));
		}
	}

	public void printAverageRatingsByMinutes() throws IOException {
		int minimalRaters = 1;
		int minMin = 110;
		int maxMin = 170;
		String movieFile = "ratedmovies_short.csv";
		String ratingsFile = "data/ratings_short.csv";
		ThirdRatings tr = new ThirdRatings(ratingsFile);
		System.out.println("Total Ratings = " + tr.getRaterSize());
		MovieDatabase.initialize(movieFile);
		System.out.println("Total Movies = " + MovieDatabase.size());
		Filter minutesFilter = new MinutesFilter(minMin, maxMin);
		ArrayList<Rating> list = tr.getAverageRatingsByFilter(minimalRaters, minutesFilter);
		Collections.sort(list);
		System.out.println("Found " + list.size() + " Movies");
		for (Rating rater : list) {
			System.out.println(df.format(rater.getValue()) + " " + MovieDatabase.getTitle(rater.getItem()));
			System.out.println("\t" + MovieDatabase.getMinutes(rater.getItem()));
		}
	}

	public void printAverageRatingsByDirectors() throws IOException {
		int minimalRaters = 1;
		String director = "Nolan";
		String movieFile = "ratedmoviesfull.csv";
		String ratingsFile = "data/ratings.csv";
		ThirdRatings tr = new ThirdRatings(ratingsFile);
		System.out.println("Total Ratings = " + tr.getRaterSize());
		MovieDatabase.initialize(movieFile);
		System.out.println("Total Movies = " + MovieDatabase.size());
		Filter directorFilter = new DirectorsFilter(director);
		ArrayList<Rating> list = tr.getAverageRatingsByFilter(minimalRaters, directorFilter);
		Collections.sort(list);
		System.out.println("Found " + list.size() + " Movies");
		for (Rating rater : list) {
			System.out.println(df.format(rater.getValue()) + " " + MovieDatabase.getTitle(rater.getItem()));
			System.out.println("\t" + MovieDatabase.getDirector(rater.getItem()));
		}
	}

	public void printAverageRatingsByYearAfterAndGenre() throws IOException {
		int minimalRaters = 1;
		int year = 2000;
		String genre = "Comedy";
		String movieFile = "ratedmoviesfull.csv";
		String ratingsFile = "data/ratings.csv";
		ThirdRatings tr = new ThirdRatings(ratingsFile);
		System.out.println("Total Ratings = " + tr.getRaterSize());
		MovieDatabase.initialize(movieFile);
		System.out.println("Total Movies = " + MovieDatabase.size());

		Filter yearFilter = new YearsAfterFilter(year);
		Filter genreFilter = new GenreFilter(genre);
		AllFilters filterList = new AllFilters();
		filterList.addFilter(yearFilter);
		filterList.addFilter(genreFilter);
		ArrayList<Rating> list = tr.getAverageRatingsByFilter(minimalRaters, filterList);
		Collections.sort(list);
		System.out.println("Found " + list.size() + " Movies");
		for (Rating rater : list) {
			System.out.println(df.format(rater.getValue()) + " " + MovieDatabase.getTitle(rater.getItem()));
			System.out.println("\t" + MovieDatabase.getYear(rater.getItem()));
			System.out.println("\t" + MovieDatabase.getGenres(rater.getItem()));
		}
	}

	public void printAverageRatingsByDirectorsAndMinutes() throws IOException {

		int minimalRaters = 1;
		int min = 110;
		int max = 160;
		String director = "Nolan";
		String movieFile = "ratedmoviesfull.csv";
		String ratingsFile = "data/ratings.csv";
		ThirdRatings tr = new ThirdRatings(ratingsFile);
		System.out.println("Total Ratings = " + tr.getRaterSize());
		MovieDatabase.initialize(movieFile);
		System.out.println("Total Movies = " + MovieDatabase.size());

		Filter minutesFilter = new MinutesFilter(min, max);
		Filter directorsFilter = new DirectorsFilter(director);
		AllFilters filterList = new AllFilters();
		filterList.addFilter(minutesFilter);
		filterList.addFilter(directorsFilter);
		ArrayList<Rating> list = tr.getAverageRatingsByFilter(minimalRaters, filterList);
		Collections.sort(list);
		System.out.println("Found " + list.size() + " Movies");
		for (Rating rater : list) {
			System.out.println(df.format(rater.getValue()) + " " + MovieDatabase.getTitle(rater.getItem()));
			System.out.println("\t" + MovieDatabase.getMinutes(rater.getItem()));
			System.out.println("\t" + MovieDatabase.getDirector(rater.getItem()));
		}
	}

	public static void main(String[] arg) throws IOException {
		MovieRunnerWithFilters mr = new MovieRunnerWithFilters();
		mr.printAverageRatingsByDirectorsAndMinutes();
	}
}
