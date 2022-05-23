package isaproject.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "FishingReservation")
public class FishingReservation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	private DateTimeSpan duration;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "location_id", referencedColumnName = "id")
	private Address location;
	
	@Column(nullable = false)
	private Integer capacity;
	
	@OneToMany(mappedBy = "fishingQuickReservation", fetch = FetchType.EAGER)
	private Set<AdditionalService> additionalService = new HashSet<AdditionalService>();
	
	@Column(nullable = false)
	private Double price = 0.0;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fishingCourse_id", referencedColumnName = "id")
	@JsonBackReference
	private FishingCourse fishingCourse;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	private Customer customer;
	
	public FishingReservation() {
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