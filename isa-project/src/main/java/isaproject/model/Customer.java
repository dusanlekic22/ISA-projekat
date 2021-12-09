package isaproject.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Customer")
public class Customer extends User {

	private static final long serialVersionUID = 1L;
	private String points;
	private String loyalityProgram;
	@OneToMany(mappedBy = "customer")
	private Set<BoatReservation> boatReservation;
	@OneToMany(mappedBy = "customer")
	private Set<CottageReservation> cottageReservation;
	@OneToMany(mappedBy = "customer")
	private Collection<FishingReservation> fishingReservation;
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
}