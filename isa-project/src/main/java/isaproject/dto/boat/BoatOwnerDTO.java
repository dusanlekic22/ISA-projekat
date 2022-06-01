package isaproject.dto.boat;

import java.util.HashSet;
import java.util.Set;

import isaproject.dto.UserDTO;
import isaproject.model.DateTimeSpan;
import isaproject.model.boat.Boat;

public class BoatOwnerDTO extends UserDTO {
	private Set<Boat> boat;
	private Set<DateTimeSpan> unavailableReservationDateSpan = new HashSet<DateTimeSpan>();

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
}
