package isaproject.model;

public class Income {
	private double[] yearlySum;
	private double[][] monthlySum;
	private double[][][] dailySum;

	public double[] getYearlySum() {
		return yearlySum;
	}

	public void setYearlySum(double[] yearlySum) {
		this.yearlySum = yearlySum;
	}

	public double[][] getMonthlySum() {
		return monthlySum;
	}

	public void setMonthlySum(double[][] monthlySum) {
		this.monthlySum = monthlySum;
	}

	public double[][][] getDailySum() {
		return dailySum;
	}

	public void setDailySum(double[][][] incomeSum) {
		this.dailySum = incomeSum;
	}

}
