package rsj;

public class MinutesFilter implements Filter {

	private int minMinutes, maxMinutes;

	public MinutesFilter(int min, int max) {
		minMinutes = min;
		maxMinutes = max;
	}

	public boolean satisfies(String id) {
		return (MovieDatabase.getMinutes(id) >= minMinutes && MovieDatabase.getMinutes(id) <= maxMinutes) ? true
				: false;
	}

}
