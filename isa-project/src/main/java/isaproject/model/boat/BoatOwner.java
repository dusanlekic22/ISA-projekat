package isaproject.model.boat;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import isaproject.model.DateTimeSpan;
import isaproject.model.LoyaltyProgram;
import isaproject.model.User;

@Entity
public class BoatOwner extends User {

	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy = "boatOwner", fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<Boat> boat;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "boat_owner_unavailable_date_spans", joinColumns = @JoinColumn(name = "boat_owner_id"), foreignKey = @ForeignKey(name = "no_date_spans_boat_owner"))
	private Set<DateTimeSpan> unavailableReservationDateSpan = new HashSet<DateTimeSpan>();

	@Embedded
	private LoyaltyProgram loyaltyProgram;

	public BoatOwner() {
	}

	public Set<Boat> getBoat() {
		return boat;
	}

	public void setBoat(Set<Boat> param) {
		this.boat = param;
	}

	public Set<DateTimeSpan> getUnavailableReservationDateSpan() {
		return unavailableReservationDateSpan;
	}

	public void setUnavailableReservationDateSpan(Set<DateTimeSpan> unavailableReservationDateSpan) {
		this.unavailableReservationDateSpan = unavailableReservationDateSpan;
	}

	public LoyaltyProgram getLoyaltyProgram() {
		return loyaltyProgram;
	}

	public void setLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
		this.loyaltyProgram = loyaltyProgram;
	}

}