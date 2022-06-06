package isaproject.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import isaproject.model.DateTimeSpan;
import isaproject.service.StatisticsService;

@Service
public class StatiscticsServiceImpl implements StatisticsService {

	private boolean isCurrentYear(DateTimeSpan reservationSpan) {
		return reservationSpan.getEndDate().getYear() == LocalDateTime.now().getYear();
	}

	private boolean isCurrentMonth(DateTimeSpan reservationSpan) {
		return reservationSpan.getEndDate().getMonthValue() == LocalDateTime.now().getMonthValue();
	}

	@Override
	public int[] countYearly(DateTimeSpan reservationSpan, int year, int[] count) {
		if (reservationSpan.getEndDate().getYear() == LocalDateTime.now().getYear() + year - 3) {
			count[year-1] += 1;
		}
		return count;
	}

	@Override
	public int[] countMonthly(DateTimeSpan reservationSpan, int month, int count[]) {
		if (isCurrentYear(reservationSpan) && reservationSpan.getEndDate().getMonthValue() == month) {
			count[month - 1] += 1;
		}
		return count;
	}

	@Override
	public int[] countWeekly(DateTimeSpan reservationSpan, int week, int count[]) {
		int monthLength = LocalDate.now().lengthOfMonth();
		int dayOfMonth = reservationSpan.getEndDate().getDayOfMonth();
		if (isCurrentYear(reservationSpan) && isCurrentMonth(reservationSpan)) {
			if (dayOfMonth >= (week - 1) * 7 && dayOfMonth < week * 7) {
				count[week - 1] += 1;
			} else if (dayOfMonth >= 4 * 7 && dayOfMonth <= monthLength) {
				count[3] += 1;
			}
		}
		return count;
	}

	@Override
	public double[] yearlyIncome(DateTimeSpan reservationSpan, Double reservationPrice, int year, double income[],
			int yearBackward) {
		if (reservationSpan.getEndDate().getYear() == LocalDateTime.now().getYear() + year
				- (LocalDateTime.now().getYear() - yearBackward + 1)) {
			income[year - 1] += reservationPrice;
		}
		return income;
	}

	@Override
	public double[][] monthlyIncome(DateTimeSpan reservationSpan, Double reservationPrice, int year, int month,
			double income[][], int yearBackward) {
		if (reservationSpan.getStartDate().getYear() == LocalDateTime.now().getYear() + year
				- (LocalDateTime.now().getYear() - yearBackward + 1)
				&& reservationSpan.getEndDate().getMonthValue() == month) {
			income[year - 1][month - 1] += reservationPrice;
		}
		return income;
	}

	@Override
	public double[][][] dailyIncome(DateTimeSpan reservationSpan, Double reservationPrice, int year, int month, int day,
			double income[][][], int yearBackward) {
		if (reservationSpan.getStartDate().getYear() == LocalDateTime.now().getYear() + year
				- (LocalDateTime.now().getYear() - yearBackward + 1)
				&& reservationSpan.getEndDate().getMonthValue() == month
				&& reservationSpan.getEndDate().getDayOfMonth() == day) {
			income[year - 1][month - 1][day-1] += reservationPrice;
		}
		return income;
	}
}
