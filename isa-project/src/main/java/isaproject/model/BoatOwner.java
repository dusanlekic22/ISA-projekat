package isaproject.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class BoatOwner extends User {

	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy = "boatOwner")
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