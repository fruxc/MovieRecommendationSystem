package rsj;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.apache.commons.csv.*;

public class FirstRatings {
	public ArrayList<Movie> loadMovies(String filename) throws IOException {
		ArrayList<Movie> movies = new ArrayList<Movie>();
		Path path = Paths.get(filename);
		try (Reader reader = Files.newBufferedReader(path);
				CSVParser parser = CSVFormat.EXCEL.withHeader().parse(reader)) {
			for (CSVRecord record : parser) {
				Movie movie = new Movie(record.get("id"), record.get("title"), record.get("year"), record.get("genre"),
						record.get("director"), record.get("country"), record.get("poster"),
						Integer.parseInt(record.get("minutes")));
				movies.add(movie);
			}
		}
		return movies;
	}

	public void testLoadMovies() throws IOException {
		HashMap<String, Integer> moviesByDirector = new HashMap<String, Integer>();
		int comedyMovie = 0, extraLength = 0;
		ArrayList<Movie> movieList = loadMovies("data/ratedmoviesfull.csv");
		for (Movie movie : movieList) {
			System.out.println(movie);
			if (movie.getGenres().contains("Comedy")) {
				comedyMovie++;
			}
			if (movie.getMinutes() > 150) {
				extraLength++;
			}
			String currentDirector = movie.getDirector();
			if (moviesByDirector.containsKey(currentDirector)) {
				moviesByDirector.put(currentDirector, moviesByDirector.get(currentDirector) + 1);
			} else {
				moviesByDirector.put(currentDirector, 1);

			}
		}
		System.out.println("Total Movies = " + movieList.size());
		System.out.println("Comedy Movies = " + comedyMovie);
		System.out.println("150+ Movies = " + extraLength);

		int countMoviesByDirector = Collections.max(moviesByDirector.values());

		ArrayList<String> movieMax = new ArrayList<String>();
		for (String dir : moviesByDirector.keySet()) {
			if (moviesByDirector.get(dir) == countMoviesByDirector) {
				movieMax.add(dir);
			}
		}

		System.out.println(
				"Director with max movies = " + countMoviesByDirector + "\nDirector's Name = " + movieMax.get(0));
	}

	public ArrayList<EfficientRater> loadRaters(String filename) throws IOException {
		Path path = Paths.get(filename);
		ArrayList<EfficientRater> raters = new ArrayList<EfficientRater>();
		try (Reader reader = Files.newBufferedReader(path);
				CSVParser parser = CSVFormat.EXCEL.withHeader().parse(reader)) {
			for (CSVRecord record : parser) {
				String rater_id = record.get("rater_id");
				String movie_id = record.get("movie_id");
				double rating = Double.parseDouble(record.get("rating"));
				if (raters.size() == 0) {
					EfficientRater currRater = new EfficientRater(rater_id);
					currRater.addRating(movie_id, rating);
					raters.add(currRater);
				} else {
					List<EfficientRater> raterList = new ArrayList<EfficientRater>(raters);
					Iterator<EfficientRater> raterIterator = raterList.iterator();
					while (raterIterator.hasNext()) {
						EfficientRater r = raterIterator.next();
						if (r.getID().equals(rater_id)) {
							r.addRating(movie_id, rating);
							break;
						} else {
							EfficientRater currRater = new EfficientRater(rater_id);
							currRater.addRating(movie_id, rating);
							raters.add(currRater);
							break;
						}

					}
				}
			}
		}
		return raters;
	}

	public void testLoadRaters() throws IOException {
		ArrayList<EfficientRater> raterList = loadRaters("data/ratings.csv");
		System.out.println("Total Different Raters = " + raterList.get(raterList.size() - 1).getID());
		System.out.println("Total Raters = " + raterList.size());

		String rater_id = "193";
		int count = 0;
		String movie_id = "1798709";
		int numOfRatingPerMovie = 0;
		HashMap<String, Integer> ratersWithNumOfMovies = new HashMap<String, Integer>();

		for (EfficientRater currRater : raterList) {
			if (currRater.getID().equals(rater_id)) {
				count += 1;
			}
			if (currRater.hasRating(movie_id)) {
				numOfRatingPerMovie += 1;
			}
			if (!ratersWithNumOfMovies.containsKey(currRater.getID())) {
				ratersWithNumOfMovies.put(currRater.getID(), 1);
			} else {
				int num = ratersWithNumOfMovies.get(currRater.getID());
				num += 1;
				ratersWithNumOfMovies.put(currRater.getID(), num);
			}
		}
		System.out.println("rater_id " + rater_id + " has " + count + " ratings");

		int maxValue = Collections.max(ratersWithNumOfMovies.values());
		String maxKey = "";
		for (String s : ratersWithNumOfMovies.keySet()) {
			if (ratersWithNumOfMovies.get(s) == maxValue) {
				maxKey = s;
			}
		}
		System.out.println("Indivisual Rater: " + maxKey + ", " + "Max Rating: " + maxValue);
		System.out.println(movie_id + " has " + numOfRatingPerMovie + " raters");

		HashMap<String, Integer> movieRatingCounts = new HashMap<String, Integer>();

		for (int k = 0; k < raterList.size(); k++) {
			EfficientRater currRater = raterList.get(k);
			for (int i = 0; i < currRater.numRatings(); i++) {
				String currMovieID = currRater.getItemsRated().get(i);
				if (movieRatingCounts.containsKey(currMovieID)) {
					movieRatingCounts.put(currMovieID, movieRatingCounts.get(currMovieID) + 1);
				} else {
					movieRatingCounts.put(currMovieID, 1);
				}
			}
		}
		System.out.println("number of movies rated: " + movieRatingCounts.size());
	}

	public static void main(String args[]) throws IOException {
		FirstRatings fr = new FirstRatings();
		fr.testLoadRaters();
	}
}
