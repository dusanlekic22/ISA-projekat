package isaproject.dto;

import java.util.Set;

import isaproject.model.AdditionalService;
import isaproject.model.Address;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.model.FishingCourse;

public class FishingReservationDTO {

	private Long id;
	private DateTimeSpan duration;
	private Integer capacity;
	private Double price = 0.0;
	private Address location;
	private Set<AdditionalService> additionalService;
	private FishingCourse fishingCourse;
	private Customer customer;
	private boolean confirmed;

	public FishingReservationDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DateTimeSpan getDuration() {
		return duration;
	}

	public void setDuration(DateTimeSpan duration) {
		this.duration = duration;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public Set<AdditionalService> getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(Set<AdditionalService> additionalService) {
		this.additionalService = additionalService;
	}

	public FishingCourse getFishingCourse() {
		return fishingCourse;
	}

	public void setFishingCourse(FishingCourse fishingCourse) {
		this.fishingCourse = fishingCourse;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

}