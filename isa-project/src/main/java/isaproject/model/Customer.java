package isaproject.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import isaproject.model.boat.Boat;
import isaproject.model.boat.BoatReservation;
import isaproject.model.cottage.Cottage;
import isaproject.model.cottage.CottageReservation;

@Entity
@Table(name = "Customer")
public class Customer extends User {

	private static final long serialVersionUID = 1L;
	private String points;
	private String loyalityProgram;
	@JsonManagedReference("boatReservationCustomer")
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private Set<BoatReservation> boatReservation;
	@JsonManagedReference("cottageReservationCustomer")
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private Set<CottageReservation> cottageReservation = new HashSet<CottageReservation>();
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private Collection<FishingReservation> fishingReservation;
	@ManyToMany(mappedBy = "subscribers", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Cottage> subscribedCottages = new HashSet<Cottage>();
	@ManyToMany(mappedBy = "subscribers", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Boat> subscribedBoats = new HashSet<Boat>();
	private Integer penalties;

	public Customer() {
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String param) {
		this.points = param;
	}

	public String getLoyalityProgram() {
		return loyalityProgram;
	}

	public void setLoyalityProgram(String param) {
		this.loyalityProgram = param;
	}

	public Set<BoatReservation> getBoatReservation() {
		return boatReservation;
	}

	public void setBoatReservation(Set<BoatReservation> param) {
		this.boatReservation = param;
	}

	public Set<CottageReservation> getCottageReservation() {
		return cottageReservation;
	}

	public void setCottageReservation(Set<CottageReservation> param) {
		this.cottageReservation = param;
	}

	public Collection<FishingReservation> getFishingReservation() {
		return fishingReservation;
	}

	public void setFishingReservation(Collection<FishingReservation> param) {
		this.fishingReservation = param;
	}

	public Set<Cottage> getSubscribedCottages() {
		return subscribedCottages;
	}

	public void setSubscribedCottages(Set<Cottage> subscribedCottages) {
		this.subscribedCottages = subscribedCottages;
	}

	public Set<Boat> getSubscribedBoats() {
		return subscribedBoats;
	}

	public void setSubscribedBoats(Set<Boat> subscribedBoats) {
		this.subscribedBoats = subscribedBoats;
	}

	public Integer getPenalties() {
		return penalties;
	}

	public void setPenalties(Integer penalties) {
		this.penalties = penalties;
	}
}