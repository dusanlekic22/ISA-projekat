package isaproject.service;

import isaproject.model.DateTimeSpan;

public interface StatisticsService {
	
	 public int[] countYearly(DateTimeSpan reservationSpan, int year, int count[]);

	 public int[] countMonthly(DateTimeSpan reservationSpan, int month, int count[]);

	 public int[] countWeekly(DateTimeSpan reservationSpan, int week, int count[]);

	 public Double[] yearlyIncome(DateTimeSpan reservationSpan, Double reservationPrice, int year, Double income[],int yearBackward);

	 public Double[][] monthlyIncome(DateTimeSpan reservationSpan, Double reservationPrice, int year, int month, Double income[][],int yearBackward);

	 public Double[][][] dailyIncome(DateTimeSpan reservationSpan, Double reservationPrice,int year, int month, int day, Double income[][][],int yearBackward);
}
