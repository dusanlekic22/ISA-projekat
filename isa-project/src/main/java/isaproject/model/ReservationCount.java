package isaproject.model;

public class ReservationCount {

	private int[] yearlySum;
	private int[] monthlySum;
	private int[] weeklySum;

	public ReservationCount() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int[] getYearlySum() {
		return yearlySum;
	}

	public void setYearlySum(int[] yearlySum) {
		this.yearlySum = yearlySum;
	}

	public int[] getMonthlySum() {
		return monthlySum;
	}

	public void setMonthlySum(int[] monthlySum) {
		this.monthlySum = monthlySum;
	}

	public int[] getWeeklySum() {
		return weeklySum;
	}

	public void setWeeklySum(int[] weeklySum) {
		this.weeklySum = weeklySum;
	}
	
}
