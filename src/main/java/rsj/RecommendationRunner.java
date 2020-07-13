package rsj;

import java.util.*;

public class RecommendationRunner implements Recommender {

	public ArrayList<String> getItemsToRate() {
		ArrayList<String> moviesList = new ArrayList<>();
		ArrayList<String> id = MovieDatabase.filterBy(new TrueFilter());
		while (moviesList.size() < 10) {
			Random rd = new Random();
			int random = rd.nextInt(id.size());
			if (!moviesList.contains(id.get(random)))
				moviesList.add(id.get(random));
		}
		return moviesList;
	}

	public void printRecommendationsFor(String webRaterID) {
		MovieDatabase.initialize("ratedmoviesfull.csv");
		RaterDatabase.initialize("ratings.csv");
		FourthRatings fr = new FourthRatings();
		ArrayList<Rating> ratingList = fr.getSimilarRatings(webRaterID, 20, 5);
		if (ratingList.size() == 0) {
			System.out.println("<h2>Sorry, there are no movies to recommend based on your ratings!</h2>");
		} else {
			ArrayList<String> movieToBeRate = getItemsToRate();
			ArrayList<Rating> outID = new ArrayList<>();
			int count = 0;
			for (int i = 0; outID.size() + count != ratingList.size() && outID.size() < 10; i++) {
				if (!movieToBeRate.contains(ratingList.get(i).getItem())) {
					outID.add(ratingList.get(i));
				} else {
					count++;
				}
			}
			System.out.println("<style>");
			System.out.println("h2,h3{");
			System.out.println("  text-align: center;");
			System.out.println("  height: 50px;");
			System.out.println("  line-height: 50px;");
			System.out.println("  font-family: Arial, Helvetica, sans- serif;");
			System.out.println("  background-color: lightblue;");
			System.out.println("   color:  #ff6600 }");

			System.out.println(" table {");
			System.out.println("   border-collapse: collapse;");
			System.out.println("   margin: auto;}");
			System.out.println("table, th, td {");
			System.out.println("    border: 2px solid white;");
			System.out.println("    font-size: 15px;");

			System.out.println("    padding: 2px 6px 2px 6px; }");
			System.out.println(" td img{");
			System.out.println("    display: block;");
			System.out.println("    margin-left: auto;");
			System.out.println("    margin-right: auto; }");
			System.out.println("th {");
			System.out.println("    height: 40px;");
			System.out.println("    font-size: 18px;");

			System.out.println("  background-color: #95a5a6;");
			System.out.println(" color: white;");
			System.out.println("text-align: center; }");

			System.out.println(" tr:nth-child(even) {");
			System.out.println("     background-color: #f2f2f2; }");
			System.out.println("  tr:nth-child(odd) {");
			System.out.println("background-color: #cccccc; }");
			System.out.println(" tr:hover {");
			System.out.println(" background-color: #666666; ");
			System.out.println("  color:white;}");

			System.out.println("table td:first-child {");
			System.out.println(" text-align: center; }");

			System.out.println(" tr {");
			System.out.println(" font-family: Arial, Helvetica, sans-serif; }");
			System.out.println(".rating{");
			System.out.println("    color:#ff6600;");
			System.out.println("    padding: 0px 10px;");
			System.out.println("   font-weight: bold; }");
			System.out.println("</style>");

			System.out.println("<h2>Recommended movies for you</h2>");
			System.out.println("<table id = \"rater\">");
			System.out.println("<tr>");
			System.out.println("<th>Rank</th>");
			System.out.println("<th>Poster</th>");
			System.out.println("<th>Title & Rating</th>");
			System.out.println("<th>Genre</th>");
			System.out.println("<th>Directors</th>");
			System.out.println("<th>Runtime</th>");
			System.out.println("</tr>");

			int rank = 1;
			for (Rating i : outID) {
				System.out.println("<tr><td>" + rank + "</td>" + "<td><img src = \""
						+ MovieDatabase.getPoster(i.getItem()) + "\" width=\"50\" height=\"70\"></td> " + "<td>"
						+ MovieDatabase.getYear(i.getItem()) + "&ensp;&ensp; <a href=\"https://www.imdb.com/title/tt"
						+ i.getItem() + "\">" + MovieDatabase.getTitle(i.getItem())
						+ "</a><br><div class = \"rating\">&starf; &ensp;&ensp;&ensp;"
						+ String.format("%.1f", i.getValue()) + "/10</td>" + "<td>"
						+ MovieDatabase.getGenres(i.getItem()) + "</td>" + "<td>"
						+ MovieDatabase.getDirector(i.getItem()) + "</td>" + "<td>"
						+ MovieDatabase.getMinutes(i.getItem()) + "</td>" + "</tr> ");
				rank++;
			}
			System.out.println("</table>");
			System.out.println("<h3>Rating based on your taste!</h3>");
		}
	}

	public static void main(String ar[]) {
		RecommendationRunner rr = new RecommendationRunner();
		rr.printRecommendationsFor("12");
	}
}