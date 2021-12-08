package isaproject.model;

import javax.persistence.Entity;

import java.util.Collection;
import javax.persistence.OneToMany;

import isaproject.model.Cottage;

@Entity
public class CottageOwner extends User {

	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy = "cottageOwner")
	private Collection<Cottage> cottage;
	public CottageOwner() {
	}
	public Collection<Cottage> getCottage() {
	    return cottage;
	}
	public void setCottage(Collection<Cottage> param) {
	    this.cottage = param;
	}
}