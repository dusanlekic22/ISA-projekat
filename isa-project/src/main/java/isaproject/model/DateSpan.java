package isaproject.model;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class DateSpan {

	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	public DateSpan() {

	}

	public DateSpan(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		validate();
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public boolean validate() {
		return startDate.compareTo(endDate) < 0;
	}

	public boolean overlapsWith(DateSpan dateSpan) {
		// (StartA <= EndB) and (EndA >= StartB)
		return startDate.compareTo(dateSpan.startDate) <= 0 && endDate.compareTo(dateSpan.startDate) >= 0;
	}

	public DateSpan extend(long millis) {
		Date extendedTo = new Date(endDate.getTime() + millis);
		return new DateSpan(startDate, extendedTo);
	}

}
