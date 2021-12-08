package org.persistence;

import static javax.persistence.InheritanceType.JOINED;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import org.persistence.Boat;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = JOINED)
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