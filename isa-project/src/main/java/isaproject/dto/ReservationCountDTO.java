package isaproject.dto;

public class ReservationCountDTO {

	private int[] yearlySum;
	private int[] monthlySum;
	private int[] weeklySum;

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
