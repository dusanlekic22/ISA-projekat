package isaproject.model.boat;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import isaproject.model.User;

@Entity
public class BoatOwner extends User {

	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy = "boatOwner",fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<Boat> boat;
	public BoatOwner() {
	}
	public Set<Boat> getBoat() {
	    return boat;
	}
	public void setBoat(Set<Boat> param) {
	    this.boat = param;
	}
}