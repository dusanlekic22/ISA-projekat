package isaproject.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DateTimeSpan {
	
	@Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private LocalDateTime startDate;
	@Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private LocalDateTime endDate;

	public DateTimeSpan() {
		
	}

	public DateTimeSpan(LocalDateTime startDate, LocalDateTime endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		validate();
	}
	
	public DateTimeSpan(DateTimeSpan dateTimeSpan) {
		this.startDate = dateTimeSpan.startDate;
		this.endDate = dateTimeSpan.endDate;
		validate();
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public boolean validate() {
		return startDate.compareTo(endDate) < 0;
	}

	public boolean overlapsWith(DateTimeSpan dateTimeSpan) {
		// (StartA <= EndB) and (EndA >= StartB)
		return startDate.compareTo(dateTimeSpan.endDate) <= 0 && endDate.compareTo(dateTimeSpan.startDate) >= 0;
	}
	
	public boolean isBetween(LocalDateTime date) {
		// (StartA <= Date) and (Date >= EndA)
		return startDate.compareTo(date) <= 0 && endDate.compareTo(date) >= 0;
	}
	
	public boolean isTimeSpanBetween(DateTimeSpan reservationDate) {
		// (StartA <= Date) and (Date >= EndA)
		return startDate.compareTo(reservationDate.getStartDate()) <= 0 && endDate.compareTo(reservationDate.getEndDate()) >= 0;
	}
	
	public boolean isDaysAfter(LocalDateTime date,int days) {
		// (StartA >= Date + 1day) 
		return startDate.compareTo(date.plusDays(days)) >= 0;
	}

	public DateTimeSpan extend(LocalDateTime extend) {
		return new DateTimeSpan(startDate, endDate.plusDays(extend.getDayOfMonth()));
	}
	
	public boolean passed() {
		return endDate.compareTo(LocalDateTime.now()) <= 0 ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		DateTimeSpan other = (DateTimeSpan) obj;
//		if(this.startDate == other.startDate && this.endDate == other.endDate)
//			return true;
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}
	
	

}
