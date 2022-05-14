package isaproject.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;

@Embeddable
public class DateSpan {

	private LocalDate startDate;

	private LocalDate endDate;

	public DateSpan() {
		
	}

	public DateSpan(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		validate();
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public boolean validate() {
		return startDate.compareTo(endDate) < 0;
	}

	public boolean overlapsWith(DateSpan dateSpan) {
		// (StartA <= EndB) and (EndA >= StartB)
		return startDate.compareTo(dateSpan.startDate) <= 0 && endDate.compareTo(dateSpan.startDate) >= 0;
	}

	public DateSpan extend(LocalDate extend) {
		return new DateSpan(startDate, endDate.plusDays(extend.getDayOfMonth()));
	}

}
