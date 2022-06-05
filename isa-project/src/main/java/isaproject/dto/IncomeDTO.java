package isaproject.dto;

public class IncomeDTO {
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

	public void setDailySum(double[][][] dailySum) {
		this.dailySum = dailySum;
	}

}
