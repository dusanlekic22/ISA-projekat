package isaproject.dto;

import java.util.Set;

import isaproject.model.AdditionalService;
import isaproject.model.Cottage;
import isaproject.model.Customer;
import isaproject.model.DateSpan;

public class CottageReservationDTO {

	private long id;
	private DateSpan duration;
	private String guestCapacity;
	private Integer price;
	private Cottage cottage;
	private Customer customer;
	private Set<AdditionalService> additionalService;

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

	public DateSpan getDuration() {
		return duration;
	}

	public void setDuration(DateSpan duration) {
		this.duration = duration;
	}

	public String getGuestCapacity() {
		return guestCapacity;
	}

	public void setGuestCapacity(String guestCapacity) {
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

}
