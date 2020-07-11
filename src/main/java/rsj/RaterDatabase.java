package rsj;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class RaterDatabase {
	private static HashMap<String, EfficientRater> ourRaters;

	private static void initialize() {
		// this method is only called from addRatings
		if (ourRaters == null) {
			ourRaters = new HashMap<String, EfficientRater>();
		}
	}

	public static void initialize(String filename) {
		if (ourRaters == null) {
			ourRaters = new HashMap<String, EfficientRater>();
			addRatings("data/" + filename);
		}
	}

	public static void addRatings(String filename) {
		initialize();

		Path path = Paths.get(filename);
		try (Reader reader = Files.newBufferedReader(path);
				CSVParser parser = CSVFormat.EXCEL.withHeader().parse(reader)) {
			for (CSVRecord record : parser) {
				String id = record.get("rater_id");
				String item = record.get("movie_id");
				String rating = record.get("rating");
				addRaterRating(id, item, Double.parseDouble(rating));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addRaterRating(String raterID, String movieID, double rating) {
		initialize();
		EfficientRater rater = null;
		if (ourRaters.containsKey(raterID)) {
			rater = ourRaters.get(raterID);
		} else {
			rater = new EfficientRater(raterID);
			ourRaters.put(raterID, rater);
		}
		rater.addRating(movieID, rating);
	}

	public static EfficientRater getRater(String id) {
		initialize();
		return ourRaters.get(id);
	}

	public static ArrayList<EfficientRater> getRaters() {
		initialize();
		ArrayList<EfficientRater> list = new ArrayList<EfficientRater>(ourRaters.values());
		return list;
	}

	public static int size() {
		return ourRaters.size();
	}

}