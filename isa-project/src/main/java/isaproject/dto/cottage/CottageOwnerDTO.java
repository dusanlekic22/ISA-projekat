package isaproject.dto.cottage;

import java.util.HashSet;
import java.util.Set;

import isaproject.dto.UserDTO;
import isaproject.model.DateTimeSpan;
import isaproject.model.LoyaltyProgram;
import isaproject.model.cottage.Cottage;

public class CottageOwnerDTO extends UserDTO {

	private Set<Cottage> cottage;
	private Set<DateTimeSpan> unavailableReservationDateSpan = new HashSet<DateTimeSpan>();
	private LoyaltyProgram loyaltyProgram;

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

	public LoyaltyProgram getLoyaltyProgram() {
		return loyaltyProgram;
	}

	public void setLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
		this.loyaltyProgram = loyaltyProgram;
	}

}
