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
		return startDate.compareTo(dateSpan.endDate) <= 0 && endDate.compareTo(dateSpan.startDate) >= 0;
	}
	
	public boolean isBetween(LocalDate date) {
		// (StartA <= Date) and (Date >= EndA)
		return startDate.compareTo(date) <= 0 && endDate.compareTo(date) >= 0;
	}
	
	public boolean isDaysAfter(LocalDate date,int days) {
		// (StartA >= Date + 1day) 
		return startDate.compareTo(date.plusDays(days)) >= 0;
	}

	public DateSpan extend(LocalDate extend) {
		return new DateSpan(startDate, endDate.plusDays(extend.getDayOfMonth()));
	}
	
	public boolean passed() {
		return endDate.compareTo(LocalDate.now()) <= 0 ;
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
		DateSpan other = (DateSpan) obj;
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
