package isaproject.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class ReservationDateSpanWithPrice {

	@Embedded
	private DateTimeSpan dateSpan;
	
	private float price;

	public DateTimeSpan getDateSpan() {
		return dateSpan;
	}

//	public void setDateSpan(DateTimeSpan dateSpan) {
//		this.dateSpan = dateSpan;
//	}

	public float getPrice() {
		return price;
	}

//	public void setPrice(float price) {
//		this.price = price;
//	}
	
	public ReservationDateSpanWithPrice(){}

	public ReservationDateSpanWithPrice(DateTimeSpan dateSpan, float price) {
		super();
		this.dateSpan = dateSpan;
		this.price = price;
	}
	
	
	
}
