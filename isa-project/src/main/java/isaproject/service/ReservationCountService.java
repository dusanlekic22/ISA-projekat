package isaproject.service;

import isaproject.model.DateTimeSpan;

public interface ReservationCountService {
	
	 public int[] countYearly(DateTimeSpan reservationSpan, int year, int count[]);

	 public int[] countMonthly(DateTimeSpan reservationSpan, int month, int count[]);

	 public int[] countWeekly(DateTimeSpan reservationSpan, int week, int count[]);

}
