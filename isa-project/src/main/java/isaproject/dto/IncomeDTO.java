package isaproject.dto;

public class IncomeDTO {
	private Double[] yearlySum;
	private Double[][] monthlySum;
	private Double[][][] dailySum;

	public Double[] getYearlySum() {
		return yearlySum;
	}

	public void setYearlySum(Double[] yearlySum) {
		this.yearlySum = yearlySum;
	}

	public Double[][] getMonthlySum() {
		return monthlySum;
	}

	public void setMonthlySum(Double[][] monthlySum) {
		this.monthlySum = monthlySum;
	}

	public Double[][][] getDailySum() {
		return dailySum;
	}

	public void setDailySum(Double[][][] dailySum) {
		this.dailySum = dailySum;
	}

}
