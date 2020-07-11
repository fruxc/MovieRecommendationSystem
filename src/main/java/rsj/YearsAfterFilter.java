package rsj;

public class YearsAfterFilter implements Filter {
	private int year;

	public YearsAfterFilter(int value) {
		year = value;
	}

	public boolean satisfies(String id) {
		return MovieDatabase.getYear(id) >= year;
	}
}
