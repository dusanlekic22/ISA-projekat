package isaproject.model;

public class Income {
	private int[] yearlySum;
	private int[][] monthlySum;
	private int[][][] dailySum;

	public int[] getYearlySum() {
		return yearlySum;
	}

	public void setYearlySum(int[] yearlySum) {
		this.yearlySum = yearlySum;
	}

	public int[][] getMonthlySum() {
		return monthlySum;
	}

	public void setMonthlySum(int[][] monthlySum) {
		this.monthlySum = monthlySum;
	}

	public int[][][] getDailySum() {
		return dailySum;
	}

	public void setDailySum(int[][][] dailySum) {
		this.dailySum = dailySum;
	}

}
