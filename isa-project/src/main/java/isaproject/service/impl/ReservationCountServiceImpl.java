package isaproject.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import isaproject.model.DateTimeSpan;
import isaproject.service.ReservationCountService;

@Service
public class ReservationCountServiceImpl implements ReservationCountService {

	private boolean isCurrentYear(DateTimeSpan reservationSpan) {
		return reservationSpan.getStartDate().getYear() == LocalDateTime.now().getYear();
	}

	private boolean isCurrentMonth(DateTimeSpan reservationSpan) {
		return reservationSpan.getStartDate().getMonthValue() == LocalDateTime.now().getMonthValue();
	}

	@Override
	public int[] countYearly(DateTimeSpan reservationSpan, int year, int[] count) {
		if (reservationSpan.getStartDate().getYear() == LocalDateTime.now().getYear() + year - 2) {
			count[year] += 1;
		}
		return count;
	}

	@Override
	public int[] countMonthly(DateTimeSpan reservationSpan, int month, int count[]) {
		if (isCurrentYear(reservationSpan) && reservationSpan.getStartDate().getMonthValue() == month) {
			count[month - 1] += 1;
		}
		return count;
	}

	@Override
	public int[] countWeekly(DateTimeSpan reservationSpan, int week, int count[]) {
		int monthLength = LocalDate.now().lengthOfMonth();
		int dayOfMonth = reservationSpan.getStartDate().getDayOfMonth();
		if (isCurrentYear(reservationSpan) && isCurrentMonth(reservationSpan)) {
			if (dayOfMonth >= (week - 1) * 7 && dayOfMonth < week * 7) {
				count[week - 1] += 1;
			} else if (dayOfMonth >= 4 * 7 && dayOfMonth <= monthLength) {
				count[3] += 1;
			}
		}
		return count;
	}
}
