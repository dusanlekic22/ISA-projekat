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
	private Integer price;
	private String guestCapacity;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "boat_id", referencedColumnName = "id")
	@JsonBackReference
	private Boat boat;
	@ManyToOne(fetch = FetchType.EAGER)
	private Customer customer;
	@OneToMany(mappedBy = "boatQuickReservation", fetch = FetchType.EAGER)
	private Set<AdditionalService> additionalService;
	private boolean confirmed;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer param) {
		this.price = param;
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

}