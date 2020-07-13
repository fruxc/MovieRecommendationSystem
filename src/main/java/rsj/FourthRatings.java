package rsj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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

	private double dotProduct(EfficientRater me, EfficientRater r) {
		double product = 0;
		ArrayList<String> itemsRatedByMe = me.getItemsRated();
		for (String id : itemsRatedByMe) {
			if (r.hasRating(id)) {
				product += (me.getRating(id) - 5) * (r.getRating(id) - 5);
			}
		}
		return product;
	}

	private ArrayList<Rating> getSimilarities(String id) {
		ArrayList<Rating> similarityList = new ArrayList<Rating>();
		ArrayList<EfficientRater> allRaters = RaterDatabase.getRaters();
		EfficientRater me = RaterDatabase.getRater(id);
		for (EfficientRater r : allRaters) {
			if (!r.getID().equals(id)) {
				similarityList.add(new Rating(r.getID(), dotProduct(me, r)));
			}
		}
		Collections.sort(similarityList, Collections.reverseOrder());
		return similarityList;
	}

	public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters,
			Filter filterCriteria) {
		ArrayList<Rating> movieSimilarRatings = new ArrayList<Rating>();
		HashMap<String, Double> similarMap = new HashMap<String, Double>();
		int mapSize = getSimilarities(id).size();
		int minIndex = Math.min(mapSize, numSimilarRaters);

		for (Rating similar : getSimilarities(id).subList(0, minIndex)) {
			if (similar.getValue() > 0) {
				similarMap.put(similar.getItem(), similar.getValue());
			}
		}

		for (String movieID : MovieDatabase.filterBy(filterCriteria)) {
			int count = 0;
			double total = 0;

			for (EfficientRater curRater : RaterDatabase.getRaters()) {
				double rating = -1;
				if (similarMap.containsKey(curRater.getID()) && curRater.hasRating(movieID)) {
					rating = curRater.getRating(movieID) * similarMap.get(curRater.getID());
				}

				if (rating == -1) {
				}

				else {
					count++;
					total = total + rating;
				}
			}

			if (count < minimalRaters || total == 0) {
			} else {
				movieSimilarRatings.add(new Rating(movieID, total / count));
			}

		}
		Collections.sort(movieSimilarRatings, Collections.reverseOrder());
		return movieSimilarRatings;
	}

	public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
		return getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, new TrueFilter());
	}

	public static void main(String ar[]) throws IOException {
		ThirdRatings s = new ThirdRatings();
		System.out.println(s.getAverageByID("1714915", 3));
	}

}
