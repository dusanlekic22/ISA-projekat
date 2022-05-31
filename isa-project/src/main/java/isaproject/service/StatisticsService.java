package isaproject.service;

import isaproject.model.DateTimeSpan;

public interface StatisticsService {
	
	 public int[] countYearly(DateTimeSpan reservationSpan, int year, int count[]);

	 public int[] countMonthly(DateTimeSpan reservationSpan, int month, int count[]);

	 public int[] countWeekly(DateTimeSpan reservationSpan, int week, int count[]);

	 public int[] yearlyIncome(DateTimeSpan reservationSpan, int reservationPrice, int year, int count[],int yearBackward);

	 public int[][] monthlyIncome(DateTimeSpan reservationSpan, int reservationPrice, int year, int month, int income[][],int yearBackward);

	 public int[][][] dailyIncome(DateTimeSpan reservationSpan, int reservationPrice,int year, int month, int week, int income[][][],int yearBackward);
}
