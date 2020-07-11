package rsj;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerAverage {
	private static DecimalFormat df = new DecimalFormat("0.00");

	public void printAverageRatings() throws IOException {
		SecondRatings sr = new SecondRatings();
		System.out.println("Total Movies= " + sr.getMovieSize());
		System.out.println("Total Ratings= " + sr.getRaterSize());
		ArrayList<Rating> list = sr.getAverageRatingsList(3);
		Collections.sort(list);
		for (Rating rater : list) {
			System.out.println(df.format(rater.getValue()) + " " + sr.getTitle(rater.getItem()));
		}
	}

	public void getAverageRatingOneMovie() throws IOException {
		SecondRatings sr = new SecondRatings();
		String title = "The Godfather";
		String Id = sr.getID(title);
		int minimalRaters = 3;
		System.out.println("Movie Name = " + title + " Average Rating = " + sr.getAverageByID(Id, minimalRaters));
	}

	public static void main(String[] args) throws IOException {
		MovieRunnerAverage mr = new MovieRunnerAverage();
		mr.printAverageRatings();
		mr.getAverageRatingOneMovie();
	}
}
