package isaproject.dto.cottage;

import java.util.HashSet;
import java.util.Set;

import isaproject.dto.UserDTO;
import isaproject.model.DateTimeSpan;
import isaproject.model.cottage.Cottage;

public class CottageOwnerDTO extends UserDTO {

	private Set<Cottage> cottage;
	private Set<DateTimeSpan> unavailableReservationDateSpan = new HashSet<DateTimeSpan>();

	public CottageOwnerDTO() {
		super();
	}

	public Set<Cottage> getCottage() {
		return cottage;
	}

	public void setCottage(Set<Cottage> cottage) {
		this.cottage = cottage;
	}

	public Set<DateTimeSpan> getUnavailableReservationDateSpan() {
		return unavailableReservationDateSpan;
	}

	public void setUnavailableReservationDateSpan(Set<DateTimeSpan> unavailableReservationDateSpan) {
		this.unavailableReservationDateSpan = unavailableReservationDateSpan;
	}

}
