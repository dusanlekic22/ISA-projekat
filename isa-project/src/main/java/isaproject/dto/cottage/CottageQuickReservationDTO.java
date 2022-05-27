package isaproject.dto.cottage;

import java.util.Set;

import isaproject.model.AdditionalService;
import isaproject.model.DateTimeSpan;
import isaproject.model.cottage.Cottage;

public class CottageQuickReservationDTO {

	private long id;
	private DateTimeSpan duration;
	private Integer guestCapacity;
	private Integer price;
	private Set<AdditionalService> additionalService;
	private Cottage cottage;
	private boolean isReserved;

	public CottageQuickReservationDTO() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DateTimeSpan getDuration() {
		return duration;
	}

	public void setDuration(DateTimeSpan duration) {
		this.duration = duration;
	}

	public Integer getGuestCapacity() {
		return guestCapacity;
	}

	public void setGuestCapacity(Integer guestCapacity) {
		this.guestCapacity = guestCapacity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Set<AdditionalService> getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(Set<AdditionalService> additionalService) {
		this.additionalService = additionalService;
	}

	public Cottage getCottage() {
		return cottage;
	}

	public void setCottage(Cottage cottage) {
		this.cottage = cottage;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

}
