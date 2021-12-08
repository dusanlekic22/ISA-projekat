package isaproject.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import isaproject.model.AdditionalService;
import isaproject.model.Customer;
import isaproject.model.FishingCourse;

import java.util.Collection;
import javax.persistence.OneToMany;

@Entity
@Table(name = "FishingReservation")
public class FishingReservation implements Serializable {

	private static final long serialVersionUID = 1L;

	public FishingReservation() {
	}

	@Id
	private long id;
	private Date startDate;
	private Date endDate;
	private String location;
	private String capacity;
	private String additionalServices;
	private String price;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "fishingCourse_id", referencedColumnName = "id")
	private FishingCourse fishingCourse;
	@ManyToOne(fetch = LAZY)
	private Customer customer;
	@OneToMany(mappedBy = "fishingReservation")
	private Collection<AdditionalService> additionalService;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date param) {
		this.startDate = param;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date param) {
		this.endDate = param;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String param) {
		this.location = param;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String param) {
		this.capacity = param;
	}

	public String getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(String param) {
		this.additionalServices = param;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String param) {
		this.price = param;
	}

	public FishingCourse getFishingCourse() {
		return fishingCourse;
	}

	public void setFishingCourse(FishingCourse param) {
		this.fishingCourse = param;
	}

	public Customer getCustomer() {
	    return customer;
	}

	public void setCustomer(Customer param) {
	    this.customer = param;
	}

	public Collection<AdditionalService> getAdditionalService() {
	    return additionalService;
	}

	public void setAdditionalService(Collection<AdditionalService> param) {
	    this.additionalService = param;
	}

}