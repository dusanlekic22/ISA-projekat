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
import isaproject.model.DateTimeSpan;

@Entity
@Table(name = "BoatQuickReservation")
public class BoatQuickReservation implements Serializable {

	private static final long serialVersionUID = 1L;

	public BoatQuickReservation() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Embedded
	private DateTimeSpan duration;
	private Double price;
	private String guestCapacity;
	@OneToMany(mappedBy = "boatQuickReservation", fetch = FetchType.EAGER)
	@JsonManagedReference(value = "boatQuickReservationService")
	private Set<AdditionalService> additionalService;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "boat_id", referencedColumnName = "id")
	@JsonBackReference
	private Boat boat;
	private boolean isReserved;
	
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

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	public void setAdditionalService(Set<AdditionalService> additionalService) {
		this.additionalService = additionalService;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double param) {
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

	public Set<AdditionalService> getAdditionalService() {
	    return additionalService;
	}

}