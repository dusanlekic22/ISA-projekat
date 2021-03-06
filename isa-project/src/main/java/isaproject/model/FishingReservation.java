package isaproject.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "FishingReservation")
public class FishingReservation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private DateTimeSpan duration;

	@Column(nullable = false)
	private Integer capacity;

	@Column(nullable = false)
	private Double price;

	private Double ownerIncome;

	private Double siteIncome;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "location_id", referencedColumnName = "id")
	private Address location;

	@OneToMany(mappedBy = "fishingQuickReservation", fetch = FetchType.EAGER)
	@JsonManagedReference(value = "fishingReservationService")
	private Set<AdditionalService> additionalService = new HashSet<AdditionalService>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fishingCourse_id", referencedColumnName = "id")
	@JsonBackReference("fishingReservation")
	private FishingCourse fishingCourse;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	@JsonBackReference("fishingReservationCustomer")
	private Customer customer;

	private boolean confirmed;

	private boolean isCancelled;

	public FishingReservation() {
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
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

	public Double getOwnerIncome() {
		return ownerIncome;
	}

	public void setOwnerIncome(Double ownerIncome) {
		this.ownerIncome = ownerIncome;
	}

	public Double getSiteIncome() {
		return siteIncome;
	}

	public void setSiteIncome(Double siteIncome) {
		this.siteIncome = siteIncome;
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