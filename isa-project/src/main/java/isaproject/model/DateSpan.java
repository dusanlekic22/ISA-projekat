package isaproject.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class DateSpan {

	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime startDate;

	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime endDate;

	public DateSpan() {
 
	}

	public DateSpan(LocalDateTime startDate, LocalDateTime endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		validate();
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public boolean validate() {
		return startDate.compareTo(endDate) < 0;
	}

	public boolean overlapsWith(DateSpan dateSpan) {
		// (StartA <= EndB) and (EndA >= StartB)
		return startDate.compareTo(dateSpan.startDate) <= 0 && endDate.compareTo(dateSpan.startDate) >= 0;
	}
	public boolean isBetween(DateSpan dateSpan) {
		// (StartA <= EndB) and (EndA >= StartB)
		return startDate.compareTo(dateSpan.startDate) <= 0 && endDate.compareTo(dateSpan.endDate) >= 0;
	} 

	public DateSpan extend(long millis) {
		LocalDateTime extendedTo = new LocalDateTime(endDate.getTime() + millis);
		return new DateSpan(startDate, extendedTo);
	}

}
