package isaproject.dto.cottage;

import java.util.Set;

import isaproject.model.AdditionalService;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.model.cottage.Cottage;

public class CottageReservationDTO {

	private long id;
	private DateTimeSpan duration;
	private Integer guestCapacity;
	private Integer price;
	private Cottage cottage;
	private Customer customer;
	private Set<AdditionalService> additionalService;
	private boolean confirmed;

	public CottageReservationDTO() {
		super();
		// TODO Auto-generated constructor stub
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

	public Cottage getCottage() {
		return cottage;
	}

	public void setCottage(Cottage cottage) {
		this.cottage = cottage;
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
