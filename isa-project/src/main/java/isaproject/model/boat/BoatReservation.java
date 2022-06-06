package isaproject.model.boat;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import isaproject.model.AdditionalService;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;

@Entity
@Table(name = "BoatReservation")
public class BoatReservation implements Serializable {

	private static final long serialVersionUID = 1L;

	public BoatReservation() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Embedded
	private DateTimeSpan duration;
	private Double price;
	private Double ownerIncome;
	private Double siteIncome;
	private String guestCapacity;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "boat_id", referencedColumnName = "id")
	@JsonBackReference("boatReservation")
	private Boat boat;
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference("boatReservationCustomer")
	private Customer customer;
	@OneToMany(mappedBy = "boatQuickReservation", fetch = FetchType.EAGER)
	@JsonManagedReference(value = "boatReservationService")
	private Set<AdditionalService> additionalService;
	private boolean confirmed;
	private boolean isCancelled;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double param) {
		this.price = param;
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

	public String getGuestCapacity() {
		return guestCapacity;
	}

	public void setGuestCapacity(String param) {
		this.guestCapacity = param;
	}

	public Boat getBoat() {
		return boat;
	}

	public void setBoat(Boat param) {
		this.boat = param;
	}

	public Customer getCustomer() {
	    return customer;
	}

	public void setCustomer(Customer param) {
	    this.customer = param;
	}

	public DateTimeSpan getDuration() {
		return duration;
	}

	public void setDuration(DateTimeSpan duration) {
		this.duration = duration;
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

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}	

}