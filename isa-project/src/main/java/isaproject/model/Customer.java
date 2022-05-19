package isaproject.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Customer")
public class Customer extends User {

	private static final long serialVersionUID = 1L;
	private String points;
	private String loyalityProgram;
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private Set<BoatReservation> boatReservation;
	@JsonBackReference
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private Set<CottageReservation> cottageReservation = new HashSet<CottageReservation>();
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private Collection<FishingReservation> fishingReservation;
	@ManyToMany(mappedBy = "subscribers", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Cottage> subscribedCottages = new HashSet<Cottage>();

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
}