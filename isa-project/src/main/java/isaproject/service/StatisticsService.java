package isaproject.service;

import isaproject.model.DateTimeSpan;

public interface StatisticsService {
	
	 public int[] countYearly(DateTimeSpan reservationSpan, int year, int count[]);

	 public int[] countMonthly(DateTimeSpan reservationSpan, int month, int count[]);

	 public int[] countWeekly(DateTimeSpan reservationSpan, int week, int count[]);

	 public double[] yearlyIncome(DateTimeSpan reservationSpan, Double reservationPrice, int year, double[] income,int yearBackward);

	 public double[][] monthlyIncome(DateTimeSpan reservationSpan, Double reservationPrice, int year, int month, double[][] incomeSum,int yearBackward);

	 public double[][][] dailyIncome(DateTimeSpan reservationSpan, Double reservationPrice,int year, int month, int day, double[][][] incomeSum,int yearBackward);
}
