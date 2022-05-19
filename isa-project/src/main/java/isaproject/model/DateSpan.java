package isaproject.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DateSpan {
	
	@Column(columnDefinition = "DATE")
	private LocalDate startDate;
	@Column(columnDefinition = "DATE")
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
	
	public boolean isBetween(LocalDate date) {
		// (StartA <= Date) and (Date >= EndA)
		return startDate.compareTo(date) <= 0 && date.compareTo(endDate) >= 0;
	}

	public DateSpan extend(LocalDate extend) {
		return new DateSpan(startDate, endDate.plusDays(extend.getDayOfMonth()));
	}
	
	public boolean passed() {
		return endDate.compareTo(LocalDate.now()) <= 0 ;
	}

}
