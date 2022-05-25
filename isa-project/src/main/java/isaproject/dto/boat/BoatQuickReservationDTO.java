package isaproject.dto.boat;

import java.util.Set;

import isaproject.model.AdditionalService;
import isaproject.model.DateTimeSpan;
import isaproject.model.boat.Boat;

public class BoatQuickReservationDTO {
	private long id;
	private DateTimeSpan duration;
	private Integer price;
	private String guestCapacity;
	private Set<AdditionalService> additionalService;
	private Boat boat;
	private boolean isReserved;

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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getGuestCapacity() {
		return guestCapacity;
	}

	public void setGuestCapacity(String guestCapacity) {
		this.guestCapacity = guestCapacity;
	}

	public Set<AdditionalService> getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(Set<AdditionalService> additionalService) {
		this.additionalService = additionalService;
	}

	public Boat getBoat() {
		return boat;
	}

	public void setBoat(Boat boat) {
		this.boat = boat;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

}
