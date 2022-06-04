package isaproject.dto.boat;

import java.util.HashSet;
import java.util.Set;

import isaproject.dto.UserDTO;
import isaproject.model.DateTimeSpan;
import isaproject.model.LoyaltyProgram;
import isaproject.model.boat.Boat;

public class BoatOwnerDTO extends UserDTO {
	private Set<Boat> boat;
	private Set<DateTimeSpan> unavailableReservationDateSpan = new HashSet<DateTimeSpan>();
	private LoyaltyProgram loyaltyProgram;

	public BoatOwnerDTO() {
		super();
	}

	public Set<Boat> getBoat() {
		return boat;
	}

	public void setBoat(Set<Boat> boat) {
		this.boat = boat;
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
