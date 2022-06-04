package isaproject.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class DateTimeSpan {
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	public DateTimeSpan() {
	}

	public DateTimeSpan(LocalDateTime startDate, LocalDateTime endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		validate();
	}
	
	public DateTimeSpan(DateTimeSpan dateTimeSpan){
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
	
	public boolean isHoursBefore(LocalDateTime date,int hours) {
		// (StartA <= Date + 1hours) 
		return startDate.compareTo(date.plusHours(hours)) <= 0;
	}


	public DateTimeSpan extend(LocalDateTime extend) {
		return new DateTimeSpan(startDate, endDate.plusDays(extend.getDayOfMonth()));
	}
	
	public boolean passed() {
		return endDate.compareTo(LocalDateTime.now()) <= 0 ;
	}
	
	public long getHours() {
		return ChronoUnit.HOURS.between(startDate, endDate);
	}
	
	public long getDays() {
		return ChronoUnit.DAYS.between(startDate, endDate);
	}
	
	public long getMonths() {
		return ChronoUnit.MONTHS.between(startDate, endDate);
	}
	
	public long getYears() {
		return ChronoUnit.YEARS.between(startDate, endDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(endDate, startDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DateTimeSpan other = (DateTimeSpan) obj;
		return Objects.equals(endDate, other.endDate) && Objects.equals(startDate, other.startDate);
	}

}
