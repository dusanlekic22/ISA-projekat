package isaproject.model.cottage;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import isaproject.model.DateTimeSpan;
import isaproject.model.User;

@Entity
public class CottageOwner extends User {

	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy = "cottageOwner",fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<Cottage> cottage;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "cottage_owner_unavailable_date_spans",
					 joinColumns = @JoinColumn(name = "cottage_owner_id"),
					 foreignKey = @ForeignKey(name = "no_date_spans_cottage_owner"))
	private Set<DateTimeSpan> unavailableReservationDateSpan = new HashSet<DateTimeSpan>();
	
	public CottageOwner() {
	}
	public Set<Cottage> getCottage() {
	    return cottage;
	}
	public void setCottage(Set<Cottage> param) {
	    this.cottage = param;
	}
	public Set<DateTimeSpan> getUnavailableReservationDateSpan() {
		return unavailableReservationDateSpan;
	}
	public void setUnavailableReservationDateSpan(Set<DateTimeSpan> unavailableReservationDateSpan) {
		this.unavailableReservationDateSpan = unavailableReservationDateSpan;
	}
}