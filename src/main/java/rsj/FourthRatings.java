package rsj;

import java.io.IOException;
import java.util.ArrayList;

public class FourthRatings {

	public double getAverageByID(String movieId, int minimalRaters) {
		double average = 0, total = 0;
		int count = 0;

		ArrayList<EfficientRater> myRaters = RaterDatabase.getRaters();
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
		ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
		for (String movie : movies) {
			double avg = getAverageByID(movie, minimalRaters);
			if (avg > 0.0) {
				Rating currRating = new Rating(movie, avg);
				averageRatingList.add(currRating);
			}
		}
		return averageRatingList;
	}

	public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
		ArrayList<Rating> averageRatingListByFilter = new ArrayList<Rating>();
		ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);

		for (String movie : movies) {
			double avg = getAverageByID(movie, minimalRaters);
			if (avg > 0.0) {
				Rating currRating = new Rating(movie, avg);
				averageRatingListByFilter.add(currRating);
			}
		}
		return averageRatingListByFilter;
	}

	public static void main(String ar[]) throws IOException {
		ThirdRatings s = new ThirdRatings();
		System.out.println(s.getAverageRatingsList(3));
	}

}
