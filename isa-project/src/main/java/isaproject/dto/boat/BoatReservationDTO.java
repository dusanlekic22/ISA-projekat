package isaproject.dto.boat;

import java.util.Set;

import isaproject.model.AdditionalService;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.model.boat.Boat;

public class BoatReservationDTO {
	private long id;
	private DateTimeSpan duration;
	private Double price;
	private String guestCapacity;
	private Boat boat;
	private Customer customer;
	private Set<AdditionalService> additionalService;
	private boolean confirmed;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getGuestCapacity() {
		return guestCapacity;
	}
	public void setGuestCapacity(String guestCapacity) {
		this.guestCapacity = guestCapacity;
	}
	public Boat getBoat() {
		return boat;
	}
	public void setBoat(Boat boat) {
		this.boat = boat;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Set<AdditionalService> getAdditionalService() {
		return additionalService;
	}
	public void setAdditionalService(Set<AdditionalService> additionalService) {
		this.additionalService = additionalService;
	}
	public boolean isConfirmed() {
		return confirmed;
	}
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	
}
