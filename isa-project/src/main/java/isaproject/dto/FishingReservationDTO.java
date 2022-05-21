package isaproject.dto;

import java.util.Set;

import isaproject.model.AdditionalService;
import isaproject.model.Address;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.model.FishingCourse;

public class FishingReservationDTO{
	
	private Long id;
	private DateTimeSpan duration;
	private Address location;
	private Integer capacity;
	private Set<AdditionalService> additionalService;
	private Double price = 0.0;
	private FishingCourse fishingCourse;
	private Customer customer;
	
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

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Set<AdditionalService> getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(Set<AdditionalService> additionalService) {
		this.additionalService = additionalService;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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
	
}