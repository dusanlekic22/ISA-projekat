package isaproject.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class CottageOwner extends User {

	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy = "cottageOwner",fetch = FetchType.EAGER)
	@JsonManagedReference
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