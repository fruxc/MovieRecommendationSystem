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
		int min = 0;
		int max = 180;
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

	public void printSimiliarRatings() {
		int minimalRaters = 5;
		String id = "71";
		int numSimilarRaters = 20;
		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		RaterDatabase.initialize(ratingsfile);
		MovieDatabase.initialize(moviefile);

		ArrayList<Rating> recommendations = fr.getSimilarRatings(id, numSimilarRaters, minimalRaters);
		System.out.println(recommendations.size() + " movies " + "matched");

		for (Rating r : recommendations) {
			String movieTitle = MovieDatabase.getTitle((r.getItem()));
			System.out.println(movieTitle + " : " + r.getValue());
		}
	}

	public void printSimiliarRatingsByGenre() {
		int minimalRaters = 5;
		String id = "964";
		int numSimilarRaters = 20;
		String selecGenre = "Mystery";

		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		RaterDatabase.initialize(ratingsfile);
		MovieDatabase.initialize(moviefile);

		Filter gf = new GenreFilter(selecGenre);
		ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, gf);
		System.out.println(recommendations.size() + " movies " + "matched");

		for (Rating rating : recommendations) {
			String movieTitle = MovieDatabase.getTitle((rating.getItem()));
			System.out.println(movieTitle + " : " + rating.getValue());
		}
	}

	public void printSimiliarRatingsByDirector() {
		int minimalRaters = 2;
		String id = "120";
		int numSimilarRaters = 10;
		String directors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";

		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		RaterDatabase.initialize(ratingsfile);
		MovieDatabase.initialize(moviefile);

		Filter df = new DirectorsFilter(directors);
		ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, df);
		System.out.println(recommendations.size() + " movies " + "matched");

		for (Rating rating : recommendations) {
			String movieTitle = MovieDatabase.getTitle((rating.getItem()));
			System.out.println(movieTitle + " : " + rating.getValue());
		}
	}

	public void printSimiliarRatingsByGenreAndMinutes() {
		int minimalRaters = 3;
		String id = "168";
		int numSimilarRaters = 10;
		int minMinutes = 80;
		int maxMinutes = 160;
		String selecGenre = "Drama";

		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		RaterDatabase.initialize(ratingsfile);
		MovieDatabase.initialize(moviefile);

		Filter gf = new GenreFilter(selecGenre);
		Filter mf = new MinutesFilter(minMinutes, maxMinutes);
		AllFilters filtersList = new AllFilters();
		filtersList.addFilter(gf);
		filtersList.addFilter(mf);

		ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters,
				filtersList);
		System.out.println(recommendations.size() + " movies " + "matched");
		for (Rating rating : recommendations) {
			String movieTitle = MovieDatabase.getTitle((rating.getItem()));
			System.out.println(movieTitle + " : " + rating.getValue());
		}
	}

	public void printSimilarRatingsByYearAfterAndMinutes() {
		int minimalRaters = 5;
		String id = "314";
		int numSimilarRaters = 10;
		int minMinutes = 70;
		int maxMinutes = 200;
		int year = 1975;

		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		RaterDatabase.initialize(ratingsfile);
		MovieDatabase.initialize(moviefile);

		Filter yf = new YearsAfterFilter(year);
		Filter mf = new MinutesFilter(minMinutes, maxMinutes);
		AllFilters filtersList = new AllFilters();
		filtersList.addFilter(mf);
		filtersList.addFilter(yf);

		ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters,
				filtersList);
		System.out.println(recommendations.size() + " movies " + "matched");

		for (Rating rating : recommendations) {
			String movieTitle = MovieDatabase.getTitle((rating.getItem()));
			System.out.println(movieTitle + " : " + rating.getValue());
		}
	}

	public static void main(String[] arg) throws IOException {
		MovieRunnerSimilarRatings mr = new MovieRunnerSimilarRatings();
		mr.printSimilarRatingsByYearAfterAndMinutes();
	}

}
